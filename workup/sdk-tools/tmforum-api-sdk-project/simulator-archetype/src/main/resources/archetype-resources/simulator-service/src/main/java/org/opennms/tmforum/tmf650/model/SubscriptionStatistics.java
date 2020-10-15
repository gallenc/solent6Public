#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.tmf650.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionStatistics {
    

    private String id;

    private Long messageCount;

    private Long errorCount;

    private Long consecutiveErrorCount;

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

    @Override
    public String toString() {
        return "SubscriptionStatistics [id=" + id + ", messageCount=" + messageCount + ", errorCount=" + errorCount
                + ", consecutiveErrorCount=" + consecutiveErrorCount + "]";
    }
    
    

}
