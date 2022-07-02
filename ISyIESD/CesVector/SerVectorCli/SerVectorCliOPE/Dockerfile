#
# Package stage
#
FROM openjdk:11-jre-slim
COPY /target/SerVectorCliOPE-0.1.0-jar-with-dependencies.jar /usr/local/lib/vectorcli.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/vectorcli.jar"]

