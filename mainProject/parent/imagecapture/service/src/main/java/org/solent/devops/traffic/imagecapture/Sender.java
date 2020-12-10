package org.solent.devops.traffic.imagecapture;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.solent.devops.message.jms.JSONMessage;

public class Sender {
    String topic;
    int qos;
    String broker;
    String clientId;
    MemoryPersistence memoryPersistence;
    final static Logger LOG = LogManager.getLogger(Controller.class);

    public Sender(String topic, int qos, String broker, String clientId) {
        this.topic = topic;
        this.qos = qos;
        this.broker = broker;
        this.clientId = clientId;
        memoryPersistence = new MemoryPersistence();
    }

    public boolean sendImage(JSONMessage jsonMessage) {
        try {
            MqttClient camera = new MqttClient(broker, clientId, memoryPersistence);
            MqttConnectOptions connectionOptions = new MqttConnectOptions();
            camera.connect(connectionOptions);
            MqttMessage message = new MqttMessage(jsonMessage.toJson().getBytes());
            message.setQos(qos);
            camera.publish(topic, message);
            camera.disconnect();
            return true;
        } catch(MqttException | JsonProcessingException ex) {
            LOG.error(ex);
            return false;
        }
    }
}
