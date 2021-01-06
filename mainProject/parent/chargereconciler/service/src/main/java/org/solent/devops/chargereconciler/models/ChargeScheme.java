package org.solent.devops.chargereconciler.models;

import javax.persistence.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ChargeScheme implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="cs_id")
    private  Long id;

    @OneToMany(mappedBy="chargeScheme", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Charge> charges = new HashSet<>();

    public ChargeScheme() {
        super();
    }

    public Set<Charge> getChargeScheme() {
        return charges;
    }

    public void setChargeScheme(Set<Charge> chargeScheme) {
        this.charges = chargeScheme;
    }

    public Set<Charge> getCharges() {
        return charges;
    }
}
