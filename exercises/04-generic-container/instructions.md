# Exercise 4

In this exercise, you will learn how to use Testcontainers to use a generic container. First, you'll create a container object of type `org.testcontainers.containers.GenericContainer`. Later, you'll build the container on-the-fly.

You can choose to run the build using Maven or Gradle. Pick the tool you are most comfortable with.

1. In the existing test class, create a `GenericContainer` that uses the image named `nginx:1.21.3`. The test code should be able to reach the endpoint of the Ngix container.
2. Run the test and verify its correct behavior. For Maven the command is `./mvnw test`, for Gradle the command is `./gradlew test`. The console output should indicate that the container has been created and destroyed after the test finished.
3. Instead of using a pre-built Nginx, build the container on-the-fly using the provided Dockerfile.
4. Run the test and verify its correct behavior. For Maven the command is `./mvnw test`, for Gradle the command is `./gradlew test`. The console output should indicate that the container has been created and destroyed after the test finished.