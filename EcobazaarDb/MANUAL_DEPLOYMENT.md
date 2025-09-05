# EcoBazaarDb - Manual Deployment Guide

This guide explains how to manually deploy the EcoBazaarDb MySQL database service on Render.

## 📁 Current Structure

```
C:\Users\AkashK\Desktop\Eco\
├── EcobazaarDb/                 # 🗄️ MySQL Database Service (Separate)
│   ├── Dockerfile              # MySQL container configuration
│   ├── docker-compose.yml      # Local development setup
│   ├── init.sql               # Database initialization script
│   ├── render.yaml            # Render deployment configuration
│   ├── deploy.ps1             # Local testing script
│   ├── README.md              # Service documentation
│   └── MANUAL_DEPLOYMENT.md   # This file
└── EcoBazaarX-Backend/         # 🚀 Spring Boot Backend (Separate)
```

## 🚀 Manual Deployment Steps

### Step 1: Test Locally (Optional but Recommended)

1. **Navigate to EcobazaarDb folder:**
   ```bash
   cd C:\Users\AkashK\Desktop\Eco\EcobazaarDb
   ```

2. **Test MySQL locally:**
   ```bash
   .\deploy.ps1
   ```

3. **Verify MySQL is working:**
   - Check if container starts successfully
   - Verify database connection
   - Test database initialization

### Step 2: Create GitHub Repository

1. **Initialize Git in EcobazaarDb folder:**
   ```bash
   cd C:\Users\AkashK\Desktop\Eco\EcobazaarDb
   git init
   git add .
   git commit -m "Initial MySQL database service"
   ```

2. **Create new repository on GitHub:**
   - Go to [github.com](https://github.com)
   - Click "New repository"
   - Name: `EcobazaarDb`
   - Description: `MySQL Database Service for EcoBazaarX`
   - Make it public or private as needed
   - Don't initialize with README (we already have files)

3. **Push to GitHub:**
   ```bash
   git remote add origin https://github.com/YOUR_USERNAME/EcobazaarDb.git
   git push -u origin main
   ```

### Step 3: Deploy on Render

1. **Go to Render:**
   - Visit [render.com](https://render.com)
   - Sign in or create account

2. **Create New Web Service:**
   - Click "New +" button
   - Select "Web Service"

3. **Connect Repository:**
   - Click "Connect a repository"
   - Select your `EcobazaarDb` repository
   - Click "Connect"

4. **Configure Service:**
   - **Name**: `ecobazaardb`
   - **Environment**: `Docker`
   - **Dockerfile Path**: `./Dockerfile`
   - **Plan**: `Free` (or choose paid plan)
   - **Region**: Choose closest to your users

5. **Environment Variables:**
   Render will automatically detect the environment variables from `render.yaml`:
   - `MYSQL_ROOT_PASSWORD`: rootpassword
   - `MYSQL_DATABASE`: ecobazaar_db
   - `MYSQL_USER`: ecobazaar_user
   - `MYSQL_PASSWORD`: ecobazaar_password

6. **Deploy:**
   - Click "Create Web Service"
   - Wait for deployment to complete (5-10 minutes)

### Step 4: Get Service URL

1. **After deployment completes:**
   - Your MySQL service will be available at: `https://ecobazaardb.onrender.com`
   - Note this URL for backend configuration

2. **Test the service:**
   - Check the service logs for any errors
   - Verify the service is running

## 🔧 Configuration Details

### Service Information:
- **Service Name**: ecobazaardb
- **Service URL**: https://ecobazaardb.onrender.com
- **Port**: 3306
- **Database**: ecobazaar_db
- **Username**: ecobazaar_user
- **Password**: ecobazaar_password

### Connection String for Backend:
```
jdbc:mysql://ecobazaardb.onrender.com:3306/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
```

## 🔄 Next Steps

After MySQL is deployed:

1. **Update Backend Configuration:**
   - Update `render-backend.yaml` with MySQL service URL
   - Deploy the Spring Boot backend

2. **Run Data Migration:**
   - Use the migration scripts to populate the database
   - Test all endpoints

## 🛠️ Troubleshooting

### Common Issues:

1. **Deployment fails:**
   - Check Dockerfile syntax
   - Verify all files are committed to Git
   - Check Render logs for errors

2. **Service won't start:**
   - Check environment variables
   - Verify init.sql file
   - Check service logs

3. **Connection issues:**
   - Verify service URL
   - Check port configuration
   - Test from backend service

### Useful Commands:

```bash
# Check service status
curl https://ecobazaardb.onrender.com

# View service logs (in Render dashboard)
# Go to your service → Logs tab

# Test database connection (from backend)
mysql -h ecobazaardb.onrender.com -u ecobazaar_user -p ecobazaar_db
```

## 📊 Monitoring

- **Service Health**: Check Render dashboard
- **Logs**: Available in Render service logs
- **Metrics**: Monitor CPU, memory usage in Render

## 🔒 Security Notes

- Change default passwords in production
- Use environment variables for sensitive data
- Enable SSL for database connections
- Configure proper firewall rules

## 📝 Important Notes

1. **Free Plan Limitations:**
   - Service sleeps after 15 minutes of inactivity
   - May take 30-60 seconds to wake up
   - Consider upgrading for production use

2. **Database Persistence:**
   - Data is stored in Render's managed storage
   - Regular backups recommended
   - Consider external database for production

3. **Scaling:**
   - Free plan has limited resources
   - Upgrade to paid plan for better performance
   - Consider dedicated database service for high traffic

## 🎯 Success Checklist

- [ ] MySQL service deployed successfully
- [ ] Service URL obtained: `https://ecobazaardb.onrender.com`
- [ ] Database accessible from backend
- [ ] All tables created successfully
- [ ] Data migration completed
- [ ] Backend connected to MySQL
- [ ] All endpoints working correctly

Your MySQL database service is now ready for production use! 🎉