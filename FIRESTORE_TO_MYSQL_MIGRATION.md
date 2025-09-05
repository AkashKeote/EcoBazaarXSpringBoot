# Firestore to MySQL Migration Guide

## Overview
This guide helps you migrate your existing Firestore data to MySQL database for the EcoBazaarX backend.

## Current Firestore Collections
Based on your Firestore database, you have these collections:
- `wishlists` - User wishlists
- `carts` - Shopping carts
- `eco_challenges` - Environmental challenges
- `orders` - Order management
- `payment_transactions` - Payment records
- `products` - Product catalog
- `settings` - App settings
- `stores` - Store information
- `user_orders` - User-specific order details
- `user_settings` - User preferences
- `users` - User accounts
- `wishlist_items` - Items in wishlists

## Migration Process

### 1. Export Data from Firestore

#### Option A: Using Firebase Admin SDK (Recommended)
```javascript
// Create a script to export all collections
const admin = require('firebase-admin');
const fs = require('fs');

// Initialize Firebase Admin
const serviceAccount = require('./path-to-service-account.json');
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

async function exportCollection(collectionName) {
  const snapshot = await db.collection(collectionName).get();
  const data = [];
  
  snapshot.forEach(doc => {
    data.push({
      id: doc.id,
      ...doc.data()
    });
  });
  
  fs.writeFileSync(`${collectionName}.json`, JSON.stringify(data, null, 2));
  console.log(`Exported ${data.length} documents from ${collectionName}`);
}

// Export all collections
const collections = [
  'users', 'products', 'orders', 'wishlists', 'wishlist_items',
  'carts', 'eco_challenges', 'payment_transactions', 'stores',
  'user_orders', 'user_settings', 'settings'
];

collections.forEach(collection => {
  exportCollection(collection);
});
```

#### Option B: Using Firebase Console
1. Go to Firebase Console → Firestore Database
2. For each collection, manually export the data
3. Save as JSON files

### 2. Prepare Migration Data

Create JSON files for each collection with the exported data:

```json
// users.json
[
  {
    "id": "175bd26c-ec2a-4ecd-aa9d-99506695e954",
    "email": "user@example.com",
    "name": "John Doe",
    "phone": "+1234567890",
    "role": "USER",
    "createdAt": "2024-01-01T00:00:00Z"
  }
]

// wishlists.json
[
  {
    "id": "175bd26c-ec2a-4ecd-aa9d-99506695e954",
    "userId": "175bd26c-ec2a-4ecd-aa9d-99506695e954",
    "totalItems": 3,
    "createdAt": "2024-09-05T11:21:09Z",
    "updatedAt": "2024-09-05T11:21:11Z"
  }
]
```

### 3. Run Migration

#### Start the Application
```bash
# Start with Docker Compose
docker-compose up -d

# Or run locally
./mvnw spring-boot:run
```

#### Use Migration Endpoints

1. **Check Migration Status:**
```bash
curl http://localhost:8080/api/migration/stats
```

2. **Migrate Users:**
```bash
curl -X POST http://localhost:8080/api/migration/user \
  -H "Content-Type: application/json" \
  -d '{
    "firestoreUserId": "175bd26c-ec2a-4ecd-aa9d-99506695e954",
    "email": "user@example.com",
    "name": "John Doe",
    "phone": "+1234567890",
    "role": "USER"
  }'
```

3. **Migrate Products:**
```bash
curl -X POST http://localhost:8080/api/migration/product \
  -H "Content-Type: application/json" \
  -d '{
    "firestoreProductId": "product-id",
    "name": "Eco Product",
    "description": "Environmentally friendly product",
    "price": 99.99,
    "quantity": 100,
    "category": "Eco-Friendly",
    "storeId": "store-id",
    "storeName": "Eco Store"
  }'
```

4. **Migrate Wishlists:**
```bash
curl -X POST http://localhost:8080/api/migration/wishlist \
  -H "Content-Type: application/json" \
  -d '{
    "firestoreWishlistId": "175bd26c-ec2a-4ecd-aa9d-99506695e954",
    "userId": "175bd26c-ec2a-4ecd-aa9d-99506695e954",
    "totalItems": 3
  }'
```

### 4. Automated Migration Script

Create a Node.js script to automate the migration:

```javascript
const axios = require('axios');
const fs = require('fs');

const BASE_URL = 'http://localhost:8080/api/migration';

async function migrateCollection(collectionName) {
  const data = JSON.parse(fs.readFileSync(`${collectionName}.json`, 'utf8'));
  
  for (const item of data) {
    try {
      const endpoint = getMigrationEndpoint(collectionName);
      const payload = transformData(collectionName, item);
      
      await axios.post(`${BASE_URL}${endpoint}`, payload);
      console.log(`✅ Migrated ${collectionName} item: ${item.id}`);
    } catch (error) {
      console.error(`❌ Error migrating ${collectionName} item ${item.id}:`, error.message);
    }
  }
}

function getMigrationEndpoint(collectionName) {
  const endpoints = {
    'users': '/user',
    'products': '/product',
    'wishlists': '/wishlist',
    'stores': '/store'
  };
  return endpoints[collectionName] || '';
}

function transformData(collectionName, item) {
  // Transform Firestore data to MySQL format
  switch (collectionName) {
    case 'users':
      return {
        firestoreUserId: item.id,
        email: item.email,
        name: item.name,
        phone: item.phone,
        role: item.role
      };
    case 'wishlists':
      return {
        firestoreWishlistId: item.id,
        userId: item.userId,
        totalItems: item.totalItems
      };
    // Add more transformations as needed
    default:
      return item;
  }
}

// Run migration
async function runMigration() {
  const collections = ['users', 'products', 'wishlists', 'stores'];
  
  for (const collection of collections) {
    console.log(`🔄 Migrating ${collection}...`);
    await migrateCollection(collection);
  }
  
  console.log('✅ Migration completed!');
}

runMigration();
```

### 5. Verify Migration

1. **Check Database:**
```sql
-- Connect to MySQL
mysql -u ecobazaar_user -p ecobazaar_db

-- Check tables
SHOW TABLES;

-- Check data
SELECT COUNT(*) FROM users;
SELECT COUNT(*) FROM products;
SELECT COUNT(*) FROM wishlists;
```

2. **Test API Endpoints:**
```bash
# Test user endpoints
curl http://localhost:8080/api/users

# Test product endpoints
curl http://localhost:8080/api/products

# Test wishlist endpoints
curl http://localhost:8080/api/wishlists
```

## Data Mapping

### Firestore → MySQL Field Mapping

| Firestore Field | MySQL Field | Notes |
|----------------|-------------|-------|
| `id` | `{entity}_id` | Custom ID field |
| `createdAt` | `created_at` | Timestamp |
| `updatedAt` | `updated_at` | Timestamp |
| `userId` | `user_id` | Foreign key |
| `productId` | `product_id` | Foreign key |
| `storeId` | `store_id` | Foreign key |

### Data Type Conversions

| Firestore Type | MySQL Type |
|---------------|------------|
| `string` | `VARCHAR` |
| `number` | `DECIMAL` or `INT` |
| `boolean` | `BOOLEAN` |
| `timestamp` | `DATETIME` |
| `array` | `JSON` or separate table |
| `map` | `JSON` |

## Troubleshooting

### Common Issues

1. **Data Type Mismatches:**
   - Check Firestore data types
   - Ensure MySQL schema matches
   - Handle null values properly

2. **Foreign Key Constraints:**
   - Migrate parent tables first (users, stores)
   - Then migrate child tables (products, orders)

3. **Duplicate Data:**
   - Check for existing records before migration
   - Use upsert operations where needed

4. **Large Datasets:**
   - Migrate in batches
   - Use pagination for large collections
   - Monitor memory usage

### Migration Best Practices

1. **Backup First:**
   ```bash
   mysqldump -u ecobazaar_user -p ecobazaar_db > backup.sql
   ```

2. **Test on Staging:**
   - Run migration on staging environment first
   - Verify data integrity
   - Test application functionality

3. **Monitor Performance:**
   - Check database performance during migration
   - Optimize queries if needed
   - Monitor application logs

4. **Rollback Plan:**
   - Keep Firestore data until migration is verified
   - Have rollback scripts ready
   - Document the migration process

## Post-Migration

1. **Update Application:**
   - Remove Firebase dependencies
   - Update frontend to use new API endpoints
   - Test all functionality

2. **Clean Up:**
   - Remove Firebase configuration
   - Archive Firestore data
   - Update documentation

3. **Monitor:**
   - Check application performance
   - Monitor database usage
   - Set up alerts for issues

## Support

If you encounter issues during migration:
1. Check the application logs
2. Verify database connectivity
3. Test individual migration endpoints
4. Review the data transformation logic
