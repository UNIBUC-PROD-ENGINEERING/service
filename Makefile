SERVICE_NAME=hello
BUILDER_TAG=$(SERVICE_NAME)-build
IMAGE_TAG=$(SERVICE_NAME)-img
IMAGE_VERSION=latest
BUILDER_CONTAINER_NAME=$(SERVICE_NAME)-builder-container
default: build

clean:
	# Remove the build folder
	rm -rf build
	
	# Remove the builder image and service image
	docker rmi $(BUILDER_TAG) || true
	docker rmi $(IMAGE_TAG):$(IMAGE_VERSION) || true
	
	# Remove dangling images (untagged images)
	docker image prune -f

build: clean
	# A separate image for build allows the process to avoid dependencies with the build machine.
	docker build -t $(BUILDER_TAG) -f Dockerfile.build .
	
	# Runs the image generated in the above step to create the actual deployable artifact (i.e. jar file).
	# Use either this or the next command (that uses local maven cache)
	# docker run -t -v `pwd`:/build $(BUILDER_TAG)
	
	# In the above build step, maven starts of by downloading all dependencies and
	# then proceeds to build the artifact. This can be time consuming. To reuse mvn cache
	# please use the following command instead. This will ensure, a docker volume is
	# responsible for incrementally saving the dependencies and avoids the time taken to download
	# all dependencies every time.
	docker run --name $(BUILDER_CONTAINER_NAME) \
		-v "m2:/root/.m2" \
		-v "`pwd`:/build" \
		$(BUILDER_TAG)
	
	# Stop and remove the builder container
	docker stop $(BUILDER_CONTAINER_NAME) || true
	docker rm $(BUILDER_CONTAINER_NAME) || true
	
	# Builds the docker image for running the service.
	docker build -t $(IMAGE_TAG):$(IMAGE_VERSION) .
	echo "Success"

post-deploy-build:
	echo "Nothing is defined in post-deploy-build step"
