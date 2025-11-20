# A demo-postgre-app

The application provides a set of endpoints to perform CRUD (Create, Read, Update, Delete) operations on bookmarks, which are stored in a PostgreSQL database. It's a great
 example of how to integrate a database with a Spring Boot application, and it's designed to be easy to understand and run.

## Project Requirements

To build and run this project, you'll need the following:

*   Java 21 or later
*   Maven 3.9.9 or later
*   Docker and Docker Compose (The application uses Docker to run a PostgreSQL database, so you'll need to
    have Docker installed and running.)

## Dependencies

The project relies on a few key dependencies to function:

*   **Spring Boot**: The core framework for building the application.
*   **Spring Data JDBC**: For easy integration with the PostgreSQL database.
*   **Spring Web**: To create the RESTful API.

*   **PostgreSQL Driver**: The JDBC driver for connecting to the PostgreSQL database.
*   **Testcontainers**: For running integration tests with a real database.

For a complete list of dependencies, please see the `pom.xml` file.

## Getting Started

To get started with the project, you'll need to have the project on your local machine.

### Environment Setup

* The project uses SDKMAN for managing Java and Maven versions.
* Initialize your development environment using **SDKMAN** CLI and sdkman env file [`sdkmanrc`](.sdkmanrc)

```shell
sdk env install
sdk env
```
#### Note: To install SDKMAN refer: [sdkman.io](https://sdkman.io/install)

---

### How to run the application

The application can be run in a few different ways, depending on your preference.

### Running with an Makefile

The project includes a `Makefile` that simplifies the process of running the application. You can use the following commands:

- To see all `make` commands available:

```shell
make help
```

### Running with Maven

The simplest way to run the application is to use the Maven wrapper script included in the project.

```shell
sdk env
./mvnw spring-boot:run
```

OR

```shell
sdk env
./mvnw clean spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=yugabyte" 
```

This will start the application and the PostgreSQL database in docker container using Spring Boot's built-in support for Docker Compose.

### Running with Docker

The project also includes a `compose.yml` to be used by spring-boot docker-compose support and file that can be used to run the application and the database in Docker containers.

## Conclusion

The `demo-postgre-app` project is a great starting point for anyone looking to learn how to build a simple RESTful API with Spring Boot and PostgreSQL. It's designed to be easy to understand and run, and it provides a solid foundation for building more complex applications.

## Contributing

To explore the code, run the application, and experiment with the API. If you have any questions or suggestions, feel free to open an issue or submit a pull request.

Feel free to contribute to this project!

For questions or issues, please open a GitHub issue or submit a pull request.

Happy coding! ✌️
