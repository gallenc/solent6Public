# Centos Linux with OpenJDK JRE
# startup script included
# https://hub.docker.com/r/fabric8/java-centos-openjdk11-jdk

FROM fabric8/java-centos-openjdk11-jdk
# copy fat jar into image
COPY /target/docker/imagecapture-assembly-0.1-SNAPSHOT-onejar.jar /deployments/onejar.jar
ENV APPLICATION_ENVIRONMENT_FILE="/deployments/properties/system.properties"