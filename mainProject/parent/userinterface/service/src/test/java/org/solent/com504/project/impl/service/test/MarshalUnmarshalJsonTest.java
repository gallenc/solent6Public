/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.service.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.UUID;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;

/**
 *
 * @author cgallen
 */
public class MarshalUnmarshalJsonTest {

    @Test
    public void testMarshalUnmarshal() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // writes prity output see https://mkyong.com/java/how-to-enable-pretty-print-json-output-jackson/
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        objectMapper.registerModule(new JaxbAnnotationModule());

        ChargingRecord chargingRecord = new ChargingRecord();
        chargingRecord.setUuid(UUID.randomUUID().toString());
        chargingRecord.setCharge(10.0);
        chargingRecord.setChargeRate(1.0);
        chargingRecord.setEntryDate(new Date());
        chargingRecord.setEntryLocation("Southampton");
        chargingRecord.setEntryPhotoId(UUID.randomUUID().toString());
        chargingRecord.setExitDate(new Date());
        chargingRecord.setExitLocation("London");
        chargingRecord.setExitPhotoId(UUID.randomUUID().toString());

        StringWriter sw1 = new StringWriter();
        objectMapper.writeValue(sw1, chargingRecord);

        String json = sw1.toString();

        System.out.println("marshalled chargingRecord: " + json);

        ChargingRecord receivedChargingRecord = objectMapper.readValue(json, ChargingRecord.class);

        assertTrue(chargingRecord.toString().equals(receivedChargingRecord.toString()));

    }
}
