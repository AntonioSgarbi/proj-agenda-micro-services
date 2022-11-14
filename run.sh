#!/bin/bash

runProjects() {
    projectDirectories=("$@")

    for projectPath in ${projectDirectories[@]}; do
        java -jar "${projectPath}/target/${projectPath}-0.0.1-SNAPSHOT.jar" &
    done
}    

projects=("proj-eureka" "ms-agenda" "ms-funcionario" "ms-sala")

runProjects "${projects[@]}"
