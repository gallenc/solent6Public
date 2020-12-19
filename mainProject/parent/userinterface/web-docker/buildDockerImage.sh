#!/bin/bash
# A sample Bash script, to build docker file
echo Building docker image
docker build -t solent-traffic/userinterface:latest .
