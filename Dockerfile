FROM maven:3.8.1-openjdk-17-slim AS build
RUN mkdir /home/cbook-app
copy . /home/cbook-app
RUN cd /home/cbook-app && mvn package -DskipTests
RUN cp /home/cbook-app/target/*.jar cbook-app.jar
ENTRYPOINT [ "java","-jar","-Dspring.profiles.active=prod","cbook-app.jar"]
EXPOSE 8080