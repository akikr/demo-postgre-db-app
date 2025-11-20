# Set the base-image for build stage
FROM base-jdk:v21-graal-arm AS build
# Set the maintainer label
LABEL org.opencontainers.image.source="https://github.com/akikr/demo-postgre-db-app"
LABEL maintainer="ankit akikr@duck.com"
# Set up working directory
WORKDIR /usr/app
COPY pom.xml .
# Download the dependencies
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B
COPY . .
# Build the application
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests -B
# Build the application specific JRE
RUN jdeps --ignore-missing-deps -q \
    --recursive \
    --multi-release 21 \
    --print-module-deps \
    --class-path 'target/dependencies/*' \
    target/*.jar > modules.info
# Add 'jdk.management' module for JDK-specific management interfaces for the JVM while building application specific JRE
RUN jlink --add-modules $(cat modules.info) \
    --no-header-files \
    --no-man-pages \
    --output /app-jre

# Set the base-image for final stage
FROM ubuntu:24.10
# Set the maintainer label
LABEL org.opencontainers.image.source="https://github.com/akikr/demo-postgre-db-app"
LABEL maintainer="ankit akikr@duck.com"
# Set JAVA_HOME using application specific JRE from build-stage
ENV JAVA_HOME=/usr/lib/java/jre
ENV PATH=$JAVA_HOME/bin:$PATH
COPY --from=build /app-jre $JAVA_HOME
# Copy the artifact from build-stage
WORKDIR /usr/webapp
COPY --from=build /usr/app/target/*.jar /usr/webapp/app.jar
# Define environment variables for java-options and application-arguments
ENV JAVA_OPTS=""
ENV APP_ARGS=""
# Build the application start-up script
RUN echo 'java ${JAVA_OPTS} -jar app.jar ${APP_ARGS}' > ./start-app.sh
RUN chmod +x ./start-app.sh
# Set a non-root user: Add a system group 'appgroup' and a system user 'appuser' in this group
RUN groupadd -r appgroup && useradd -r -m -s /bin/bash -G appgroup appuser
RUN chown -R appuser:appgroup /usr/webapp
USER appuser
# Expose the application port
EXPOSE 8090
# Run using start-up script
CMD ["sh", "-c", "./start-app.sh"]
