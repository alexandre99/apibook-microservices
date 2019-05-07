FROM openjdk:8-jdk-alpine
LABEL "alexanddy99@gmail.com"="Alexandre Teixeira"

ARG APPLICATION_NAME
ARG NUMBER_PORT

ENV PORT=${NUMBER_PORT}
ENV CONFIG_SERVER_URL="http://configServer:8888"

ADD target/${APPLICATION_NAME} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

EXPOSE ${NUMBER_PORT}