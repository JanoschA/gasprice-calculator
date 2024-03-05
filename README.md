# Gasprice Calculator Backend Application ![Java](https://img.shields.io/badge/Code-Java-informational?style=flat&logo=openjdk&logoColor=white&color=2bbc8a) ![Project Status](https://img.shields.io/badge/Project_Status-PAUSED-red)

[![CI/CD Pipeline](https://github.com/JanoschA/gasprice-calculator/actions/workflows/pipeline_master.yml/badge.svg?branch=master)](https://github.com/JanoschA/gasprice-calculator/actions/workflows/pipeline_master.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=JanoschA_gasprice-calculator&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=JanoschA_gasprice-calculator)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=JanoschA_gasprice-calculator&metric=coverage)](https://sonarcloud.io/summary/new_code?id=JanoschA_gasprice-calculator)

# WARNING, THIS PROJECT WAS PAUSED!!!
## The Code will be still there, but the infrustructure will be gone until I started it again.

The Gasprice Calculator Backend Application is a RESTful web service designed to provide real-time calculations and predictions of future gas prices. It achieves this by leveraging a sophisticated algorithm that takes into account various factors such as historical data, current market trends, and geopolitical events.

The application is built using Java and Spring Boot, providing a robust and scalable solution that can handle high volumes of requests. It exposes a simple, user-friendly API that allows clients to retrieve calculated and predicted gas prices with ease.

The application is hosted and can be accessed at. Here, you can find detailed API documentation, usage examples, and more information about the underlying algorithm and data sources.

## Table of Contents
-[Prerequisites](#prerequisites)
- [Installation](#installation)
  - [Database](#database)
  - [Running the Application Locally](#Running-the-Application-Locally)
- [Deployment CI/CD Information](#deployment-cicd-information)
- [Default Spring Init Helper](#default-spring-init-helper)

## Prerequisites

Before you begin, ensure you have met the following requirements:

* You have installed the latest version of [Java](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html). This project uses Java 17.
* You have a Linux/Mac/Windows machine.
* You have installed [Docker](https://docs.docker.com/get-docker/). This project uses Docker for creating isolated environments.

## Installation

### Database

You can run a local PostgreSQL database using Docker. Here are the steps:

1. To create and start the database, run the following command:

    ```shell
    docker run --name gpcPostgres -p 5432:5432 -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=gpc -d postgres
    ```

   This command will start a PostgreSQL database in a Docker container named `gpcPostgres`, accessible at port `5432`. The database name, user, and password are all set to `admin`.

2. If the container is already created but not running, you can start it with the following command:

    ```shell
    docker start gpcPostgres
    ```

Remember to replace `admin` with your actual username and password, and `gpc` with your actual database name.

### Running the Application Locally

Follow these steps to run the application:

1. Execute the following command:

    ```shell
    ./mvnw spring-boot:run
    ```

   This command starts the Spring Boot application using Maven.

2. Once the application starts, navigate to `http://localhost:8080` in your web browser.

3. To verify that the application is running correctly, access the `/actuator/health` endpoint. You should receive a `200` status code and a response body of `{"status":"UP"}`.

## Deployment CI/CD Information
For information about the deployment CI/CD, please check out the [deployment guide](DEPLOYMENT_README.md).

## Default Spring Init Helper
For additional help, see the [Spring Init Helper](HELP.md).
