/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.devops.traffic.platerecognition;

import java.util.Arrays;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

/**
 * This is simply a class to print out hello and prove spring application context is loading
 *
 * @author gallenc
 */
public class HelloWorld {

    final static Logger LOG = LogManager.getLogger(HelloWorld.class);

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    private String message = "";

    public void setMessage(String message) {
        this.message = message;
    }

    public void init() {
        LOG.debug("init() Application context started HelloWorld " + message);

        // this simply prints out the contents of the applicationContext
        String[] beannames = applicationContext.getBeanDefinitionNames();

        String msg = "\n---- beans in applicationContext " + beannames.length + " ----\n";
        for (String b : Arrays.asList(beannames)) {
            msg = msg + "\n " + b + ", ";
        }

        //  this prints out the properties in the environment
        ConfigurableEnvironment env = applicationContext.getEnvironment();
        msg = msg + printSources(env);
        msg = msg + "\n---- System properties (java) -----\n";
        msg = msg + printMap(env.getSystemProperties());
        msg = msg + "\n---- System Env properties -----\n";
        msg = msg + printMap(env.getSystemEnvironment());

        LOG.debug(msg);

    }

    public void destroy() {
        LOG.debug("destroy() Application context stopped HelloWorld " + message);
    }

    private String printSources(ConfigurableEnvironment env) {
        String msg = "\n---- property sources ----\n";
        for (PropertySource<?> propertySource : env.getPropertySources()) {
            msg = msg + "   name =  " + propertySource.getName() + "\nsource = " + propertySource
                    .getSource().getClass() + "\n";
        }
        return msg;
    }

    private String printMap(Map<?, ?> map) {
        String msg = "";
        for (Map.Entry<?, ?> e : map.entrySet()) {
            msg = msg + "   " + e.getKey() + " = " + e.getValue()+"\n";
        }
        return msg;
    }

}
