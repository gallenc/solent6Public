package com.solent.devops.traffic.csvtocamera;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author james
 */
public class CameraCsv {

public ArrayList<Camera> CsvToCamera() throws IOException{
    return GetCameras();
}
    
public ArrayList<Camera> GetCameras() throws IOException{
    BufferedReader csvReader;
    ArrayList<Camera> cameras = new ArrayList<Camera>();
    String row;

    try {
        URL url = Thread.currentThread().getContextClassLoader().getResource("testfile.csv");
        if (url == null) return null;
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        while ((row = in.readLine()) != null) {
            String[] data = row.split(",");

            int item2 = Integer.parseInt(data[2]);
            Camera camera = new Camera(Integer.parseInt(data[0]), data[1], (CameraType.values()[item2]), data[3], Date.from(LocalDateTime.parse(data[4]).atZone(ZoneId.systemDefault()).toInstant()), data[5]);
            cameras.add(camera);
        }
        in.close();
        return cameras;

    } catch (FileNotFoundException ex) {
       throw new IllegalArgumentException("Cannot Read File " ,ex);
    }
}}
