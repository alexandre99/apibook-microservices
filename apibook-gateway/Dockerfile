FROM openjdk:8-jdk-alpine
LABEL "alexanddy99@gmail.com"="Alexandre Teixeira"

ARG APPLICATION_NAME
ARG NUMBER_PORT

ENV PORT=${NUMBER_PORT}
ENV CONFIG_SERVER_URL="http://configServer:8888"
ENV DISCOVERY_SERVER_URL="http://discoveryServer:8761/eureka"

ADD target/${APPLICATION_NAME} app.jar
ADD wait /wait

RUN chmod +x /wait
CMD /wait && java -jar ./app.jar

EXPOSE ${NUMBER_PORT}