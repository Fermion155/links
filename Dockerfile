FROM gradle:8.10.1-jdk17

WORKDIR /home/gradle/project
COPY . .
RUN gradle build
RUN mkdir /app
RUN cp build/libs/*.jar /app/application.jar

CMD ["java","-jar","-Dspring.profiles.active=docker","/app/application.jar"]