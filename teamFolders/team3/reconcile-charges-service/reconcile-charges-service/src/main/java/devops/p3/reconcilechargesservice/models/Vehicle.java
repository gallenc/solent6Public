package devops.p3.reconcilechargesservice.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vehicle {

    @Id
    private String uuid;
    private String cameraId;
    private String timestamp;
    private String numberplate;
    private String photo;

    protected Vehicle() {
        super();
    }

    public Vehicle(String uuid, String cameraId, String timestamp, String numberplate,  String photo) {
        this.uuid = uuid;
        this.cameraId = cameraId;
        this.timestamp = timestamp;
        this.numberplate = numberplate;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return String.format(
                "Vehicle[uuid='%s', cameraId='%s', timestamp='%s', numberplate='%s', photo='%s']",
                uuid, cameraId, timestamp, numberplate, photo);
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
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

}
