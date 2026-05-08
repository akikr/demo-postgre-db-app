# ==============================================================================
# Makefile for Spring Boot Maven Project
# Manages environment setup via SDKMAN and provides common build tasks.
# ==============================================================================

# Note: SDKMAN requires bash, so we set SHELL to /bin/bash.
SHELL := /bin/bash
# -e: exit on error, -u: exit on unset variable, -o pipefail: pipeline fails if any command fails
.SHELLFLAGS := -euo pipefail -c
# Ensure undefined variables cause an error and disable built-in suffix rules.
MAKEFLAGS += --warn-undefined-variables
MAKEFLAGS += --no-builtin-rules

# --- Variables ---
# Command prefix to activate the SDKMAN environment in a subshell.
# The 'sdk env' output is redirected to /dev/null to keep the console clean.
# The double dollar sign '$$' is to escape it for Make, so the shell sees a single '$'.
SDK_ENV := source $${HOME}/.sdkman/bin/sdkman-init.sh && sdk env > /dev/null
# The main command to run Maven.
MVN := ./mvnw
# Use 'docker-compose' if you have the older version
DOCKER_COMPOSE := docker compose

# --- Main Targets ---
# Set the default goal to 'help' so that running 'make' shows the help message.
.DEFAULT_GOAL := help

.PHONY: help all run test clean build format lint

all: test run ## Run both 'test' and 'run' targets sequentially.

help: ## ✨ Show this help message.
	@echo "Usage: make [target]"
	@echo ""
	@echo "Workflow:"
	@echo " 1. Format code:         make format"
	@echo " 2. Check formatting:    make lint"
	@echo " 3. Test services:       make test"
	@echo " 4. Build the project:   make build"
	@echo " 5. Run the app:         make run"
	@echo " 6. Clean-up services:   make clean"
	@echo ""
	@echo " When finished, press Ctrl+C to stop"
	@echo ""
	@echo "Available targets:"
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z_-]+:.*?## / {printf "  \033[36m%-15s\033[0m %s\n", $$1, $$2}' $(MAKEFILE_LIST)

clean: ## 🧹 Clean the Maven project and Docker environment.
	@echo "--- 🧹 Cleaning the project ---"
	@$(SDK_ENV) && $(MVN) clean
	@echo "--- 🚮 Cleaning up Docker environment ---"
	@$(DOCKER_COMPOSE) down --volumes

format: ## ✅ Format Java sources with Spotless via Maven.
	@echo "--- ✅ Formatting Java sources with Spotless ---"
	@$(SDK_ENV) && $(MVN) spotless:apply

lint: ## 🔎 Check Java formatting with Spotless via Maven.
	@echo "--- 🔎 Checking Java formatting with Spotless ---"
	@$(SDK_ENV) && $(MVN) spotless:check

test: ## 🧪 Run tests with Testcontainers and Colima environment.
	@echo "--- 🧪 Running tests with Testcontainers environment ---"
	@$(SDK_ENV) && \
	$(MVN) clean test

build: ## 📦 Build the project with 'mvn clean package -DkipTests'
	@echo "--- 📦 Building the project ---"
	@$(SDK_ENV) && $(MVN) clean package -DskipTests

run: ## 🚀 Run application and press Ctrl+C to stop.
	@echo "--- 🚀 Starting the application 🚀 ---"
	@$(SDK_ENV) && \
	$(MVN) spring-boot:run && \
	echo "--- 🛑 Application stopped 🛑 ---" && \
	echo "--- 🚮 Cleaning up Docker environment ---" && \
    $(DOCKER_COMPOSE) down --volumes
