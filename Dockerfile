#Based on tutorial: https://docs.docker.com/develop/develop-images/multistage-build/
#Build the project for Java 17
FROM openjdk:17-alpine AS builder

#Get APK up to date
RUN apk update && apk upgrade

#Install Maven
RUN apk add maven

#Git
RUN apk add git
RUN mkdir /trams-operations
RUN git clone https://github.com/daveajlee/trams-operations.git /trams-operations

#Build
RUN mvn -f /trams-operations clean install

# Build release image
FROM openjdk:17-alpine

#Debug

#Copy result
WORKDIR /Executables
COPY --from=builder /trams-operations/target .

RUN chown -R appuser:appuser /Executables
RUN chmod 755 /Executables

#Add user and group for running as unprivileged user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

#Define how to start
ENTRYPOINT ["java", "-jar", "trams-operations.jar"]