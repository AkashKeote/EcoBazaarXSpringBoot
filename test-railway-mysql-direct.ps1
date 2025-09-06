# PowerShell script to test Railway MySQL database directly

Write-Host "🔍 Testing Railway MySQL Database Directly..." -ForegroundColor Green

Write-Host ""
Write-Host "📋 Railway MySQL Connection Details:" -ForegroundColor Cyan
Write-Host "Host: mysql-production-59d9.up.railway.app" -ForegroundColor White
Write-Host "Port: 3306" -ForegroundColor White
Write-Host "Username: root" -ForegroundColor White
Write-Host "Password: cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr" -ForegroundColor White
Write-Host "Database: railway" -ForegroundColor White

Write-Host ""
Write-Host "🚀 Testing Connection Methods:" -ForegroundColor Yellow

Write-Host ""
Write-Host "🔧 Method 1: Test with telnet (Basic connectivity)" -ForegroundColor Green
Write-Host "Testing if port 3306 is accessible..." -ForegroundColor White

try {
    $tcpClient = New-Object System.Net.Sockets.TcpClient
    $connect = $tcpClient.BeginConnect("mysql-production-59d9.up.railway.app", 3306, $null, $null)
    $wait = $connect.AsyncWaitHandle.WaitOne(5000, $false)
    
    if ($wait) {
        $tcpClient.EndConnect($connect)
        Write-Host "✅ Port 3306 is accessible!" -ForegroundColor Green
        $tcpClient.Close()
    } else {
        Write-Host "❌ Port 3306 is not accessible (timeout)" -ForegroundColor Red
    }
} catch {
    Write-Host "❌ Port 3306 connection failed: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "🔧 Method 2: Test with MySQL Client (if available)" -ForegroundColor Green
Write-Host "Checking if MySQL client is installed..." -ForegroundColor White

try {
    $mysqlVersion = mysql --version
    Write-Host "✅ MySQL client found: $mysqlVersion" -ForegroundColor Green
    Write-Host ""
    Write-Host "Testing direct MySQL connection..." -ForegroundColor Yellow
    Write-Host "Command: mysql -h mysql-production-59d9.up.railway.app -P 3306 -u root -p" -ForegroundColor Gray
    Write-Host "Password: cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr" -ForegroundColor Gray
    Write-Host ""
    Write-Host "Run this command manually to test:" -ForegroundColor White
    Write-Host "mysql -h mysql-production-59d9.up.railway.app -P 3306 -u root -p" -ForegroundColor Cyan
} catch {
    Write-Host "❌ MySQL client not found. Install MySQL client to test directly." -ForegroundColor Red
    Write-Host "Download from: https://dev.mysql.com/downloads/mysql/" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "🔧 Method 3: Test with PowerShell MySQL Module" -ForegroundColor Green
Write-Host "Checking if MySQL PowerShell module is available..." -ForegroundColor White

try {
    Import-Module MySql.Data -ErrorAction Stop
    Write-Host "✅ MySQL PowerShell module found" -ForegroundColor Green
    
    Write-Host ""
    Write-Host "Testing connection with PowerShell..." -ForegroundColor Yellow
    
    $connectionString = "Server=mysql-production-59d9.up.railway.app;Port=3306;Database=railway;Uid=root;Pwd=cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr;"
    
    try {
        $connection = New-Object MySql.Data.MySqlClient.MySqlConnection($connectionString)
        $connection.Open()
        Write-Host "✅ MySQL connection successful!" -ForegroundColor Green
        Write-Host "Database: $($connection.Database)" -ForegroundColor White
        Write-Host "Server Version: $($connection.ServerVersion)" -ForegroundColor White
        $connection.Close()
    } catch {
        Write-Host "❌ MySQL connection failed: $($_.Exception.Message)" -ForegroundColor Red
    }
} catch {
    Write-Host "❌ MySQL PowerShell module not found" -ForegroundColor Red
    Write-Host "Install with: Install-Module -Name MySql.Data" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "🔧 Method 4: Test with curl (HTTP-like test)" -ForegroundColor Green
Write-Host "Testing if the host is reachable..." -ForegroundColor White

try {
    $response = Test-NetConnection -ComputerName "mysql-production-59d9.up.railway.app" -Port 3306 -WarningAction SilentlyContinue
    if ($response.TcpTestSucceeded) {
        Write-Host "✅ Host is reachable on port 3306" -ForegroundColor Green
    } else {
        Write-Host "❌ Host is not reachable on port 3306" -ForegroundColor Red
    }
} catch {
    Write-Host "❌ Network test failed: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "🔧 Method 5: Test Alternative Connection Strings" -ForegroundColor Green
Write-Host "Testing proxy connection..." -ForegroundColor White

try {
    $tcpClient = New-Object System.Net.Sockets.TcpClient
    $connect = $tcpClient.BeginConnect("centerbeam.proxy.rlwy.net", 36676, $null, $null)
    $wait = $connect.AsyncWaitHandle.WaitOne(5000, $false)
    
    if ($wait) {
        $tcpClient.EndConnect($connect)
        Write-Host "✅ Proxy port 36676 is accessible!" -ForegroundColor Green
        $tcpClient.Close()
    } else {
        Write-Host "❌ Proxy port 36676 is not accessible (timeout)" -ForegroundColor Red
    }
} catch {
    Write-Host "❌ Proxy connection failed: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "📊 Summary:" -ForegroundColor Cyan
Write-Host "If any of the above tests show ✅, your Railway MySQL is accessible" -ForegroundColor White
Write-Host "If all tests show ❌, there might be a network or service issue" -ForegroundColor White
Write-Host ""
Write-Host "🚀 Next Steps:" -ForegroundColor Yellow
Write-Host "1. Check Railway Dashboard to ensure MySQL service is running" -ForegroundColor White
Write-Host "2. Try the proxy connection in your backend" -ForegroundColor White
Write-Host "3. Contact Railway support if all tests fail" -ForegroundColor White
