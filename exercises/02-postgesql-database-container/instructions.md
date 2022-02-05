# Exercise 2

In this exercise, you will learn how to use Testcontainers to bring up a PostgreSQL database with seed data. You can find the initial code in the `start` directory. The production source class under test is named `WarehouseRepository.java` under the directory `src/main/java`. The test class `WarehouseRepositoryIntegrationTest.java` under the directory `src/test/java` already implements a test case.

> **_NOTE:_** You can choose to run the build using Maven or Gradle. Pick the tool you are most comfortable with.

1. Add the Testcontainers dependency that supports creating a PostgreSQL container. Add the dependency for the PostgreSQL JDBC driver with version `42.3.2`.
2. In the test class, create an instance of `org.testcontainers.containers.PostgreSQLContainer`. Assign the image `postgres:9.6.12` and the database name `warehouse`. Upon creation, the container instance should run the script `warehouse.sql` to create the database schema.
3. Create an instance of `WarehouseRepositoryImpl` and inject the JDBC URL, username, and password into the constructor before every test method.
4. Run the test and verify its correct behavior. For Maven the command is `./mvnw test`, for Gradle the command is `./gradlew test`. The console output should indicate that the container has been created and destroyed after the test finished.