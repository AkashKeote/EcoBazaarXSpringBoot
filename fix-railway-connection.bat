@echo off
REM Batch script to fix Railway MySQL connection issues

echo 🔧 Fixing Railway MySQL Connection Issues...

echo.
echo ❌ Current Issue: Communications link failure
echo 🔍 Possible Causes:
echo 1. Railway MySQL service is sleeping
echo 2. Connection string is incorrect
echo 3. Network/firewall issues

echo.
echo 🚀 Solutions to Try:

echo.
echo 📋 Step 1: Check Railway Service Status
echo 1. Go to Railway Dashboard
echo 2. Open your MySQL service
echo 3. Check if it's running (not sleeping)
echo 4. If sleeping, click 'Wake Up' or restart the service

echo.
echo 📋 Step 2: Try Alternative Connection Strings

echo.
echo 🔧 Option A: Proxy Connection (Recommended)
echo Update Render environment variable to:
echo.
echo DATABASE_URL = jdbc:mysql://centerbeam.proxy.rlwy.net:36676/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000

echo.
echo 🔧 Option B: Private Connection
echo DATABASE_URL = jdbc:mysql://mysql.railway.internal:3306/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000

echo.
echo 🔧 Option C: Different Port
echo DATABASE_URL = jdbc:mysql://mysql-production-59d9.up.railway.app:33060/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000

echo.
echo 📋 Step 3: Manual Setup
echo 1. Go to Render Dashboard: https://dashboard.render.com
echo 2. Open: EcoBazaarXSpringBoot-1
echo 3. Environment tab
echo 4. Update DATABASE_URL with one of the options above
echo 5. Save Changes

echo.
echo 🎯 Expected Results After Fix:
echo ✅ HikariCP Started: Connection pool initializes successfully
echo ✅ Database Connected: No more 'Communications link failure'
echo ✅ Backend Running: Application starts completely
echo ✅ Migration Ready: Firebase data migration can proceed

echo.
echo 🚀 Try the proxy connection first - it's most likely to work!
echo centerbeam.proxy.rlwy.net:36676

pause
