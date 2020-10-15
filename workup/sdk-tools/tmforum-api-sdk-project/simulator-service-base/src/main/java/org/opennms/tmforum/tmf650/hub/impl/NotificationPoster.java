package org.opennms.tmforum.tmf650.hub.impl;

import java.net.URI;
import java.util.concurrent.Future;

import javax.ws.rs.core.Response;

import org.opennms.tmforum.tmf650.model.SubscriptionStatistics;

public interface NotificationPoster {

    Future<Response> postNotification(Object notification);

    URI getTargetUri();

    Integer getTimeout();

    Long getMaxConsecutiveErrors();

    String getNotificationFilter();

    Long getMessageCount();

    Long getErrorCount();

    Long getConsecutiveErrorCount();

    String getSubscriptionId();
    
    void shutdown();

    SubscriptionStatistics getSubscriptionStatistics();

}