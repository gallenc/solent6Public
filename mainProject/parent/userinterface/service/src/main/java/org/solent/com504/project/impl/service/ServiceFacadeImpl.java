package org.solent.com504.project.impl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.solent.com504.project.model.party.dto.Party;
import org.solent.com504.project.model.party.dto.PartyRole;
import org.solent.com504.project.model.service.ServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.solent.com504.project.model.party.dao.PartyDAO;
import org.solent.com504.project.model.user.dao.InvoiceDAO;
import org.solent.com504.project.model.user.dto.Invoice;

// note we give the bean this name so it is picked up later in application context
@Component("serviceFacade")
public class ServiceFacadeImpl implements ServiceFacade {

    @Autowired
    private PartyDAO partyDao = null;
    
    @Autowired
    private InvoiceDAO invoiceDao = null;

    // used to concurently count heartbeat requests
    private static AtomicInteger heartbeatRequests = new AtomicInteger();

    // setters for DAOs
    public void setPartyDao(PartyDAO partyDao) {
        this.partyDao = partyDao;
    }

    // Service facade methods
    @Override
    public String getHeartbeat() {
        return "heartbeat number " + heartbeatRequests.getAndIncrement() + " " + new Date().toString();

    }

    @Override
    public List<Party> findByPartyRole(PartyRole partyRole) {
        if (partyRole == null) {
            return partyDao.findAll();
        } else {
            return partyDao.findByPartyRole(partyRole);
        }
    }
    
    @Override
    public Invoice findInvoice(long id){
        return invoiceDao.findById(id);
    }
    
    @Override
    public Invoice saveInvoice(Invoice invoice){
        return invoiceDao.save(invoice);
    }

}
