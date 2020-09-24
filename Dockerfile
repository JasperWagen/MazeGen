FROM openjdk:8-jre

WORKDIR app

ADD ./mazesite-1.0-SNAPSHOT .

EXPOSE 80

CMD ["/bin/sh", "-c", "/app/bin/mazesite"]
