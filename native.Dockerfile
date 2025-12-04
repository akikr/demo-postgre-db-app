# Set the base-image for build stage
FROM base-jdk:v21-graal-arm AS build
# Set up working directory
WORKDIR /usr/app
COPY pom.xml .
# Download the dependencies
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B
COPY . .
# Build the application
RUN --mount=type=cache,target=/root/.m2 mvn clean package -Pnative native:compile-no-fork -DskipTests -B

# Set the base-image for final stage
FROM ubuntu:24.10
# Set the maintainer label
LABEL org.opencontainers.image.source="https://github.com/akikr/demo-postgre-db-app"
LABEL maintainer="ankit akikr@duck.com"
# Copy the artifact from build-stage
WORKDIR /usr/webapp
COPY --from=build /usr/app/target/*-app /usr/webapp/app
# Define environment variables for application-arguments
ENV APP_ARGS=""
# Build the application start-up script
RUN echo './app ${APP_ARGS}' > ./start-app.sh
RUN chmod +x ./start-app.sh
# Set a non-root user: Add a system group 'appgroup' and a system user 'appuser' in this group
RUN groupadd -r appgroup && useradd -r -m -s /bin/bash -G appgroup appuser
RUN chown -R appuser:appgroup /usr/webapp
USER appuser
# Expose the application port
EXPOSE 8090
# Run using start-up script
CMD ["sh", "-c", "./start-app.sh"]
