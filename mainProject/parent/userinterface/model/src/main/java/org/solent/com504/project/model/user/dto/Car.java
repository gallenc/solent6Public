/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.user.dto;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import org.solent.com504.project.model.party.dto.Party;

/**
 *
 * @author ruipi
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Entity
@Table(name = "car")
public class Car {
    
    private Long id;
    private String model;
    private String numberPlate;
    
    @XmlElementWrapper(name = "parties")
    @XmlElement(name = "party")
    private Set<Party> parties = new HashSet<Party>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }
    
    // parties owns the relationship
    @ManyToMany(mappedBy = "cars", fetch = FetchType.EAGER)
//   @ManyToMany(fetch = FetchType.EAGER,  cascade={CascadeType.PERSIST, CascadeType.MERGE} )
//    @JoinTable(name = "user_party", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "party_id"))
    public Set<Party> getCars() {
        return parties;
    }

    public void setCars(Set<Party> parties) {
        this.parties = parties;
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", model=" + model + ", numberPlate=" + numberPlate + '}';
    }            
}
