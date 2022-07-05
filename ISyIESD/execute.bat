::Maven setup
::Run in terminal: mvn clean install

::Docker setup
ECHO Building Docker images
docker build -t registry .\CesRegistry\SerRegistry\SerRegistryOPE\
docker build -t tm .\CesTC\SerTM\SerTMOPE\
docker build -t tplm .\CesTC\SerTPLM\SerTPLMOPE\
docker build -t vector .\CesVector\SerVector\SerVectorOPE\

ECHO Tagging Docker images
docker tag registry:latest tiago47611/registry:latest
docker tag tm:latest tiago47611/tm:latest
docker tag tplm:latest tiago47611/tplm:latest
docker tag vector:latest tiago47611/vector:latest

ECHO Pushing Docker images into remote repo
docker push tiago47611/registry:latest
docker push tiago47611/tm:latest
docker push tiago47611/tplm:latest
docker push tiago47611/vector:latest

::Kubernetes setup
ECHO Deploying system
kubectl apply -f ./Deployment.yaml
