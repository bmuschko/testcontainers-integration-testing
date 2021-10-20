# Exercise 3

In this exercise, you will learn how to use Testcontainers to bring up multiple containers, one for a PostgreSQL database and one for Solr. You can find the initial code in the `start` directory. The production source class under test is named `WarehouseServiceImpl.java` in the directory `src/main/java`. The test class `WarehouseServiceImplIntegrationTest.java` in the directory `src/test/java` already implements a test case.

You can choose to run the build using Maven or Gradle. Pick the tool you are most comfortable with.

1. In the test class, create an instance of `org.testcontainers.containers.PostgreSQLContainer`. Assign the image `postgres:9.6.12` and the database name `warehouse`. Upon creation, the container instance should run the script `warehouse.sql` to create the database schema.
2. Create another instance of `org.testcontainers.containers.SolrContainer`. Assign the image `solr:8.9.0`. Call the method `createProductsCollection` to create the initial Solr collection. Make sure to add the right dependency for the container type.
3. Run the test and verify its correct behavior. For Maven the command is `./mvnw test`, for Gradle the command is `./gradlew test`. The console output should indicate that the container has been created and destroyed after the test finished.
4. Instead of creating individual container instances, use the TestContainers Docker Compose functionality instead. Create an instance of `org.testcontainers.containers.DockerComposeContainer` and point it to the YAML file `src/test/resources/warehouse-test.yml`. PostgreSQL runs on port 5432, Solr exposes its service on port 8983.
5. Run the test and verify its correct behavior. For Maven the command is `./mvnw test`, for Gradle the command is `./gradlew test`. The console output should indicate that the container has been created and destroyed after the test finished.