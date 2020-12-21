FROM openjdk:8-jdk-alpine
MAINTAINER Aaron Jenkins

ARG JAR_FILE=out/artifacts/chargereconciler_service_jar/*.jar
COPY ${JAR_FILE} /chargereconciler-service.jar
ENTRYPOINT ["java","-jar","/chargereconciler-service.jar"]