/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.party.dto.Address;
import org.solent.com504.project.model.service.ServiceFacade;
import org.solent.com504.project.model.user.dto.Role;
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
 * @author Afonso
 */

@Controller
@Transactional
public class ViewModifyUserController {
    
    final static Logger LOG = LogManager.getLogger(UserController.class);

    {
        LOG.debug("UserController created");
    }

    @Autowired
    private UserService userService;

    @Autowired(required = true)
    @Qualifier("serviceFacade")
    ServiceFacade serviceFacade = null;
    
    
    @RequestMapping(value = {"/viewModifyUser"}, method = RequestMethod.GET)
    public String modifyuser(Model model,
            @RequestParam(value = "username", required = true) String username, Authentication authentication) {

        // security check if party is allowed to access or modify this party
        if (!hasRole(UserRoles.ROLE_GLOBAL_ADMIN.name())) {
            if (!username.equals(authentication.getName())) {
                LOG.warn("security warning without permissions, modifyuser called for username=" + username);
                return ("denied");
            }
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            LOG.warn("security warning modifyuser called for unknown username=" + username);
            return ("denied");
        }

        LOG.debug("viewUser called for username=" + username + " user=" + user);
        model.addAttribute("user", user);

        Map<String, String> selectedRolesMap = selectedRolesMap(user);

        for (Entry entry : selectedRolesMap.entrySet()) {
            LOG.debug(username + " role:" + entry.getKey() + " selected:" + entry.getValue());
        }

        model.addAttribute("selectedRolesMap", selectedRolesMap);

        return "viewModifyUser";
    }
   
    @RequestMapping(value = {"/viewModifyUser"}, method = RequestMethod.POST)
    public String updateuser(Model model,
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "secondName", required = false) String secondName,
            @RequestParam(value = "addressLine1", required = false) String addressLine1,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "postcode", required = false) String postcode,
            @RequestParam(value = "mobile", required = false) String mobile,
            Authentication authentication
    ) {
        LOG.debug("updateUser called for username=" + username);

        // security check if party is allowed to access or modify this party
        if (!hasRole(UserRoles.ROLE_GLOBAL_ADMIN.name())) {
            if (!username.equals(authentication.getName())) {
                LOG.warn("security warning without permissions, updateUser called for username=" + username);
                return ("denied");
            }
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            LOG.warn("security warning updateUser called for unknown username=" + username);
            return ("denied");
        }

        String errorMessage = "";

        if (firstName != null) {
            user.setFirstName(firstName);
        }
        if (secondName != null) {
            user.setSecondName(secondName);
        }else {
            user.setEnabled(Boolean.FALSE);
        }

        Address address = new Address();
        address.setAddressLine1(addressLine1);
        address.setCountry(country);
        address.setPostcode(postcode);
        address.setMobile(mobile);
        user.setAddress(address);

        user = userService.save(user);

        Map<String, String> selectedRolesMap = selectedRolesMap(user);

        model.addAttribute("user", user);

        model.addAttribute("selectedRolesMap", selectedRolesMap);

        // add message if there are any 
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("message", "User " + user.getUsername() + " updated successfully");

        return "viewModifyUser";
    }

    private Map<String, String> selectedRolesMap(User user) {

        List<String> availableRoles = userService.getAvailableUserRoleNames();

        List<String> selectedRoles = new ArrayList();
        for (Role role : user.getRoles()) {
            selectedRoles.add(role.getName());
            LOG.debug("user " + user.toString()
                    + "roles from database:" + role.getName());
        }

        Map<String, String> selectedRolesMap = new LinkedHashMap();
        for (String availableRole : availableRoles) {
            if (selectedRoles.contains(availableRole)) {
                selectedRolesMap.put(availableRole, "checked");
                LOG.debug("availableRole " + availableRole
                        + " user " + user.toString() + " available role:checked");
            } else {
                selectedRolesMap.put(availableRole, "");
                LOG.debug("availableRole " + availableRole
                        + " user " + user.toString() + " available role:not checked");
            }
        }

        return selectedRolesMap;

    }

    /**
     * returns true if the party has the role specified
     *
     * @param role
     * @return
     */
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
