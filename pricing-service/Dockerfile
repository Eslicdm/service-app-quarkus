FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

COPY pricing-service/pom.xml ./pricing-service/pom.xml

RUN mvn -B -f pricing-service/pom.xml dependency:go-offline -DskipTests

COPY pricing-service/src ./pricing-service/src
RUN mvn -B -f pricing-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:25-jre
WORKDIR /app

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/pricing-service/target/quarkus-app/lib/ /app/lib/
COPY --from=build /app/pricing-service/target/quarkus-app/*.jar /app/
COPY --from=build /app/pricing-service/target/quarkus-app/app/ /app/app/
COPY --from=build /app/pricing-service/target/quarkus-app/quarkus/ /app/quarkus/

EXPOSE 8082

ENV QUARKUS_PROFILE=docker

ENTRYPOINT ["java", "-jar", "/app/quarkus-run.jar"]