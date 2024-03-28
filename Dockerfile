# Build Stage
FROM amazoncorretto:17-alpine3.15-jdk AS build

WORKDIR /build

RUN apk --no-cache add maven=3.8.3-r0 unzip

COPY . .
RUN mvn clean install -U -DskipTests

#/_____________________________________________________/

FROM amazoncorretto:17-alpine3.15-jdk AS runner
WORKDIR /app

COPY --from=build /build/contra-cheque/target/*.jar ./application.jar
COPY contra-cheque/src/main/docker/wait-for-db.sh /usr/local/bin/


RUN chmod +x /usr/local/bin/wait-for-db.sh
ENTRYPOINT ["wait-for-db.sh", "database:3306", "--", "java", "-jar", "*.jar"]
EXPOSE 80
