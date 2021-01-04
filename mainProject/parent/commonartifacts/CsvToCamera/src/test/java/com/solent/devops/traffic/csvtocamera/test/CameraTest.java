package com.solent.devops.traffic.csvtocamera.test;

import com.solent.devops.traffic.csvtocamera.Camera;
import com.solent.devops.traffic.csvtocamera.CameraCsv;
import com.solent.devops.traffic.csvtocamera.CameraType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author james
 */
public class CameraTest {

    @Test
    public void CsvToCameraTest() throws IOException {
        CameraCsv cameraCsv = new CameraCsv();
        ArrayList<Camera> cameras = cameraCsv.CsvToCamera();

        // Ensure all 5 cameras are passed in
        assertEquals(60, cameras.size());

        // Check Values of One of The Objects (2nd Item)  1903,J1,0
        assertEquals(1903, cameras.get(1).getCamerId());
        assertEquals("J1", cameras.get(1).getJunctionNumber());
        assertEquals(CameraType.EntranceCamera, cameras.get(1).getCameraType());
        assertEquals(Date.from(LocalDateTime.parse("2018-05-05T12:50:55").atZone(ZoneId.systemDefault()).toInstant()), cameras.get(1).getDateTime());
        assertEquals("39a99f08-3afd-11eb-adc1-0242ac120002", cameras.get(1).getUuid());
    }
}
