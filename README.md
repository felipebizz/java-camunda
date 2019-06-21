# Camunda Spring Boot Application
Spring Boot Application using [Camunda](http://docs.camunda.org).


### Running the application
You can also build and run the process application with Spring Boot.

#### Manually
1. Build the application using:
```bash
mvn clean package
```
2. Run the *.jar file from the `target` directory using:
```bash
java -jar target/Camunda Spring Boot Application.jar
```

For a faster 1-click (re-)deployment see the alternatives below.

#### Maven Spring Boot Plugin
1. Build and deploy the process application using:
```bash
mvn clean package spring-boot:run
```

#### Your Java IDE
1. Run the project as a Java application in your IDE using CamundaApplication as the main class.


### Opening your Application
```
http://localhost:8080
user: demo
pass: demo
```

### Run and Inspect with Tasklist and Cockpit
Once you deployed the application you can run it using
[Camunda Tasklist](http://docs.camunda.org/latest/guides/user-guide/#tasklist)
and inspect it using
[Camunda Cockpit](http://docs.camunda.org/latest/guides/user-guide/#cockpit).


### Unit Test
You can run the JUnit test [InMemoryH2Test](src/main/resources/archetype-resources/src/test/java/ProcessTest.java) in your IDE or using:
```bash
mvn clean test
```
