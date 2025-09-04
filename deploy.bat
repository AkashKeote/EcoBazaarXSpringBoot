@echo off
REM EcoBazaarX Backend Deployment Script for Windows
REM This script helps with local testing and deployment preparation

echo 🚀 EcoBazaarX Backend Deployment Script
echo ========================================

REM Check if Maven wrapper exists
if not exist "mvnw.cmd" (
    echo ❌ Maven wrapper not found. Please ensure mvnw.cmd is in the current directory.
    exit /b 1
)

REM Clean and build the project
echo 🔨 Building the project...
call mvnw.cmd clean install -DskipTests

if %ERRORLEVEL% EQU 0 (
    echo ✅ Build successful!
    echo 📦 JAR file created: target\ecobazaar-backend-1.0.0.jar
    
    REM Check if JAR file exists
    if exist "target\ecobazaar-backend-1.0.0.jar" (
        echo 🎯 Ready for deployment!
        echo 📋 To run locally: java -jar target\ecobazaar-backend-1.0.0.jar
        echo 🌐 Application will be available at: http://localhost:8080/api
    ) else (
        echo ❌ JAR file not found after build
        exit /b 1
    )
) else (
    echo ❌ Build failed!
    exit /b 1
)
