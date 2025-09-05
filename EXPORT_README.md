# Firestore Data Export Guide

## Quick Start

### 1. Install Dependencies
```bash
npm install
```

### 2. Get Firebase Service Account Key
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Select your project
3. Go to **Project Settings** → **Service accounts**
4. Click **"Generate new private key"**
5. Download the JSON file
6. Rename it to `firebase-service-account.json`
7. Place it in the project root directory

### 3. Run Export
```bash
# Export all collections
npm run export

# Or export specific collections
npm run export:users
npm run export:products
npm run export:wishlists
```

## What Gets Exported

The script will export these collections from your Firestore:

- ✅ `users` - User accounts
- ✅ `products` - Product catalog
- ✅ `orders` - Order management
- ✅ `wishlists` - User wishlists
- ✅ `wishlist_items` - Items in wishlists
- ✅ `carts` - Shopping carts
- ✅ `eco_challenges` - Environmental challenges
- ✅ `payment_transactions` - Payment records
- ✅ `stores` - Store information
- ✅ `user_orders` - User-specific order details
- ✅ `user_settings` - User preferences
- ✅ `settings` - App settings

## Output Structure

```
firestore-exports/
├── users.json
├── products.json
├── orders.json
├── wishlists.json
├── wishlist_items.json
├── carts.json
├── eco_challenges.json
├── payment_transactions.json
├── stores.json
├── user_orders.json
├── user_settings.json
├── settings.json
└── export-summary.json
```

## Data Format

Each JSON file contains an array of documents:

```json
[
  {
    "id": "document-id",
    "field1": "value1",
    "field2": "value2",
    "createdAt": "2024-01-01T00:00:00.000Z",
    "updatedAt": "2024-01-01T00:00:00.000Z"
  }
]
```

## Special Data Types

The script automatically converts:

- **Firestore Timestamps** → ISO Date strings
- **Firestore GeoPoints** → `{latitude, longitude}` objects
- **Nested objects** → Recursively processed
- **Arrays** → Preserved with type conversion

## Export Summary

After export, you'll get a summary file with:

```json
{
  "exportDate": "2024-01-01T00:00:00.000Z",
  "totalCollections": 12,
  "totalDocuments": 1500,
  "results": {
    "users": 100,
    "products": 500,
    "orders": 200,
    "wishlists": 50,
    "wishlist_items": 300,
    "carts": 75,
    "eco_challenges": 25,
    "payment_transactions": 150,
    "stores": 30,
    "user_orders": 400,
    "user_settings": 100,
    "settings": 5
  }
}
```

## Troubleshooting

### Common Issues

1. **"Firebase service account file not found"**
   - Make sure `firebase-service-account.json` is in the project root
   - Check the file name is exactly correct

2. **"Permission denied"**
   - Verify your service account has Firestore read permissions
   - Check if your Firebase project is active

3. **"Collection not found"**
   - Some collections might be empty or not exist
   - This is normal and won't cause errors

4. **Rate limiting**
   - The script includes delays between requests
   - If you get rate limit errors, increase the delay in the script

### Large Datasets

For large datasets:

1. **Export in batches:**
   ```bash
   # Export specific collections first
   npm run export:users
   npm run export:products
   ```

2. **Monitor memory usage:**
   - Large collections might use significant memory
   - Consider exporting during off-peak hours

3. **Check file sizes:**
   - Large JSON files might be difficult to process
   - Consider splitting very large collections

## Security Notes

⚠️ **Important Security Considerations:**

1. **Never commit service account keys to version control**
2. **Add `firebase-service-account.json` to `.gitignore`**
3. **Delete the service account file after export**
4. **Use environment variables in production**

## Next Steps

After successful export:

1. **Verify the exported data:**
   ```bash
   # Check file sizes
   ls -la firestore-exports/
   
   # Check document counts
   cat firestore-exports/export-summary.json
   ```

2. **Start MySQL migration:**
   ```bash
   # Start the backend
   docker-compose up -d
   
   # Run migration using the exported data
   # (See FIRESTORE_TO_MYSQL_MIGRATION.md)
   ```

3. **Clean up:**
   ```bash
   # Remove service account file
   rm firebase-service-account.json
   
   # Archive exported data
   tar -czf firestore-backup-$(date +%Y%m%d).tar.gz firestore-exports/
   ```

## Support

If you encounter issues:

1. Check the console output for error messages
2. Verify your Firebase project configuration
3. Ensure you have the correct permissions
4. Check the export summary for any missing collections

## Example Output

```
🚀 Starting Firestore data export...

🔄 Exporting users...
✅ Exported 100 documents from users
🔄 Exporting products...
✅ Exported 500 documents from products
🔄 Exporting orders...
✅ Exported 200 documents from orders
🔄 Exporting wishlists...
✅ Exported 50 documents from wishlists
🔄 Exporting wishlist_items...
✅ Exported 300 documents from wishlist_items
🔄 Exporting carts...
✅ Exported 75 documents from carts
🔄 Exporting eco_challenges...
✅ Exported 25 documents from eco_challenges
🔄 Exporting payment_transactions...
✅ Exported 150 documents from payment_transactions
🔄 Exporting stores...
✅ Exported 30 documents from stores
🔄 Exporting user_orders...
✅ Exported 400 documents from user_orders
🔄 Exporting user_settings...
✅ Exported 100 documents from user_settings
🔄 Exporting settings...
✅ Exported 5 documents from settings

📊 Export Summary:
==================
Total Collections: 12
Total Documents: 1935

Per Collection:
  users: 100 documents
  products: 500 documents
  orders: 200 documents
  wishlists: 50 documents
  wishlist_items: 300 documents
  carts: 75 documents
  eco_challenges: 25 documents
  payment_transactions: 150 documents
  stores: 30 documents
  user_orders: 400 documents
  user_settings: 100 documents
  settings: 5 documents

✅ Export completed! Files saved in: ./firestore-exports

📁 Exported files:
  users.json (45.2 KB)
  products.json (234.1 KB)
  orders.json (89.3 KB)
  wishlists.json (12.4 KB)
  wishlist_items.json (67.8 KB)
  carts.json (18.9 KB)
  eco_challenges.json (8.2 KB)
  payment_transactions.json (34.5 KB)
  stores.json (15.6 KB)
  user_orders.json (156.7 KB)
  user_settings.json (23.1 KB)
  settings.json (1.2 KB)
  export-summary.json (0.8 KB)
```
