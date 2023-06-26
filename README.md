[![CI/CD Pipeline](https://github.com/JanoschA/gasprice-calculator/actions/workflows/pipeline_master.yml/badge.svg?branch=master)](https://github.com/JanoschA/gasprice-calculator/actions/workflows/pipeline_master.yml)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=JanoschA_gasprice-calculator&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=JanoschA_gasprice-calculator)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=JanoschA_gasprice-calculator&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=JanoschA_gasprice-calculator)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=JanoschA_gasprice-calculator&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=JanoschA_gasprice-calculator)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=JanoschA_gasprice-calculator&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=JanoschA_gasprice-calculator)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=JanoschA_gasprice-calculator&metric=bugs)](https://sonarcloud.io/summary/new_code?id=JanoschA_gasprice-calculator)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=JanoschA_gasprice-calculator&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=JanoschA_gasprice-calculator)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=JanoschA_gasprice-calculator&metric=coverage)](https://sonarcloud.io/summary/new_code?id=JanoschA_gasprice-calculator)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=JanoschA_gasprice-calculator&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=JanoschA_gasprice-calculator)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=JanoschA_gasprice-calculator&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=JanoschA_gasprice-calculator)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=JanoschA_gasprice-calculator&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=JanoschA_gasprice-calculator)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=JanoschA_gasprice-calculator&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=JanoschA_gasprice-calculator)

# Gasprice Calculator Backend Application ![](https://img.shields.io/badge/Code-Java-informational?style=flat&logo=openjdk&logoColor=white&color=2bbc8a) ![Project Status](https://img.shields.io/badge/Project_Status-IN_PROGRESS-red)

The Gasprice Calculator Backend Application is a REST-Service to handle/calculator/predict the future gas prices.

You can check it out [gasprice-calculator.com](https://gasprice-calculator.com).

## Table Of Content
- [Installation](#installation)
    - [Database](#Database)
    - [Run Locally](#Run-Locally)
- [Deployment CI/CD Information](#Deployment-CI/CD-Information)
- [Default Spring Init Helper](#Default-Spring-Init-Helper)

## Installation

### Database
You can run with docker your locally db with this command:
```
docker run
    --name gpcPostgres
    -p 5432:5432
    -e POSTGRES_USER=admin
    -e POSTGRES_PASSWORD=admin
    -e POSTGRES_DB=gpc
    -d
    postgres
```

### Run-Locally
Run this following command:
```
./mvnw spring-boot:run
```

## Deployment CI/CD Information
For information about the deployment CI/CD please check this [out](DEPLOYMENT_README.md).

## Default Spring Init Helper
[Link](HELP.md)