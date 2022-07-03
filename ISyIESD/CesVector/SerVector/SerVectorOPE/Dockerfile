#
# Package stage
#
FROM openjdk:11-jre-slim
COPY /target/SerVectorOPE-0.1.0-jar-with-dependencies.jar /usr/local/lib/vector.jar
EXPOSE 9003
EXPOSE 9004
ENTRYPOINT ["java","-jar","/usr/local/lib/vector.jar"]

