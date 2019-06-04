#!/bin/sh

class_path=./target/classes/
working_directory=$(pwd)
codebase="file:///${working_directory}/target/classes/"

# Run Adrenalina application in client mode
echo "Start Adrenalina application as a client..."
echo "(codebase: ${codebase})"
echo "USE OPTION 'c' WHEN THE APP START!"
java \
	-Djava.rmi.server.useCodebaseOnly=false \
	-Djava.rmi.server.logCalls=true \
	-Djava.rmi.server.codebase=${codebase} \
	-cp ${class_path} \
	adrenalina.Adrenalina
