#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.${tmfSpecPackageName}.rest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.CommonProperties;
//import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.opennms.tmforum.tmf650.impl.NewJacksonFeature;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import org.springframework.stereotype.Component;

//public class ApiApplication extends Application {
//    @Override
//    public Set<Class<?>> getClasses() {
//        Set<Class<?>> resources = new HashSet();
//
//        //resources.add(FirstResource.class);
//        //resources.add(SecondResource.class);
//        //...
//
//        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
//        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
//        
//        return resources;
//    }
//}

public class RestApp extends ResourceConfig {
    private static Logger LOG = LoggerFactory.getLogger(RestApp.class);

    // should be injected but leave for now
    private String basePath = "/${tmfSpecPackageName}-simulator-war${tmfSpecUrlPath}";

    // produces
    // http://localhost:8080${tmfSpecUrlPath}swagger.json
    // see
    // https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Getting-started

    // setting package names programatically because we may refactor
    String packageBaseApi="org.opennms.tmforum.tmf650.api.rest";
    String packageApi = ${package}.${tmfSpecPackageName}.swagger.api.StringUtil.class.getPackage().getName();
    String packageModel = ${package}.${tmfSpecPackageName}.swagger.model.Any.class.getPackage().getName();

    public RestApp() {
        LOG.info("**************************** DEBUG STARTING INTERFACE REST APP ");

        LOG.info("**************************** Registering packages " + packageApi + " " + packageModel);

        //// search in jackson's package "com.fasterxml.jackson"
        
        packages(packageApi, packageModel, 
                "org.opennms.tmforum.${tmfSpecPackageName}.simulator.api.impl",
                "org.opennms.tmforum.rest",
                "org.opennms.tmforum.tmf650.api.rest");
        
        //register(JacksonObjectMapperProvider.class);

		//register(JacksonFeature.class);
        register(NewJacksonFeature.class);
		
        // this logs over jul. Use jul to slf4j bridge in classpath
        registerInstances(new LoggingFeature(new JulFacade(), Level.FINE, 
                LoggingFeature.Verbosity.PAYLOAD_ANY, 1000));

        configureSwagger();
    }

    // used to map jul logging into slf4j
    // see
    // https://stackoverflow.com/questions/4121722/how-to-make-jersey-to-use-slf4j-instead-of-jul
    private static class JulFacade extends java.util.logging.Logger {
        JulFacade() {
            super("Jersey", null);
            LOG.info("**************************** set up logging on jersey ");
        }

        @Override
        public void info(String msg) {
            LOG.info(msg);
        }
        
        @Override
        public void fine(String msg) {
            LOG.debug(msg);
        }
        
        @Override
        public void finest(String msg) {
            LOG.trace(msg);
        }
        
    }
    

    // swagger 1.5
    // see
    // https://stackoverflow.com/questions/40480131/how-to-use-swagger-with-resourceconfig-in-jersey
    private void configureSwagger() {
        this.register(ApiListingResource.class);
        this.register(SwaggerSerializers.class);
        BeanConfig config = new BeanConfig();
        config.setConfigId("spring-jaxrs-swagger");
        config.setTitle("Swagger Server");
        config.setVersion("1.0.0");
        // swagger is at http://localhost:8080/basePath/swagger.json
        config.setBasePath(basePath);
        // config.setResourcePackage("${package}.${tmfSpecPackageName}.swagger.api");
        config.setResourcePackage(packageApi+","+packageBaseApi);
        config.setPrettyPrint(true);
        config.setScan(true);

        // http://localhost:8084/project-web/rest/swagger/v1.0/swagger.json
        // http://localhost:8080/swagger/v1.0/swagger.json
    }
}
