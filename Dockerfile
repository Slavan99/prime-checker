FROM openjdk:21
EXPOSE 8080
COPY target/*.jar primechecker-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","primechecker-1.0.0-SNAPSHOT.jar"]