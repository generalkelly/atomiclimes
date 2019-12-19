#!/bin/bash
while ! exec 6<>/dev/tcp/mysql/3306; do
    echo "Trying to connect to MySQL at mysql:3306..."
    sleep 10
done
echo ">> connected to MySQL database! <<"
#STARTING APPLICATION
echo "Starting Application in Docker..."
java -jar app.jar