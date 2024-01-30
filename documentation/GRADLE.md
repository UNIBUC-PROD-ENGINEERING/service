# Introduction 

`Gradle` is a powerful build automation tool that is widely used in the software development industry. It is designed to handle the entire build lifecycle and provides a flexible and extensible platform for building and testing software projects. It uses a `domain-specific language` (DSL) based on `Groovy`. 

In order to get started with Gradle you can install it following the steps from the [official documentation](https://gradle.org/install/). 
To verify the installation, open a terminal and run 
```
gradle -v
```
This should display information about the installed Gradle version.

A typical Gradle project has a specific directory structure. Here are some key directories/files:
* `src` - source code for your project.
* `build` - output directory where compiled classes and other build artifacts are placed.
* `build.gradle` - main build script defining `tasks`, `dependencies`, and other `configurations`. It is one per project. In a multi-project build you’ll have several build.gradle files, one for each project.

# Build Scripts 

## build.gradle 

The` build.gradle` file is the heart of a Gradle project. It defines the project configuration, tasks, and dependencies. A basic `hello,world` would be:
```groovy
task hello {
    doLast {
        println "Hello, world!"
    }
}
```

Run this using 
```
gradle -q :hello
```
and you’ll see the message.  The `-q` flag stands for `quiet` and tells Gradle not to print some additional info.
You can also ask Gradle what tasks are available using the command 
```
gradle tasks –all
```

## settings.gradle 

The `settings.gradle` file is used to include or exclude subprojects in a multi-project build. If you build a single project this file is not needed (mandatory for multi-project build but optional for single project). See an example below:


```groovy
rootProject.name = 'MyProject'

include 'module1'
include 'module2'
```

## Tasks

`Tasks` are the building blocks of a Gradle build. It is like a collection of instruction that are performed together. 
Tasks are `objects`: every task you declare is actually a task object contained in the project. A task object has a `type`,  `properties` and `methods` just like any other object. You could even declare your own task type. By default, each new task receives the type of `DefaultTask`. Here are some examples of the methods and properties that every Gradle task has:
* `dependsOn()` - this make the current task to depend on another task’s execution. This makes mandatory that the other task will run and complete before the execution of the current task starts.
* `doFirst()` - using this method you can pass a piece of executable code to the task. The code is guaranteed to be executed before any other instructions included in the task. You can call this method multiple times: every time you’ll just append you instruction and all of them will run at the very beginning of the task.
* `doLast()` - similar with doFirst() but it will execute the code after the task completes. 

In order to demonstrate how the above methods work consider the build file:

```groovy
task updateDatabase {
    println "update database schema"
}
updateDatabase.doFirst({
    println "check if database exists"
})
updateDatabase.doLast({
    println "populate database"
})
```
The result of running this build is:
```
update database schema
check if database exists
populate database
```

We would normally expect to have `update database schema` in the middle, between the doFirst() and doLast(). If we remove the `-q` from the command, we get:
```
> Configure project : 
update database schema

> Task updateDatabase 
check if database exists 
populate database

BUILD SUCCESSFUL in 1s
1 actionable task: 1 executed
```

What happened actually is that the code from the task itself is considered a `configuration block` and as the output shows it runs during the `configuration lifecycle phase`. The doFirst() and doLast() code is called during the `execution phase`.

Below is a dependsOn example. If we run the task `world` we still get `Hello, world!` because the task `hello` runs before task `world`. 

```groovy
task world {
    dependsOn 'hello'
    doLast {
        println " world!"
    }
}

task hello {
    doLast {
        println "Hello,"
    }
}
```

Properties of `DefaultTask`:
* `didWork` - indicates that a task completed successfully. Caveat: some built-in tasks have this property but if you define your own custom task, you must make sure that you set this property. In the below example we must set the didWork. It then prints `Hello, world!`.
```groovy
task world {
    dependsOn 'hello'
    doLast {
        if (task.hello.didWork){
            println " world!"
        }
    }
}

task hello {
    println "Hello,"
    doLast {
        didWork=true
    }
}
```
* `enabled` – by default all tasks are enabled. You can set this property to `false` if you want the specific task not to be executed. If we consider the first `Hello, world!` example and add the last line we do not see any output.
```groovy
task hello {
    doLast {
        println "Hello, world!"
    }
}

hello.enabled=false
```
But running again the build with `-d` flag (`debug`) we see that the task is skipped:
```
> Task :hello SKIPPED
```
* `temporaryDir` – you’ll get the temporary directory of the current build. May be useful when you have to store some files.

## Dependencies 
* `external dependencies` - declare external dependencies in the dependencies block of `build.gradle`. Use the `groupId`, `artifactId`, and `version` to specify the dependency. Example:
```groovy
dependencies {
    implementation 'com.example:my-library:2.0'
}
```
* `internal dependencies` - for multi-project builds specify dependencies between subprojects:
```groovy
dependencies {
    implementation project':module1'
}
```

## Plugins 
Gradle `plugins` play a crucial role in extending and enhancing the functionality of the Gradle build system. They provide a way to modularize and reuse common build logic across different projects. Let’s consider several aspects about Gradle plugins:
* `applying plugins` - in a Gradle build script you can apply plugins using the `plugins` block:
```groovy
plugins {
    id 'java' 
    id 'application'
}
application {
    mainClassName = 'com.example.MyMainClass'
}
```
* `types of plugins`:
    * `core plugins` - Gradle comes with a set of core plugins that provide basic build capabilities for tasks like Java compilation, testing, and more. Examples include `java`, `war`, and `application`.
    * `community plugins` - The [Gradle Plugin Portal](https://plugins.gradle.org/) hosts a wide variety of community-contributed plugins. You can easily integrate these plugins into your project by specifying their coordinates in the `plugins` block.
    * `custom plugins` - You can create your own custom plugins to encapsulate and share build logic across multiple projects. Custom plugins can be written in either Groovy or Kotlin and applied to projects like any other plugin.
* configuration: plugins can be configured in the `plugins` block or by using the `configure` method. Configuration options depend on the plugin's implementation. Example:
```groovy
plugins {
    id 'java'
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
```

## Gradle Java plugin 
The `Gradle Java Plugin` is a `core plugin` that provides essential build capabilities for Java projects. It simplifies the build configuration for Java projects, including `compilation`, `testing`, and `packaging`. 
The Java Plugin assumes a default project layout where your source code is in the `src/main/java` directory and your tests are in the `src/test/java` directory. The compiled classes are placed in the `build/classes/` directory.
`Tasks` Provided by the Java Plugin:
* `compileJava` - compiles the main source code.
* `processResources` - copies resources from `src/main/resources` to the output directory.
* `classes` - assembles classes and resources into the output directory.
* `compileTestJava` - compiles the test source code.
* `processTestResources` - copies test resources from `src/test/resources/` to the output directory.
* `testClasses` - assembles test classes and resources into the output directory.
* `test` - executes `JUnit` or `TestNG` tests.
* `jar` - assembles a JAR archive of the main classes and resources.
* `javadoc` - generates API documentation for the production Java source using `Javadoc`.
* `clean` - deletes the project build directory.
* `cleanTaskName` - deletes files created by the specified task. For example, `cleanJar` will delete the JAR file created by the jar task and `cleanTest` will delete the test results created by the test task.

## Build Lifecycle

Every time Gradle executes a build, it runs through three `lifecycle phases`: 
* `initialization` - the phase in which Gradle decides which projects are to participate in the build, important in multi-project builds. At first, Gradle checks the `settings.gradle` file and instantiate a `Settings` object, then, for each project it instantiate a `Project` object.
* `configuration` - the phase in which those task objects are assembled into an internal object model, usually called the `DAG` (for directed acyclic graph); other `properties` are added.
* `execution` - the phase in which build tasks are `executed` in the order required by their dependency relationship and following the DAGcreated at configuration step.

# Case study

Let’s have a look at the [build.gradle](../build.gradle) file from the service project and understand each block of code:

```groovy
plugins {
	id 'org.springframework.boot' version '2.5.3'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id 'org.asciidoctor.convert' version '1.5.8'
	id 'java'
	id 'jacoco'
}
```

Here the plugins are added. Except the java plugin that we already covered we have:
* `Spring Boot` - open-source Java-based framework that simplifies the development of production-ready, stand-alone, and highly scalable applications. It offers a convention-over-configuration approach, making it easy to create robust and efficient Spring-powered applications with minimal setup and boilerplate code.
* `Spring dependency management` – controls the versions needed for your project’s dependencies.
* `Asciidoctor` – needed to process asciiDoc source files.
* `Jacoco` -  provides code coverage metrics for Java code.


Then we have some intormation about the project:
* the entity that runs the project:
```groovy
group = 'ro.unibuc' 
```
* unique identifier for project's artifacts:
```groovy
version = '0.0.1-SNAPSHOT' 
```
* version of the Java source code that the project should be compiled with:
```groovy
sourceCompatibility = '11' 
```

```groovy
repositories {
	mavenCentral()
}
```
A `repository` is used to resolve `dependencies` from. The `mavenCentral()` is a convention in Gradle – you do not need to add the complete url – just specify it and Gradle knows how to find it.

```groovy
ext {
	set('snippetsDir', file("build/generated-snippets"))
	set('testcontainersVersion', "1.15.3")
}
```
`Ext` is used to define `extra properties` in the project, in our case it sets a path and a version.

```groovy
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-actuator'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.session:spring-session-core'
	implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
	implementation 'org.springframework.data:spring-data-mongodb'
	implementation 'org.apache.commons:commons-io:1.3.2'
	runtimeOnly 'com.h2database:h2'
	runtimeOnly 'io.micrometer:micrometer-registry-prometheus'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.springframework.restdocs:spring-restdocs-mockmvc'
	testImplementation 'org.testcontainers:junit-jupiter'
	testImplementation 'org.junit.jupiter:junit-jupiter-api:5.3.1'
	testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.3.1'

	//	E2E tests
	runtimeOnly "org.junit.vintage:junit-vintage-engine:5.8.2"
	testImplementation 'io.cucumber:cucumber-core:6.8.0'
	testImplementation 'io.cucumber:cucumber-java:6.8.0'
	testImplementation 'io.cucumber:cucumber-junit:6.8.0'
	testImplementation 'io.cucumber:cucumber-spring:6.8.0'
}
```
The `dependencies` refer to all external artifacts required for compilation, testing and runtime. Before each dependency we specify for what will be used. Thus, we have:
* `implementation` – it will be used for compilation but also at runtime.
* `runtimeOnly` – needed only at runtime, not for compilation.
* `testImplementation` – needed only for compiling and running the tests.

```groovy
dependencyManagement {
	imports {
		mavenBom "org.testcontainers:testcontainers-bom:${testcontainersVersion}"
	}
}
```

`Maven BOM`(Bill of Materials) is a special kind of `POM`  allows you to manage versions of dependencies in a central location, making it easier to ensure `consistent and compatible versions` across multiple projects. 

```groovy
test {
	outputs.dir snippetsDir
	useJUnitPlatform {
		excludeTags ("IT", "E2E")
	}
	finalizedBy jacocoTestReport
}
```

This snippet gives `Gradle` instruction on how to handle testing:
* what is the output directory
* to use Junit 5 to run the tests and excluede Integration Tests and End to End tests.
* instruct that after the tests runs to continue with task jacocoTestReport

```groovy
jacocoTestReport {
	dependsOn test
}
```
Generates a detailed `code coverage report` based on the results of the tests. It depends on test so it should run after all tests are run.

```groovy
task testIT(type: Test) {
	outputs.dir snippetsDir
	outputs.upToDateWhen { false }

	useJUnitPlatform {
		includeTags "IT"
	}
}
```
This defines a task of type test that should run the Integration tests using JUnit.

```groovy
configurations {
	cucumberRuntime {
		extendsFrom testImplementation
	}
}
```

This is a custom configuration that indicates that the cucumber runtime should include all dependencies declared using `testImplementation`.

```groovy
task testE2E() {
	dependsOn assemble, testClasses
	doLast {
		javaexec {
			main = "io.cucumber.core.cli.Main"
			classpath = configurations.cucumberRuntime + sourceSets.main.output + sourceSets.test.output
			args = [
					'--plugin', 'pretty',
					'--plugin', 'html:target/cucumber-report.html',
					'--glue', 'ro.unibuc.hello.e2e.steps',
					'src/test/resources']
		}
	}
}
```

This snippet runs the end-to-end tests.

```groovy
asciidoctor {
	inputs.dir snippetsDir
	dependsOn test
}
```
This should run after test and generate files based on the input dir.