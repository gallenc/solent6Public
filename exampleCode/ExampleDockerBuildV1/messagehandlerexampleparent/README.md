## example message handler microservice

This service is provided to help build a JMS service which runs in a docker container

## Packaging

The parent project contains the following modules

### messagehandlerexampleparent

parent project with default and packageAsDocker profile. 

### messagehandler-service

Contains the example code which runs in a spring application context

### messagehandler-assembly

Contains a main class and a one jar maven project to package the app

The assembly uses one-jar to package all of the dependencies as a single executable jar file
The packaged app can be run from messagehandlerexampleparent using 
```
 java -jar .\assembly\target\messagehandler-assembly-onejar.jar
```

### messagehandler-docker

packages the project as a docker file. 
This can only be run on a linux machine with docker installed.
To build the docker image from the parent project use
```
 mvn clean install -P packageAsDocker
```

once you have packaged the docker image you can run it directly using 

```
docker run messagehandler:latest
```

or  as a deamon

```
docker run -d  messagehandler:latest
```

to see the container daemon image name use
```
docker ps
```
output will be like
```
CONTAINER ID        IMAGE                   COMMAND                  CREATED             STATUS              PORTS                NAMES
e86a5ced4ec1        messagehandler:latest   "/deployments/run-ja…"   3 minutes ago       Up 3 minutes        8778/tcp, 9779/tcp   pedantic_archimedes

```

to see logs in running container
```
docker logs <container id>

docker logs --follow  <container id>
```

to stop the daemon container

```
docker stop  <container id>
```




