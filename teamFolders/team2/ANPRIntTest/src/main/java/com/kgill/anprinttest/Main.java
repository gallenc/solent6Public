package com.kgill.anprinttest;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;

import sun.misc.BASE64Decoder;

/**
 *
 * @author 4GILLK91 <4GILLK91@solent.ac.uk>
 */
public class Main {
    
    public static void main(String[] args) throws Exception {
        // txt contains a Base64 string of the jpg image
        File initialFile = new File("src/main/resources/test_001.txt");
        InputStream targetStream = new FileInputStream(initialFile);
        
        // create a buffered image
        BufferedImage image = null;
        byte[] imageByte;

        BASE64Decoder decoder = new BASE64Decoder();
        imageByte = decoder.decodeBuffer(targetStream);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        
        image = ImageIO.read(bis);

        // Read height and width of original image
        System.out.println(image.getHeight() + ", " + image.getWidth());
        
        Intelligence intelligence = new Intelligence();
        // NOTE: Had to copy over configuration resources from JavaANPR main/
        // into ./src/main/resources
        
        // create 
        CarSnapshot photo = new CarSnapshot(image);

        // Should print out: "PP587A0"
        System.out.println(intelligence.recognize(photo));
    }
}
