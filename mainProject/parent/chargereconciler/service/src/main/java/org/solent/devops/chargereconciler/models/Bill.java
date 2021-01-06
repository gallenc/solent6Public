package org.solent.devops.chargereconciler.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

public class Bill implements Serializable  {

    @JsonProperty("entry_camera")
    private String entryCamera;
    @JsonProperty("entry_time")
    private String entryTime;
    @JsonProperty("exit_camera")
    private String exitCamera;
    @JsonProperty("exit_time")
    private String exitTime;
    @JsonProperty("number_plate")
    private String numberplate;
    @JsonProperty("charge")
    private Double charge;
    @JsonProperty("charge_rate")
    private Double rate;


    public Bill(Vehicle entryVehicle, Vehicle exitVehicle) {
        this.entryCamera = entryVehicle.getCameraId();
        this.entryTime = entryVehicle.getTimestamp();
        this.exitCamera = exitVehicle.getCameraId();
        this.exitTime = exitVehicle.getTimestamp();
        this.numberplate = entryVehicle.getNumberplate();
    }

    public String getEntryCamera() {
        return entryCamera;
    }

    public void setEntryCamera(String entryCamera) {
        this.entryCamera = entryCamera;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getExitCamera() {
        return exitCamera;
    }

    public void setExitCamera(String exitCamera) {
        this.exitCamera = exitCamera;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public String getNumberplate() {
        return numberplate;
    }

    public void setNumberplate(String numberplate) {
        this.numberplate = numberplate;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String toJsonString() {
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
