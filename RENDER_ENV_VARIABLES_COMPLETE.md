# Complete Render Environment Variables

## 🚀 **Complete Environment Variables for Render Backend**

Add these **ALL** environment variables to your Render backend service:

```
SPRING_PROFILES_ACTIVE = prod
DATABASE_URL = jdbc:mysql://mysql-production-59d9.up.railway.app:3306/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
DB_USERNAME = root
DB_PASSWORD = cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr
SERVER_PORT = 10000
```

## 🔧 **Server Port Explanation**

- **SERVER_PORT = 10000** - Render ke liye standard port
- Render automatically port 10000 use karta hai
- Aapko ye add karna chahiye environment variables mein

## 📝 **Complete Setup Steps**

1. **Go to Render Dashboard**: https://dashboard.render.com
2. **Open Backend Service**: `EcoBazaarXSpringBoot-1`
3. **Environment Tab**: Click Environment tab
4. **Add ALL Variables**:
   ```
   SPRING_PROFILES_ACTIVE = prod
   DATABASE_URL = jdbc:mysql://mysql-production-59d9.up.railway.app:3306/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
   DB_USERNAME = root
   DB_PASSWORD = cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr
   SERVER_PORT = 10000
   ```
5. **Save Changes**: Click Save Changes

## 🔍 **Test After Deployment**

```bash
# Health check
curl https://ecobazaarxspringboot-1.onrender.com/api/health

# Start migration
curl -X POST https://ecobazaarxspringboot-1.onrender.com/api/migration/start
```

## 🎯 **Expected Results**

- ✅ **Database Connected**: Railway MySQL connection established
- ✅ **Server Running**: Backend running on port 10000
- ✅ **No More Errors**: No "Communications link failure"
- ✅ **Data Migrated**: Firebase data copied to MySQL

## 🚀 **Ready to Go!**

Ab aapka backend properly configured hai with all required environment variables! 🎉
