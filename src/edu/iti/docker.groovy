package edu.iti;

def build(imageName, imageTag){
        sh "docker build -t ${imageName}:${imageTag}  ."
}

def login(username, password){
        sh "docker login --username ${username} --password ${password}"
}

def push(imageName, imageTag){
        sh "docker push ${imageName}:${imageTag}"
}
