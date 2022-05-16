pipeline {
    agent any

    environment {
        DOCKER = credentials("DOCKER")
        GITHUB = credentials("GITHUB")
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

                    env.MAJOR_VERSION = sh([
                        script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 1',
                        returnStdout: true]
                    ).trim()
                    env.MINOR_VERSION = sh([
                        script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 2',
                        returnStdout: true]
                    ).trim()
                    env.PATCH_VERSION = sh([
                        script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 3',
                        returnStdout: true]
                    ).trim()
                    env.IMAGE_TAG = "${env.MAJOR_VERSION}.\$((${env.MINOR_VERSION} + 1)).${env.PATCH_VERSION}"
                }

                sh "docker build -t $DOCKER_USR/slots-img:${env.IMAGE_TAG} ."
                sh "git tag ${env.IMAGE_TAG}"
                sh "git push https://$GITHUB@github.com/pLuck-sTudios/slot-machine.git ${env.IMAGE_TAG}"
            }
        }

        stage('Spawn service') {
            steps {
                script {
                    IMAGE_TAG = ${env.IMAGE_TAG} docker compose up -d slots
                }
            }
        }

        stage('Test E2E') {
            steps {
                sh './gradlew testE2E'
            }
        }
    }
}
