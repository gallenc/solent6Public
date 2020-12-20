## build file for tomcat docker images

you need to build the parent project on a linux machine with docker installed using the profile
```
mvn clean install -P packageAsDocker

```

To run in docker-compose 

set the system.properties file to point at your broker

```
cd web-docker
docker-compose up -d
```


Main application is at http://localhost:8080/project-web/


activemq admin is at http://localhost:8161/
(user admin password admin)





