# EcoBazaarX Backend Deployment Steps

## 🚀 **Step-by-Step Deployment Guide**

### **Step 1: Deploy MySQL Database Service**

1. **Go to Render Dashboard**
   - Visit: https://dashboard.render.com
   - Click "New +" → "Database"

2. **Create MySQL Database**
   - **Name**: `ecobazaardb`
   - **Database**: `ecobazaar_db`
   - **User**: `ecobazaar_user`
   - **Plan**: `Free`
   - **Region**: Choose closest to your users

3. **Note the Connection Details**
   - Copy the **External Database URL**
   - Copy the **Internal Database URL**
   - Copy the **Username** and **Password**

### **Step 2: Deploy Backend Service**

1. **Go to Render Dashboard**
   - Click "New +" → "Web Service"

2. **Connect GitHub Repository**
   - Select: `EcoBazaarXSpringBoot` repository
   - Branch: `main`

3. **Configure Service**
   - **Name**: `ecobazaar-backend`
   - **Environment**: `Docker`
   - **Dockerfile Path**: `./Dockerfile`
   - **Plan**: `Free`

4. **Set Environment Variables**
   ```
   SPRING_PROFILES_ACTIVE=prod
   SERVER_PORT=10000
   JWT_SECRET=ecobazaarX2024SecretKeyForJWTTokenGenerationProduction
   CORS_ORIGINS=https://ecobazzarx.web.app/
   DATABASE_URL=<External Database URL from Step 1>
   DB_USERNAME=<Username from Step 1>
   DB_PASSWORD=<Password from Step 1>
   ```

5. **Deploy**
   - Click "Create Web Service"
   - Wait for deployment to complete

### **Step 3: Test Deployment**

1. **Check Health Endpoint**
   ```
   GET https://ecobazaar-backend.onrender.com/api/actuator/health
   ```

2. **Test API Endpoints**
   ```
   GET https://ecobazaar-backend.onrender.com/api/products
   GET https://ecobazaar-backend.onrender.com/api/users
   ```

### **Step 4: Update Frontend**

Update your frontend to use the new backend URL:
```javascript
const API_BASE_URL = 'https://ecobazaar-backend.onrender.com/api';
```

## 🔧 **Troubleshooting**

### **Database Connection Issues**
- Ensure MySQL service is running
- Check environment variables are set correctly
- Verify database URL format

### **Backend Startup Issues**
- Check logs in Render dashboard
- Verify all environment variables
- Ensure database is accessible

### **CORS Issues**
- Update `CORS_ORIGINS` environment variable
- Include your frontend domain

## 📝 **Important Notes**

1. **Free Tier Limitations**
   - Services sleep after 15 minutes of inactivity
   - First request after sleep takes ~30 seconds
   - Consider upgrading for production use

2. **Database Access**
   - External URL for backend connection
   - Internal URL for services within Render
   - Use external URL in your backend configuration

3. **Security**
   - Keep JWT secret secure
   - Use strong database passwords
   - Enable HTTPS (automatic on Render)

## 🎯 **Success Indicators**

✅ Backend starts without errors  
✅ Health endpoint returns 200 OK  
✅ Database connection established  
✅ API endpoints respond correctly  
✅ Frontend can connect to backend  

## 📞 **Support**

If you encounter issues:
1. Check Render service logs
2. Verify environment variables
3. Test database connectivity
4. Review this deployment guide
