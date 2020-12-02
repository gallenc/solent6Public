/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.party.dto.Address;
import org.solent.com504.project.model.party.dto.Party;
import org.solent.com504.project.model.party.dto.PartyRole;
import org.solent.com504.project.model.party.service.PartyService;
import org.solent.com504.project.model.service.ServiceFacade;
import org.solent.com504.project.model.user.dto.User;
import org.solent.com504.project.model.user.dto.UserRoles;
import org.solent.com504.project.model.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author aeryk
 */

@Controller
@Transactional
public class PartyController {
    
    final static Logger LOG = LogManager.getLogger(UserController.class);

    {
        LOG.debug("UserController created");
    }

    @Autowired
    private UserService userService;

    @Autowired
    private PartyService partyService;

    
    @Autowired(required = true)
    @Qualifier("serviceFacade")
    ServiceFacade serviceFacade = null;

    
    @RequestMapping(value = {"/partys"}, method = RequestMethod.GET)
    public String partys(Model model) {

        LOG.debug("partys called:");
        List<Party> partyList = partyService.findAll();

        for (Party party : partyList) {
            LOG.debug(" party:" + party + " users.size="
                    + ((party.getUsers() == null) ? "null" : party.getUsers().size()));
        }

        model.addAttribute("partyListSize", partyList.size());
        model.addAttribute("partyList", partyList);

        return "partys";
    }

    @RequestMapping(value = {"/partys"}, method = RequestMethod.POST)
    @Transactional
    public String changePartys(Model model) {

        LOG.debug("partys POST called:");
        List<Party> partyList = partyService.findAll();

        for (Party party : partyList) {
            LOG.debug(" party:" + party + " users.size="
                    + ((party.getUsers() == null) ? "null" : party.getUsers().size()));
        }

        model.addAttribute("partyListSize", partyList.size());
        model.addAttribute("partyList", partyList);

        return "partys";
    }
    
    @RequestMapping(value = {"/viewModifyParty"}, method = RequestMethod.GET)
    public String reviewParty(Model model,
            @RequestParam(value = "partyuuid") String partyuuid, Authentication authentication) {

        Party party = null;

        LOG.debug("viewModifyParty GET called for partyuuid=" + partyuuid);
        party = partyService.findByUuid(partyuuid);
        if (party == null) {
            LOG.warn("security warning modifyparty called for unknown partyuuid=" + partyuuid);
            return ("denied");
        }

        // security check if party is allowed to access or modify this party
//        if (!hasRole(UserRoles.ROLE_GLOBAL_ADMIN.name())) {
//            if (!partyuuid.equals(authentication.getName())) {
//                LOG.warn("security warning without permissions, modifyuser called for partyuuid=" + partyuuid);
//                return ("denied");
//            }
//        }
        LOG.debug("viewModifyParty GET called for uuid=" + partyuuid + " party=" + party);
        model.addAttribute("party", party);

        // find selected party role
        List<PartyRole> availablePartyRoles = partyService.getAvailablePartyRoles();
        Map<String, String> availablePartyRolesMap = new LinkedHashMap();
        for (PartyRole prole : availablePartyRoles) {
            availablePartyRolesMap.put(prole.name(), ((prole.equals(party.getPartyRole()) ? "selected" : "")));
        }
        model.addAttribute("availablePartyRolesMap", availablePartyRolesMap);

        Map<String, String> selectedRolesMap = new HashMap(); // = selectedRolesMap(party);
        //for (Entry entry : selectedRolesMap.entrySet()) {
        //   LOG.debug(partyuuid + " role:" + entry.getKey() + " selected:" + entry.getValue());
        // }
        model.addAttribute("selectedUsersMap", selectedRolesMap);
        return "viewModifyParty";
    }

    @RequestMapping(value = {"/viewModifyParty"}, method = RequestMethod.POST)
    public String updateParty(Model model,
            @RequestParam(value = "partyuuid", required = false) String partyuuid,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "secondName", required = false) String secondName,
            @RequestParam(value = "partyRole", required = false) String partyRole,
            @RequestParam(value = "partyEnabled", required = false) String partyEnabled,
            @RequestParam(value = "number", required = false) String number,
            @RequestParam(value = "addressLine1", required = false) String addressLine1,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "postcode", required = false) String postcode,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "removeUsername", required = false) String removeUsername,
            @RequestParam(value = "addUsers", required = false) List<String> addUsers,
            Authentication authentication) {

        LOG.debug("viewModifyParty POST called for partyuuid=" + partyuuid);
        String errorMessage = "";

        // security check if user is allowed to access or modify this party
        if (!hasRole(UserRoles.ROLE_GLOBAL_ADMIN.name())) {
            //     if (!partyuuid.equals(authentication.getName())) {
            LOG.warn("security warning without permissions, viewModifyParty called for username=" + partyuuid);
            return ("denied");
            //     }
        }

        Party party = null;

        // If partyuuid is null or empty in a post assume create new party
        if (partyuuid == null || partyuuid.isEmpty()) {
            party = new Party();
            LOG.debug("viewModifyParty POST called to create Party uuid=" + party.getUuid());
            
            // else try to modify an existing party    
        } else {
            party = partyService.findByUuid(partyuuid);
            if (party == null) {
                LOG.warn("security warning viewModifyParty POST called for unknown partyuuid=" + partyuuid);
                return ("denied");
            }

            // add user if requested
            if (addUsers != null) {
                for (String username : addUsers) {
                    User user = userService.findByUsername(username);
                    if (user != null) {
                        party.addUser(user);
                    }
                    LOG.debug("adding username" + username + " user " + user
                            + " to party " + party);
                }
                // remove user if requested
            } else if (removeUsername != null ) {
                LOG.debug("removing username=" + removeUsername + " from party " + party);
                Iterator<User> users = party.getUsers().iterator();
                while (users.hasNext()) {
                    User user = users.next();
                    if (removeUsername.equals(user.getUsername())) {
                        party.removeUser(user);
                        LOG.debug("removing username" + removeUsername + " user " + user
                                + " from party " + party);
                        break;
                    }
                }

            } else { // update values if not adding / removing user
                LOG.debug("updating party partyuuid="+partyuuid);
                party.setUuid(partyuuid);

                if (partyRole != null) {
                    try {
                        party.setPartyRole(PartyRole.valueOf(partyRole));
                    } catch (IllegalArgumentException ex) {
                        LOG.error("update pary used unknown partyRole" + partyRole);
                    }
                }

                if (firstName != null) {
                    party.setFirstName(firstName);
                }
                if (secondName != null) {
                    party.setSecondName(secondName);
                }
                if (partyEnabled != null && "true".equals(partyEnabled)) {
                    party.setEnabled(Boolean.TRUE);
                } else {
                    party.setEnabled(Boolean.FALSE);
                }

                Address address = new Address();
                address.setNumber(number);
                address.setAddressLine1(addressLine1);
                address.setCountry(country);
                address.setPostcode(postcode);
                address.setMobile(mobile);
                party.setAddress(address);
            }

        }

        // find selected party role
        List<PartyRole> availablePartyRoles = partyService.getAvailablePartyRoles();
        Map<String, String> availablePartyRolesMap = new LinkedHashMap();
        for (PartyRole prole : availablePartyRoles) {
            availablePartyRolesMap.put(prole.name(), ((prole.equals(party.getPartyRole()) ? "selected" : "")));
        }
        model.addAttribute("availablePartyRolesMap", availablePartyRolesMap);

        party = partyService.save(party);

        // update roles if roles in list
        // if (selectedRolesIn != null) {
        //     party = partyService.updateUserRoles(partyuuid, selectedRolesIn);
        // }
        // Map<String, String> selectedRolesMap = selectedRolesMap(party);
        model.addAttribute("party", party);

        Map<String, String> selectedRolesMap = new HashMap(); // = selectedRolesMap(party);
        //for (Entry entry : selectedRolesMap.entrySet()) {
        //   LOG.debug(uuid + " role:" + entry.getKey() + " selected:" + entry.getValue());
        // }
        model.addAttribute("selectedUsersMap", selectedRolesMap);

        // add message if there are any 
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("message", "Party " + party.getUuid() + " updated successfully");

        return "viewModifyParty";
    }
    

    @RequestMapping(value = {"/addUsersToParty"}, method = RequestMethod.POST)
    public String addUsersToParty(Model model,
            @RequestParam(value = "partyuuid", required = false) String partyuuid
    ) {
        List<User> userList = userService.findAll();

        LOG.debug("addUsersToParty called:");
        for (User user : userList) {
            LOG.debug(" user:" + user);
        }

        model.addAttribute("userListSize", userList.size());
        model.addAttribute("userList", userList);
        model.addAttribute("partyuuid", partyuuid);

        return "addUsersToParty";
    }
    
    private boolean hasRole(String role) {
        Collection<GrantedAuthority> authorities
                = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            hasRole = authority.getAuthority().equals(role);
            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }
    
}