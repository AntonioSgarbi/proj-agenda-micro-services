#!/bin/bash

buildProjects() {
    projectDirectories=("$@")

    for projectPath in ${projectDirectories[@]}; do
        mvn package -f "${projectPath}"
    done
}    

projects=("proj-eureka" "ms-agenda" "ms-funcionario" "ms-sala" "proj-gateway" "proj-config")

buildProjects "${projects[@]}"
