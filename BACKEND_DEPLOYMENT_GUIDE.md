# Backend Deployment Guide - Connect to Running Database

## ✅ Current Status
- **Database Service**: ✅ Running successfully at `ecobazaardb.onrender.com`
- **Database Status**: ✅ MariaDB/MySQL is ready and responding
- **Next Step**: Deploy Spring Boot backend service

## 🚀 Deploy Backend Service

### Option 1: Using Complete Configuration (Recommended)
```bash
# Deploy both services together (if database isn't already deployed)
render deploy --config render-complete.yaml
```

### Option 2: Deploy Backend Only (Since DB is already running)
```bash
# Deploy just the backend service
render deploy --config render-backend-fixed.yaml
```

### Option 3: Manual Deployment via Render Dashboard

1. **Go to Render Dashboard**
2. **Create New Web Service**
3. **Configure the service**:
   - **Name**: `ecobazaar-backend`
   - **Environment**: `Docker`
   - **Dockerfile Path**: `./Dockerfile`
   - **Plan**: `Free`

4. **Set Environment Variables**:
   ```
   SPRING_PROFILES_ACTIVE = prod
   SERVER_PORT = 10000
   JWT_SECRET = ecobazaarX2024SecretKeyForJWTTokenGenerationProduction
   CORS_ORIGINS = https://ecobazzarx.web.app/
   DATABASE_URL = jdbc:mysql://ecobazaardb.onrender.com:3306/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
   DB_USERNAME = ecobazaar_user
   DB_PASSWORD = ecobazaar_password
   ```

5. **Health Check Configuration**:
   - **Health Check Path**: `/api/health`
   - **Port**: `10000`

## 🔧 Connection Configuration

### Database Connection Details
- **Host**: `ecobazaardb.onrender.com`
- **Port**: `3306`
- **Database**: `ecobazaar_db`
- **Username**: `ecobazaar_user`
- **Password**: `ecobazaar_password`

### Connection String
```
jdbc:mysql://ecobazaardb.onrender.com:3306/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
```

## 📊 Expected Results

After successful deployment:
- ✅ Backend service starts without connection errors
- ✅ Database connection established
- ✅ Health endpoint responds: `/api/health`
- ✅ All API endpoints available

## 🔍 Monitoring & Testing

### 1. Check Deployment Logs
Look for these success messages:
```
INFO - Starting EcoBazaarXApplication
INFO - HikariPool-1 - Starting...
INFO - HikariPool-1 - Start completed
INFO - Tomcat started on port(s): 10000 (http)
```

### 2. Test Health Endpoint
```
GET https://your-backend-url.onrender.com/api/health
```

Expected response:
```json
{
  "status": "UP",
  "service": "Authentication Service",
  "timestamp": 1234567890
}
```

### 3. Test Database Connection
Check logs for successful Hibernate initialization:
```
INFO - Hibernate: connection obtained from JdbcConnectionAccess
INFO - Hibernate: connection released
```

## 🛠️ Troubleshooting

### If Backend Fails to Start:
1. **Check Environment Variables**: Ensure all required variables are set
2. **Verify Database Connection**: Test connection string
3. **Check Logs**: Look for specific error messages
4. **Database Service**: Ensure database is running and accessible

### Common Issues:
- **Connection timeout**: Increase `connectTimeout` and `socketTimeout`
- **Authentication failed**: Verify username/password
- **Database not found**: Check database name and initialization

## 📝 Next Steps After Deployment

1. **Test API Endpoints**:
   - Authentication: `/api/auth/login`
   - Products: `/api/products`
   - Health: `/api/health`

2. **Verify Database Integration**:
   - Check if tables are created
   - Test data operations
   - Verify JPA/Hibernate is working

3. **Frontend Integration**:
   - Update frontend to use new backend URL
   - Test complete application flow

## 🎯 Success Criteria

- ✅ Backend service deployed and running
- ✅ Database connection established
- ✅ Health endpoint responding
- ✅ No connection errors in logs
- ✅ API endpoints accessible

Your database is ready - now let's get the backend connected! 🚀
