# Prerequisites

In this section we work to configure our local machine so we can build and run all lab examples.

## Overview
The main lab prerequisite is having [Docker](https://www.docker.com/) installed and running. 
We are also using [Gradle](https://gradle.org/) and [Spring Boot](https://gradle.org/)

All the lab examples are built to work with:
* OS-X
* Ubuntu

If you DO NOT have one of those OSs installed we will use the following section to configure WSL or an Ubuntu virtual machine and use it for the lab.

# 1. Install WSL
  * Follow the instructions from the official Microsoft documentation: https://docs.microsoft.com/en-us/windows/wsl/install
  * Install Java 21 in WSL
  * Install Docker in WSL

## Open WSL in Visual Studio Code
  * Install the Remote - WSL extension in Visual Studio Code
  * Open the WSL terminal in Visual Studio Code
  * Navigate to your Gradle project folder:
    * Locally:
      * ```chmod +x gradlew``` to make the gradlew script executable
      * ```./gradlew build``` to build the project
      * ```./gradlew bootRun``` to run the project
    * Follow the steps in the README.md file to build and run the project with Docker


# 2. Install Virtual Box

* Download Virtual Box: https://www.virtualbox.org/wiki/Downloads
* Download Ubuntu Virtual Box image: https://www.osboxes.org/ubuntu/#ubuntu-22-04-jammy-vbox
* Create a new virtual machine using the downloaded image: https://www.leawo.org/entips/vdi-file-what-is-it-how-to-open-it-1400.html#part2

## Configure your Ubuntu VM
Note: Default username is `osboxes.org` and password is `osboxes.org`

Install required software:
* [Visual Studio Code](https://code.visualstudio.com/docs/setup/linux)
* Docker: 
    * Install: https://docs.docker.com/engine/install/ubuntu/
    * Add your user in docker users group: 
      ```
        sudo groupadd docker
        sudo usermod -aG docker $USER
        newgrp docker
      ```
    * Add at the end of your ~/.bashrc file the following lines line so you can build from any shell: 
    ```# Switch groups, but only if necessary
    if [[ `id -gn` != "docker" ]]
    then
      newgrp docker
      exit
    fi
    ```

Install Docker Compose:
* https://docs.docker.com/compose/install/

Install IntelliJ Community Edition: 
* Download and extract: https://www.jetbrains.com/help/idea/installation-guide.html#standalone 
* Add in your ~/.bashrc file an alias to help you open IntelliJ easier:
  * IntelliJ would be located on a path similar to `/home/osboxes/Downloads/ideaIC-2021.2.3/idea-IC-212.5457.46/bin/idea.sh`
  * Adding an alias is done by adding in your ~/.bashrc file the path like this: ```alias IntelliJ=/home/osboxes/Downloads/ideaIC-2021.2.3/idea-IC-212.5457.46/bin/idea.sh```
* Source the new profile: `source ~/.profile`

Install OpenJDK 21:  
* `sudo apt install openjdk-21-jdk`

Install Make: 
* `sudo apt install make`
