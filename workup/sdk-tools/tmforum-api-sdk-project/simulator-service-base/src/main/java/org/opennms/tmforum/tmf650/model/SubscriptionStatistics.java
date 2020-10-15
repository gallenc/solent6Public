package org.opennms.tmforum.tmf650.model;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionStatistics {
    

    private String id;

    private Long messageCount;

    private Long errorCount;

    private Long consecutiveErrorCount;
    
    private URI targetUri;
    
    private String notificationFilter;

	@JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("messageCount")
    public Long getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Long messageCount) {
        this.messageCount = messageCount;
    }

    @JsonProperty("errorCount")
    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    @JsonProperty("consecutiveErrorCount")
    public Long getConsecutiveErrorCount() {
        return consecutiveErrorCount;
    }

    public void setConsecutiveErrorCount(Long consecutiveErrorCount) {
        this.consecutiveErrorCount = consecutiveErrorCount;
    }
    
	@JsonProperty("targetUri")
    public URI getTargetUri() {
		return targetUri;
	}

	public void setTargetUri(URI targetUri) {
		this.targetUri = targetUri;
	}

	@JsonProperty("notificationFilter")
	public String getNotificationFilter() {
		return notificationFilter;
	}

	public void setNotificationFilter(String notificationFilter) {
		this.notificationFilter = notificationFilter;
	}
    
}
