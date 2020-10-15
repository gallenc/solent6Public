#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.tmf650.hub.impl;

import org.opennms.tmforum.tmf650.model.AllSubscriptionStatistics;
import org.opennms.tmforum.tmf650.model.GenericEventSubscription;
import org.opennms.tmforum.tmf650.model.GenericEventSubscriptionInput;

public interface NotificationDispatcher {

    int getTimeout();

    void setTimeout(int timeout);

    long getMaxConsecutiveErrors();

    void setMaxConsecutiveErrors(long maxConsecutiveErrors);

    void unregisterListener(String subscriptionId);

    GenericEventSubscription registerListener(GenericEventSubscriptionInput subscription);

    void sendNotification(Object notification);

    void shutdown();

    AllSubscriptionStatistics getAllSubscriptionStatistics();

}