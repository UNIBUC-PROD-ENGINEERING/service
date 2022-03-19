pipeline {
    environment {
        DOCKER+PASSWORD = credentials("")
    }

    stages {
        stage('Build & Test') {
            steps {
                sh './gradlew clean build'
            }
        }
}
