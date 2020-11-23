/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.chargingrecord.dto;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joao-
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Entity
@Table(name = "chargingRecord")
public class ChargingRecord {
    
    private Long id;
    private String uuid;
    private String entryPhotoId;
    private String exitPhotoId;
    private Date entryDate;
    private Date exitDate;
    private String entryLocation;
    private String exitLocation;
    private String numberPlate;
    private Double charge;
    private Double chargeRate;       
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntryPhotoId() {
        return entryPhotoId;
    }

    public void setEntryPhotoId(String entryPhotoId) {
        this.entryPhotoId = entryPhotoId;
    }

    public String getExitPhotoId() {
        return exitPhotoId;
    }

    public void setExitPhotoId(String exitPhotoId) {
        this.exitPhotoId = exitPhotoId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public String getEntryLocation() {
        return entryLocation;
    }

    public void setEntryLocation(String entryLocation) {
        this.entryLocation = entryLocation;
    }

    public String getExitLocation() {
        return exitLocation;
    }

    public void setExitLocation(String exitLocation) {
        this.exitLocation = exitLocation;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public Double getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(Double chargeRate) {
        this.chargeRate = chargeRate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "ChargingRecord{" + "id=" + id + ", uuid=" + uuid + ", entryPhotoId=" + entryPhotoId + ", exitPhotoId=" + exitPhotoId + ", entryDate=" + entryDate + ", exitDate=" + exitDate + ", entryLocation=" + entryLocation + ", exitLocation=" + exitLocation + ", numberPlate=" + numberPlate + ", charge=" + charge + ", chargeRate=" + chargeRate + '}';
    }
                   
}

