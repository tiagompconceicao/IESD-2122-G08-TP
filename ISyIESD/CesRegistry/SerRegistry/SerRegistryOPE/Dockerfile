#
# Package stage
#
FROM openjdk:11-jre-slim
COPY /target/SerRegistryOPE-0.1.0-jar-with-dependencies.jar /usr/local/lib/registry.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/registry.jar"]

