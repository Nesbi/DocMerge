# DocMerge
Simple application to visually merge PDF documents

## Run Requirements
* Java 8 or higher

## Installation
Download the newest version from [releases](https://github.com/NesbiDevelopment/DocMerge/releases) 
### Install Java Runtime
#### Ubuntu, Debian, Mint
```
apt-get install openjdk-8-jre
```

#### Solus
```
eopkg install openjdk-8
```

#### Fedora
```
dnf install java-1.8.0-openjdk
```

#### Windows
Download/Install Java JRE 8 (or higher) from [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

## Installation from Source
(You can skip this step if you have installed by downloading from [releases](https://github.com/NesbiDevelopment/DocMerge/releases) )
### Linux
Build from source with Maven
#### Packages (examples)
##### Ubuntu, Debian, Mint
```
apt-get install openjdk-8-jdk
apt-get install openjfx
apt-get install maven
```

##### Solus
```
eopkg install openjdk-8
eopkg install openjfx-8
eopkg install apache-maven
```

##### Fedora
```
dnf install java-1.8.0-openjdk
dnf install java-1.8.0-openjfx
dnf install maven
```

#### Clone repository
```
git clone https://github.com/NesbiDevelopment/DocMerge.git
```

#### Compile
Open the repository folder and run
```
mvn clean install
```

#### Copy .jar
DocMerge is now successfully installed and can be copied/moved from `<GitFolder>/DocMerge/target/` to a desired location.

### Windows
It is recommended to use an IDE to compile from source on Windows (e.g. eclipse)

#### Eclipse
Download the [DocMerge source code from github](https://github.com/NesbiDevelopment/DocMerge/archive/master.zip),.
After that download and install the Java JDK 8 (or higher) from [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html) and Eclipse EE from [the official eclipse webpage](https://www.eclipse.org/downloads/eclipse-packages/).
Open Eclipse and import the DocMerge source code as Maven Project.

Right click the project and select Run -> Maven install

#### Copy .jar
After DocMerge is successfully installed, it can be copied/moved from `<GitFolder>/DocMerge/target/` to a desired location.

## Run
DocMerge is an executable .jar file that can be executed in most desktop environments via doubleclick.
It can also be executed from a Linux terminal by typing `java -jar docmerge.jar`

## Run globally in terminal
DocMerge can be executed globally in a linux terminal by creating a bash script called 'docmerge' with the content:
```
#!/bin/bash
java -jar <installation-location>/docmerge.jar
```

The file has to be placed/linked to the direction `/usr/bin` or been added to the run environment. 
