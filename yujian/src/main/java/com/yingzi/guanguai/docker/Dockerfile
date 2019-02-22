FROM java:8
EXPOSE 8080
VOLUME /tmp
ADD guanguai-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=online"]