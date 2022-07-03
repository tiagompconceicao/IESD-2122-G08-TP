#
# Package stage
#
FROM openjdk:11-jre-slim
COPY /target/SerTPLMOPE-0.1.0-jar-with-dependencies.jar /usr/local/lib/TPLM.jar
EXPOSE 9002
ENTRYPOINT ["java","-jar","/usr/local/lib/TPLM.jar"]

