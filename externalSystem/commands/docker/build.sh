DOCKER_USER_NAME=`cat ./values/docker_user_name.txt`
DOCKER_IMAGE_NAME=`cat ./values/docker_image_name.txt`
DOCKER_VERSION=`cat ./values/docker_version.txt`

mvn package -B -D maven.test.skip=true
docker build -t $DOCKER_USER_NAME/$DOCKER_IMAGE_NAME:$DOCKER_VERSION .