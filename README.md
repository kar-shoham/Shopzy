# Shopzy - Microservices E-commerce Platform

## Overview

Shopzy is a microservices-based e-commerce platform built with Spring Boot and Spring Cloud. The application follows a distributed architecture pattern with service discovery, enabling scalable and maintainable cloud-native development.

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Service Discovery                         │
│                   (Eureka Server :8005)                      │
└──────────────────────┬──────────────────────────────────────┘
                       │
       ┌───────────────┼───────────────┐
       │               │               │
┌──────▼──────┐ ┌─────▼──────┐ ┌─────▼──────┐
│   Product   │ │    User    │ │   Order    │
│   Service   │ │  Service   │ │  Service   │
│   :8002     │ │   :8003    │ │   :8001    │
└──────┬──────┘ └─────┬──────┘ └─────┬──────┘
       │              │              │
┌──────▼──────┐ ┌────▼───────┐ ┌────▼───────┐
│ Product DB  │ │  User DB   │ │  Order DB  │
│  :5442      │ │   :5443    │ │   :5441    │
└─────────────┘ └────────────┘ └────────────┘
```

### Microservices

The platform consists of four main services:

1. **Service Discovery** - Eureka server for service registration and discovery
2. **Product Service** - Manages product catalog and reviews
3. **User Service** - Handles user authentication, authorization, and wallet management
4. **Order Service** - Processes orders and communicates with Product Service

## Tech Stack

### Core Technologies
- **Java 17** - Programming language
- **Spring Boot 3.5.7** - Application framework
- **Spring Cloud 2025.0.0** - Microservices infrastructure
- **Gradle** - Build automation tool

### Spring Cloud Components
- **Netflix Eureka** - Service discovery and registration
- **OpenFeign** - Declarative REST client for inter-service communication

### Data & Persistence
- **PostgreSQL** - Primary database for all services
- **Spring Data JPA** - Data access layer
- **Liquibase** - Database migration and version control

### Security
- **Spring Security** - Authentication and authorization
- **JWT (JSON Web Tokens)** - Token-based authentication
- **jjwt 0.13.0** - JWT implementation library

### Additional Libraries
- **Lombok** - Reduces boilerplate code
- **ModelMapper 3.2.0** - Object mapping between DTOs and entities

### DevOps
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration

## Services Description

### 1. Service Discovery (Port: 8005)
- Eureka server implementation
- Provides service registration and discovery
- All microservices register themselves upon startup
- Enables dynamic service location and load balancing

### 2. Product Service (Port: 8002)
**Features:**
- Product catalog management
- Product reviews system
- JWT-based authentication

**Database:** `product_db` (Port: 5442)

**Key Entities:**
- `Product` - Product information
- `Review` - Customer reviews
- `BaseEntity` - Audit fields (created/updated timestamps)

### 3. User Service (Port: 8003)
**Features:**
- User registration and authentication
- JWT token generation
- Wallet management
- Coupon generation and redemption
- User authorization

**Database:** `user_db` (Port: 5443)

**Key Entities:**
- `ShopzyUser` - User account information
- `CouponCode` - Promotional coupon codes
- `BaseEntity` - Audit fields

### 4. Order Service (Port: 8001)
**Features:**
- Order creation and management
- Order status tracking
- Integration with Product Service (via Feign Client)
- JWT authentication validation

**Database:** `order_db` (Port: 5441)

**Key Entities:**
- `Order` - Order header information
- `OrderItem` - Individual items in an order
- `OrderStatus` enum - Order lifecycle states
- `UserType` enum - User role types
- `BaseEntity` - Audit fields

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java 17** or higher
- **Gradle 8.x** or higher
- **Docker** and **Docker Compose**
- **Git**

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd Shopzy
```

### 2. Configure Environment Variables

The project uses a `local.env` file for configuration. Create or verify the file exists with the following variables:

```properties
# Product Service
PRODUCT_SERVER_PORT=8002
PRODUCT_DB_URL=jdbc:postgresql://localhost:5442/product_db
PRODUCT_DB_USERNAME=myuser
PRODUCT_DB_PASSWORD=mypass

# User Service
USER_SERVER_PORT=8003
USER_DB_URL=jdbc:postgresql://localhost:5443/user_db
USER_DB_USERNAME=myuser
USER_DB_PASSWORD=mypass
USER_JWT_SECRET=bd1a2def-bba5-4e8d-a116-5c85b6be99cd

# Order Service
ORDER_SERVER_PORT=8001
ORDER_DB_URL=jdbc:postgresql://localhost:5441/order_db
ORDER_DB_USERNAME=myuser
ORDER_DB_PASSWORD=mypass

# Service Discovery
SERVICE_DISCOVERY_PORT=8005
```

**Security Note:** In production, use strong, randomly generated secrets and store them securely (e.g., AWS Secrets Manager, HashiCorp Vault).

### 3. Start PostgreSQL Databases

Start all three PostgreSQL database instances using Docker Compose:

```bash
docker-compose up -d
```

This will create and start:
- `shopzy-order-db` on port 5441
- `shopzy-product-db` on port 5442
- `shopzy-user-db` on port 5443

### 4. Build All Services

```bash
# Build all services
./gradlew clean build

# Or build individual services
cd service_discovery && ./gradlew clean build && cd ..
cd product-service && ./gradlew clean build && cd ..
cd user-service && ./gradlew clean build && cd ..
cd order-service && ./gradlew clean build && cd ..
```

### 5. Run Services

Start services in the following order to ensure proper registration:

```bash
# 1. Start Service Discovery first
cd service_discovery
./gradlew bootRun &

# Wait 30 seconds for Eureka to fully start

# 2. Start all other services (order doesn't matter)
cd ../product-service
./gradlew bootRun &

cd ../user-service
./gradlew bootRun &

cd ../order-service
./gradlew bootRun &
```

### 6. Verify Service Registration

Open the Eureka Dashboard in your browser:
```
http://localhost:8005
```

You should see all three services (PRODUCT-SERVICE, USER-SERVICE, ORDER-SERVICE) registered.

## Running Tests

```bash
# Run tests for all services
./gradlew test

# Run tests for a specific service
cd <service-name>
./gradlew test
```

## Database Migrations

The application uses Liquibase for database schema management:

- Migration files are located in `src/main/resources/db/changelog/`
- Migrations run automatically on application startup
- Each service manages its own database schema independently

## Security

### Authentication Flow

1. User registers/logs in via User Service
2. User Service generates a JWT token upon successful authentication
3. Client includes JWT token in the `Authorization` header for subsequent requests
4. Each service validates the JWT token using the shared secret
5. JWT contains user information and roles for authorization

### JWT Structure

- **Algorithm:** HS256
- **Secret:** Configured via `USER_JWT_SECRET` environment variable
- **Claims:** User ID, username, roles, expiration

### Security Filters

Each service implements `JwtFilter` to:
- Extract JWT from Authorization header
- Validate token signature and expiration
- Set authentication context for the request
- Block unauthorized requests

## API Endpoints

### Product Service (http://localhost:8002)
- Product management endpoints
- Review endpoints
- Requires JWT authentication

### User Service (http://localhost:8003)
- `POST /auth/register` - User registration
- `POST /auth/login` - User authentication
- Wallet management endpoints
- Coupon generation and redemption

### Order Service (http://localhost:8001)
- Order creation and retrieval
- Order status management
- Requires JWT authentication

## Inter-Service Communication

The Order Service communicates with the Product Service using:

- **OpenFeign Client** - Declarative REST client
- **Service Discovery** - Dynamic service resolution via Eureka
- **Load Balancing** - Client-side load balancing through Ribbon (built into Eureka client)

Example Feign Client:
```java
@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/api/products/{id}")
    ProductDTO getProductById(@PathVariable Long id);
}
```

## Development

### Project Structure

```
Shopzy/
├── service_discovery/       # Eureka server
├── product-service/         # Product microservice
├── user-service/            # User microservice
├── order-service/           # Order microservice
├── volumes/                 # Docker volume mounts
├── docker-compose.yml       # Database containers
├── local.env               # Environment configuration
└── README.md               # This file
```

### Service Package Structure

Each service follows standard Spring Boot structure:
```
src/main/java/com/shopzy/<service>/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── dto/            # Data Transfer Objects
├── entity/         # JPA entities
├── enums/          # Enum types
├── filters/        # Security filters
├── repository/     # JPA repositories
├── service/        # Business logic
├── utils/          # Utility classes
└── Application.java # Main application class
```

### Adding a New Microservice

1. Create new Spring Boot project with required dependencies
2. Add Eureka client dependency
3. Configure `application.properties` with Eureka server URL
4. Add `@EnableEurekaClient` annotation
5. Implement business logic
6. Update Docker Compose for database (if needed)
7. Add configuration to `local.env`

## Troubleshooting

### Services Not Registering with Eureka

- Ensure Service Discovery is running and accessible
- Check `eureka.client.service-url.defaultZone` configuration
- Verify network connectivity between services
- Check service logs for registration errors

### Database Connection Issues

- Verify Docker containers are running: `docker ps`
- Check database credentials in `local.env`
- Ensure ports 5441-5443 are not in use
- Check database logs: `docker logs <container-name>`

### JWT Authentication Failures

- Verify `USER_JWT_SECRET` is consistent across services
- Check token expiration
- Ensure Authorization header format: `Bearer <token>`
- Validate token claims and signature

## Monitoring and Observability

### Eureka Dashboard
- URL: `http://localhost:8005`
- View registered services, instances, and health status

### Application Health Endpoints
- `http://localhost:8001/actuator/health` - Order Service
- `http://localhost:8002/actuator/health` - Product Service
- `http://localhost:8003/actuator/health` - User Service

## Future Enhancements

- [ ] API Gateway (Spring Cloud Gateway)
- [ ] Distributed tracing (Spring Cloud Sleuth + Zipkin)
- [ ] Centralized configuration (Spring Cloud Config)
- [ ] Circuit breaker (Resilience4j)
- [ ] API documentation (SpringDoc OpenAPI)
- [ ] Containerization of microservices
- [ ] Kubernetes deployment
- [ ] Monitoring (Prometheus + Grafana)
- [ ] Message queue integration (RabbitMQ/Kafka)
- [ ] Caching layer (Redis)

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

For questions or support, please open an issue in the repository.

---

**Built with Spring Boot & Spring Cloud**