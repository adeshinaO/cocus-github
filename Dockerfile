FROM adoptopenjdk/openjdk11:latest
MAINTAINER Adeshina Ogunmodede
COPY /target/githubclient-1.0.0.jar githubclient-1.0.0.jar
ENTRYPOINT ["java", "-jar", "/githubclient-1.0.0.jar"]
