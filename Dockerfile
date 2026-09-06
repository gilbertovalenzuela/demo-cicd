#Define base docker immage
FROM amazoncorretto:17-alpine-jdk

EXPOSE 8080

COPY ./target/demo-cicd-app.jar /usr/app/
WORKDIR /usr/app

CMD ["java", "-jar", "demo-cicd-app.jar"]