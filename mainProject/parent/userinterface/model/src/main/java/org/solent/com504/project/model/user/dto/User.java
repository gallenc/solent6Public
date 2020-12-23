package org.solent.com504.project.model.user.dto;

import java.util.HashSet;
import java.util.Objects;
import javax.persistence.*;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.solent.com504.project.model.car.dto.Car;
import org.solent.com504.project.model.dto.Bank;
import org.solent.com504.project.model.invoice.dto.Invoice;
import org.solent.com504.project.model.party.dto.Address;
import org.solent.com504.project.model.party.dto.Party;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Entity
@Table(name = "user")
public class User {

    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private String firstName;
    private String secondName;
    private Address address = new Address(); // need not null initial value
    //Bank is an embedded field of User - rui
    private Bank bank = new Bank();
    private Boolean enabled = true;

    @XmlElementWrapper(name = "roles")
    @XmlElement(name = "role")
    private Set<Role> roles = new HashSet<Role>();

    @XmlElementWrapper(name = "parties")
    @XmlElement(name = "party")
    private Set<Party> parties = new HashSet<Party>();      
    
    @XmlElementWrapper(name = "cars")
    @XmlElement(name = "car")
    private Set<Car> cars = new HashSet();

    @XmlElementWrapper(name = "invoices")
    @XmlElement(name = "invoice")
    private Set<Invoice> invoices = new HashSet();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Embedded
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    @Embedded
    public Bank getBank(){
        return bank;
    }
    public void setBank(Bank bank){
        this.bank = bank;
    }

    // parties owns the relationship
//   @ManyToMany(fetch = FetchType.EAGER,  cascade={CascadeType.PERSIST, CascadeType.MERGE} )
//    @JoinTable(name = "user_party", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "party_id"))
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    public Set<Party> getParties() {
        return parties;
    }

    public void setParties(Set<Party> parties) {
        this.parties = parties;
    }    
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "car_user", joinColumns = @JoinColumn(name = "car_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "invoice_user", joinColumns = @JoinColumn(name = "invoice_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }    
    
        public void addInvoice(Invoice invoice){
        this.invoices.add(invoice);
        invoice.getUser().add(this);        
    }
    
    public void removeInvoice(Invoice invoice){
        this.invoices.remove(invoice);
        invoice.getUser().remove(this);        
    }

    // Note Password and roles omitted from tostring
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username
                + ", firstName=" + firstName + ", secondName="
                + secondName + ", address=" + address
                + ", enabled=" + enabled + "PASSWORD ROLES omitted }";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.username);
        return hash;
    }

}
