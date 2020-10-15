#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.tmf650.hub.impl;

import java.net.URI;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;
import org.opennms.tmforum.tmf650.impl.NewJacksonFeature;
import org.opennms.tmforum.tmf650.model.SubscriptionStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

public class GenericRestNotificationPoster implements NotificationPoster {
    private static Logger LOG = LoggerFactory.getLogger(GenericRestNotificationPoster.class);
    
    private final String subscriptionId;

    private final URI targetUri;

    private final int timeout; // 10 seconds

    private final long maxConsecutiveErrors; 

    private final String notificationFilter;

    private final AtomicLong messageCount = new AtomicLong();

    private final AtomicLong errorCount = new AtomicLong();

    private final AtomicLong consecutiveErrorCount = new AtomicLong();

    private final WebTarget target;
    
    private Client client;
    
    private NotificationDispatcher dispatcher;

    public GenericRestNotificationPoster(URI targetUri, int timeout, long maxConsecutiveErrors,
            String notificationFilter, String subscriptionId, NotificationDispatcher dispatcher) {
        super();

        // check only https / http posts i.e. not web sockets
        if (!( "http".equals(targetUri.getScheme()) || "https".equals(targetUri.getScheme())) ) {
            throw new IllegalArgumentException("scheme must be http or https in targetUri=" + targetUri);
        }

        this.targetUri = targetUri;

        client = ClientBuilder.newClient();
        client.property(ClientProperties.CONNECT_TIMEOUT, timeout);
        client.property(ClientProperties.READ_TIMEOUT, timeout);

        // sets up ObjectMapper
        client.register(NewJacksonFeature.class);

        target = client.target(targetUri);

        this.timeout = timeout;
        this.maxConsecutiveErrors = maxConsecutiveErrors;
        this.notificationFilter = notificationFilter;
        this.subscriptionId = subscriptionId;
        this.dispatcher=dispatcher;
    }

    @Override
    public Future<Response> postNotification(Object notification) {
        messageCount.incrementAndGet();
        return target.request().async().post(Entity.entity(notification, MediaType.APPLICATION_JSON),
                new InvocationCallback<Response>() {
                    @Override
                    public void completed(Response reply) {
                        // on complete
                        consecutiveErrorCount.set(0);
                        int status = reply.getStatus();

                        String notificationReply = reply.readEntity(String.class);
                        LOG.debug("success response status=" + status + " notificationReply=" + notificationReply);

                    }

                    @Override
                    public void failed(Throwable throwable) {
                        // on fail
                        long ec = errorCount.incrementAndGet();
                        long cec = consecutiveErrorCount.incrementAndGet();
                        LOG.debug("message failure errorCount=" + ec + ", consecutiveErrorCount=" + cec+" exception: "+throwable.getMessage());
                        
                        if(cec> maxConsecutiveErrors) {
                            LOG.debug(" consecutiveErrorCount "+ cec
                                    + " > maxConsecutiveErrors "+maxConsecutiveErrors
                                    + " unregistering client subscriptionId="+subscriptionId );
                            if(dispatcher!=null) {
                                dispatcher.unregisterListener(subscriptionId);
                                dispatcher=null;
                            }
                        }
                        
                    }
                });
    }

    @Override
    public URI getTargetUri() {
        return targetUri;
    }

    @Override
    public Integer getTimeout() {
        return timeout;
    }

    @Override
    public Long getMaxConsecutiveErrors() {
        return maxConsecutiveErrors;
    }

    @Override
    public String getNotificationFilter() {
        return notificationFilter;
    }

    @Override
    public Long getMessageCount() {
        return messageCount.get();
    }

    @Override
    public Long getErrorCount() {
        return errorCount.get();
    }

    @Override
    public Long getConsecutiveErrorCount() {
        return consecutiveErrorCount.get();
    }

    @Override
    public String getSubscriptionId() {
        return subscriptionId;
    }

    @Override
    public void shutdown() {
       client.close();
    }
    
    @Override
    public SubscriptionStatistics getSubscriptionStatistics() {
        SubscriptionStatistics stats = new SubscriptionStatistics();
        
        stats.setErrorCount(errorCount.get());
        stats.setConsecutiveErrorCount(consecutiveErrorCount.get());
        stats.setMessageCount(messageCount.get());
        stats.setId(subscriptionId);

        return stats;
    }

}
