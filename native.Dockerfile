# Set the base-image for build stage
FROM base-jdk:v25-graal-arm AS build
# Set up working directory
WORKDIR /usr/app
COPY . .
# Download the dependencies
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B
# Build the application
RUN --mount=type=cache,target=/root/.m2 mvn clean package -Pnative native:compile-no-fork -DskipTests -Dspring.aot.native-image.args="-Ob0 --no-fallback" -B

# Set the installer-image to use chiseled Ubuntu for final-satge base image
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
LABEL org.opencontainers.image.source="https://github.com/akikr/demo-postgres-db-app"
LABEL maintainer="ankit akikr@duck.com"
WORKDIR /usr/webapp
# Copy the chiseled Ubuntu filesystem
COPY --from=installer /staging /
# Copy the artifact from build-stage
COPY --from=build /usr/app/target/*-app /usr/webapp/app
# Set the non-root user
USER 1000
# Expose the application port
EXPOSE 8090
# Run using start-up script
ENTRYPOINT ["./app"]
