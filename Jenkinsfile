pipeline {
    agent any

    environment {
        // The only two values you replace with your own
        IMAGE_NAME  = 'DOCKERHUBUSER/demo-cicd-app'
        DEPLOY_HOST = 'PUBLIC_IP'           // public IP of your EC2 instance

        IMAGE_TAG   = '1.0'
        DEPLOY_USER = 'ubuntu'
        APP_NAME    = 'demo-app'
        APP_PORT    = '8081'
    }

    stages {

        stage('[CI] Testing locally (UT+IT)') {
            steps {
                sh "./mvnw clean test"
            }
        }

        stage('[CI] Build App - Create Package') {
            steps {
                sh "./mvnw package -DskipTests"
            }
        }

        stage('[CI] Build Docker image') {
            steps {
                // The image is stored in the host Docker daemon, not in the workspace.
                // That is why the next stage can still find it.
                sh 'docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .'
            }
        }

        stage('[CD] Push image to Docker Hub (Delivery)') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                    // Password goes through stdin so it never shows up in the Jenkins console
                    sh 'echo $PASS | docker login -u $USER --password-stdin'

                    sh 'docker push ${IMAGE_NAME}:${IMAGE_TAG}'

                    // Always log out, otherwise the next run may fail to authenticate
                    sh 'docker logout'
                }
            }
        }

        stage('[CD] Run App on server (Deploy)') {
            steps {
                sshagent(['deploy-server-key']) {
                    sh """
                        ssh -o StrictHostKeyChecking=no ${DEPLOY_USER}@${DEPLOY_HOST} '
                            docker pull ${IMAGE_NAME}:${IMAGE_TAG} &&
                            docker rm -f ${APP_NAME} || true &&
                            docker run -d --name ${APP_NAME} -p ${APP_PORT}:8080 --restart unless-stopped ${IMAGE_NAME}:${IMAGE_TAG}
                        '
                    """
                }
            }
        }

        stage('[Checks] Wait for app to be ready') {
            steps {
                script {
                    timeout(time: 60, unit: 'SECONDS') {
                        waitUntil {
                            def status = sh(
                                script: "curl -s -o /dev/null -w '%{http_code}' http://${DEPLOY_HOST}:${APP_PORT}/health || true",
                                returnStdout: true
                            ).trim()
                            return status == '200'
                        }
                    }
                }
            }
        }

        stage('[Checks] API tests') {
            steps {
                sh 'newman run postman/demo-cicd.postman_collection.json --env-var "base_url=http://${DEPLOY_HOST}:${APP_PORT}"'
            }
        }
    }
}