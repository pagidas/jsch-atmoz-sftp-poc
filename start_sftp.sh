#!/bin/bash

echo "Starting atmoz sftp server..."
docker-compose -f atmoz-sftp-setup.yml up -d
echo -e "Displaying running docker containers...\n"
docker ps -a