# Fraud Detection Service

Spring Boot service that receives transaction requests, sends them to Kafka for processing, and returns a scored response.

## Tech Stack

- Java 17
- Maven Wrapper (`mvnw`, `mvnw.cmd`)
- Spring Boot
- Apache Kafka
- Docker / Docker Compose (optional, recommended for dependencies)

## Prerequisites

Install the following tools:

- Git
- Java 17
- Docker Desktop (for containerized run)

You can use the Maven wrapper included in this repo, so a separate Maven install is not required.

## 1) Clone the Repository

```bash
git clone https://github.com/Govind1212/Fraud-Detection-System.git
cd demo
```

## 2) Build the Project

### Windows (PowerShell)

```powershell
.\mvnw.cmd clean package -DskipTests
```

### Linux/macOS

```bash
./mvnw clean package -DskipTests
```

## 3) Run the Project

You can run using Docker Compose (recommended) or run the app locally.

### Option A: Run with Docker Compose (Recommended)

This starts:

- Zookeeper
- Kafka
- Redis
- Fraud service (`8080`)

```bash
docker compose up --build
```

Service URL:

- `http://localhost:8080`

Stop containers:

```bash
docker compose down
```

### Option B: Run Spring Boot Locally

If Kafka is running locally on port `9092`, set bootstrap servers to localhost when starting the app:

### Windows (PowerShell)

```powershell
$env:SPRING_KAFKA_BOOTSTRAP_SERVERS="localhost:9092"
.\mvnw.cmd spring-boot:run
```

### Linux/macOS

```bash
SPRING_KAFKA_BOOTSTRAP_SERVERS=localhost:9092 ./mvnw spring-boot:run
```

By default, the app listens on:

- `http://localhost:8080`

## 4) Run Tests

### Windows (PowerShell)

```powershell
.\mvnw.cmd test
```

### Linux/macOS

```bash
./mvnw test
```

## 5) Quick API Check

Submit a transaction:

```bash
curl -X POST http://localhost:8080/api/v1/transactions \
  -H "Content-Type: application/json" \
  -d '{
    "transactionId": "txn-1001",
    "accountId": "acc-11",
    "amount": 18000,
    "currency": "USD",
    "ipAddress": "10.10.10.10",
    "merchantCountry": "iran",
    "timestamp": 1713350000
  }'

```
## Windows
```
 Invoke-RestMethod -Method POST -Uri "http://localhost:8080/api/v1/transactions" -ContentType "application/json" -Body '{"transactionId":"txn-5001","accountId":"acc-11","amount":18000,"currency":"USD","ipAddress":"10.10.10.10","merchantCountry":"iran","timestamp":1713350000}'
```

Expected behavior:

- Returns scored transaction when processing completes quickly.
- Returns `202 Accepted` with `PENDING` status if processing times out.

## Check Kafka Topic
```
    docker exec -it kafka kafka-console-consumer --bootstrap-server kafka:9092 --topic txn-inbound --from-beginning
```
## Common Commands

```bash
# Build jar
./mvnw clean package

# Run all tests
./mvnw test

# Start only app locally
./mvnw spring-boot:run
```
