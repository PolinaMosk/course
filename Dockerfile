FROM openjdk:11.0-jdk
MAINTAINER course
COPY build/libs/course.jar course.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/course.jar"]
