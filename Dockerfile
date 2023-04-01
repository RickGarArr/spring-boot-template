FROM openjdk:8

LABEL maintainer="Ricardo Garcia - ricardogarciarreola@gmail.com"

ENV PATH_PROJECT=/var/gradle-submission
ENV PATH_JAR=${PATH_PROJECT}/own-0.0.1-SNAPSHOT.jar

COPY build/libs ${PATH_PROJECT}

WORKDIR ${PATH_PROJECT}

EXPOSE 8081

CMD ["sh", "-c", "java -jar ${PATH_JAR}"]
