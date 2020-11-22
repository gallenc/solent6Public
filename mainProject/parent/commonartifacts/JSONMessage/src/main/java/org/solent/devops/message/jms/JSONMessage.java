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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Emma
 */
public class JSONMessage {

    private String uuid;
    private int cameraId;
    private Date timestamp;
    private String numberplate;
    private String photo;

    /**
     *
     * @param uuid
     * @param cameraId
     * @param timestamp
     * @param numberplate
     * @param photo
     * <p> 
     * Object Constructor 
     * </p>
     */
    public JSONMessage(String uuid, int cameraId, Date timestamp, String numberplate, String photo) {
        this.uuid = uuid;
        this.cameraId = cameraId;
        this.timestamp = timestamp;
        this.numberplate = numberplate;
        this.photo = photo;
    }

    /**
     *<p> 
     * Object Constructor 
     * </p>
     */
    public JSONMessage() {

    }

    /**
     * <p>Converts JSON Message Object to a JSON string and returns the string.
     * Uses the ObjectMapper, converts the time into the correct ISO format.
     * Writes the converted time to the object and returns the entire object as a JSON String.
     * </p>
     * @return String containing the JSON Object
     * @throws JsonProcessingException
     */
    public String toJson() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        om.setDateFormat(df);
        return om.writeValueAsString(this);
    }

    /**
     *
     * @return
     */
    public String getUuid() {
        return uuid;
    }

    /**
     *
     * @param uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * @return
     */
    public int getCameraId() {
        return cameraId;
    }

    /**
     *
     * @param cameraId
     */
    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    /**
     *
     * @return
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     *
     * @return
     */
    public String getNumberplate() {
        return numberplate;
    }

    /**
     *
     * @param numberplate
     */
    public void setNumberplate(String numberplate) {
        this.numberplate = numberplate;
    }

    /**
     *
     * @return
     */
    public String getPhoto() {
        return photo;
    }

    /**
     *
     * @param photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     *<p> 
     * Creates an empty buffered image.
     * Gets the string of the photo within the JSONMessage Object and stores it in a variable.
     * Creates a byte array that contains the string of the photo that has been converted into base64.
     * Creates a new input string and the byte array is put into the input stream.
     * Converts the Inputstream into an image and returns it.
     * </p>
     * @return BufferedImage Containing the numberplate image
     */
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

    /**
     *<p>
     * Takes in a parameter of a buffered image.
     * Creates a new ByteArrayOutputStream.
     * Writes the image to a string.
     * Sets the photo of the object to the string created. 
     * 
     * </p>
     * @param image
     */
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

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "CarInfo{" + "uuid=" + uuid + ", CameraID=" + cameraId + ", Timestamp=" + timestamp + ", Numberplate=" + numberplate + ", Photo=" + photo + "}";
    }
}
