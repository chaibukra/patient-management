# Patient Management Platform

A production-like microservices platform built with Spring Boot, Spring Cloud Gateway, Kafka, PostgreSQL, gRPC, Docker, Nginx and AWS Infrastructure as Code.

The project demonstrates how modern cloud-native systems are designed, developed, tested, containerized and deployed using industry-standard technologies.

## 🛠️ Development & Architecture Notes

### Repository Upload Structure
Please note that this project was developed independently as an architectural benchmark. To provide a holistic view of the system's design and structural patterns from day one, the codebase was uploaded in a single, consolidated commit rather than incremental feature branches. 

### Local Environment Configuration
A `.env` file has been intentionally included in the repository root for development purposes only. 
* **Zero Secrets:** No sensitive production data, private keys, or actual API secrets are contained within this file.
* **Instant Spin-up:** It contains basic local environment variables configured strictly to allow automated tools, peer reviewers, and developers to `git clone` the repository and run `docker-compose up` immediately without manual setup friction.
* 
---

## Architecture

```text
                    +----------------+
                    |     NGINX      |
                    | Load Balancer  |
                    +--------+-------+
                             |
                             v
                    +----------------+
                    |  API Gateway   |
                    | Spring Gateway |
                    +--------+-------+
                             |
          +------------------+------------------+
          |                                     |
          v                                     v
+-------------------+                +-------------------+
|   Auth Service    |                | Patient Service   |
| Spring Security   |                | CRUD Operations   |
| JWT Generation    |                | gRPC Client       |
+---------+---------+                +---------+---------+
          |                                    |
          |                                    |
          v                                    v
+-------------------+                +-------------------+
| Auth PostgreSQL   |                | Patient PostgreSQL|
+-------------------+                +-------------------+

                             |
                             v

                    +----------------+
                    | Billing Service|
                    | gRPC Server    |
                    +--------+-------+
                             |
                             v
                    +----------------+
                    |     Kafka      |
                    +--------+-------+
                             |
                             v
                    +----------------+
                    | Analytics      |
                    | Kafka Consumer |
                    +----------------+
```

---

## Features

### Patient Service

* CRUD operations
* Validation
* Exception handling
* OpenAPI documentation
* PostgreSQL persistence
* Kafka producer
* gRPC client

### Billing Service

* gRPC server
* Receives billing requests from Patient Service

### Analytics Service

* Kafka consumer
* Processes patient events asynchronously

### Authentication Service

* Spring Security
* JWT authentication
* Login endpoint
* Token validation endpoint
* PostgreSQL persistence

### API Gateway

* Spring Cloud Gateway
* Route management
* JWT validation filter
* Service-to-service routing
* Centralized entry point

### Infrastructure

* Docker Compose
* Kafka
* PostgreSQL
* Nginx Load Balancer
* LocalStack
* AWS CDK
* ECS
* MSK
* RDS
* VPC
* CloudFormation

---

## Technology Stack

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Cloud Gateway
* Spring Data JPA

### Communication

* REST
* gRPC
* Kafka

### Databases

* PostgreSQL

### Infrastructure

* Docker
* Docker Compose
* Nginx
* AWS CDK
* LocalStack

### Testing

* Integration Tests
* JUnit

---

## Running Locally

### 1. Clone the repository

```bash
git clone <repository-url>
cd patient-management
```

### 2. Create .env (upload to github for esay dev testing)

```env
AUTH_DB_NAME=auth-service-db
AUTH_DB_USER=admin_user
AUTH_DB_PASSWORD=admin_password

PATIENT_DB_NAME=patient-service-db
PATIENT_DB_USER=admin_user
PATIENT_DB_PASSWORD=admin_password

JWT_SECRET=replace-me

KAFKA_BOOTSTRAP_SERVERS=kafka:9092

BILLING_SERVICE_ADDRESS=billing-service
BILLING_SERVICE_GRPC_PORT=9001

AUTH_SERVICE_URL=http://auth-service:4005

SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_SQL_INIT_MODE=always
SPRING_DATASOURCE_HIKARI_INITIALIZATION_FAIL_TIMEOUT=60000
```

### 3. Build and start the platform

```bash
docker compose up --build
```

### Run multiple API Gateway instances

```bash
docker compose up -d --build --scale api-gateway=3
```

---

## Available Endpoints

### API Gateway

```text
http://localhost
```

### Authentication

Login

```text
POST http://localhost/auth/login
```

Validate Token

```text
POST http://localhost/auth/validate
```

Swagger

```text
http://localhost/api-docs/auth
```

---

### Patients

Get All Patients

```text
GET http://localhost/api/patients
```

Get Patient By Id

```text
GET http://localhost/api/patients/{id}
```

Create Patient

```text
POST http://localhost/api/patients
```

Update Patient

```text
PUT http://localhost/api/patients/{id}
```

Delete Patient

```text
DELETE http://localhost/api/patients/{id}
```

Swagger

```text
http://localhost/api-docs/patients
```

---

## Infrastructure as Code

The project includes AWS CDK infrastructure definitions for:

* VPC
* ECS Cluster
* ECS Services
* PostgreSQL Databases (RDS)
* Kafka Cluster (MSK)
* Load Balanced API Gateway
* Health Checks
* CloudFormation Deployment

The same architecture can be executed locally using Docker Compose and LocalStack.

---

## Learning Objectives

This project demonstrates:

* Microservices Architecture
* REST APIs
* gRPC Communication
* Event-Driven Architecture
* Kafka Messaging
* JWT Authentication
* API Gateway Pattern
* Containerization
* Infrastructure as Code
* Cloud-Native Development
* Local AWS Simulation with LocalStack

---

## Project Status

Production-like architecture completed and fully containerized.

Ready for local execution, testing, demonstration and cloud deployment experiments.
