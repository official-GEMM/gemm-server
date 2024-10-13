#!/bin/bash

sudo docker-compose down
docker_image_ids = $(sudo docker images -q)
sudo docker rmi -f $docker_images_id

sudo docker-compose up -d