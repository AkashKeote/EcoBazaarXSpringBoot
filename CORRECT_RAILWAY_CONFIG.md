# Correct Railway MySQL Configuration

## 🔑 **Actual Railway MySQL Details**

From your Railway service:
- **Database Name**: `railway`
- **Username**: `root`
- **Password**: `cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr`
- **Public Host**: `mysql-production-59d9.up.railway.app`
- **Public Port**: `3306`
- **Private Host**: `mysql.railway.internal`
- **Private Port**: `3306`

## 🚀 **Correct Render Environment Variables**

Update your Render backend service with these **CORRECT** variables:

```
SPRING_PROFILES_ACTIVE = prod
DATABASE_URL = jdbc:mysql://mysql-production-59d9.up.railway.app:3306/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
DB_USERNAME = root
DB_PASSWORD = cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr
```

## 🔧 **Key Changes Made:**

1. **Database Name**: Changed from `ecobazaar_db` to `railway`
2. **Port**: Changed from `33060` to `3306`
3. **Connection String**: Updated to use correct database name and port

## 📝 **Manual Setup Steps:**

1. **Go to Render Dashboard**: https://dashboard.render.com
2. **Open Backend Service**: `EcoBazaarXSpringBoot-1`
3. **Environment Tab**: Click Environment tab
4. **Update Variables**:
   ```
   SPRING_PROFILES_ACTIVE = prod
   DATABASE_URL = jdbc:mysql://mysql-production-59d9.up.railway.app:3306/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
   DB_USERNAME = root
   DB_PASSWORD = cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr
   ```
5. **Save Changes**: Click Save Changes
6. **Wait for Deployment**: Backend will redeploy automatically

## 🔍 **Test After Deployment:**

```bash
# Health check
curl https://ecobazaarxspringboot-1.onrender.com/api/health

# Start migration
curl -X POST https://ecobazaarxspringboot-1.onrender.com/api/migration/start
```

## 🎯 **Expected Results:**

- ✅ **Database Connected**: Railway MySQL connection established
- ✅ **No More Errors**: No "Communications link failure"
- ✅ **Backend Running**: Application starts successfully
- ✅ **Data Migrated**: Firebase data copied to MySQL

## 🆘 **If Still Having Issues:**

Try these alternative connection strings:

### **Option 1: Public Connection**
```
jdbc:mysql://mysql-production-59d9.up.railway.app:3306/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
```

### **Option 2: Proxy Connection**
```
jdbc:mysql://centerbeam.proxy.rlwy.net:36676/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
```

This should resolve all database connection issues! 🚀
