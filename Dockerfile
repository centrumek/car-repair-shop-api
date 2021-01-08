FROM openjdk:15

ARG JAR_FILE
ADD ${JAR_FILE} service.jar

ENTRYPOINT ["java"]
