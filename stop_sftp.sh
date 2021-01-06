#!/bin/bash

echo "Stopping atmoz sftp server..."
docker-compose -f atmoz-sftp-setup.yml down
echo -e "Displaying running docker containers...\n"
docker ps -a