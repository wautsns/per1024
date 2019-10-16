FROM sidereal/jre8:latest
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /app/app.jar
WORKDIR /app/
EXPOSE 8888
ENTRYPOINT ["java","-jar","./app.jar"]