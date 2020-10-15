#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.tmf650.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AllSubscriptionStatistics {
    
    private List<SubscriptionStatistics> allSubscriptionStatistics = new ArrayList<SubscriptionStatistics>();

    @JsonProperty("allSubscriptionStatistics")
    public List<SubscriptionStatistics> getAllSubscriptionStatistics() {
        return allSubscriptionStatistics;
    }

    public void setAllSubscriptionStatistics(List<SubscriptionStatistics> allSubscriptionStatistics) {
        this.allSubscriptionStatistics = allSubscriptionStatistics;
    }

    @Override
    public String toString() {
        return "AllSubscriptionStatistics [allSubscriptionStatistics=" + allSubscriptionStatistics + "]";
    }
    

}
