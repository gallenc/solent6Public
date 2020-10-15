#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.tmf650.listener.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PreDestroy;
import javax.inject.Named;

import org.opennms.tmforum.tmf650.model.GenericNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class GenericNotificationSubscriberImpl implements GenericNotificationListener, GenericNotificationSubscriber {
    private static Logger LOG = LoggerFactory.getLogger(GenericNotificationSubscriberImpl.class);

    private Map<GenericNotificationListener, Set<String>> listenerFilterMap = new ConcurrentHashMap<GenericNotificationListener, Set<String>>();

    @Override
    public void onNotification(GenericNotification genericNotification) {
        for (GenericNotificationListener listener : listenerFilterMap.keySet()) {
            try {
                Set<String> filter = listenerFilterMap.get(listener);
                if (filter.isEmpty()) {
                    listener.onNotification(genericNotification);
                } else {
                    if (filter.contains(genericNotification.getEventType())) {
                        listener.onNotification(genericNotification);
                    }
                }
            } catch (Exception ex) {
                LOG.error("problem posting genericNotification " + genericNotification
                        + " to GenericNotificationListener", ex);
            }
        }
    }

    @Override
    public void subscribe(GenericNotificationListener genericNotificationListener) {
        listenerFilterMap.put(genericNotificationListener, new HashSet<String>());
    }

    @Override
    public void subscribe(GenericNotificationListener genericNotificationListener, Set<String> filter) {
        listenerFilterMap.put(genericNotificationListener, filter);
    }

    @Override
    public void unsubscribe(GenericNotificationListener genericNotificationListener) {
        listenerFilterMap.remove(genericNotificationListener);
    }

    @PreDestroy
    public void shutdown() {
        listenerFilterMap.clear();
    }

}
