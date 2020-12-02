package org.solent.devops.traffic.imagecapture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Receiver implements MqttCallback {
    String topic;
    int qos;
    String broker;
    String clientId;
    MemoryPersistence memoryPersistence;
    final static Logger LOG = LogManager.getLogger(Controller.class);

    MqttClient client;

    public Receiver(String broker, String clientId, String topic) {
        this.broker = broker;
        this.clientId = clientId;
        this.topic = topic;
        try {
            client = new MqttClient(broker, clientId);
            client.connect();
            client.setCallback(this);
            client.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        LOG.error("Connection lost to queue");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        LOG.debug(s, mqttMessage.toString());
        //Need to forward onto the next queue.
        //Need to send to database with api
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
