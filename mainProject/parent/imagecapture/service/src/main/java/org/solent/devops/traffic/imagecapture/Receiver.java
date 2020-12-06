package org.solent.devops.traffic.imagecapture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.jms.core.JmsTemplate;

public class Receiver implements MqttCallback {
    String topic;
    int qos;
    String broker;
    String clientId;
    MemoryPersistence memoryPersistence;
    final static Logger LOG = LogManager.getLogger(Controller.class);
    JmsTemplate jmsTemplate;
    String toQueue;

    MqttClient client;

    public Receiver(String broker, String clientId, String topic, String toQueue) {
        this.broker = broker;
        this.clientId = clientId;
        this.topic = topic;
        this.toQueue = toQueue;
        jmsTemplate = new JmsTemplate();
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
        jmsTemplate.convertAndSend(toQueue, mqttMessage.toString());
        //Need to send to database with api
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
