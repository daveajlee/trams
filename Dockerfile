#Build the project for Java 11
FROM openjdk:12-alpine AS builder

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
FROM openjdk:12-alpine

#Debug

#Copy result
WORKDIR /Executables
COPY --from=builder /trams-operations/target .

#Add user and group for running as unprivileged user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

#Define how to start
ENTRYPOINT ["java", "-jar", "trams-operations.jar"]

#FROM openjdk:11
#COPY target/trams-operations.jar /trams-operations.jar
#ENTRYPOINT [ "sh", "-c", "java -jar /trams-operations.jar" ]