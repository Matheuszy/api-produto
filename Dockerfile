FROM alpine/java:21-jdk

COPY application/target/api-produto.application-0.0.1-SNAPSHOT.jar api-produto.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev, infra_dev", "/api-produto.jar"]


