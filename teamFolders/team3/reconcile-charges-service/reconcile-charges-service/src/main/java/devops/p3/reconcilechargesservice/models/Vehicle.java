package devops.p3.reconcilechargesservice.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String numberPlate;
    private String time;
    private String camera;


    protected Vehicle() {
        super();
    }

    public Vehicle(String numberPlate, String time, String camera) {
        this.numberPlate = numberPlate;
        this.time = time;
        this.camera = camera;
    }

    @Override
    public String toString() {
        return String.format(
                "Vehicle[id=%d, number_plate='%s', time='%s', camera='%s']",
                id, numberPlate, time, camera);
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }


}
