# Java, Jetty and Cargo
# uses official jetty docker https://hub.docker.com/_/jetty
# VERSION 0.0.1
# MAINTAINER Craig Gallen "craig.gallen@solent.ac.uk"

FROM jetty:9.4.31-jdk11

RUN java -jar "$JETTY_HOME/start.jar" --create-startd --add-to-start=ssl,https

# Install Cargo
# ADD --chown=jetty:jetty https://repo1.maven.org/maven2/org/codehaus/cargo/cargo-jetty-7-and-onwards-deployer/1.8.0/cargo-jetty-7-and-onwards-deployer-1.8.0.war /var/lib/jetty/webapps/cargo-jetty-7-and-onwards-deployer-1.8.0.war

RUN wget -O /var/lib/jetty/webapps/cargo-jetty-7-and-onwards-deployer.war "https://repo1.maven.org/maven2/org/codehaus/cargo/cargo-jetty-7-and-onwards-deployer/1.8.0/cargo-jetty-7-and-onwards-deployer-1.8.0.war"
RUN unzip /var/lib/jetty/webapps/cargo-jetty-7-and-onwards-deployer.war -d /var/lib/jetty/webapps/cargo-jetty-7-and-onwards-deployer
RUN chown -R jetty:jetty /var/lib/jetty/webapps/cargo-jetty-7-and-onwards-deployer
RUN rm /var/lib/jetty/webapps/cargo-jetty-7-and-onwards-deployer.war 

COPY --chown=jetty:jetty config/cargo/web.xml /var/lib/jetty/webapps/cargo-jetty-7-and-onwards-deployer/WEB-INF/web.xml

COPY --chown=jetty:jetty config/openoss-realm.properties /usr/local/jetty/etc/openoss-realm.properties
COPY --chown=jetty:jetty config/jetty.xml /usr/local/jetty/etc/jetty.xml

#COPY --chown=jetty:jetty config/jetty.keystore /usr/local/jetty/etc/jetty.keystore


# Run Jetty

USER jetty
EXPOSE 8080
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["java","-jar","/usr/local/jetty/start.jar"]
