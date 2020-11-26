/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solent.ac.uk.devops.traffic.messageexample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author cgallen
 */
@Component
public class Controller {
    final static Logger LOG = LogManager.getLogger(Controller.class);

    public void start(){
        LOG.debug("Starting "+Controller.class);
    }

}
