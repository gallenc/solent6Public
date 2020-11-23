/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;
import org.solent.com504.project.model.chargingrecord.service.ChargingRecordService;
import org.solent.com504.project.model.service.ServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author gallenc
 */
@Controller
//@RequestMapping("/mvc")
public class ChargingRecordController {

    public static final String WEB_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    final static Logger LOG = LogManager.getLogger(ChargingRecordController.class);

    {
        LOG.debug("ChargingRecordController created");
    }

    // This chargingRecordService object is injected by Spring
    @Autowired(required = true)
    @Qualifier("chargingRecordService")
    ChargingRecordService chargingRecordService = null;

    @RequestMapping("/chargingRecords")
    public String chargingRecord(@RequestParam(value = "numberPlate", required = false) String numberPlate,
            @RequestParam(value = "entryDate", required = false) String entryDate,
            @RequestParam(value = "exitDate", required = false) String exitDate,
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "soze", required = false) String size,
            Model m) {

        SimpleDateFormat df = new SimpleDateFormat(WEB_DATE_FORMAT);

        LOG.debug("chargingRecord called numberPlate:" + numberPlate
                + " entryDate:" + entryDate
                + " exitDate:" + exitDate
                + "");
        if (chargingRecordService == null) {
            throw new RuntimeException("chargingRecordService==null and has not been initialised");
        }

        m.addAttribute("chargingRecordService", chargingRecordService);

        // add error / response messages to page
        String errorMessage = "";
        String message = "";

        Date m_entryDate = null;
        Date m_exitDate = null;
        int m_page = 0;
        int m_size = 20;
        Long m_totalRecords = 0L;

        try {
            m_entryDate = df.parse(entryDate);
            m_exitDate = df.parse(exitDate);
            m_page = Integer.parseInt(page);
            m_size = Integer.parseInt(size);
            m_totalRecords = chargingRecordService.totalRecordsByNumberPlate(numberPlate, m_entryDate, m_exitDate);

        } catch (Exception ex) {
            errorMessage = ex.getMessage();
        }

        List<ChargingRecord> chargingRecordList = chargingRecordService.findByNumberPlate(numberPlate, m_entryDate, m_exitDate, m_page, m_size);
        m.addAttribute("chargingRecordList", chargingRecordList);
        m.addAttribute("entryDate", (m_entryDate==null) ? "" : df.format(m_entryDate));
        m.addAttribute("exitDate", (m_exitDate==null) ? df.format(new Date()) :df.format(m_exitDate));
        m.addAttribute("page", m_page);
        m.addAttribute("size", m_size);
        m.addAttribute("totalRecords", m_totalRecords);
        m.addAttribute("totalPages", m_totalRecords / m_size );

        m.addAttribute("errorMessage", errorMessage);
        m.addAttribute("message", message);

        // render view with jsp
        return "chargingRecords";
    }

//    @RequestMapping("/farmhome")
//    public String farmhome(Model m,
//            @RequestParam(value = "animalName", required = false) String animalName,
//            @RequestParam(value = "animalType", required = false) String animalType) {
//
//        LOG.debug("farmhome called animalType=" + animalType + " animalName=" + animalName);
//        if (chargingRecordService == null) {
//            throw new RuntimeException("chargingRecordService==null and has not been initialised");
//        }
//
//        List<Animal> animalsList = chargingRecordService.getAllAnimals();
//        List<String> supportedAnimalTypes = chargingRecordService.getSupportedAnimalTypes();
//        m.addAttribute("animalsList", animalsList);
//        m.addAttribute("supportedAnimalTypes", supportedAnimalTypes);
//
//        // add error / response messages to page
//        String errorMessage = "";
//        String message = "";
//        m.addAttribute("errorMessage", errorMessage);
//        m.addAttribute("message", message);
//
//        // render view with jsp
//        return "farmlist";
//    }
//
//    @RequestMapping("/deleteAnimal")
//    public String deleteAnimal(Model m,
//            @RequestParam(value = "animalName", required = false) String animalName,
//            @RequestParam(value = "animalType", required = false) String animalType) {
//        LOG.debug("deleteAnimal called animalType=" + animalType + " animalName=" + animalName);
//        if (chargingRecordService == null) {
//            throw new RuntimeException("chargingRecordService==null and has not been initialised");
//        }
//
//        String errorMessage = "";
//        String message = "";
//
//        if (animalName == null || animalName.isEmpty()) {
//            errorMessage = "ERROR: animalName must be set when deleting animal.";
//        } else {
//            message = "Deleting animal name=" + animalName;
//            chargingRecordService.removeAnimal(animalName);
//        }
//
//        m.addAttribute("errorMessage", errorMessage);
//        m.addAttribute("message", message);
//
//        // render view with jsp
//        return "redirect:/mvc/farmhome";//will redirect to farmhome request mapping    
//    }
//
//    @RequestMapping("/createAnimal")
//    public String createAnimal(Model m,
//            @RequestParam(value = "animalName", required = false) String animalName,
//            @RequestParam(value = "animalType", required = false) String animalType) {
//        LOG.debug("createAnimal called animalType=" + animalType + " animalName=" + animalName);
//        if (chargingRecordService == null) {
//            throw new RuntimeException("chargingRecordService==null and has not been initialised");
//        }
//
//        m.addAttribute("animalType", animalType);
//        m.addAttribute("animalName", animalName);
//
//        // add error / response messages to page
//        String errorMessage = "";
//        String message = "";
//
//        if (animalType == null || animalType.isEmpty()) {
//            errorMessage = "ERROR: animalType must be set when adding animal.";
//        }
//
//        m.addAttribute("errorMessage", errorMessage);
//        m.addAttribute("message", message);
//
//        // render view with jsp
//        return "reviewAnimal";
//    }
//
//    @RequestMapping("/addAnimal")
//    public String addAnimal(Model m,
//            @RequestParam(value = "animalName", required = false) String animalName,
//            @RequestParam(value = "animalType", required = false) String animalType) {
//        LOG.debug("addAnimal called animalType=" + animalType + " animalName=" + animalName);
//        if (chargingRecordService == null) {
//            throw new RuntimeException("chargingRecordService==null and has not been initialised");
//        }
//        // add error / response messages to page
//        String errorMessage = "";
//        String message = "";
//
//        m.addAttribute("animalType", animalType);
//        m.addAttribute("animalName", animalName);
//
//        if (animalName == null || animalName.isEmpty() || animalType == null || animalType.isEmpty()) {
//            errorMessage = "ERROR: animalType and animalName must both be set when adding animal.";
//        } else {
//            if (chargingRecordService.getAnimal(animalName) != null) {
//                errorMessage = "ERROR: you cannot have dupicate animal names (" + animalName + ")";
//            } else {
//                chargingRecordService.addAnimal(animalType, animalName);
//                return "redirect:/mvc/farmhome";//will redirect to farmhome request mapping  
//            }
//        }
//
//        m.addAttribute("errorMessage", errorMessage);
//        m.addAttribute("message", message);
//
//        // render view with jsp
//        return "reviewAnimal";//will redirect to farmhome request mapping    
//    }
}
