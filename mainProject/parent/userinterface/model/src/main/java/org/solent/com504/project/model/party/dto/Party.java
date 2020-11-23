package org.solent.com504.project.model.party.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.solent.com504.project.model.user.dto.Bank;
import org.solent.com504.project.model.user.dto.Car;
import org.solent.com504.project.model.user.dto.Invoice;
import org.solent.com504.project.model.user.dto.User;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Entity
public class Party {

    private Long id;

    private String firstName;

    private String secondName;
    
    private Bank bank = new Bank();

    private PartyRole partyRole = PartyRole.UNDEFINED;

    private Address address = new Address(); // need not null initial value

    private PartyStatus partyStatus = PartyStatus.ACTIVE;

    // unique UUID created for every Party
    private String uuid = Long.toHexString(new Date().getTime());

    private Boolean enabled = true;

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    private Set<User> users = new HashSet();
    
    //party takes a set of cars - rui
    @XmlElementWrapper(name = "cars")
    @XmlElement(name = "car")
    private Set<Car> cars = new HashSet();

    @XmlElementWrapper(name = "invoices")
    @XmlElement(name = "invoice")
    private Set<Invoice> invoices = new HashSet();

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    
    @Embedded
    public Bank getBank(){
        return bank;
    }
    public void setBank(Bank bank){
        this.bank = bank;
    }

    public PartyRole getPartyRole() {
        return partyRole;
    }

    public void setPartyRole(PartyRole partyRole) {
        this.partyRole = partyRole;
    }

    @Embedded
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PartyStatus getPartyStatus() {
        return partyStatus;
    }

    public void setPartyStatus(PartyStatus partystatus) {
        this.partyStatus = partystatus;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    // party owns relationship
    // see https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_party", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "party_id"))
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    // note ad remove depend upon identity
    public void addUser(User user){
        this.users.add(user);
        user.getParties().add(this);
    }
    
    public void removeUser(User user){
        this.users.remove(user);
        user.getParties().remove(this);
    }
    

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "car_party", joinColumns = @JoinColumn(name = "car_id"), inverseJoinColumns = @JoinColumn(name = "party_id"))
    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "invoice_party", joinColumns = @JoinColumn(name = "invoice_id"), inverseJoinColumns = @JoinColumn(name = "party_id"))
    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }    
    
    @Override
    public String toString() {
        return "Party{" + "id=" + id + ", firstName=" + firstName + ", secondName=" + secondName + ", partyRole=" + partyRole + ", address=" + address + ", partyStatus=" + partyStatus + ", uuid=" + uuid + ", enabled=" + enabled + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.uuid);
        return hash;
    }

    // uuid is unique for identity
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Party other = (Party) obj;
        if (!Objects.equals(this.uuid, other.uuid)) {
            return false;
        }
        return true;
    }
    
    

}
