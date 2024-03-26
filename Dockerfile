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

EXPOSE 80
CMD [ "java","-jar", "application.jar"]
