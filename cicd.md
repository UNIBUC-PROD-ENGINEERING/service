## CI with Jenkins

### Install Jenkins
- [Install Jenkins](https://www.jenkins.io/doc/pipeline/tour/getting-started/) by following the steps from the link provided
- Start Jenkins on port 8083
- Login to Jenkins and go to `Dashboard` -> `Manage Jenkins` -> `Manage Plugins` and search for `Git integration`. Install it and restart Jenkins
- Go to `Dashboard`. Select `New item` and add a new folder.

### Add a job that will build the image
- In the new folder hit `New item` again and add a `Freestyle project`.
- Configure the new job to build git `main` branch.
- Run the job

#### Adding image build with a different version
- Go to job and hit `Configure`
- Add the section below under `Build -> Execute Shell`
```bash
export MAJOR_VERSION=`git tag | cut -d . -f 1`
export MINOR_VERSION=`git tag | cut -d . -f 2`
export PATCH_VERSION=`git tag | cut -d . -f 3`
## Increment minor version in order to create a new one
export NEW_MINOR_VERSION="$((MINOR_VERSION + 1))"
export IMAGE_VERSION=$MAJOR_VERSION.$NEW_MINOR_VERSION.$PATCH_VERSION
## Build a new image with the new version tag
docker build -t hello-img:$IMAGE_VERSION .
```

#### Publishing image to public docker hub
- Add the following two lines at the end of the shell script
```bash
# Having the password in clear here is not a good practice. It should be added as a secret in Jenkins and fetched from it.
docker login docker.io -u <username> -p <password>
## Publish the new image to a public repository
docker push alinaeftn/hello-img:$IMAGE_VERSION
```

### Helpful links
- Add webhooks on github repo for triggering [automated builds](https://devopscube.com/jenkins-build-trigger-github-pull-request/) at PR time
- Using a [Jenkinsfile](https://www.jenkins.io/doc/book/pipeline/jenkinsfile/) will also help fetching secrets from Jenkins credentials.
