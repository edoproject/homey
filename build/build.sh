#!/bin/bash
docker run -it --rm --name loco_core_build -v $WORKSPACE/core:/loco/core -w /loco/core maven:3.3-jdk-8 mvn package