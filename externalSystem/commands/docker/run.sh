DOCKER_USER_NAME=`cat ./values/docker_user_name.txt`
DOCKER_IMAGE_NAME=`cat ./values/docker_image_name.txt`
DOCKER_VERSION=`cat ./values/docker_version.txt`
DOCKER_IMAGE_PORT=`cat ./values/docker_image_port.txt`

docker run --name ${DOCKER_IMAGE_NAME}_con \
    -e AWS_ACCESS_KEY=$AWS_ACCESS_KEY \
    -e AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY \
    -e AWS_BUCKET_NAME=sinsung6722-toygroupchat \
    -e AWS_REGION_CODE=ap-northeast-2 \
    -p $DOCKER_IMAGE_PORT:8086 $DOCKER_USER_NAME/$DOCKER_IMAGE_NAME:$DOCKER_VERSION