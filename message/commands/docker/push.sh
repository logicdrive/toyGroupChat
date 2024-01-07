DOCKER_USER_NAME=`cat ./values/docker_user_name.txt`
DOCKER_IMAGE_NAME=`cat ./values/docker_image_name.txt`
DOCKER_VERSION=`cat ./values/docker_version.txt`

docker push $DOCKER_USER_NAME/$DOCKER_IMAGE_NAME:$DOCKER_VERSION