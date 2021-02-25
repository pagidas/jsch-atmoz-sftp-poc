#!/bin/bash

echo "Starting atmoz sftp server..."
docker-compose -f atmoz-sftp-setup.yml up -d --build
echo -e "Displaying running docker containers...\n"
docker ps -a