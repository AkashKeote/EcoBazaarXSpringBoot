#!/bin/bash

# EcoBazaarX Backend Deployment Script
# This script helps with local testing and deployment preparation

echo "🚀 EcoBazaarX Backend Deployment Script"
echo "========================================"

# Check if Maven wrapper exists
if [ ! -f "./mvnw" ]; then
    echo "❌ Maven wrapper not found. Please ensure mvnw is in the current directory."
    exit 1
fi

# Make Maven wrapper executable
chmod +x ./mvnw

# Clean and build the project
echo "🔨 Building the project..."
./mvnw clean install -DskipTests

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo "📦 JAR file created: target/ecobazaar-backend-1.0.0.jar"
    
    # Check if JAR file exists
    if [ -f "target/ecobazaar-backend-1.0.0.jar" ]; then
        echo "🎯 Ready for deployment!"
        echo "📋 To run locally: java -jar target/ecobazaar-backend-1.0.0.jar"
        echo "🌐 Application will be available at: http://localhost:8080/api"
    else
        echo "❌ JAR file not found after build"
        exit 1
    fi
else
    echo "❌ Build failed!"
    exit 1
fi
