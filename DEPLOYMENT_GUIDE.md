# EcoBazaarX Separate Services Deployment Guide

This guide explains how to deploy the EcoBazaarX application with separate MySQL database and Spring Boot backend services.

## 📁 Project Structure

```
EcoBazaarX-Backend/
├── EcobazaarDb/                 # MySQL Database Service
│   ├── Dockerfile
│   ├── docker-compose.yml
│   ├── init.sql
│   ├── render.yaml
│   ├── deploy.ps1
│   └── README.md
├── src/                         # Spring Boot Backend
├── Dockerfile.backend
├── render-backend.yaml
├── deploy-backend.ps1
└── README.md
```

## 🚀 Deployment Process

### Step 1: Deploy MySQL Database Service

1. **Navigate to MySQL folder:**
   ```bash
   cd EcobazaarDb
   ```

2. **Test locally:**
   ```bash
   .\deploy.ps1
   ```

3. **Create separate GitHub repository:**
   ```bash
   # Initialize git in MySQL folder
   git init
   git add .
   git commit -m "Initial MySQL database service"
   
   # Create new repository on GitHub
   # Push to new repository
   git remote add origin https://github.com/yourusername/EcobazaarDb.git
   git push -u origin main
   ```

4. **Deploy on Render:**
   - Go to [render.com](https://render.com)
   - Create new Web Service
   - Connect the MySQL repository
   - Deploy using `render.yaml`

5. **Note the MySQL service URL:**
   - Service URL: `https://ecobazaardb.onrender.com`

### Step 2: Deploy Spring Boot Backend

1. **Update backend configuration:**
   ```bash
   # Update DATABASE_URL in render-backend.yaml
   # (Already updated to use ecobazaardb.onrender.com)
   ```

2. **Deploy backend:**
   ```bash
   .\deploy-backend.ps1
   ```

3. **Push to GitHub:**
   ```bash
   git add .
   git commit -m "Deploy Spring Boot backend with separate MySQL"
   git push origin main
   ```

4. **Deploy on Render:**
   - Create new Web Service
   - Connect the backend repository
   - Deploy using `render.yaml`

## 🔧 Service Configuration

### MySQL Database Service
- **Repository**: `EcobazaarDb`
- **Service URL**: `https://ecobazaardb.onrender.com`
- **Port**: 3306
- **Database**: ecobazaar_db
- **User**: ecobazaar_user
- **Password**: ecobazaar_password

### Spring Boot Backend
- **Repository**: `EcoBazaarX-Backend`
- **Service URL**: `https://ecobazaar-backend.onrender.com`
- **Port**: 10000
- **Database Connection**: Connects to MySQL service

## 🔄 Data Migration

After both services are deployed:

1. **Run data migration:**
   ```bash
   # Use the migration script
   .\run-migration.ps1
   ```

2. **Or use the migration endpoint:**
   ```bash
   curl -X POST https://ecobazaar-backend.onrender.com/api/migration/start
   ```

## 🌐 Service URLs

- **MySQL Database**: `https://ecobazaardb.onrender.com`
- **Spring Boot Backend**: `https://ecobazaar-backend.onrender.com`
- **Frontend**: `https://ecobazzarx.web.app`

## 🛠️ Local Development

### Start MySQL Database
```bash
cd EcobazaarDb
docker-compose up -d
```

### Start Spring Boot Backend
```bash
# In main project directory
docker-compose up -d
```

## 📊 Monitoring

- **MySQL Health**: Check MySQL service logs on Render
- **Backend Health**: Visit `/api/actuator/health`
- **Migration Status**: Check `/api/migration/stats`

## 🔒 Security

- Change default passwords in production
- Use environment variables for sensitive data
- Enable SSL for database connections
- Configure proper CORS origins

## 📝 Benefits of Separate Services

- ✅ **Independent scaling** - Scale database and backend separately
- ✅ **Better performance** - Dedicated resources for each service
- ✅ **Easier maintenance** - Update services independently
- ✅ **Cost effective** - Pay only for what you use
- ✅ **Production ready** - Industry standard approach
- ✅ **Better organization** - Clear separation of concerns

## 🚨 Important Notes

1. **Deploy MySQL first** - Backend depends on MySQL
2. **Update DATABASE_URL** - Use the MySQL service URL
3. **Run data migration** - After both services are deployed
4. **Test all endpoints** - Ensure everything works correctly
5. **Monitor services** - Check logs and health endpoints

## 🔄 Updates and Maintenance

### Update MySQL Service
```bash
cd EcobazaarDb
# Make changes
git add .
git commit -m "Update MySQL service"
git push origin main
# Render will auto-deploy
```

### Update Backend Service
```bash
# In main project directory
# Make changes
git add .
git commit -m "Update backend service"
git push origin main
# Render will auto-deploy
```
