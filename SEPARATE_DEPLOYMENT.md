# Separate Deployment Guide for EcoBazaarX Backend

This guide explains how to deploy MySQL and Spring Boot backend as separate services on Render.

## 📁 Files Created

### Docker Files:
- `Dockerfile.mysql` - MySQL container configuration
- `Dockerfile.backend` - Spring Boot backend container configuration

### Render Configuration:
- `render-mysql.yaml` - MySQL service configuration
- `render-backend.yaml` - Spring Boot backend service configuration

### Deployment Scripts:
- `deploy-mysql.bat` / `deploy-mysql.ps1` - Deploy MySQL service
- `deploy-backend.bat` / `deploy-backend.ps1` - Deploy backend service

## 🚀 Deployment Steps

### Step 1: Deploy MySQL Service

1. **Run deployment script:**
   ```bash
   # Windows Command Prompt
   .\deploy-mysql.bat
   
   # PowerShell
   .\deploy-mysql.ps1
   ```

2. **Push to GitHub:**
   ```bash
   git add .
   git commit -m "Deploy MySQL service"
   git push origin main
   ```

3. **Deploy on Render:**
   - Go to [render.com](https://render.com)
   - Create new Web Service
   - Connect your GitHub repository
   - Render will detect `render.yaml` and deploy MySQL

4. **Note the MySQL service URL** (you'll need this for backend)

### Step 2: Deploy Spring Boot Backend

1. **Update DATABASE_URL in render-backend.yaml:**
   ```yaml
   - key: DATABASE_URL
     value: jdbc:mysql://YOUR_MYSQL_SERVICE_URL:3306/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   ```

2. **Run deployment script:**
   ```bash
   # Windows Command Prompt
   .\deploy-backend.bat
   
   # PowerShell
   .\deploy-backend.ps1
   ```

3. **Push to GitHub:**
   ```bash
   git add .
   git commit -m "Deploy Spring Boot backend"
   git push origin main
   ```

4. **Deploy on Render:**
   - Create new Web Service
   - Connect your GitHub repository
   - Render will detect `render.yaml` and deploy backend

## 🔧 Configuration Details

### MySQL Service:
- **Port:** 3306
- **Database:** ecobazaar_db
- **User:** ecobazaar_user
- **Password:** ecobazaar_password

### Spring Boot Backend:
- **Port:** 10000
- **Profile:** prod
- **Health Check:** /api/actuator/health

## 🌐 Service URLs

After deployment, you'll have:
- **MySQL Service:** `https://ecobazaar-mysql.onrender.com`
- **Backend Service:** `https://ecobazaar-backend.onrender.com`

## 🔄 Data Migration

After both services are deployed:

1. **Run data migration:**
   ```bash
   # Use the migration script we created earlier
   .\run-migration.ps1
   ```

2. **Or use the migration endpoint:**
   ```bash
   curl -X POST https://ecobazaar-backend.onrender.com/api/migration/start
   ```

## 🛠️ Troubleshooting

### Common Issues:

1. **Connection refused:**
   - Check if MySQL service is running
   - Verify DATABASE_URL is correct

2. **Authentication failed:**
   - Check MySQL credentials
   - Verify user permissions

3. **Port conflicts:**
   - Ensure ports are correctly configured
   - Check Render service settings

## 📊 Monitoring

- **MySQL Health:** Check MySQL service logs
- **Backend Health:** Visit `/api/actuator/health`
- **Migration Status:** Check `/api/migration/stats`

## 🔒 Security Notes

- Change default passwords in production
- Use environment variables for sensitive data
- Enable SSL for database connections
- Configure proper CORS origins

## 📝 Next Steps

1. Deploy MySQL service first
2. Deploy Spring Boot backend
3. Run data migration
4. Test all endpoints
5. Configure domain and SSL
