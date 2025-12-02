@Library("docker")_
pipeline {
    agent any
    tools {
        maven 'maven-3.5.2'
        jdk 'java-11'
    }
    stages {
        stage('Checkout SCM') {
          steps {
            echo 'Cloning Repo ....'
            git branch: 'main', url: 'http://github.com/Hassan-Eid-Hassan/cicd-lab2.git'
            echo 'Done Cloning Repo ....'
          }
        }
        stage('Build') {
          steps {
            echo 'Building....'
            sh 'java --version'
            sh 'mvn package install -DskipTests'
            echo 'Done Building....'
          }
        }
        stage('Test') {
            steps {
                echo 'Testing....'
                sh 'mvn test'
                echo 'Done Testing....'
            }
        }
        stage('Docker Build and Push') {
            steps {
                script {
                    def dockerClass = new edu.iti.docker()
                    //sh 'docker build -t ahmedibrahim1999/javaapp-sysadmin:v1 .'
                    dockerClass.build("ahmedibrahim1999/javaapp-sysadmin", "v1")
                    withCredentials([string(credentialsId: 'DOCKERHUB_PASSWORD', variable: 'DOCKERHUB_PASSWORD')]) {
                        //sh 'docker login --username ahmedibrahim1999 --password ${DOCKERHUB_PASSWORD}'
                        dockerClass.login("ahmedibrahim1999", "${DOCKERHUB_PASSWORD}")
                    }
                    sh 'docker pull alpine:latest'
                    sh 'docker tag alpine:latest ahmedibrahim1999/alpine:v${BUILD_NUMBER}'
                    //sh 'docker push ahmedibrahim1999/alpine:v${BUILD_NUMBER}'
                    dockerClass.push("ahmedibrahim1999/alpine", "v${BUILD_NUMBER}")
                // sh 'docker push ahmedibrahim1999/javaapp-sysadmin:v1' // this one is large in size and does not get uploaded due to upload speed issues
                }            
                    
            }
        }
        stage("Deploy") {
            steps {
                sh 'docker stop java-app 2>/dev/null || echo "" > /dev/null ; docker rm -f java-app 2>/dev/null || echo "" >/dev/null'
                sh 'docker run -d -p 8090:8090 --name java-app ahmedibrahim1999/javaapp-sysadmin:v1 || echo error $? while deploying java app'
            }
        }
    }
    post {
      always {
        echo 'This line is printed anyway'
      }
      success {
        echo 'this line is printed on pipeline success only'
      }
      failure {
        echo 'this line is printed on pipeline failure only'
      }
    }
}
