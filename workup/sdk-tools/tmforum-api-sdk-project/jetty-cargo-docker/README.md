# jetty-cargo-docker

This project creates a service runner using docker for use on centos docker installations.

## Dockerfile
builds a docker image with official jetty image and cargo war file installed

see https://codehaus-cargo.github.io/cargo/Jetty+Remote+Deployer.html

The openoss-realm.properties file contains the username and password for cargo. 
These names must match the settings in ./simulator-war/pom.xml 

NOTE this needs changed to a more secure properties based mechanism

```
<cargo.remote.username>someusername</cargo.remote.username>
<cargo.remote.password>somepassword</cargo.remote.password>
```

## Docker compose
does a build of the docker file and spins up a jetty image with cargo running

## systemd service template

see https://community.hetzner.com/tutorials/docker-compose-as-systemd-service

also systemd service templates https://www.freedesktop.org/software/systemd/man/systemd.service.html#Service%20Templates

Template unit files allow systemd to address multiple units from a single configuration file. You can call a systemd template unit file using a special format to use this feature:

```
<service_name>@<argument>.service
```

The argument is a bit of text (string) passed to systemd to use in the unit file as the variable %i  

place the docker-compose@.service systemd service template at 

```
/etc/systemd/system/docker-compose@.service
```

This sets up an environment where you can easily start different Docker Compose services as systemd services. For each additional service you just need to create a docker compose service directory

```
/etc/docker-compose/servicename
```
put in this directory at least the docker-compose.yml file (and whatever else you need for the service)

```
/etc/docker-compose/servicename/docker-compose.yml 
```

Start the service via 

```
systemctl start docker-compose@servicename
```

(Optional) Start the service on boot with 

```
systemctl enable docker-compose@servicename
```

## jetty docker compose example

In this case simply 

```
sudo cp ./docker-compose@.service /etc/systemd/system/
sudo mkdir /etc/docker-compose/
sudo cp -r jetty-cargo-docker /etc/docker-compose/

# this reloads the systemctl deamon to use the file
sudo systemctl daemon-reload
```

to run the jetty service

```
sudo systemctl start docker-compose@jetty-cargo-docker
```

to stop the jetty service

```
systemctl stop docker-compose@jetty-cargo-docker
```

## deploying to server with cargo
in the similator-war project you can use cargo to deploy to the remote server using system properties

```
mvn install -Pcargo-deploy -Dcargo.hostname=127.0.0.1 -Dcargo.servlet.port=8080 -Dcargo.remote.username=someusername \
-Dcargo.remote.password=somepassword

```


