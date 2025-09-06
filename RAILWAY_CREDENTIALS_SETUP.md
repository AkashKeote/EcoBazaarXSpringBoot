# Railway MySQL Credentials Setup for Render Backend

## 🔑 **Your Railway MySQL Credentials**

- **Username**: `root`
- **Password**: `cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr`
- **Database**: `ecobazaar_db`
- **Host**: `mysql-production-59d9.up.railway.app`
- **Port**: `33060`

## 🚀 **Quick Setup (2 Minutes)**

### **Option A: Use Automation Scripts**

#### **PowerShell Script:**
```powershell
.\update-render-railway-creds.ps1
```

#### **Batch Script:**
```cmd
update-render-railway-creds.bat
```

### **Option B: Manual Setup**

1. **Go to Render Dashboard**:
   - Visit: https://dashboard.render.com
   - Open your backend service: `EcoBazaarXSpringBoot-1`

2. **Update Environment Variables**:
   - Click **Environment** tab
   - Add/Update these variables:

   ```
   SPRING_PROFILES_ACTIVE = prod
   DATABASE_URL = jdbc:mysql://mysql-production-59d9.up.railway.app:33060/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
   DB_USERNAME = root
   DB_PASSWORD = cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr
   ```

3. **Save Changes**:
   - Click **Save Changes**
   - Backend will automatically redeploy

## 🔍 **Test Your Setup**

### **After Deployment Completes:**

1. **Health Check**:
   ```bash
   curl https://ecobazaarxspringboot-1.onrender.com/api/health
   ```

2. **Test Migration Endpoint**:
   ```bash
   curl https://ecobazaarxspringboot-1.onrender.com/api/migration/test
   ```

3. **Start Data Migration**:
   ```bash
   curl -X POST https://ecobazaarxspringboot-1.onrender.com/api/migration/start
   ```

## 📊 **Expected Results**

After setup:
- ✅ **Database Connected**: Railway MySQL connection established
- ✅ **No More Errors**: No "Communications link failure"
- ✅ **Backend Running**: Application starts successfully
- ✅ **Ready for Migration**: Firebase data migration ready

## 🎯 **Next Steps**

1. **Add Firebase Export Files**:
   - Place your Firebase export files in `src/main/resources/firebase-exports/`
   - Commit and push to trigger deployment

2. **Run Data Migration**:
   - After deployment, run the migration endpoint
   - Verify data in Railway MySQL database

3. **Test API Endpoints**:
   - Test all your API endpoints
   - Verify data is accessible

## 🆘 **Troubleshooting**

### **If Connection Fails:**
1. **Check Credentials**: Verify username/password are correct
2. **Check Database Name**: Ensure `ecobazaar_db` exists in Railway
3. **Check Port**: Use port `33060` for public connection
4. **Check Variables**: Ensure all environment variables are set

### **Connection String Details:**
```properties
# Your specific connection string
jdbc:mysql://mysql-production-59d9.up.railway.app:33060/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
```

## 🚀 **Ready for Firebase Migration**

Once the backend is connected to Railway MySQL:
1. Add your Firebase export files
2. Push to GitHub to trigger deployment
3. Run the migration endpoint
4. Verify data in Railway MySQL

Your Railway MySQL database is now ready to receive your Firebase data! 🎉
