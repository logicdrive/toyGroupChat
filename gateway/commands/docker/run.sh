DOCKER_USER_NAME=`cat ./values/docker_user_name.txt`
DOCKER_IMAGE_NAME=`cat ./values/docker_image_name.txt`
DOCKER_VERSION=`cat ./values/docker_version.txt`
DOCKER_IMAGE_PORT=`cat ./values/docker_image_port.txt`

docker run --name ${DOCKER_IMAGE_NAME}_con -e SPRING_PROFILES_ACTIVE=docker -p $DOCKER_IMAGE_PORT:8088 $DOCKER_USER_NAME/$DOCKER_IMAGE_NAME:$DOCKER_VERSION