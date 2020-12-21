package org.solent.com504.project.model.service;

import java.util.List;
import java.util.Set;
import org.solent.com504.project.model.party.dto.Party;
import org.solent.com504.project.model.party.dto.PartyRole;
import org.solent.com504.project.model.car.dto.Car;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;
import org.solent.com504.project.model.invoice.dto.Invoice;

public interface ServiceFacade {

    public String getHeartbeat();

    /**
     * find all partys in database by partyRole if partyRole is null return all partys
     *
     * @param partyRole
     * @return list of party objects
     */
    public List<Party> findByPartyRole(PartyRole partyRole);
    
    public Invoice findByIdInvoice(long id);
    
    public List<Invoice> findAllInvoice();
    
    public Invoice saveInvoice(Invoice invoice);
    
    public void deleteByIdInvoice(Long id);
    
    public void deleteInvoice(Invoice invoice);
    
    public void deleteAllInvoice();
    
    public ChargingRecord findByIdCRecord(Long id);
    
    public List<ChargingRecord> findAllCRecord();
    
    public ChargingRecord saveChargingRecord(ChargingRecord cr);
    
    public void deleteByIdCRecord(Long id);
    
    public void deleteCRecord(ChargingRecord CRecord);
    
    public void deleteAllCRecord();
    
    public Car findByIdCar(long id);
    
    public Car saveCar(Car car);
    
    public void deleteCar(Car car);
    
    public void deleteByIdCar(long id);
    
    public void deleteAllCars();

}
