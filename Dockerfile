FROM openjdk:11-jdk
LABEL maintainer="omid.nazifi@gmail.com"
VOLUME /tmp
ARG JAR_FILE
ADD target/UserManager-1.0-SNAPSHOT.jar UserManager-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/UserManager-1.0-SNAPSHOT.jar"]
