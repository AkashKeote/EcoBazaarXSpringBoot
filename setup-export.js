const fs = require('fs');
const path = require('path');

console.log('🔧 Setting up Firestore Export...\n');

// Check if firebase-service-account.json exists
const serviceAccountPath = './firebase-service-account.json';
if (!fs.existsSync(serviceAccountPath)) {
  console.log('❌ Firebase service account file not found!');
  console.log('\n📋 To get your service account file:');
  console.log('1. Go to Firebase Console → Project Settings');
  console.log('2. Go to "Service accounts" tab');
  console.log('3. Click "Generate new private key"');
  console.log('4. Download the JSON file');
  console.log('5. Rename it to "firebase-service-account.json"');
  console.log('6. Place it in the project root directory');
  console.log('\n⚠️  Make sure to add this file to .gitignore for security!');
  process.exit(1);
}

console.log('✅ Firebase service account file found');

// Check if node_modules exists
if (!fs.existsSync('./node_modules')) {
  console.log('📦 Installing dependencies...');
  console.log('Run: npm install');
  console.log('Then run: npm run export');
} else {
  console.log('✅ Dependencies already installed');
}

console.log('\n🚀 Ready to export!');
console.log('\nAvailable commands:');
console.log('  npm run export        - Export all collections');
console.log('  npm run export:users  - Export only users collection');
console.log('  npm run export:products - Export only products collection');
console.log('  npm run export:wishlists - Export only wishlists collection');
console.log('\n📁 Exported files will be saved in: ./firestore-exports/');
