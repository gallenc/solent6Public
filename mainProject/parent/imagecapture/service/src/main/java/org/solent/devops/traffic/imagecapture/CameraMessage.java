package org.solent.devops.traffic.imagecapture;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class CameraMessage {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(URL imageFile) {
        //Convert image file to binary
    }

    Timestamp timestamp;
    int cameraId;
    byte[] image;

    public CameraMessage(int id, Timestamp timestamp, int cameraId, byte[] image) {
        this.id = id;
        this.timestamp = timestamp;
        this.cameraId = cameraId;
        this.image = image;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        om.setDateFormat(df);
        return om.writeValueAsString(this);
    }
}