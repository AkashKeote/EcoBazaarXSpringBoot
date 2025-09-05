# EcoBazaarX Backend - MySQL Deployment Guide

## Overview
This guide explains how to deploy the EcoBazaarX backend with MySQL database on Render.

## Architecture
- **Backend**: Spring Boot application with MySQL database
- **Database**: MySQL 8.0
- **Deployment**: Render with Docker containers

## Local Development

### Prerequisites
- Docker and Docker Compose
- Java 17 (for local development without Docker)

### Running Locally with Docker Compose
```bash
# Start MySQL and Backend services
docker-compose up -d

# View logs
docker-compose logs -f backend

# Stop services
docker-compose down
```

### Running Locally without Docker
1. Start MySQL database locally
2. Update `application.properties` with local MySQL connection
3. Run: `./mvnw spring-boot:run`

## Render Deployment

### 1. Database Setup
The `render.yaml` file automatically creates a MySQL database service:
- **Database Name**: ecobazaar-mysql
- **Database**: ecobazaar_db
- **User**: ecobazaar_user
- **Plan**: Free tier

### 2. Environment Variables
The following environment variables are automatically configured:
- `DATABASE_URL`: MySQL connection string
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `JWT_SECRET`: JWT signing secret
- `CORS_ORIGINS`: Allowed CORS origins

### 3. Deployment Steps
1. Push your code to GitHub
2. Connect your repository to Render
3. Render will automatically:
   - Build the Docker image
   - Create MySQL database
   - Deploy the application
   - Configure environment variables

### 4. Health Checks
- **Backend**: `GET /api/actuator/health`
- **Database**: MySQL connection health check

## Database Schema
The application uses JPA/Hibernate with `ddl-auto=update`, so tables are automatically created/updated based on your entity classes:

- `users`: User accounts and authentication
- `products`: Product catalog
- `orders`: Order management

## Security
- JWT-based authentication
- Password encryption with BCrypt
- CORS configuration for frontend integration
- Environment-based secrets

## Monitoring
- Spring Boot Actuator endpoints available
- Health checks configured
- Logging configured for production

## Troubleshooting

### Common Issues
1. **Database Connection**: Ensure MySQL service is running and accessible
2. **Port Conflicts**: Default ports are 8080 (backend) and 3306 (MySQL)
3. **Memory Issues**: Free tier has limited resources

### Logs
```bash
# View application logs
docker-compose logs backend

# View database logs
docker-compose logs mysql
```

## Production Considerations
- Use a paid MySQL plan for production
- Configure proper backup strategies
- Set up monitoring and alerting
- Use environment-specific secrets
- Configure SSL/TLS for database connections
