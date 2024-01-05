# Gasprice Calculator Backend Application ![Java](https://img.shields.io/badge/Code-Java-informational?style=flat&logo=openjdk&logoColor=white&color=2bbc8a) ![Project Status](https://img.shields.io/badge/Project_Status-IN_PROGRESS-red)

[![CI/CD Pipeline](https://github.com/JanoschA/gasprice-calculator/actions/workflows/pipeline_master.yml/badge.svg?branch=master)](https://github.com/JanoschA/gasprice-calculator/actions/workflows/pipeline_master.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=JanoschA_gasprice-calculator&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=JanoschA_gasprice-calculator)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=JanoschA_gasprice-calculator&metric=coverage)](https://sonarcloud.io/summary/new_code?id=JanoschA_gasprice-calculator)

The Gasprice Calculator Backend Application is a RESTful web service designed to provide real-time calculations and predictions of future gas prices. It achieves this by leveraging a sophisticated algorithm that takes into account various factors such as historical data, current market trends, and geopolitical events.

The application is built using Java and Spring Boot, providing a robust and scalable solution that can handle high volumes of requests. It exposes a simple, user-friendly API that allows clients to retrieve calculated and predicted gas prices with ease.

The application is hosted and can be accessed at [gasprice-calculator.com](https://gasprice-calculator.com). Here, you can find detailed API documentation, usage examples, and more information about the underlying algorithm and data sources.

## Table of Contents
- [Installation](#installation)
  - [Database](#database)
  - [Run Locally](#run-locally)
- [Deployment CI/CD Information](#deployment-cicd-information)
- [Default Spring Init Helper](#default-spring-init-helper)

## Installation

### Database
Run a local PostgreSQL database using Docker with the following command:

```shell
docker run \
    --name gpcPostgres \
    -p 5432:5432 \
    -e POSTGRES_USER=admin \
    -e POSTGRES_PASSWORD=admin \
    -e POSTGRES_DB=gpc \
    -d \
    postgres
```
This command will start a PostgreSQL database in a Docker container with the name `gpcPostgres`, accessible at port `5432`. The database name, user, and password are all set to `admin`.

### Run-Locally
Run this following command:
```
./mvnw spring-boot:run
```
This command will start the Spring Boot application using Maven.

## Deployment CI/CD Information
For information about the deployment CI/CD, please check out the [deployment guide](DEPLOYMENT_README.md).

## Default Spring Init Helper
For additional help, see the [Spring Init Helper](HELP.md).