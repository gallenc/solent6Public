/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opennms.tmforum.tmf656.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.opennms.tmforum.swagger.tmf656.swagger.model.ResourceRef;
import org.opennms.tmforum.swagger.tmf656.swagger.model.SLAViolationRef;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceRef;
import org.opennms.tmforum.tmf656.simulator.dao.ServiceProblemRepository;
import org.opennms.tmforum.tmf656.simulator.model.ServiceProblemEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author gallenc
 */
@Controller
@RequestMapping("/")
public class ViewController {
    
    private static final Logger LOG = LoggerFactory.getLogger(ViewController.class);


    {
        LOG.debug("ViewController created");
    }

    // This serviceFacade object is injected by Spring
    @Autowired(required = true)
    private ServiceProblemRepository serviceProblemRepository = null;

    
    @RequestMapping(value = {"/serviceProblemList"}, method = RequestMethod.GET)
    public String serviceProblemsGet(Model model) {
        
        long numberOfProblems = serviceProblemRepository.count();
        model.addAttribute("numberOfProblems", numberOfProblems);
        
        List<ServiceProblemEntity> problemList = serviceProblemRepository.findAll();
        model.addAttribute("problemList", problemList);

        return "serviceProblemList";
    }
    
    @Transactional
    @RequestMapping(value = {"/serviceProblemList"}, method = RequestMethod.POST)
    public String serviceProblemsPost(Model model,
          @RequestParam(value = "action") String action            
            ) {
        
        if("deleteAll".contentEquals(action)) {
            serviceProblemRepository.deleteAll();
        }
        
        long numberOfProblems = serviceProblemRepository.count();
        model.addAttribute("numberOfProblems", numberOfProblems);
        
        List<ServiceProblemEntity> problemList = serviceProblemRepository.findAll();
        model.addAttribute("problemList", problemList);

        return "serviceProblemList";
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(Model model, HttpServletRequest request) {
        try {
            URL requestURL = new URL(request.getRequestURL().toString());
            String host = requestURL.getHost();
            String path = requestURL.getPath();
            model.addAttribute("host", host);
            model.addAttribute("path", path);
        } catch (MalformedURLException e) {}

        return "home";
    }
    
    @RequestMapping(value = {"/swaggerUI"}, method = RequestMethod.GET)
    public String swaggerUI(Model model) {
        return "swaggerUI";
    }

    @RequestMapping(value = {"/about"}, method = RequestMethod.GET)
    public String about(Model model) {
        return "about";
    }

    @RequestMapping(value = {"/contact"}, method = RequestMethod.GET)
    public String contact(Model model) {
        return "contact";
    }

}
