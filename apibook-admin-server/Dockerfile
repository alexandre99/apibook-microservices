FROM openjdk:8-jdk-alpine
LABEL "alexanddy99@gmail.com"="Alexandre Teixeira"

ARG APPLICATION_NAME
ARG NUMBER_PORT

ENV PORT=${NUMBER_PORT}
ENV CONFIG_SERVER_URL="http://configServer:8888"
ENV END_POINT_STATUS_CONFIG="http://configServer:8888/actuator/health"

ADD target/${APPLICATION_NAME} app.jar
ADD checkIfCanRun.sh checkIfCanRun.sh

RUN chmod +x checkIfCanRun.sh

ENTRYPOINT ["./checkIfCanRun.sh"]

EXPOSE ${NUMBER_PORT}