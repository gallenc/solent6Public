# Deployment Guide

You will need to modify some of the values in the application.properties file to suit live. these are as follows.
```
# ActiveMQ production broker
spring.activemq.broker-url=tcp://localhost:61616
spring.activemq.user=admin
spring.activemq.password=admin

receiverDestination=motorway_traffic.q
senderDestination=motorway_traffic.q
```


Run this command to create docker image

```
docker build -t chargereconciler-service .
```

Run this command to test its working
```
docker run chargereconciler-service:latest
```

If there are any issues with this step try running the below and repeating the previous commands
```
mvn package spring-boot:repackage
```
