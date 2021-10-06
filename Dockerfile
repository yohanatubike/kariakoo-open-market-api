FROM openjdk:11-oracle
#FROM openjdk:8-jre-alpine
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
VOLUME /tmp
COPY target/*.jar app.jar
#COPY shop.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar", "--spring.profiles.active=docker"]
