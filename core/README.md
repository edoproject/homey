# CLI

## Prerequisities

### Ubuntu
   sudo apt install maven

## Build
   cd core
   mvn package

## Run
   java -jar target/loco-0.1.0.jar

# Docker

## Build
  export WORKSPACE=`pwd`
  docker run -it --rm --name loco_core_build -v $WORKSPACE/core:/loco/core -w /loco/core maven:3.3-jdk-8 mvn package

## Run
  cd loco/docker/compose/dev
  docker-compose up

# IntelliJ IDEA

File -> Open File or Project
loco/core/pom.xml as project
loco/core/src/main/java/net/edoproject/Application.java
Play the main method

# Check locally

## Browser check

[loco](http://localhost:8080)