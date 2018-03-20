#!/bin/bash
set -xv

#./clean.sh

mvn clean install -Dserver=jetty9x -Darquillian=arq-jetty-embedded
#-Parq-jetty-embedded,jetty9x

exit 0
