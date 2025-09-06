# EcoBazaarX Backend

Spring Boot Backend for EcoBazaarX E-commerce Platform

## 🚀 Features

- **RESTful API** - Complete CRUD operations
- **MySQL Database** - Production-ready database with Railway
- **JWT Authentication** - Secure user authentication with 24-hour expiry
- **Caching** - Performance optimization with Caffeine
- **Data Migration** - Firebase to MySQL migration support
- **Security** - Spring Security integration
- **Monitoring** - Actuator health checks
- **CORS Support** - Cross-origin resource sharing

## 🛠️ Tech Stack

- **Java 17** - Latest LTS version
- **Spring Boot 3.2.0** - Modern Spring framework
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - Database operations
- **MySQL 8.0** - Production database (Railway)
- **Maven** - Build tool
- **Caffeine Cache** - High-performance caching
- **JWT** - JSON Web Token authentication

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/ecobazaar/backend/
│   │       ├── config/          # Configuration classes
│   │       ├── controller/      # REST API controllers
│   │       ├── entity/         # JPA entities
│   │       ├── model/          # Data models
│   │       ├── repository/     # Data repositories
│   │       ├── service/        # Business logic
│   │       └── EcoBazaarXApplication.java
│   └── resources/
│       ├── application.properties      # Development config
│       ├── application-prod.properties # Production config
│       └── firebase-exports/          # Sample data for migration
├── test/                       # Test files
└── pom.xml                    # Maven configuration
```

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL database (local or Railway)

### Local Development
```bash
# Clone repository
git clone https://github.com/AkashKeote/EcoBazaarXSpringBoot.git
cd EcoBazaarX-Backend

# Configure database in application.properties
# Update MySQL connection details

# Run application
mvn spring-boot:run
```

### Production Deployment
```bash
# Build JAR
mvn clean package -DskipTests

# Run JAR
java -jar target/ecobazaar-backend-1.0.0.jar
```

## 🌐 API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `POST /api/auth/validate` - Validate JWT token
- `POST /api/auth/logout` - User logout
- `POST /api/auth/refresh` - Refresh JWT token

### Settings Management
- `GET /api/settings/{userId}` - Get user settings
- `POST /api/settings/{userId}/update` - Update setting
- `POST /api/settings/{userId}/notifications` - Update notifications
- `POST /api/settings/{userId}/reset` - Reset to defaults
- `GET /api/settings/{userId}/export` - Export settings
- `POST /api/settings/{userId}/import` - Import settings
- `GET /api/settings/health` - Health check

### Data Migration
- `POST /api/migration/run` - Run data migration from Firebase exports
- `GET /api/migration/status` - Get migration status
- `POST /api/migration/reset` - Reset migration data

### Health & Monitoring
- `GET /api/actuator/health` - System health
- `GET /api/actuator/info` - Application info
- `GET /api/actuator/metrics` - Performance metrics

## 🔧 Configuration

### Environment Variables
- `DATABASE_URL` - MySQL database URL (Railway)
- `DB_USERNAME` - Database username
- `DB_PASSWORD` - Database password
- `SERVER_PORT` - Application port (default: 8080)
- `JWT_SECRET` - JWT signing secret
- `CORS_ORIGINS` - Allowed CORS origins

### Database Configuration
- **Development**: Local MySQL (localhost:3306)
- **Production**: Railway MySQL (mysql-production-59d9.up.railway.app:3306)

### Profiles
- **default** - Development configuration (local MySQL)
- **prod** - Production configuration (Railway MySQL)

## 🚀 Deployment

### Render (Current Production)
- **URL**: https://ecobazaarxspringboot-1.onrender.com
- **Status**: ✅ Live and running
- **Database**: Railway MySQL connected
- **Environment**: Production profile active

### Railway Database
- **Host**: mysql-production-59d9.up.railway.app
- **Port**: 3306
- **Database**: railway
- **Status**: ✅ Connected and working

### Environment Variables (Production)
- `DATABASE_URL`: jdbc:mysql://mysql-production-59d9.up.railway.app:3306/railway
- `DB_USERNAME`: root
- `DB_PASSWORD`: cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr
- `JWT_SECRET`: ecobazaarX2024SecretKeyForJWTTokenGeneration
- `CORS_ORIGINS`: https://ecobazaar.vercel.app,https://ecobazaar.netlify.app

### Docker Deployment
```bash
# Build Docker image
docker build -t ecobazaar-backend .

# Run container
docker run -p 10000:10000 ecobazaar-backend
```

## 🔒 Security

- JWT-based authentication
- CORS configuration
- Input validation
- Secure headers
- Rate limiting ready

## 📱 Frontend Integration Guide

### For Frontend Developers
**IMPORTANT**: This backend uses **MySQL database**, NOT Firebase Firestore.

### API Base URL
```
Production: https://ecobazaarxspringboot-1.onrender.com/api
```

### Authentication Flow
1. **Register**: `POST /api/auth/register`
2. **Login**: `POST /api/auth/login` → Returns JWT token
3. **Use Token**: Include in Authorization header: `Bearer <token>`
4. **Validate**: `POST /api/auth/validate` → Check token validity

### Sample API Calls
```javascript
// Login
const response = await fetch('https://ecobazaarxspringboot-1.onrender.com/api/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    email: 'user@example.com',
    password: 'password123',
    role: 'customer'
  })
});

// Use token for authenticated requests
const token = response.data.token;
const authResponse = await fetch('https://ecobazaarxspringboot-1.onrender.com/api/settings/1', {
  headers: { 'Authorization': `Bearer ${token}` }
});
```

### Database Entities
- **Users** - User accounts and authentication
- **Products** - Product catalog
- **Orders** - Order management
- **Wishlists** - User wishlists
- **Settings** - User preferences

## 📊 Performance

- Caffeine caching
- Connection pooling
- Async processing
- Health monitoring
- Metrics collection

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

## 📝 License

This project is licensed under the MIT License.

## 🆘 Support

For support and questions:
- Create an issue
- Contact the development team
- Check documentation

## 🎯 Current Status

### ✅ Completed Features
- [x] **User Authentication** - JWT-based login/register
- [x] **Database Migration** - Firebase to MySQL migration
- [x] **Settings Management** - User preferences and settings
- [x] **Production Deployment** - Live on Render + Railway
- [x] **API Documentation** - Complete endpoint documentation
- [x] **Security** - JWT authentication with 24-hour expiry
- [x] **CORS Support** - Frontend integration ready

### 🔄 In Progress
- [ ] **Product Catalog API** - Product management endpoints
- [ ] **Order Management API** - Order processing system
- [ ] **Payment Integration** - Payment gateway integration

### 📋 Future Roadmap
- [ ] **Real-time Notifications** - WebSocket support
- [ ] **Analytics Dashboard** - User analytics and reporting
- [ ] **File Upload** - Image and document upload
- [ ] **Email Service** - Email notifications and verification

---

**Built with ❤️ by Akash**
