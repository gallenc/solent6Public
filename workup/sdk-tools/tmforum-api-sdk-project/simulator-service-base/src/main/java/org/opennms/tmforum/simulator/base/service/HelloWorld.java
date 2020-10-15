package org.opennms.tmforum.simulator.base.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

/**
 * This is simply a class to print out hello and prove spring application
 * context is loading
 *
 * @author gallenc
 */
public class HelloWorld {

    final static Logger LOG = LoggerFactory.getLogger(HelloWorld.class);

    @Autowired
    private ApplicationContext applicationContext;

    private String message = "";

    public void setMessage(String message) {
        this.message = message;
    }

    public void init() {
        LOG.debug("init() Application context started HelloWorld " + message);
        LOG.debug("init() java.io.tmpdir =" + System.getProperty("java.io.tmpdir"));

        // this simply prints out the contents of the applicationContext
        if (LOG.isDebugEnabled()) {

            String[] beannames = applicationContext.getBeanDefinitionNames();

            String msg = "******* beans in applicationContext " + beannames.length + " names: ";
            for (String b : Arrays.asList(beannames)) {
                msg = msg + "\n " + b + ", ";
            }

            // reveals all local properties. may need changed to prevent security issues in
            // logs
            msg = msg + "\n\n******* properties in applicationContext applicationPropertyConfig ";
            if (applicationContext.containsBean("applicationPropertyConfig")) {
                PropertySourcesPlaceholderConfigurer psconfig = applicationContext.getBean("applicationPropertyConfig",
                        PropertySourcesPlaceholderConfigurer.class);
                PropertySource<?> ps = psconfig.getAppliedPropertySources().get("localProperties");
                if(ps==null) {
                    msg = msg + "\n no localProperties in context.";
                } else {
                msg = msg + "\n " + ps.toString() + ", ";
                }

            }

            // uncomment to see all environment properties
//            Map<String, Object> allKnownProperties = getAllKnownProperties(applicationContext.getEnvironment());
//            msg = msg + "\n\n******* properties in applicationContext environment " + allKnownProperties.size()
//                    + " values: ";
//            for (String key : allKnownProperties.keySet()) {
//                msg = msg + "\n " + key + "=" + allKnownProperties.get(key);
//            }

            LOG.debug(msg);
        }

    }

    // see
    // https://stackoverflow.com/questions/23506471/spring-access-all-environment-properties-as-a-map-or-properties-object
    public Map<String, Object> getAllKnownProperties(Environment env) {
        Map<String, Object> rtn = new TreeMap<>();
        if (env instanceof ConfigurableEnvironment) {
            for (PropertySource<?> propertySource : ((ConfigurableEnvironment) env).getPropertySources()) {
                if (propertySource instanceof EnumerablePropertySource) {
                    for (String key : ((EnumerablePropertySource<?>) propertySource).getPropertyNames()) {
                        rtn.put(key, propertySource.getProperty(key));
                    }
                }
            }
        }
        return rtn;
    }

    public void destroy() {
        LOG.debug("destroy() Application context stopped HelloWorld " + message);

    }

}