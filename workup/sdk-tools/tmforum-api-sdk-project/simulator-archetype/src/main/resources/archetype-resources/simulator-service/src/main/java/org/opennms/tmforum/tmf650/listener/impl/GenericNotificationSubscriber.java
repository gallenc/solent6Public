#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.tmf650.listener.impl;

import java.util.Set;

import org.opennms.tmforum.tmf650.model.GenericNotification;

public interface GenericNotificationSubscriber {

    void onNotification(GenericNotification genericNotification);

    void subscribe(GenericNotificationListener genericNotificationListener);

    void unsubscribe(GenericNotificationListener genericNotificationListener);

    void subscribe(GenericNotificationListener genericNotificationListener, Set<String> filter);

}