#!/bin/sh

class_path=./target/classes/
working_directory=$(pwd)
codebase="file:///${working_directory}/target/classes/"

# Run Adrenalina application in server mode using local codebase
echo "Using LOCAL codebase..."
echo "Start Adrenalina application as a server..."
echo "(codebase: ${codebase})"
java \
	-Djava.rmi.server.useCodebaseOnly=false \
	-Djava.rmi.server.logCalls=false \
	-Djava.rmi.server.codebase=${codebase} \
	-cp ${class_path} \
	network.ServerLauncher

