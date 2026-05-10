# A demo-postgres-app

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

### Running with a Makefile

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

### CVEs Scan Result

- The images are scanned using [cve-scan](cve-scan.sh) script and the result of CEVs for every type of images are as follows: 

**Scanned image: `app/postgres-app-native:v0`**

```txt
NAME               INSTALLED  FIXED IN  TYPE          VULNERABILITY        SEVERITY  EPSS           RISK
tomcat-embed-core  11.0.15    11.0.20   java-archive  GHSA-8mc5-53m5-3qj2  Medium    0.2% (40th)    0.1
tomcat-embed-core  11.0.15    11.0.21   java-archive  GHSA-24j9-x2wg-9qv6  Medium    0.2% (36th)    < 0.1
tomcat-embed-core  11.0.15    11.0.18   java-archive  GHSA-mgp5-rv84-w37q  High      < 0.1% (25th)  < 0.1
tomcat-embed-core  11.0.15    11.0.21   java-archive  GHSA-rv64-5gf8-9qq8  High      < 0.1% (23rd)  < 0.1
tomcat-embed-core  11.0.15    11.0.21   java-archive  GHSA-x4m4-345f-5h5g  High      < 0.1% (23rd)  < 0.1
spring-boot        4.0.1      4.0.6     java-archive  GHSA-8v8j-3hxp-93wr  Critical  < 0.1% (18th)  < 0.1
spring-webmvc      7.0.2      7.0.6     java-archive  GHSA-4773-3jfm-qmx3  Medium    < 0.1% (25th)  < 0.1
tomcat-embed-core  11.0.15    11.0.20   java-archive  GHSA-95jq-rwvf-vjx4  Critical  < 0.1% (10th)  < 0.1
spring-webmvc      7.0.2      7.0.7     java-archive  GHSA-6p4f-wcwh-5vvm  Medium    < 0.1% (17th)  < 0.1
spring-webmvc      7.0.2      7.0.6     java-archive  GHSA-6hcq-hmm3-jj3c  Low       < 0.1% (25th)  < 0.1
postgresql         42.7.8     42.7.11   java-archive  GHSA-98qh-xjc8-98pq  High      < 0.1% (8th)   < 0.1
spring-webmvc      7.0.2      7.0.7     java-archive  GHSA-wg35-8jpf-2xv3  Low       < 0.1% (20th)  < 0.1
jackson-core       3.0.3      3.1.0     java-archive  GHSA-6v53-7c9g-w56r  High      < 0.1% (5th)   < 0.1
tomcat-embed-core  11.0.15    11.0.20   java-archive  GHSA-9m3c-qcxr-9x87  Medium    < 0.1% (7th)   < 0.1
spring-boot        4.0.1      4.0.6     java-archive  GHSA-wwpq-f5c3-7hvx  High      < 0.1% (3rd)   < 0.1
logback-core       1.5.22     1.5.25    java-archive  GHSA-qqpg-mvqg-649v  Low       < 0.1% (1st)   < 0.1
jackson-core       3.0.3      3.1.1     java-archive  GHSA-2m67-wjpj-xhg9  High      N/A            N/A
jackson-core       3.0.3      3.1.0     java-archive  GHSA-72hv-8253-57qq  Medium    N/A            N/A
```

**Scanned image: `app/postgres-app-jre:v1`**

```txt
NAME               INSTALLED  FIXED IN                                           TYPE            VULNERABILITY        SEVERITY  EPSS           RISK
tomcat-embed-core  11.0.15    11.0.20                                            java-archive    GHSA-8mc5-53m5-3qj2  Medium    0.2% (40th)    0.1
tomcat-embed-core  11.0.15    11.0.21                                            java-archive    GHSA-24j9-x2wg-9qv6  Medium    0.2% (36th)    < 0.1
openjdk            25         1.8.0_492, 8.0.492, 11.0.31, 17.0.19, 25.0.3, ...  UnknownPackage  CVE-2026-22016       High      0.1% (30th)    < 0.1
tomcat-embed-core  11.0.15    11.0.18                                            java-archive    GHSA-mgp5-rv84-w37q  High      < 0.1% (25th)  < 0.1
tomcat-embed-core  11.0.15    11.0.21                                            java-archive    GHSA-rv64-5gf8-9qq8  High      < 0.1% (23rd)  < 0.1
tomcat-embed-core  11.0.15    11.0.21                                            java-archive    GHSA-x4m4-345f-5h5g  High      < 0.1% (23rd)  < 0.1
spring-boot        4.0.1      4.0.6                                              java-archive    GHSA-8v8j-3hxp-93wr  Critical  < 0.1% (18th)  < 0.1
spring-webmvc      7.0.2      7.0.6                                              java-archive    GHSA-4773-3jfm-qmx3  Medium    < 0.1% (25th)  < 0.1
openjdk            25         1.8.0_472, 8.0.472, 11.0.29, 17.0.17, 25.0.1, ...  UnknownPackage  CVE-2025-53066       High      < 0.1% (17th)  < 0.1
openjdk            25         1.8.0_472, 8.0.472, 11.0.29, 17.0.17, 25.0.1, ...  UnknownPackage  CVE-2025-53057       Medium    < 0.1% (22nd)  < 0.1
openjdk            25         1.8.0_482, 8.0.482, 11.0.30, 17.0.18, 25.0.2, ...  UnknownPackage  CVE-2026-21945       High      < 0.1% (16th)  < 0.1
openjdk            25         1.8.0_492, 8.0.492, 11.0.31, 17.0.19, 25.0.3, ...  UnknownPackage  CVE-2026-34282       High      < 0.1% (14th)  < 0.1
tomcat-embed-core  11.0.15    11.0.20                                            java-archive    GHSA-95jq-rwvf-vjx4  Critical  < 0.1% (10th)  < 0.1
spring-webmvc      7.0.2      7.0.7                                              java-archive    GHSA-6p4f-wcwh-5vvm  Medium    < 0.1% (17th)  < 0.1
openjdk            25         1.8.0_492, 8.0.492, 11.0.31, 17.0.19, 25.0.3, ...  UnknownPackage  CVE-2026-22013       Medium    < 0.1% (16th)  < 0.1
spring-webmvc      7.0.2      7.0.6                                              java-archive    GHSA-6hcq-hmm3-jj3c  Low       < 0.1% (25th)  < 0.1
openjdk            25         1.8.0_492, 8.0.492, 11.0.31, 17.0.19, 25.0.3, ...  UnknownPackage  CVE-2026-22021       Medium    < 0.1% (14th)  < 0.1
openjdk            25         1.8.0_482, 8.0.482, 11.0.30, 17.0.18, 25.0.2, ...  UnknownPackage  CVE-2026-21932       High      < 0.1% (9th)   < 0.1
postgresql         42.7.8     42.7.11                                            java-archive    GHSA-98qh-xjc8-98pq  High      < 0.1% (8th)   < 0.1
spring-webmvc      7.0.2      7.0.7                                              java-archive    GHSA-wg35-8jpf-2xv3  Low       < 0.1% (20th)  < 0.1
openjdk            25         1.8.0_482, 8.0.482, 11.0.30, 17.0.18, 25.0.2, ...  UnknownPackage  CVE-2026-21933       Medium    < 0.1% (9th)   < 0.1
openjdk            25         1.8.0_482, 8.0.482, 11.0.30, 17.0.18, 25.0.2, ...  UnknownPackage  CVE-2026-21925       Medium    < 0.1% (10th)  < 0.1
jackson-core       3.0.3      3.1.0                                              java-archive    GHSA-6v53-7c9g-w56r  High      < 0.1% (5th)   < 0.1
openjdk            25         1.8.0_492, 8.0.492, 11.0.31, 17.0.19, 25.0.3, ...  UnknownPackage  CVE-2026-22018       Low       < 0.1% (14th)  < 0.1
tomcat-embed-core  11.0.15    11.0.20                                            java-archive    GHSA-9m3c-qcxr-9x87  Medium    < 0.1% (7th)   < 0.1
openjdk            25         25.0.3, 26.0.1                                     UnknownPackage  CVE-2026-22008       Low       < 0.1% (10th)  < 0.1
spring-boot        4.0.1      4.0.6                                              java-archive    GHSA-wwpq-f5c3-7hvx  High      < 0.1% (3rd)   < 0.1
openjdk            25         21.0.9, 25.0.1                                     UnknownPackage  CVE-2025-61748       Low       < 0.1% (9th)   < 0.1
openjdk            25         11.0.31, 17.0.19, 21.0.11, 25.0.3, ...             UnknownPackage  CVE-2026-23865       Medium    < 0.1% (4th)   < 0.1
openjdk            25         1.8.0_492, 8.0.492, 11.0.31, 17.0.19, 25.0.3, ...  UnknownPackage  CVE-2026-22007       Low       < 0.1% (5th)   < 0.1
openjdk            25         1.8.0_492, 8.0.492, 11.0.31, 17.0.19, 25.0.3, ...  UnknownPackage  CVE-2026-34268       Low       < 0.1% (5th)   < 0.1
logback-core       1.5.22     1.5.25                                             java-archive    GHSA-qqpg-mvqg-649v  Low       < 0.1% (1st)   < 0.1
jackson-core       3.0.3      3.1.1                                              java-archive    GHSA-2m67-wjpj-xhg9  High      N/A            N/A
jackson-core       3.0.3      3.1.0                                              java-archive    GHSA-72hv-8253-57qq  Medium    N/A            N/A
```

**Scanned image: `app/postgres-app-jib:v1`**

```txt
NAME                          INSTALLED   FIXED IN  TYPE          VULNERABILITY        SEVERITY  EPSS           RISK
binutils                      2.45.1-r0             apk           CVE-2025-69650       High      0.1% (35th)    0.1
tomcat-embed-core             11.0.15     11.0.20   java-archive  GHSA-8mc5-53m5-3qj2  Medium    0.2% (40th)    0.1
tomcat-embed-core             11.0.15     11.0.21   java-archive  GHSA-24j9-x2wg-9qv6  Medium    0.2% (36th)    < 0.1
tomcat-embed-core             11.0.15     11.0.18   java-archive  GHSA-mgp5-rv84-w37q  High      < 0.1% (25th)  < 0.1
tomcat-embed-core             11.0.15     11.0.21   java-archive  GHSA-rv64-5gf8-9qq8  High      < 0.1% (23rd)  < 0.1
tomcat-embed-core             11.0.15     11.0.21   java-archive  GHSA-x4m4-345f-5h5g  High      < 0.1% (23rd)  < 0.1
spring-boot                   4.0.1       4.0.6     java-archive  GHSA-8v8j-3hxp-93wr  Critical  < 0.1% (18th)  < 0.1
spring-webmvc                 7.0.2       7.0.6     java-archive  GHSA-4773-3jfm-qmx3  Medium    < 0.1% (25th)  < 0.1
coreutils                     9.8-r1                apk           CVE-2016-2781        Medium    < 0.1% (24th)  < 0.1
coreutils-env                 9.8-r1                apk           CVE-2016-2781        Medium    < 0.1% (24th)  < 0.1
coreutils-fmt                 9.8-r1                apk           CVE-2016-2781        Medium    < 0.1% (24th)  < 0.1
coreutils-sha512sum           9.8-r1                apk           CVE-2016-2781        Medium    < 0.1% (24th)  < 0.1
tomcat-embed-core             11.0.15     11.0.20   java-archive  GHSA-95jq-rwvf-vjx4  Critical  < 0.1% (10th)  < 0.1
binutils                      2.45.1-r0             apk           CVE-2025-69649       High      < 0.1% (13th)  < 0.1
spring-webmvc                 7.0.2       7.0.7     java-archive  GHSA-6p4f-wcwh-5vvm  Medium    < 0.1% (17th)  < 0.1
busybox                       1.37.0-r30            apk           CVE-2025-60876       Medium    < 0.1% (15th)  < 0.1
busybox-binsh                 1.37.0-r30            apk           CVE-2025-60876       Medium    < 0.1% (15th)  < 0.1
ssl_client                    1.37.0-r30            apk           CVE-2025-60876       Medium    < 0.1% (15th)  < 0.1
spring-webmvc                 7.0.2       7.0.6     java-archive  GHSA-6hcq-hmm3-jj3c  Low       < 0.1% (25th)  < 0.1
postgresql                    42.7.8      42.7.11   java-archive  GHSA-98qh-xjc8-98pq  High      < 0.1% (8th)   < 0.1
spring-boot-starter-actuator  4.0.1       4.0.4     java-archive  GHSA-8hfc-fq58-r658  High      < 0.1% (7th)   < 0.1
spring-boot-starter-actuator  4.0.1       4.0.4     java-archive  GHSA-mgvc-8q2h-5pgc  High      < 0.1% (7th)   < 0.1
spring-webmvc                 7.0.2       7.0.7     java-archive  GHSA-wg35-8jpf-2xv3  Low       < 0.1% (20th)  < 0.1
jackson-core                  3.0.3       3.1.0     java-archive  GHSA-6v53-7c9g-w56r  High      < 0.1% (5th)   < 0.1
tomcat-embed-core             11.0.15     11.0.20   java-archive  GHSA-9m3c-qcxr-9x87  Medium    < 0.1% (7th)   < 0.1
binutils                      2.45.1-r0             apk           CVE-2025-69647       Medium    < 0.1% (6th)   < 0.1
binutils                      2.45.1-r0             apk           CVE-2025-69648       Medium    < 0.1% (6th)   < 0.1
binutils                      2.45.1-r0             apk           CVE-2025-69652       Medium    < 0.1% (6th)   < 0.1
spring-boot                   4.0.1       4.0.6     java-archive  GHSA-wwpq-f5c3-7hvx  High      < 0.1% (3rd)   < 0.1
freetype                      2.14.1-r0             apk           CVE-2026-23865       Medium    < 0.1% (4th)   < 0.1
binutils                      2.45.1-r0             apk           CVE-2026-3441        High      < 0.1% (0th)   < 0.1
binutils                      2.45.1-r0             apk           CVE-2026-3442        High      < 0.1% (0th)   < 0.1
binutils                      2.45.1-r0             apk           CVE-2025-69651       Medium    < 0.1% (0th)   < 0.1
binutils                      2.45.1-r0             apk           CVE-2025-69644       Medium    < 0.1% (0th)   < 0.1
logback-core                  1.5.22      1.5.25    java-archive  GHSA-qqpg-mvqg-649v  Low       < 0.1% (1st)   < 0.1
binutils                      2.45.1-r0             apk           CVE-2026-4647        Medium    < 0.1% (0th)   < 0.1
jackson-core                  3.0.3       3.1.1     java-archive  GHSA-2m67-wjpj-xhg9  High      N/A            N/A
jackson-core                  3.0.3       3.1.0     java-archive  GHSA-72hv-8253-57qq  Medium    N/A            N/A
```

**Scanned image: `app/postgres-app-sb:v1`**

```txt
NAME               INSTALLED                FIXED IN         TYPE          VULNERABILITY        SEVERITY    EPSS           RISK
libssl3t64         3.0.13-0ubuntu3.9        (won't fix)      deb           CVE-2024-41996       Low         0.4% (63rd)    0.1
openssl            3.0.13-0ubuntu3.9        (won't fix)      deb           CVE-2024-41996       Low         0.4% (63rd)    0.1
tomcat-embed-core  11.0.15                  11.0.20          java-archive  GHSA-8mc5-53m5-3qj2  Medium      0.2% (40th)    0.1
tomcat-embed-core  11.0.15                  11.0.21          java-archive  GHSA-24j9-x2wg-9qv6  Medium      0.2% (36th)    < 0.1
tomcat-embed-core  11.0.15                  11.0.18          java-archive  GHSA-mgp5-rv84-w37q  High        < 0.1% (25th)  < 0.1
libssl3t64         3.0.13-0ubuntu3.9        (won't fix)      deb           CVE-2025-27587       Low         0.2% (44th)    < 0.1
openssl            3.0.13-0ubuntu3.9        (won't fix)      deb           CVE-2025-27587       Low         0.2% (44th)    < 0.1
tomcat-embed-core  11.0.15                  11.0.21          java-archive  GHSA-rv64-5gf8-9qq8  High        < 0.1% (23rd)  < 0.1
tomcat-embed-core  11.0.15                  11.0.21          java-archive  GHSA-x4m4-345f-5h5g  High        < 0.1% (23rd)  < 0.1
spring-boot        4.0.1                    4.0.6            java-archive  GHSA-8v8j-3hxp-93wr  Critical    < 0.1% (18th)  < 0.1
spring-webmvc      7.0.2                    7.0.6            java-archive  GHSA-4773-3jfm-qmx3  Medium      < 0.1% (25th)  < 0.1
libc6              2.39-0ubuntu8.7                           deb           CVE-2026-4437        Medium      < 0.1% (20th)  < 0.1
tomcat-embed-core  11.0.15                  11.0.20          java-archive  GHSA-95jq-rwvf-vjx4  Critical    < 0.1% (10th)  < 0.1
stdlib             go1.26.2                 1.25.10, 1.26.3  go-module     CVE-2026-39820       High        < 0.1% (12th)  < 0.1
spring-webmvc      7.0.2                    7.0.7            java-archive  GHSA-6p4f-wcwh-5vvm  Medium      < 0.1% (17th)  < 0.1
spring-webmvc      7.0.2                    7.0.6            java-archive  GHSA-6hcq-hmm3-jj3c  Low         < 0.1% (25th)  < 0.1
libc6              2.39-0ubuntu8.7                           deb           CVE-2026-5450        Medium      < 0.1% (15th)  < 0.1
libc6              2.39-0ubuntu8.7                           deb           CVE-2026-4046        Medium      < 0.1% (14th)  < 0.1
libc6              2.39-0ubuntu8.7                           deb           CVE-2026-5928        Medium      < 0.1% (14th)  < 0.1
postgresql         42.7.8                   42.7.11          java-archive  GHSA-98qh-xjc8-98pq  High        < 0.1% (8th)   < 0.1
libc6              2.39-0ubuntu8.7                           deb           CVE-2026-5435        Medium      < 0.1% (13th)  < 0.1
libc6              2.39-0ubuntu8.7                           deb           CVE-2026-6238        Medium      < 0.1% (13th)  < 0.1
libc6              2.39-0ubuntu8.7                           deb           CVE-2026-4438        Medium      < 0.1% (13th)  < 0.1
spring-webmvc      7.0.2                    7.0.7            java-archive  GHSA-wg35-8jpf-2xv3  Low         < 0.1% (20th)  < 0.1
stdlib             go1.26.2                 1.25.10, 1.26.3  go-module     CVE-2026-42499       High        < 0.1% (6th)   < 0.1
jackson-core       3.0.3                    3.1.0            java-archive  GHSA-6v53-7c9g-w56r  High        < 0.1% (5th)   < 0.1
libc6              2.39-0ubuntu8.7          (won't fix)      deb           CVE-2016-20013       Negligible  0.3% (53rd)    < 0.1
tomcat-embed-core  11.0.15                  11.0.20          java-archive  GHSA-9m3c-qcxr-9x87  Medium      < 0.1% (7th)   < 0.1
stdlib             go1.26.2                 1.25.10, 1.26.3  go-module     CVE-2026-33814       High        < 0.1% (5th)   < 0.1
stdlib             go1.26.2                 1.25.10, 1.26.3  go-module     CVE-2026-33811       High        < 0.1% (4th)   < 0.1
stdlib             go1.26.2                 1.25.10, 1.26.3  go-module     CVE-2026-39836       High        < 0.1% (4th)   < 0.1
spring-boot        4.0.1                    4.0.6            java-archive  GHSA-wwpq-f5c3-7hvx  High        < 0.1% (3rd)   < 0.1
stdlib             go1.26.2                 1.25.10, 1.26.3  go-module     CVE-2026-42501       High        < 0.1% (2nd)   < 0.1
stdlib             go1.26.2                 1.25.10, 1.26.3  go-module     CVE-2026-39817       Medium      < 0.1% (5th)   < 0.1
stdlib             go1.26.2                 1.25.10, 1.26.3  go-module     CVE-2026-39826       Medium      < 0.1% (3rd)   < 0.1
stdlib             go1.26.2                 1.25.10, 1.26.3  go-module     CVE-2026-39823       Medium      < 0.1% (2nd)   < 0.1
stdlib             go1.26.2                 1.25.10, 1.26.3  go-module     CVE-2026-39825       Medium      < 0.1% (1st)   < 0.1
stdlib             go1.26.2                 1.25.10, 1.26.3  go-module     CVE-2026-39819       Medium      < 0.1% (0th)   < 0.1
logback-core       1.5.22                   1.5.25           java-archive  GHSA-qqpg-mvqg-649v  Low         < 0.1% (1st)   < 0.1
zlib1g             1:1.3.dfsg-3.1ubuntu2.1                   deb           CVE-2026-27171       Low         < 0.1% (0th)   < 0.1
jackson-core       3.0.3                    3.1.1            java-archive  GHSA-2m67-wjpj-xhg9  High        N/A            N/A
jackson-core       3.0.3                    3.1.0            java-archive  GHSA-72hv-8253-57qq  Medium      N/A            N/A
libc6              2.39-0ubuntu8.7                           deb           CVE-2026-5358        Medium      N/A            N/A
```

## Conclusion

The `demo-postgres-app` project is a great starting point for anyone looking to learn how to build a simple RESTful API with Spring Boot and PostgreSQL. It's designed to be easy to understand and run, and it provides a solid foundation for building more complex applications.

## Contributing

Explore the code, run the application, and experiment with the API.  Feel free to contribute to this project!

For questions or issues, please open a GitHub issue or submit a pull request.

Happy coding! ✌️
