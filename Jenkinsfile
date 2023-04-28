pipeline {
    agent any
    environment {
        DOCKER_PASSWORD = credentials('docker_password')
        DOCKER_USERNAME = credentials('docker_username')
        GITHUB_TOKEN = credentials("github_token")
    }

    stages {
        stage('Build & Test') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Tag image') {
              steps {
                script {
                   sh([script: 'git fetch --tag', returnStdout: true]).trim()
                   env.MAJOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 1', returnStdout: true]).trim()
                   env.MINOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 2', returnStdout: true]).trim()
                   env.PATCH_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 3', returnStdout: true]).trim()
                   env.IMAGE_TAG = "${env.MAJOR_VERSION}.\$((${env.MINOR_VERSION} + 1)).${env.PATCH_VERSION}"
                }
                sh "docker build -t ${DOCKER_USERNAME}/hello-img:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION} ."
                sh "docker login docker.io -u ${DOCKER_USERNAME}-p ${DOCKER_PASSWORD}"
                sh "docker push ${DOCKER_USERNAME}/hello-img:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION}"

                sh "git tag ${env.IMAGE_TAG}"
                sh "git push https://$GITHUB_TOKEN@github.com/TheBugFighters/service.git ${env.IMAGE_TAG}"

              }

        }

        stage('Start Docker') {
                    steps{
                    sh "IMAGE_TAG=${env.IMAGE_TAG} docker-compose up -d hello"
                    }
                }

        stage('E2E tests') {
             steps {
                 sh './gradlew test'
                 }
        }
    }
}
