package org.solent.com504.project.model.invoice.dto;

import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;
import java.time.LocalDateTime;
import java.util.Date;
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
    private Date issueDate;
    private Date paidDate;
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

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    


    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "invoice_chargingRecord", joinColumns = @JoinColumn(name = "invoice_id"), inverseJoinColumns = @JoinColumn(name = "chargingRecord_id"))
    public Set<ChargingRecord> getChargingRecord() {
        return chargingRecord;
    }

    public void setChargingRecord(Set<ChargingRecord> chargingRecord) {
        this.chargingRecord = chargingRecord;
    }

    @ManyToMany(mappedBy = "invoices", fetch = FetchType.EAGER)
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
