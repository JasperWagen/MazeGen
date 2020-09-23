FROM openjdk:8-jre

WORKDIR app

COPY target/universal/mazesite-1.0-SNAPSHOT .

CMD ["/bin/sh", "-c", "/app/bin/mazesite"]
