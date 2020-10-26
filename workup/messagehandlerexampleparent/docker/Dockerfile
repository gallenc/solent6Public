# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine
# copy WAR into image
COPY /target/docker/messagehandler-assembly-0.1-SNAPSHOT-onejar.jar /onejar.jar 
# run application with this command line 
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/onejar.jar"]
