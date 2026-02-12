## Stage 1 : Build with Maven builder image with GraalVM
FROM quay.io/quarkus/ubi-quarkus-mandrel-builder-image:jdk-25 AS build
COPY --chown=quarkus:quarkus mvnw /code/mvnw
COPY --chown=quarkus:quarkus .mvn /code/.mvn
COPY --chown=quarkus:quarkus pom.xml /code/
USER quarkus
WORKDIR /code
RUN chmod +x mvnw
RUN ./mvnw -B org.apache.maven.plugins:maven-dependency-plugin:3.6.1:go-offline
COPY src /code/src
RUN ./mvnw package -Dnative -DskipTests

## Stage 2 : Create the docker container
FROM quay.io/quarkus/quarkus-micro-image:2.0
WORKDIR /work/
COPY --from=build /code/target/*-runner /work/application

RUN chmod 775 /work /work/application \
  && chown -R 1001 /work \
  && chmod -R "g+rwX" /work \
  && chown -R 1001:root /work

EXPOSE 8090
USER 1001

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]