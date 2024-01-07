DOCKER_IMAGE_NAME=`cat ./values/docker_image_name.txt`

docker container stop ${DOCKER_IMAGE_NAME}_con
docker container rm ${DOCKER_IMAGE_NAME}_con