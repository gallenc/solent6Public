package org.solent.com504.project.model.user.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.solent.com504.project.model.party.dto.Party;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Entity
@Table(name = "invoice")
public class Invoice {
    
    private Long id;
//    private Party party;
    private LocalDateTime issueDate;
    private LocalDateTime paidDate;
    private Double amount;
    
    @XmlElementWrapper(name = "chargingRecords")
    @XmlElement(name = "chargingRecord")
    private Set<ChargingRecord> chargingRecord = new HashSet<ChargingRecord>();

    @XmlElementWrapper(name = "parties")
    @XmlElement(name = "party")
    private Set<Party> party = new HashSet<>();
        
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Party getParty() {
//        return party;
//    }
//
//    public void setParty(Party party) {
//        this.party = party;
//    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDateTime getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDateTime paidDate) {
        this.paidDate = paidDate;
    }

    public Double getAmmount() {
        return amount;
    }

    public void setAmmount(Double ammount) {
        this.amount = ammount;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "invoice_chargingRecord", joinColumns = @JoinColumn(name = "invoice_id"), inverseJoinColumns = @JoinColumn(name = "chargingRecord_id"))
    public Set<ChargingRecord> getChargingRecord() {
        return chargingRecord;
    }

    public void setChargingRecord(Set<ChargingRecord> chargingRecord) {
        this.chargingRecord = chargingRecord;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "invoice_party", joinColumns = @JoinColumn(name = "invoice_id"), inverseJoinColumns = @JoinColumn(name = "party_id"))
    public Set<Party> getParties() {
        return party;
    }

    public void setParties(Set<Party> parties) {
        this.party = parties;
    }
    
    @Override
    public String toString() {
        return "Invoice{" + "id=" + id + ", party=" + party + ", issueDate=" + issueDate + ", paidDate=" + paidDate + ", ammount=" + amount + ", chargingRecord=" + chargingRecord + '}';
    }        
}
