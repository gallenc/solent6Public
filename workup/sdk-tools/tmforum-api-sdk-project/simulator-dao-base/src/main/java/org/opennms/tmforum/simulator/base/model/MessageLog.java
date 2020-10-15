package org.opennms.tmforum.simulator.base.model;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MessageLog {
    
    private Long id;
    
    private MessageType messageType;
    
    private OffsetDateTime timeReceived = OffsetDateTime.now();
    
    private String url;
    
    private String method;
    
    private String body;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
    
    public OffsetDateTime getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(OffsetDateTime timeReceived) {
        this.timeReceived = timeReceived;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "MessageLog [id=" + id + ", messageType=" + messageType + ", timeReceived=" + timeReceived + ", url="
                + url + ", method=" + method + ", body=" + body + "]";
    }


}
