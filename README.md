# Spring Boot ToDo REST API (DevOps Demo)

A minimal Spring Boot application with RESTful API, H2 database, Docker support and GitHub Actions CI.

This project is used as a DevOps demo application (CI → Docker → Kubernetes → GitOps).

--------------------------------------------------

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 (in-memory DB)
- Maven Wrapper
- Docker (multi-stage build)
- GitHub Actions (CI)

--------------------------------------------------

## Project Structure

.
├── .github/workflows/ci.yml
├── Dockerfile
├── app/
│   ├── pom.xml
│   ├── mvnw
│   ├── src/
│   └── target/
└── README.md

--------------------------------------------------

## Local Development

### Run locally (without Docker)

cd app
./mvnw spring-boot:run

Open in browser:
http://localhost:8080/

### Run tests

cd app
./mvnw clean test

--------------------------------------------------

## REST API Endpoints

GET     /api/tasks        → Get all tasks  
GET     /api/tasks/{id}   → Get task by id  
POST    /api/tasks        → Create new task  
PATCH   /api/tasks/{id}   → Update task  
DELETE  /api/tasks/{id}   → Delete task  

Example:

curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"DevOps demo"}'

--------------------------------------------------

## Docker

Build image:

docker build -t todo-app:local .

Run container:

docker run --rm -p 8080:8080 todo-app:local

--------------------------------------------------

## CI (GitHub Actions)

On every push and pull request:

- Run tests
- Build jar
- Build Docker image

Pipeline file:
.github/workflows/ci.yml

--------------------------------------------------

## Next Steps (DevOps Roadmap)

[ ] Deploy to local Kubernetes (kind)  
[ ] Add Helm chart  
[ ] Integrate Argo CD (GitOps)  
[ ] Add monitoring (Prometheus + Grafana)  
[ ] Add Actuator health checks  