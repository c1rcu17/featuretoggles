FROM openjdk:8-jdk-alpine AS builder

COPY . /ft

WORKDIR /ft

RUN ./gradlew bootJar

FROM openjdk:8-jre-alpine

COPY --from=builder /ft/build/libs /ft

WORKDIR /ft

CMD [ "java", "-jar", "./FTWebAPI-1.0.0.jar" ]
