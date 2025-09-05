# EcoBazaarDb - Environment Variables for Render

This document explains the environment variables you need to configure when deploying the MySQL database service on Render.

## 🔧 Environment Variables for MySQL Database

When you deploy the EcobazaarDb service on Render, you need to configure these environment variables:

### Required Environment Variables:

| Variable Name | Value | Description |
|---------------|-------|-------------|
| `MYSQL_ROOT_PASSWORD` | `rootpassword` | Root password for MySQL |
| `MYSQL_DATABASE` | `ecobazaar_db` | Database name |
| `MYSQL_USER` | `ecobazaar_user` | Database user |
| `MYSQL_PASSWORD` | `ecobazaar_password` | Database user password |

## 🚀 How to Set Environment Variables in Render

### Method 1: Using render.yaml (Automatic)
The `render.yaml` file already contains these environment variables, so Render will automatically configure them when you deploy.

### Method 2: Manual Configuration in Render Dashboard
If you prefer to set them manually:

1. **Go to your service in Render dashboard**
2. **Click on "Environment" tab**
3. **Add each environment variable:**

```
MYSQL_ROOT_PASSWORD = rootpassword
MYSQL_DATABASE = ecobazaar_db
MYSQL_USER = ecobazaar_user
MYSQL_PASSWORD = ecobazaar_password
```

## 🔒 Security Considerations

### For Production:
- **Change default passwords** - Use strong, unique passwords
- **Use environment variables** - Don't hardcode sensitive data
- **Enable SSL** - For secure connections

### Example Production Environment Variables:
```
MYSQL_ROOT_PASSWORD = YourStrongRootPassword123!
MYSQL_DATABASE = ecobazaar_db
MYSQL_USER = ecobazaar_user
MYSQL_PASSWORD = YourStrongUserPassword456!
```

## 📝 Environment Variables in render.yaml

Your `render.yaml` file should look like this:

```yaml
services:
  - type: web
    name: ecobazaardb
    env: docker
    dockerfilePath: ./Dockerfile
    plan: free
    envVars:
      - key: MYSQL_ROOT_PASSWORD
        value: rootpassword
      - key: MYSQL_DATABASE
        value: ecobazaar_db
      - key: MYSQL_USER
        value: ecobazaar_user
      - key: MYSQL_PASSWORD
        value: ecobazaar_password
    healthCheckPath: /health
    port: 3306
```

## 🔄 Connection Information for Backend

After deployment, your backend will connect to MySQL using:

### Connection Details:
- **Host**: `ecobazaardb.onrender.com`
- **Port**: `3306`
- **Database**: `ecobazaar_db`
- **Username**: `ecobazaar_user`
- **Password**: `ecobazaar_password`

### Connection String for Backend:
```
jdbc:mysql://ecobazaardb.onrender.com:3306/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
```

## 🛠️ Troubleshooting

### Common Issues:

1. **Environment variables not set:**
   - Check render.yaml file
   - Verify variables in Render dashboard
   - Redeploy service

2. **Connection failed:**
   - Verify environment variables
   - Check service logs
   - Test connection string

3. **Database not initialized:**
   - Check init.sql file
   - Verify MYSQL_DATABASE variable
   - Check service logs

## ✅ Verification Steps

After deployment, verify:

1. **Service is running** - Check Render dashboard
2. **Environment variables set** - Check Environment tab
3. **Database accessible** - Test connection from backend
4. **Tables created** - Check if init.sql executed

## 📊 Monitoring

- **Service Health**: Check Render dashboard
- **Logs**: Available in Render service logs
- **Environment**: Check Environment tab in Render

Your MySQL database service is now properly configured with all necessary environment variables! 🎉
