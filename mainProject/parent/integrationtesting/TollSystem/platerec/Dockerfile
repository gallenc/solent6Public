FROM fabric8/java-centos-openjdk11-jdk

RUN curl https://jfrogmavenrepo1solentdevops.uksouth.cloudapp.azure.com/artifactory/solentdevops1/solent/ac/uk/devops/traffic/platerecognition-assembly/0.1-SNAPSHOT/platerecognition-assembly-0.1-20201221.103241-17-onejar.jar --output /deployments/onejar.jar

ENV APPLICATION_ENVIRONMENT_FILE="/deployments/system.properties"