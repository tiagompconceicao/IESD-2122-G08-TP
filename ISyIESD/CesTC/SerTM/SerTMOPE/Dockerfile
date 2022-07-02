#
# Package stage
#
FROM openjdk:11-jre-slim
COPY /target/SerTMOPE-0.1.0-jar-with-dependencies.jar /usr/local/lib/TM.jar
EXPOSE 9001
ENTRYPOINT ["java","-jar","/usr/local/lib/TM.jar"]

