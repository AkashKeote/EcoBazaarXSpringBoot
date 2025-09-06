# Quick Firebase to Railway MySQL Migration Setup

## 🚀 **Complete Process (10 Minutes)**

### **Step 1: Export from Firebase Console**
1. Go to [Firebase Console](https://console.firebase.google.com)
2. Select your project → **Firestore Database**
3. Export these collections as JSON:
   - `users`
   - `products`
   - `stores`
   - `wishlists`
   - `wishlistItems`
   - `carts`
   - `ecoChallenges`
   - `paymentTransactions`
   - `userOrders`
   - `userSettings`

### **Step 2: Place Export Files**
```bash
# Place your Firebase export files in:
src/main/resources/firebase-exports/
├── users.json
├── products.json
├── stores.json
├── wishlists.json
├── wishlistItems.json
├── carts.json
├── ecoChallenges.json
├── paymentTransactions.json
├── userOrders.json
└── userSettings.json
```

### **Step 3: Update Render Environment Variables**
1. Go to [Render Dashboard](https://dashboard.render.com)
2. Open `EcoBazaarXSpringBoot-1` → **Environment** tab
3. Add these variables:
   ```
   SPRING_PROFILES_ACTIVE = prod
   DATABASE_URL = jdbc:mysql://mysql-production-59d9.up.railway.app:33060/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
   DB_USERNAME = [your_railway_username]
   DB_PASSWORD = [your_railway_password]
   ```

### **Step 4: Deploy Backend**
```bash
# Commit and push to trigger deployment
git add .
git commit -m "Add Firebase export files for Railway MySQL migration"
git push origin main
```

### **Step 5: Run Migration**
```bash
# After deployment completes, trigger migration
curl -X POST https://ecobazaarxspringboot-1.onrender.com/api/migration/start
```

## 🔧 **Automated Setup**

### **PowerShell Script:**
```powershell
.\complete-migration-setup.ps1
```

### **Batch Script:**
```cmd
complete-migration-setup.bat
```

## 📊 **Expected Results**

After setup:
- ✅ **Database Connected**: Railway MySQL connection established
- ✅ **Data Migrated**: All Firebase data copied to MySQL
- ✅ **Backend Running**: Application starts successfully
- ✅ **API Working**: All endpoints responding

## 🔍 **Test Your Setup**

```bash
# Health check
curl https://ecobazaarxspringboot-1.onrender.com/api/health

# Test migration
curl https://ecobazaarxspringboot-1.onrender.com/api/migration/test

# Test API endpoints
curl https://ecobazaarxspringboot-1.onrender.com/api/users
curl https://ecobazaarxspringboot-1.onrender.com/api/products
```

## 🎯 **Quick Commands**

```bash
# 1. Export from Firebase (if using CLI)
firebase firestore:export ./firebase-export --project your-project-id

# 2. Copy files to project
cp ./firebase-export/*.json src/main/resources/firebase-exports/

# 3. Deploy backend
git add .
git commit -m "Add Firebase export files for migration"
git push origin main

# 4. Run migration
curl -X POST https://ecobazaarxspringboot-1.onrender.com/api/migration/start
```

## 🆘 **Troubleshooting**

- **Missing Files**: Add empty JSON arrays `[]` for missing collections
- **Connection Issues**: Verify Railway MySQL credentials
- **Deployment Fails**: Check Render logs for specific errors
- **Migration Fails**: Verify JSON file format and database connection

This will migrate all your Firebase data to Railway MySQL and deploy your backend! 🚀
