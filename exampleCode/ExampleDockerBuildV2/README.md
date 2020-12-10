# Spring docker packaging project

This example includes the ability to use a system environment variable to define the location of a properties
file which is read by spring.

This example also has a simple JMS message receiver and transmitter wired with spring

If system.properties is found on the class path it will be used to read properties into the application context

If the environment variable APPLICATION_PROPERTIES_FILE is set then the properties file at the location will be used

Thus if APPLICATION_PROPERTIES_FILE is given to docker as an environment variable it will define where in the docker images to look for 
the properties file. 
You then need to inject the properties file at that location

In the /service/pom.xml, the APPLICATION_PROPERTIES_FILE is given to the tests using the enviroment variable setting on the surefire plugin.

```
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
              
                <configuration>
                    <!-- you can use environment properties or systemproperties -->
                    <systemPropertyVariables>
                        <APPLICATION_PROPERTIES_FILE>${basedir}/src/test/resources/system.properties</APPLICATION_PROPERTIES_FILE>
                    </systemPropertyVariables>
             
                    <environmentVariables>
                        <APPLICATION_PROPERTIES_FILE>${basedir}/src/test/resources/system.properties</APPLICATION_PROPERTIES_FILE>
                    </environmentVariables>
                </configuration>
            </plugin>
```

Note that in this example surefire sets both a system property variable (which is a java JVM internal system property) and an environment property which is a property in the underlying operating system.
We will use the environment variables because these can be set in Docker.

The corresponding spring configuration is in appconfig-service.xml
This reads in the APPLICATION_ENVIRONMENT_FILE using:


```
    <!-- allows for ${} replacement in the spring xml configuration  -->
    <!-- either from from the system.properties file on the classpath   -->
    <!-- or APPLICATION_PROPERTIES_FILE environment in docker-->
    <!-- use local system.properties in tests-->
    <!-- use environment injected file in docker -->
    <!-- systemEnvironment or systemProperties can be used-->
    <context:property-placeholder
        location="classpath:system.properties,
                 file:#{systemEnvironment['APPLICATION_ENVIRONMENT_FILE']},
                 file:#{systemProperties['applicationPropertiesFile']}"
        ignore-unresolvable="false" ignore-resource-not-found="true" />

```

This example also uses a spring configuration which fires up an instance of activemq in each test.
The test application context is appconfig-service-test.xml

Each test starts this context before running using the header annotations shown below. 
Note that once the application context is loaded, the simpleJmsSender is autowired from the context to use in the test.

```
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appconfig-service-test.xml"})
public class JMSJUnitTest {

    final static Logger LOG = LogManager.getLogger(JMSJUnitTest.class);

    @Autowired
    SimpleJmsSender simpleJmsSender;

```

The appconfig-service-test.xml contains a configuration for starting an activemq instance before loading the main application config from classpath:appconfig-service.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:amq="http://activemq.apache.org/schema/core"
       xmlns:jms="http://www.springframework.org/schema/jms"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
http://activemq.apache.org/schema/core http://activemq.apache.org/schema/core/activemq-core-5.2.0.xsd
http://www.springframework.org/schema/context 
http://www.springframework.org/schema/context/spring-context-2.5.xsd
http://www.springframework.org/schema/jms 
http://www.springframework.org/schema/jms/spring-jms-2.5.xsd">

    <import resource="classpath:appconfig-service.xml" />
    
    <!--sets up activemq broker only for tests -->
    <!-- see http://activemq.apache.org/spring-support.html -->
    <amq:broker useJmx="false" persistent="false">
        <amq:transportConnectors>
            <amq:transportConnector uri="tcp://localhost:0" />
        </amq:transportConnectors>
    </amq:broker>
    
</beans>
```



