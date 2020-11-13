/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.devops.message.jms;

/**
 *
 * @author Emma
 */
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.sql.Time;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.xml.bind.DatatypeConverter;

public class JSONMessage {

    private String uuid;
    private int cameraId;
    private Date timestamp;
    private String numberplate;
    private String photo;

    public JSONMessage(String uuid, int cameraId, Date timestamp, String numberplate, String photo) {
        this.uuid = uuid;
        this.cameraId = cameraId;
        this.timestamp = timestamp;
        this.numberplate = numberplate;
        this.photo = photo;
    }

    public JSONMessage() {

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

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
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

    public BufferedImage imageFromString() {
        BufferedImage image = null;
        try {
            String data = this.getPhoto();
            byte[] imageByte = DatatypeConverter.parseBase64Binary(data);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
        } catch (Exception ex) {
            // TODO: Use proper logging
            System.out.println(ex.getMessage());
        } finally {
            return image;
        }
    }

    public void convertImageToString(BufferedImage image) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String data = DatatypeConverter.printBase64Binary(bytes.toByteArray());
        //proper data url format
        this.photo = data;
    }

    @Override
    public String toString() {
        return "CarInfo{" + "uuid=" + uuid + ", CameraID=" + cameraId + ", Timestamp=" + timestamp + ", Numberplate=" + numberplate + ", Photo=" + photo + "}";
    }
}
