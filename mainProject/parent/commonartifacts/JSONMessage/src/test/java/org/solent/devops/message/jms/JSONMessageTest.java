
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import com.mycompany.carinfoclass.CarInfo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.image.BufferedImage;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Emma
 */
public class CarInfoTest {

    private CarInfo init() {
        CarInfo c = new CarInfo();
        c.setUuid("0ec573fc-232e-11eb-adc1-0242ac120002");
        c.setCameraId(1);
        c.setNumberplate("TestNumberplate");
        c.setTimestamp(new Date());
        c.setPhoto("TestPhoto");
        return c;
    }

    @Test
    public void testGetUuid() {
        CarInfo c = init();
        String uuid = c.getUuid();
        assertEquals("0ec573fc-232e-11eb-adc1-0242ac120002", uuid);

    }

    @Test
    public void testSetUuid() {
        CarInfo c = init();
        c.setUuid("TestUUID");
        assertEquals("TestUUID", c.getUuid());

    }

    @Test
    public void testGetCameraId() {
        CarInfo c = init();
        int cameraId = c.getCameraId();
        assertEquals(1, cameraId);
    }

    @Test
    public void testSetCameraId() {
        CarInfo c = init();
        c.setCameraId(2);
        
        assertEquals(2, c.getCameraId());
    }

    @Test
    public void testGetTimestamp() {
        CarInfo c = init();
        Date date = new Date(); 
        c.setTimestamp(date);
        assertEquals(date, c.getTimestamp());

    }

    @Test
    public void testSetTimestamp() {
        CarInfo c = init();
    }

    @Test
    public void testGetNumberplate() {
        CarInfo c = init();
        String numberplate = c.getNumberplate();
        assertEquals("TestNumberplate", numberplate);
    }

    @Test
    public void testSetNumberplate() {
        CarInfo c = init();
        c.setNumberplate("NewNumberplate");
        assertEquals("NewNumberplate", c.getNumberplate());
    }

    @Test
    public void testGetPhoto() {
        CarInfo c = init();
        String photo = c.getPhoto();
        assertEquals("TestPhoto", photo);
    }

    @Test
    public void testSetPhoto() {
        CarInfo c = init();
        c.setPhoto("NewPhoto");
        assertEquals("NewPhoto", c.getPhoto());
    }

    @Test
    public void testCreateCarInfo() {
        CarInfo c = new CarInfo();
        c.setUuid("0ec573fc-232e-11eb-adc1-0242ac120002");
        c.setCameraId(1);
        c.setNumberplate("TestNumberplate");
        c.setTimestamp(new Date());
        c.setPhoto("TestPhoto");
        assertNotNull(c); 
    }

    
    @Test
    public void testConvertImage()
    {
        CarInfo c = init();
        
        BufferedImage img = new BufferedImage(12, 16, 1);
        c.convertImageToString(img);
        
        assertNotNull(c.getPhoto());
        
        // TODO: Consider improving this test
        String imageBase64 = "/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAQAAwDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD5/ooooA//2Q==";
        assertEquals(imageBase64, c.getPhoto());
    
        BufferedImage convertedImage = c.imageFromString();
        // Reconstructed image is not an identical BufferedImage instance
        // TODO: Consider handling the encoding/format of the image when reading
        //  and improve this test
        assertEquals(img.getHeight(), convertedImage.getHeight());
        assertEquals(img.getWidth(), convertedImage.getWidth());
    }

    @Test
    public void testCarInfoToJson() throws JsonProcessingException {
        CarInfo c = init();
        ObjectMapper om = new ObjectMapper();
        String jsonString = c.toJson(); 
        assertEquals(om.writeValueAsString(c), jsonString); 
        
        
    }

    @Test
    public void testJsonToCarInfo() throws IOException {
        CarInfo c = init();
        ObjectMapper om = new ObjectMapper();
        String jsonString = c.toJson(); 
        
        CarInfo carInfo = om.readValue(jsonString, CarInfo.class);
        assertEquals(carInfo.toString(), c.toString());
    }
}
