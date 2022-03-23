# Application Overview
This is a Java application that provides an abstraction between multiple different email service providers. If one of the services goes down, it will quickly failover to a different provider without affecting the customers.

## General Usage
### Tools Required
- HTTP client application like **Postman**

### Usage
Refer to the postman collection (postman_collection.json) included in this repository. Import in postman then click **Send**.

## API Documentation
Refer to the swagger docs (api-docs.yml) included in this repository

## Architecture Design
Refer to ARCHITECTURE.md included in this repository

## Build And Deployment
### Pre-requisites
- AWS IAM user with programmatic access
- Java 11
- Maven 3
- Serverless framework 3

### Steps
1. run **mvn clean package** to create the package in the form of a jar file
2. run **serverless deploy** to deploy the resources in AWS

