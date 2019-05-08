#!/bin/sh

url=$(echo ${END_POINT_STATUS_CONFIG})
while :
do
    echo "Check connection: $url"
    if [ $(wget --server-response --spider --quiet $url 2>&1 | awk 'NR==1{print $2}') -eq 200  ]
    then
        echo "Initializing application"
        exec java -jar ./app.jar
        echo "finalized initializing application"
        break
    fi    
    echo "No connection $url"
    sleep 3
done
