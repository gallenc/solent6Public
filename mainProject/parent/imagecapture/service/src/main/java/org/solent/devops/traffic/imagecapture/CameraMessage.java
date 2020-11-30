package org.solent.devops.traffic.imagecapture;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    Timestamp timestamp;
    int cameraId;
    byte[] image;

    public CameraMessage(int id, Timestamp timestamp, int cameraId, byte[] image) {
        this.id = id;
        this.timestamp = timestamp;
        this.cameraId = cameraId;
        this.image = image;
    }

    //Convert image file to binary
    public static String convertImage(File imageFile) {
        StringBuilder sb = new StringBuilder();
        try (BufferedInputStream is = new BufferedInputStream(new FileInputStream("imageFile"))) {
            for (int i; (i = is.read()) != -1;) {
                String temp = "0000000" + Integer.toBinaryString(i).toUpperCase();
                if (temp.length() == 1) {
                    sb.append('0');
                }
                temp = temp.substring(temp.length() - 8);
                sb.append(temp).append(' ');
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failed image binary conversion ";
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