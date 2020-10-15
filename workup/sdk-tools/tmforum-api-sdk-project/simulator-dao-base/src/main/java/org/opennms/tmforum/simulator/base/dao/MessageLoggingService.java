package org.opennms.tmforum.simulator.base.dao;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.opennms.tmforum.simulator.base.model.MessageLog;
import org.opennms.tmforum.simulator.base.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class MessageLoggingService {
    private static Logger LOG = LoggerFactory.getLogger(MessageLoggingService.class);

    @Inject
    MessageLogRepository messageLogRepository;

    @Transactional
    public void logRequest(String url, String method, String body) {
        MessageLog messageLog = new MessageLog();
        messageLog.setMessageType(MessageType.REQUEST);
        messageLog.setUrl(url);
        messageLog.setMethod(method);
        messageLog.setBody(body);
        messageLog = messageLogRepository.save(messageLog);
        LOG.debug("saved messageLog:"+messageLog.toString());
    }

    @Transactional
    public void logReply(String url, String method, String body) {
        MessageLog messageLog = new MessageLog();
        messageLog.setMessageType(MessageType.REPLY);
        messageLog.setUrl(url);
        messageLog.setMethod(method);
        messageLog.setBody(body);
        messageLog = messageLogRepository.save(messageLog);
        LOG.debug("saved messageLog:"+messageLog.toString());
    }

    @Transactional
    public void logInNotification(String body) {
        MessageLog messageLog = new MessageLog();
        messageLog.setMessageType(MessageType.NOTIFICATION_IN);
        messageLog.setBody(body);
        messageLog = messageLogRepository.save(messageLog);
        LOG.debug("saved messageLog:"+messageLog.toString());
    }

    @Transactional
    public void logOutNotification(String body) {
        MessageLog messageLog = new MessageLog();
        messageLog.setMessageType(MessageType.NOTIFICATION_OUT);
        messageLog.setBody(body);
        messageLog = messageLogRepository.save(messageLog);
        LOG.debug("saved messageLog:"+messageLog.toString());
    }
    
    @Transactional
    public void deleteLogMessages() {
        messageLogRepository.deleteAll();
        LOG.debug("deleted all messageLogs:");
    }

}
