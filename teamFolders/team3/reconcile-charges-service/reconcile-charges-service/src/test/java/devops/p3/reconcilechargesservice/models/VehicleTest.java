package devops.p3.reconcilechargesservice.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    Vehicle TestVehicle = new Vehicle(
            "123e4567-e89b-12d3-a456-556642440000",
            "1",
            "2020-11-05T17:24:30.000+00:00",
            "PP587AO",
            "");

    @Test
    void testToString() {
        assertEquals(TestVehicle.toString(), "Vehicle[uuid='123e4567-e89b-12d3-a456-556642440000', cameraId='1', timestamp='2020-11-05T17:24:30.000+00:00', numberplate='PP587AO', photo='']");
    }

    @Test
    void toJsonString() {
        assertEquals(TestVehicle.toJsonString(), "{\"uuid\":\"123e4567-e89b-12d3-a456-556642440000\",\"cameraId\":\"1\",\"timestamp\":\"2020-11-05T17:24:30.000+00:00\",\"numberplate\":\"PP587AO\",\"photo\":\"\"}");
    }

    @Test
    void getUuid() {
    }

    @Test
    void setUuid() {
    }

    @Test
    void getCameraId() {
    }

    @Test
    void setCameraId() {
    }

    @Test
    void getTimestamp() {
    }

    @Test
    void setTimestamp() {
    }

    @Test
    void getNumberplate() {
    }

    @Test
    void setNumberplate() {
    }

    @Test
    void getPhoto() {
    }

    @Test
    void setPhoto() {
    }
}