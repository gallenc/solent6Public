/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;
import org.solent.com504.project.model.chargingrecord.service.ChargingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ruipi
 */
@Controller
@Transactional
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
        Integer m_page = 0;
        Integer m_size = 20;
        Long m_totalRecords = 0L;

        try {
            m_entryDate = (entryDate == null || entryDate.isEmpty()) ? null : df.parse(entryDate);
            m_exitDate = (exitDate == null || exitDate.isEmpty()) ? null : df.parse(exitDate);
            m_page = (page == null || page.isEmpty()) ? null : Integer.parseInt(page);
            m_size = (size == null || size.isEmpty()) ? null : Integer.parseInt(size);
        } catch (Exception ex) {
            errorMessage = ex.getMessage();
        }

        m_totalRecords = chargingRecordService.totalRecordsByNumberPlate(numberPlate, m_entryDate, m_exitDate);
        List<ChargingRecord> chargingRecordList = chargingRecordService.findByNumberPlate(numberPlate, m_entryDate, m_exitDate, m_page, m_size);

        m.addAttribute("chargingRecordList", chargingRecordList);
        m.addAttribute("entryDate", (m_entryDate == null) ? "" : df.format(m_entryDate));
        m.addAttribute("exitDate", (m_exitDate == null) ? df.format(new Date()) : df.format(m_exitDate));
        m.addAttribute("page", m_page);
        m.addAttribute("size", m_size);
        m.addAttribute("totalRecords", m_totalRecords);
        m.addAttribute("totalPages", m_totalRecords / m_size);

        m.addAttribute("errorMessage", errorMessage);
        m.addAttribute("message", message);

        // render view with jsp
        return "chargingRecords";
    }

    
}
