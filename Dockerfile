FROM openjdk:11.0-jdk
MAINTAINER course
COPY build/libs/course.jar course.jar
ENTRYPOINT ["java", "-jar", "/course.jar"]
