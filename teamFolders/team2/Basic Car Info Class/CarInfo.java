/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carinfoclass;

/**
 *
 * @author Emma
 */
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Date;
import java.sql.Time;

public class CarInfo {

    private String uuid;
    private Integer cameraId;
    private Date timestamp;
    private String numberplate;
    private String photo;

    public CarInfo(String uuid, Integer cameraId, Date timestamp, String numberplate, String photo) {
        this.uuid = uuid;
        this.cameraId = cameraId;
        this.timestamp = timestamp;
        this.numberplate = numberplate;
        this.photo = photo;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(this);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getCameraId() {
        return cameraId;
    }

    public void setName(Integer cameraId) {
        this.cameraId = cameraId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getNumberplate() {
        return numberplate;
    }

    public void setNumberplate(String numberplate) {
        this.numberplate = numberplate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "CarInfo{" + "uuid=" + uuid + ", CameraID=" + cameraId + ", Timestamp=" + timestamp + ", Numberplate=" + numberplate + ", Photo=" + photo + "}";
    }
}
