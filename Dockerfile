#Based on tutorial: https://docs.docker.com/develop/develop-images/multistage-build/
#Build the project for Java 17
FROM openjdk:17-alpine AS builder

#Get APK up to date
RUN apk update && apk upgrade

#Install Maven
RUN apk add maven

#Git
RUN apk add git
RUN mkdir /trams-business
RUN git clone https://github.com/daveajlee/trams-business.git /trams-business

#Build
RUN mvn -f /trams-business clean install

# Build release image
FROM openjdk:17-alpine

#Debug

#Copy result
WORKDIR /Executables
COPY --from=builder /trams-business/target .

#Add user and group for running as unprivileged user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

#Define how to start
ENTRYPOINT ["java", "-jar", "trams-business.jar"]