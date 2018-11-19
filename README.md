# How to run this application


## Pre-reqs

Java 1.8


## Gradle

Gradle is used here to build the project and manage the dependencies.  The `gradlew` command used here will
automatically download gradle for you so you shouldn't need to install
anything other than java.


### Building your project from the command line

To build the project on Linux or MacOS run the command `./gradlew build` in a shell terminal.  This will build the 
source code in `src/main/java`, run any tests in `src/test/java` and create an output
jar file in the `build/libs` folder.

To clean out any intermediate files run `./gradlew clean`.  This will
remove all files in the `build` folder.


### Running your application from the command line

You first must create a shadow jar file.  This is a file which contains your project code and all dependencies in a 
single jar file.  To build a shadow jar from your project run `./gradlew shadowJar`.  This will create a 
`concurrent-writer-shadow.jar` file in the `build/libs` directory.

You can then start your application by running the command
`java -jar ./build/libs/concurrent-writer-shadow.jar`

### Sample CURL

```
curl -X POST \
  http://localhost:4000/concurrentwriter \
  -d 003456786
  
```
