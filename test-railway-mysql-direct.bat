@echo off
REM Batch script to test Railway MySQL database directly

echo 🔍 Testing Railway MySQL Database Directly...

echo.
echo 📋 Railway MySQL Connection Details:
echo Host: mysql-production-59d9.up.railway.app
echo Port: 3306
echo Username: root
echo Password: cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr
echo Database: railway

echo.
echo 🚀 Testing Connection Methods:

echo.
echo 🔧 Method 1: Test with telnet (Basic connectivity)
echo Testing if port 3306 is accessible...

telnet mysql-production-59d9.up.railway.app 3306
if %errorlevel% equ 0 (
    echo ✅ Port 3306 is accessible!
) else (
    echo ❌ Port 3306 is not accessible
)

echo.
echo 🔧 Method 2: Test with MySQL Client (if available)
echo Checking if MySQL client is installed...

mysql --version >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ MySQL client found
    echo.
    echo Testing direct MySQL connection...
    echo Command: mysql -h mysql-production-59d9.up.railway.app -P 3306 -u root -p
    echo Password: cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr
    echo.
    echo Run this command manually to test:
    echo mysql -h mysql-production-59d9.up.railway.app -P 3306 -u root -p
) else (
    echo ❌ MySQL client not found. Install MySQL client to test directly.
    echo Download from: https://dev.mysql.com/downloads/mysql/
)

echo.
echo 🔧 Method 3: Test with ping
echo Testing if the host is reachable...

ping -n 1 mysql-production-59d9.up.railway.app >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Host is reachable
) else (
    echo ❌ Host is not reachable
)

echo.
echo 🔧 Method 4: Test Alternative Connection Strings
echo Testing proxy connection...

telnet centerbeam.proxy.rlwy.net 36676
if %errorlevel% equ 0 (
    echo ✅ Proxy port 36676 is accessible!
) else (
    echo ❌ Proxy port 36676 is not accessible
)

echo.
echo 📊 Summary:
echo If any of the above tests show ✅, your Railway MySQL is accessible
echo If all tests show ❌, there might be a network or service issue
echo.
echo 🚀 Next Steps:
echo 1. Check Railway Dashboard to ensure MySQL service is running
echo 2. Try the proxy connection in your backend
echo 3. Contact Railway support if all tests fail

pause
