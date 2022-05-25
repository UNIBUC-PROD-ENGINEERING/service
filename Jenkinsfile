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
                        returnStdout: true
                    ]).trim()
                    env.MINOR_VERSION = sh([
                        script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 2',
                        returnStdout: true
                    ]).trim()
                    env.PATCH_VERSION = sh([
                        script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 3',
                        returnStdout: true
                    ]).trim()
                    env.IMAGE_TAG = sh([
                        script: 'echo ' + "${env.MAJOR_VERSION}" + '.\$((' + "${env.MINOR_VERSION}" + ' + 1)).' + "${env.PATCH_VERSION}",
                        returnStdout: true
                    ]).trim()

                    sh([
                        script: "docker build -t " + '$DOCKER_USR' + "/slots-img:${env.IMAGE_TAG} .",
                        returnStdout: true
                    ]).trim()

                    sh([
                        script: "git tag ${env.IMAGE_TAG}",
                        returnStdout: true
                    ]).trim()

                    sh([
                        script: "git push https://" + '$GITHUB' + "@github.com/pLuck-sTudios/slot-machine.git ${env.IMAGE_TAG}",
                        returnStdout: true
                    ]).trim()
                }
            }
        }

        stage('Spawn service') {
            environment {
                IMAGE_TAG="${env.IMAGE_TAG}"
            }
            steps {
                sh 'kubectl apply -f kubernetes/slots.yaml'
            }
        }

        stage('Test E2E') {
            steps {
                sh './gradlew testE2E'
            }
        }
    }
}
