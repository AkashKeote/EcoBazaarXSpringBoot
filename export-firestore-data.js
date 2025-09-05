const admin = require('firebase-admin');
const fs = require('fs');
const path = require('path');

// Initialize Firebase Admin SDK
// You need to download your service account key from Firebase Console
const serviceAccount = require('./firebase-service-account.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

// Create exports directory
const exportsDir = './firestore-exports';
if (!fs.existsSync(exportsDir)) {
  fs.mkdirSync(exportsDir);
}

// Collections to export based on your Firestore structure
const collections = [
  'users',
  'products', 
  'orders',
  'wishlists',
  'wishlist_items',
  'carts',
  'eco_challenges',
  'payment_transactions',
  'stores',
  'user_orders',
  'user_settings',
  'settings'
];

/**
 * Export a single collection to JSON file
 */
async function exportCollection(collectionName) {
  try {
    console.log(`🔄 Exporting ${collectionName}...`);
    
    const snapshot = await db.collection(collectionName).get();
    const data = [];
    
    snapshot.forEach(doc => {
      const docData = doc.data();
      
      // Convert Firestore timestamps to ISO strings
      const processedData = processFirestoreData(docData);
      
      data.push({
        id: doc.id,
        ...processedData
      });
    });
    
    // Write to JSON file
    const filePath = path.join(exportsDir, `${collectionName}.json`);
    fs.writeFileSync(filePath, JSON.stringify(data, null, 2));
    
    console.log(`✅ Exported ${data.length} documents from ${collectionName}`);
    return data.length;
    
  } catch (error) {
    console.error(`❌ Error exporting ${collectionName}:`, error.message);
    return 0;
  }
}

/**
 * Process Firestore data to handle special types
 */
function processFirestoreData(data) {
  const processed = {};
  
  for (const [key, value] of Object.entries(data)) {
    if (value && typeof value === 'object') {
      // Handle Firestore Timestamp
      if (value.seconds !== undefined && value.nanoseconds !== undefined) {
        processed[key] = new Date(value.seconds * 1000).toISOString();
      }
      // Handle Firestore GeoPoint
      else if (value.latitude !== undefined && value.longitude !== undefined) {
        processed[key] = {
          latitude: value.latitude,
          longitude: value.longitude
        };
      }
      // Handle nested objects
      else if (value.constructor === Object) {
        processed[key] = processFirestoreData(value);
      }
      // Handle arrays
      else if (Array.isArray(value)) {
        processed[key] = value.map(item => 
          typeof item === 'object' ? processFirestoreData(item) : item
        );
      }
      else {
        processed[key] = value;
      }
    } else {
      processed[key] = value;
    }
  }
  
  return processed;
}

/**
 * Export all collections
 */
async function exportAllCollections() {
  console.log('🚀 Starting Firestore data export...\n');
  
  const results = {};
  let totalDocuments = 0;
  
  for (const collection of collections) {
    const count = await exportCollection(collection);
    results[collection] = count;
    totalDocuments += count;
    
    // Add small delay to avoid rate limiting
    await new Promise(resolve => setTimeout(resolve, 100));
  }
  
  // Create summary file
  const summary = {
    exportDate: new Date().toISOString(),
    totalCollections: collections.length,
    totalDocuments: totalDocuments,
    results: results
  };
  
  fs.writeFileSync(
    path.join(exportsDir, 'export-summary.json'), 
    JSON.stringify(summary, null, 2)
  );
  
  console.log('\n📊 Export Summary:');
  console.log('==================');
  console.log(`Total Collections: ${collections.length}`);
  console.log(`Total Documents: ${totalDocuments}`);
  console.log('\nPer Collection:');
  
  for (const [collection, count] of Object.entries(results)) {
    console.log(`  ${collection}: ${count} documents`);
  }
  
  console.log(`\n✅ Export completed! Files saved in: ${exportsDir}`);
  console.log('\n📁 Exported files:');
  
  const files = fs.readdirSync(exportsDir);
  files.forEach(file => {
    const filePath = path.join(exportsDir, file);
    const stats = fs.statSync(filePath);
    console.log(`  ${file} (${(stats.size / 1024).toFixed(2)} KB)`);
  });
}

/**
 * Export specific collection
 */
async function exportSpecificCollection(collectionName) {
  if (!collections.includes(collectionName)) {
    console.error(`❌ Collection '${collectionName}' not found in the list`);
    console.log('Available collections:', collections.join(', '));
    return;
  }
  
  console.log(`🔄 Exporting specific collection: ${collectionName}`);
  const count = await exportCollection(collectionName);
  console.log(`✅ Exported ${count} documents from ${collectionName}`);
}

// Handle command line arguments
const args = process.argv.slice(2);

if (args.length > 0) {
  // Export specific collection
  exportSpecificCollection(args[0]);
} else {
  // Export all collections
  exportAllCollections();
}

// Handle process termination
process.on('SIGINT', () => {
  console.log('\n⏹️ Export interrupted by user');
  process.exit(0);
});

process.on('uncaughtException', (error) => {
  console.error('❌ Uncaught Exception:', error);
  process.exit(1);
});
