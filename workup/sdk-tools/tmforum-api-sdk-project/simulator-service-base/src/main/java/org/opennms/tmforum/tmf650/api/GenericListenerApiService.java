package org.opennms.tmforum.tmf650.api;


import org.opennms.tmforum.tmf650.model.GenericNotification;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;


public abstract class GenericListenerApiService {
    public abstract Response listenToGenericNotification(GenericNotification data, SecurityContext securityContext, UriInfo uriInfo);
}
