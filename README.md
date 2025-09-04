# EcoBazaarX Backend

Spring Boot Backend for EcoBazaarX E-commerce Platform

## 🚀 Features

- **RESTful API** - Complete CRUD operations
- **Firebase Integration** - Firestore database connectivity
- **JWT Authentication** - Secure user authentication
- **Caching** - Performance optimization with Caffeine
- **Database Support** - H2 (development) + PostgreSQL ready
- **Security** - Spring Security integration
- **Monitoring** - Actuator health checks
- **CORS Support** - Cross-origin resource sharing

## 🛠️ Tech Stack

- **Java 17** - Latest LTS version
- **Spring Boot 3.2.0** - Modern Spring framework
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - Database operations
- **Firebase Admin SDK** - Google Cloud services
- **Maven** - Build tool
- **H2 Database** - In-memory database
- **Caffeine Cache** - High-performance caching

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/ecobazaar/backend/
│   │       ├── config/          # Configuration classes
│   │       ├── controller/      # REST API controllers
│   │       ├── model/          # Data models
│   │       ├── service/        # Business logic
│   │       └── EcoBazaarXApplication.java
│   └── resources/
│       ├── application.properties
│       ├── application-prod.properties
│       └── firebase-service-account.json
├── test/                       # Test files
└── pom.xml                    # Maven configuration
```

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Firebase project with service account

### Local Development
```bash
# Clone repository
git clone https://github.com/YOUR_USERNAME/ecobazaar-backend.git
cd ecobazaar-backend

# Add Firebase service account
# Place firebase-service-account.json in src/main/resources/

# Run application
mvn spring-boot:run
```

### Production Deployment
```bash
# Build JAR
mvn clean install

# Run JAR
java -jar target/ecobazaar-backend-1.0.0.jar
```

## 🌐 API Endpoints

### Settings Management
- `GET /api/settings/{userId}` - Get user settings
- `POST /api/settings/{userId}/update` - Update setting
- `POST /api/settings/{userId}/notifications` - Update notifications
- `POST /api/settings/{userId}/reset` - Reset to defaults
- `GET /api/settings/{userId}/export` - Export settings
- `POST /api/settings/{userId}/import` - Import settings
- `GET /api/settings/health` - Health check

### Health & Monitoring
- `GET /api/actuator/health` - System health
- `GET /api/actuator/info` - Application info
- `GET /api/actuator/metrics` - Performance metrics

## 🔧 Configuration

### Environment Variables
- `FIREBASE_PROJECT_ID` - Firebase project ID
- `FIREBASE_CREDENTIALS_PATH` - Service account path
- `SERVER_PORT` - Application port (default: 8080)
- `JWT_SECRET` - JWT signing secret
- `DB_PASSWORD` - Database password

### Profiles
- **default** - Development configuration
- **production** - Production configuration

## 🚀 Deployment

### Render (Recommended)
1. Connect GitHub repository to Render
2. Select **Docker** environment (not Java)
3. Configure environment variables:
   - `FIREBASE_PROJECT_ID`: Your Firebase project ID
   - `FIREBASE_CREDENTIALS_JSON`: Your complete Firebase service account JSON as a string
   - `JWT_SECRET`: Your JWT secret key
   - `DB_PASSWORD`: Database password
   - `CORS_ORIGINS`: Allowed CORS origins
4. Deploy automatically

**Note**: Using Docker environment on Render provides better compatibility and control.

### Local Deployment
```bash
# Windows
deploy.bat

# Linux/Mac
./deploy.sh
```

### Docker Deployment
```bash
# Build Docker image
docker build -t ecobazaar-backend .

# Run container
docker run -p 10000:10000 ecobazaar-backend
```

### Other Platforms
- **Heroku** - Easy deployment
- **AWS** - Scalable infrastructure
- **Google Cloud** - Firebase integration
- **DigitalOcean** - Simple deployment

## 🔒 Security

- JWT-based authentication
- CORS configuration
- Input validation
- Secure headers
- Rate limiting ready

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

## 🎯 Roadmap

- [ ] User management API
- [ ] Product catalog API
- [ ] Order management API
- [ ] Payment integration
- [ ] Real-time notifications
- [ ] Analytics dashboard

---

**Built with ❤️ by EcoBazaarX Team**
