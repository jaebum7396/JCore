FROM openjdk:17
ARG WAR_FILE=./build/libs/j-core.war
COPY ${WAR_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]