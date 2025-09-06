# Manual Render Environment Variables Setup

## 🔑 **Railway MySQL Credentials**

- **Username**: `root`
- **Password**: `cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr`
- **Database**: `ecobazaar_db`
- **Host**: `mysql-production-59d9.up.railway.app`
- **Port**: `33060`

## 🚀 **Manual Setup Steps**

### **Step 1: Go to Render Dashboard**
1. Visit: https://dashboard.render.com
2. Login to your account
3. Find your backend service: `EcoBazaarXSpringBoot-1`

### **Step 2: Update Environment Variables**
1. Click on your backend service
2. Go to **Environment** tab
3. Add/Update these variables:

```
SPRING_PROFILES_ACTIVE = prod
DATABASE_URL = jdbc:mysql://mysql-production-59d9.up.railway.app:33060/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
DB_USERNAME = root
DB_PASSWORD = cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr
```

### **Step 3: Save Changes**
1. Click **Save Changes**
2. Backend will automatically redeploy
3. Monitor deployment progress

## 🔍 **Test After Deployment**

### **Health Check:**
```bash
curl https://ecobazaarxspringboot-1.onrender.com/api/health
```

### **Start Migration:**
```bash
curl -X POST https://ecobazaarxspringboot-1.onrender.com/api/migration/start
```

## 📊 **Expected Results**

After setup:
- ✅ **Database Connected**: Railway MySQL connection established
- ✅ **No More Errors**: No "Communications link failure"
- ✅ **Backend Running**: Application starts successfully
- ✅ **Data Migrated**: Firebase data copied to MySQL

## 🎯 **Next Steps**

1. **Update Environment Variables** (Manual)
2. **Wait for Deployment** (Automatic)
3. **Test Connection** (Manual)
4. **Run Migration** (Manual)

Your Firebase data is ready to migrate to Railway MySQL! 🚀
