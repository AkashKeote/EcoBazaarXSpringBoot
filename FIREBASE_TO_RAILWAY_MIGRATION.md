# Firebase to Railway MySQL Migration Guide

## 🎯 **Collections to Migrate**

Based on your `DataMigrationService`, you need to migrate these Firebase collections to Railway MySQL:

### **Core Collections**
1. **users** - User accounts and profiles
2. **products** - Product catalog
3. **stores** - Store information
4. **wishlists** - User wishlists
5. **wishlistItems** - Items in wishlists
6. **carts** - Shopping carts
7. **ecoChallenges** - Eco challenges
8. **paymentTransactions** - Payment records
9. **userOrders** - Order history
10. **userSettings** - User preferences

## 🚀 **Step 1: Export Data from Firebase Console**

### **Method 1: Firebase Console Export**
1. Go to [Firebase Console](https://console.firebase.google.com)
2. Select your project
3. Go to **Firestore Database**
4. For each collection:
   - Click on the collection name
   - Click **Export** (if available)
   - Download as JSON

### **Method 2: Firebase CLI Export**
```bash
# Install Firebase CLI
npm install -g firebase-tools

# Login to Firebase
firebase login

# Export all collections
firebase firestore:export ./firebase-export --project your-project-id
```

### **Method 3: Manual Export (if needed)**
1. Go to each collection in Firebase Console
2. Copy the data manually
3. Save as JSON files in `src/main/resources/firebase-exports/`

## 🗂️ **Step 2: Organize Export Files**

Create this directory structure:
```
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

## 🔧 **Step 3: Update Render Environment Variables**

### **Option A: Manual Update**
1. Go to [Render Dashboard](https://dashboard.render.com)
2. Open your backend service: `EcoBazaarXSpringBoot-1`
3. Click **Environment** tab
4. Add these variables:

```
SPRING_PROFILES_ACTIVE = prod
DATABASE_URL = jdbc:mysql://mysql-production-59d9.up.railway.app:33060/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
DB_USERNAME = [your_railway_username]
DB_PASSWORD = [your_railway_password]
```

### **Option B: Use Scripts**
```powershell
.\update-railway-mysql.ps1
```

## 🚀 **Step 4: Deploy Backend with Migration**

### **4.1: Add Export Files to Project**
```bash
# Create exports directory
mkdir -p src/main/resources/firebase-exports

# Add your Firebase export files here
# users.json, products.json, etc.
```

### **4.2: Commit and Push**
```bash
git add .
git commit -m "Add Firebase export files and Railway MySQL configuration"
git push origin main
```

### **4.3: Monitor Deployment**
- Render will automatically deploy
- Check logs for successful database connection
- Look for migration completion messages

## 🔍 **Step 5: Run Data Migration**

### **Option A: Via API Endpoint**
```bash
# After deployment, trigger migration
curl -X POST https://ecobazaarxspringboot-1.onrender.com/api/migration/start
```

### **Option B: Check Migration Status**
```bash
# Test health endpoint
curl https://ecobazaarxspringboot-1.onrender.com/api/health

# Test migration endpoint
curl https://ecobazaarxspringboot-1.onrender.com/api/migration/test
```

## 📊 **Step 6: Verify Migration**

### **Check Railway MySQL Database**
1. Go to Railway Dashboard
2. Open your MySQL service
3. Click **Data** tab
4. Verify tables are created with data:
   - `users`
   - `products`
   - `stores`
   - `wishlists`
   - `wishlist_items`
   - `carts`
   - `eco_challenges`
   - `payment_transactions`
   - `user_orders`
   - `user_settings`

### **Test API Endpoints**
```bash
# Test user endpoints
curl https://ecobazaarxspringboot-1.onrender.com/api/users

# Test product endpoints
curl https://ecobazaarxspringboot-1.onrender.com/api/products

# Test store endpoints
curl https://ecobazaarxspringboot-1.onrender.com/api/stores
```

## 🛠️ **Troubleshooting**

### **If Migration Fails:**
1. **Check Export Files**: Ensure JSON files are valid
2. **Check Database Connection**: Verify Railway MySQL credentials
3. **Check Logs**: Look for specific error messages
4. **Check File Paths**: Ensure files are in correct directory

### **Common Issues:**
- **File Not Found**: Check `src/main/resources/firebase-exports/` directory
- **JSON Parse Error**: Validate JSON format
- **Database Connection**: Verify Railway MySQL settings
- **Permission Issues**: Check file permissions

## 🎯 **Expected Results**

After successful migration:
- ✅ **Database Connected**: Railway MySQL connection established
- ✅ **Tables Created**: All entity tables created
- ✅ **Data Migrated**: Firebase data copied to MySQL
- ✅ **API Working**: All endpoints responding
- ✅ **No Errors**: Clean deployment logs

## 📝 **Quick Commands**

```bash
# 1. Export from Firebase (if using CLI)
firebase firestore:export ./firebase-export --project your-project-id

# 2. Copy files to project
cp ./firebase-export/*.json src/main/resources/firebase-exports/

# 3. Commit and push
git add .
git commit -m "Add Firebase export files for migration"
git push origin main

# 4. Monitor deployment
# Check Render dashboard for deployment progress

# 5. Test migration
curl -X POST https://ecobazaarxspringboot-1.onrender.com/api/migration/start
```

This will migrate all your Firebase data to Railway MySQL and deploy your backend! 🚀
