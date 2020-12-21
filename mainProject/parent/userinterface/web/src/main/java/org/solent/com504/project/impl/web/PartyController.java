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
import java.util.Set;
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
        Set<Party> partyList = partyService.findAllParties();

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