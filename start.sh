#!/bin/bash

echo Config Server: BUILDING
cd apibook-cloud-config-server
mvn clean package -Dmaven.test.skip=true
cd ..
clear

echo Config Server: BUILD DONE
echo Admin Server: BUILDING
cd apibook-admin-server
mvn clean package -Dmaven.test.skip=true
cd ..
clear

echo Config Server: BUILD DONE
echo Admin Server: BUILD DONE
echo Discovery Server: BUILDING
cd apibook-server-discovery
mvn clean package -Dmaven.test.skip=true
cd ..
clear

echo Config Server: BUILD DONE
echo Admin Server: BUILD DONE
echo Discovery Server: BUILD DONE
echo Gateway Server: BUILDING
cd apibook-gateway
mvn clean package -Dmaven.test.skip=true
cd ..
clear

echo Config Server: BUILD DONE
echo Admin Server: BUILD DONE
echo Discovery Server: BUILD DONE
echo Gateway Server: BUILD DONE
echo Auth Server: BUILDING
cd auth-service
mvn clean package -Dmaven.test.skip=true
cd ..
clear

echo Config Server: BUILD DONE
echo Admin Server: BUILD DONE
echo Discovery Server: BUILD DONE
echo Gateway Server: BUILD DONE
echo Auth Server: BUILD DONE
echo Author Server: BUILDING
cd author-service
mvn clean package -Dmaven.test.skip=true
cd ..
clear

echo Config Server: BUILD DONE
echo Admin Server: BUILD DONE
echo Discovery Server: BUILD DONE
echo Gateway Server: BUILD DONE
echo Auth Server: BUILD DONE
echo Author Server: BUILD DONE
echo Book Server: BUILDING
cd book-service
mvn clean package -Dmaven.test.skip=true
cd ..
clear

echo Config Server: BUILD DONE
echo Admin Server: BUILD DONE
echo Discovery Server: BUILD DONE
echo Gateway Server: BUILD DONE
echo Auth Server: BUILD DONE
echo Author Server: BUILD DONE
echo Book Server: BUILD DONE
echo ---
echo Starting Application...
docker-compose up -d --build