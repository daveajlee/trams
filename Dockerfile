FROM openjdk:11
COPY target/trams-operations.jar /trams-operations.jar
ENTRYPOINT [ "sh", "-c", "java -jar /trams-operations.jar" ]