package com.kgill.anprinttest;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;

/**
 *
 * @author 4GILLK91 <4GILLK91@solent.ac.uk>
 */
public class Main {
    
    public static void main(String[] args) throws Exception {
        Intelligence intelligence = new Intelligence();
        // NOTE: Had to copy over configuration resources from JavaANPR main/
        // into ./src/main/resources
        
        // Should print out: "PP587A0"
        System.out.println(intelligence.recognize(new CarSnapshot(
                "./src/test/resources/test_001.jpg")));
        
        // NOTE: CarSnapshot constructor can instead take a BufferedImage as the
        // argument
    }
}
