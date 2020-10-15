package org.opennms.tmforum.tmf650.listener.impl;

import org.opennms.tmforum.tmf650.model.GenericNotification;

public interface GenericNotificationListener {
    
    public void onNotification(GenericNotification genericNotification);

}
