# EcoBazaarX Backend - Database Integration Guide

## 🚀 **Database Service Integration**

Your backend is now configured to work with the deployed MySQL database service.

## 📊 **Database Service Details**

- **Service URL**: `https://ecobazaardb.onrender.com`
- **Database Host**: `ecobazaardb.onrender.com`
- **Port**: `3306`
- **Database Name**: `ecobazaar_db`
- **Username**: `ecobazaar_user`
- **Password**: `ecobazaar_password`

## 🔧 **Configuration Updates**

### 1. **Production Configuration** (`application-prod.properties`)
```properties
# MySQL Database Configuration for Production
spring.datasource.url=jdbc:mysql://ecobazaardb.onrender.com:3306/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=ecobazaar_user
spring.datasource.password=ecobazaar_password
```

### 2. **Environment Variables** (for Render deployment)
```env
DATABASE_URL=jdbc:mysql://ecobazaardb.onrender.com:3306/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
DB_USERNAME=ecobazaar_user
DB_PASSWORD=ecobazaar_password
```

## 🎯 **Backend Deployment Steps**

### 1. **Update Render Configuration**
In your `render.yaml` or Render dashboard, set these environment variables:

```yaml
envVars:
  - key: DATABASE_URL
    value: jdbc:mysql://ecobazaardb.onrender.com:3306/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
  - key: DB_USERNAME
    value: ecobazaar_user
  - key: DB_PASSWORD
    value: ecobazaar_password
  - key: SPRING_PROFILES_ACTIVE
    value: prod
```

### 2. **Deploy Backend**
```bash
# In your backend directory
git add .
git commit -m "Update database configuration for production"
git push origin main
```

## 🔍 **Testing Database Connection**

### 1. **Check Database Service**
```bash
# Test database service health
curl https://ecobazaardb.onrender.com/health

# Check database info
curl https://ecobazaardb.onrender.com/db/info

# List database tables
curl https://ecobazaardb.onrender.com/db/tables
```

### 2. **Test Backend Connection**
```bash
# Test backend health endpoint
curl https://your-backend.onrender.com/api/actuator/health
```

## 📋 **Available Database Operations**

### **Read Operations**
- ✅ User queries
- ✅ Product queries
- ✅ Order queries
- ✅ Cart queries
- ✅ Wishlist queries

### **Write Operations**
- ✅ User registration/login
- ✅ Product CRUD
- ✅ Order creation/updates
- ✅ Cart management
- ✅ Wishlist management

## 🛠️ **Troubleshooting**

### **Connection Issues**
1. Check if database service is running: `https://ecobazaardb.onrender.com/health`
2. Verify database credentials
3. Check network connectivity

### **Common Errors**
- **Connection timeout**: Database service might be starting up
- **Authentication failed**: Check username/password
- **Database not found**: Database will be created automatically

## 🎉 **Ready for Production**

Your backend is now ready to work with the deployed MySQL database service!

### **Next Steps:**
1. Deploy your backend to Render
2. Test the connection
3. Run your application
4. Monitor database performance

## 📞 **Support**

If you encounter any issues:
1. Check the database service logs
2. Verify backend configuration
3. Test database connectivity
4. Check Render deployment logs
