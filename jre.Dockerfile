# Set the base-image for build stage
FROM base-jdk:v25-graal-arm AS build
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
    --multi-release 25 \
    --print-module-deps \
    --class-path 'target/dependencies/*' \
    target/*.jar > modules.info
# Add 'jdk.management' module for JDK-specific management interfaces for the JVM while building application specific JRE
RUN jlink --add-modules $(cat modules.info) \
    --no-header-files \
    --no-man-pages \
    --output /app-jre

# Set the base-image and use chisel for final-satge base image
FROM ubuntu:22.04 AS installer
ARG CHISEL_VERSION=v1.4.0
ARG TARGETARCH=arm64
# Install dependencies for Chisel
RUN apt update && apt install -y ca-certificates curl
# Download and install the Chisel binary
RUN curl -SL "https://github.com/canonical/chisel/releases/download/${CHISEL_VERSION}/chisel_${CHISEL_VERSION}_linux_${TARGETARCH}.tar.gz" | tar -xz -C /usr/bin/
# Extract only the essential Ubuntu 22.04 "slices" into a staging directory
WORKDIR /staging
RUN chisel cut --release ubuntu-22.04 --root /staging \
    base-files_base \
    base-files_release-info \
    ca-certificates_data \
    zlib1g_libs \
    libc6_libs \
    libssl3_libs

# Create a non-root user (appuser) in the staging directory
RUN echo "appuser:x:1000:1000:appuser:/home/appuser:/bin/false" >> /staging/etc/passwd && \
    echo "appuser:x:1000:" >> /staging/etc/group

# Set the scratch for final stage
FROM scratch
# Set the maintainer label
LABEL org.opencontainers.image.source="https://github.com/akikr/demo-postgre-db-app"
LABEL maintainer="ankit akikr@duck.com"
WORKDIR /usr/webapp
# Copy the chiseled Ubuntu filesystem
COPY --from=installer /staging /
# Set JAVA_HOME using application specific JRE from build-stage
ENV JAVA_HOME=/usr/lib/java/jre
ENV PATH=$JAVA_HOME/bin:$PATH
COPY --from=build /app-jre $JAVA_HOME
# Copy the artifact from build-stage
COPY --from=build /usr/app/target/*.jar /usr/webapp/app.jar
# Set Permissions for the non-root user
USER 1000
# Expose the application port
EXPOSE 8090
# Run via entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]
