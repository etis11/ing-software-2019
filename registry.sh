#!/bin/bash
echo "Launching registry..."
rmiregistry \
  -J-Djava.rmi.server.logCalls=false \
  -J-Djava.rmi.server.useCodebaseOnly=false

