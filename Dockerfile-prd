FROM eclipse-temurin:17-jdk-jammy as build

WORKDIR /build

COPY --chmod=0755 .mvn/mvnw.sh mvnw
COPY .mvn/ .mvn/
COPY pom.xml .
COPY ./src src/

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw clean install -DskipTests

FROM eclipse-temurin:17-jre-jammy as final

ARG UID=10001

RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser

USER appuser

USER root
RUN apt-get update && apt-get install -y netcat
USER appuser

COPY --from=build /build/target/*.jar /app/app.jar
COPY src/main/docker/wait-for-db.sh /usr/local/bin/

USER root
RUN chmod +x /usr/local/bin/wait-for-db.sh

USER appuser

EXPOSE 80

ENTRYPOINT ["java", "-jar", "/app/app.jar"]