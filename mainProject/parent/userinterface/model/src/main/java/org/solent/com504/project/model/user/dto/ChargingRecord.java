/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.user.dto;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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
    private Long entryPhotoId;
    private Long exitPhotoId;
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;
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

    public Long getEntryPhotoId() {
        return entryPhotoId;
    }

    public void setEntryPhotoId(Long entryPhotoId) {
        this.entryPhotoId = entryPhotoId;
    }

    public Long getExitPhotoId() {
        return exitPhotoId;
    }

    public void setExitPhotoId(Long exitPhotoId) {
        this.exitPhotoId = exitPhotoId;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateTime getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDateTime exitDate) {
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
        
    @Override
    public String toString() {
        return "ChargingRecord{" + "id=" + id + ", entryPhotoId=" + entryPhotoId + ", exitPhotoId=" + exitPhotoId + ", entryDate=" + entryDate + ", exitDate=" + exitDate + ", entryLocation=" + entryLocation + ", exitLocation=" + exitLocation + ", numberPlate=" + numberPlate + ", charge=" + charge + ", chargeRate=" + chargeRate + '}';
    }                
}
