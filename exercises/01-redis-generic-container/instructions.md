# Exercise 1

In this exercise, you will learn how to use Testcontainers to run a Redis database as test fixture managed by a JUnit 5 test class. You can find the initial code in the `start` directory. The production source class under test is named `RedisRepository.java` under the directory `src/main/java`. The test class `RedisRepositoryIntegrationTest.java` under the directory `src/test/java` already implements a test case.

> **_NOTE:_** You can choose to run the build using Maven or Gradle. Pick the tool you are most comfortable with.

1. Add the JUnit Jupiter and Testcontainers dependencies. Use the version 5.8.1 for JUnit, use version 1.16.3 for Testcontainers.
2. To ensure that Testcontainers can log its messages to the console, add a SLF4J implementation as dependency.
3. In the test class, create a new container instance of type `org.testcontainers.containers.GenericContainer`. Declare the image `redis:6.2.6-alpine` and the exposed port `6379` for the container. The container should be created and destroyed for every test method.
4. Instantiate a class of `com.bmuschko.testcontainers.RedisRepository` and assign it to the variable named `redisRepository`. Determine the runtime value for the host and port of the container and provide it in the constructor of the class.
5. Run the test and verify its correct behavior. For Maven the command is `./mvnw test`, for Gradle the command is `./gradlew test`. The console output should indicate that the container has been created and destroyed after the test finished.
