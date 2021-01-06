package org.solent.devops.chargereconciler.models;

import org.hibernate.property.access.spi.Getter;
import org.hibernate.property.access.spi.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity
public class Charge {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private  Long id;
    private LocalTime time;
    private double rate;
    @ManyToOne
    @JoinColumn(name="cs_id", nullable=false)
    private ChargeScheme chargeScheme;

    public Charge() {
        super();
    }

    public Charge(ChargeScheme chargeScheme) {
        this.chargeScheme = chargeScheme;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public ChargeScheme getChargeScheme() {
        return chargeScheme;
    }

    public void setChargeScheme(ChargeScheme chargeScheme) {
        this.chargeScheme = chargeScheme;
    }
}
