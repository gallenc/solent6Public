package com.solent.devops.traffic.csvtocamera;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;

/**
 *
 * @author james
 */
public class Camera {

    private int camerId;
    private String junctionNumber;
    private CameraType cameraType;
    private String imageName;
    private Date dateTime;
    private String uuid;

    public Camera(int id, String junctionNumber, CameraType cameraType, String imageName, Date dateTime, String uuid) {
        this.camerId = id;
        this.junctionNumber = junctionNumber;
        this.cameraType = cameraType;
        this.imageName = imageName;
        this.dateTime = dateTime;
        this.uuid = uuid;
    }

    public String getUuid() {return uuid;}

    public void setUuid(String uuid) {this.uuid = uuid;}

    public Date getDateTime() {return dateTime;}

    public void setDateTime(Date dateTime) {this.dateTime = dateTime;}

    public int getCamerId() {
        return camerId;
    }

    public void setCamerId(int camerId) {
        this.camerId = camerId;
    }

    public String getJunctionNumber() {
        return junctionNumber;
    }

    public void setJunctionNumber(String junctionNumber) {
        this.junctionNumber = junctionNumber;
    }

    public CameraType getCameraType() {
        return cameraType;
    }

    public void setCameraType(CameraType cameraType) {
        this.cameraType = cameraType;
    }

    public String getImageName() {return imageName;}

    public void setImageName(String imageName) {this.imageName = imageName;}
}
