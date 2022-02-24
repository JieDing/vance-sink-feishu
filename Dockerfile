FROM openjdk:8-slim
WORKDIR /vance
COPY sink-feishu-1.0-SNAPSHOT-jar-with-dependencies.jar /vance
CMD ["java", "-jar", "./sink-feishu-1.0-SNAPSHOT-jar-with-dependencies.jar"]
