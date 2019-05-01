FROM openjdk:8-jdk-alpine
LABEL "alexanddy99@gmail.com"="Alexandre Teixeira"
ENV PORT=8888
ADD target/apibook-config-server.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

EXPOSE ${PORT}

