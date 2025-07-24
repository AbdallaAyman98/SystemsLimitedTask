# setup.ps1
# PowerShell script to install OpenJDK (Temurin 17), Maven, and Allure using Scoop
# Run this script in PowerShell as a normal user (no need for admin)

Write-Host "====================================================="
Write-Host "          Automated Setup for Java Automation"
Write-Host "====================================================="

# Function to check if a command exists
function CommandExists($command) {
    $null -ne (Get-Command $command -ErrorAction SilentlyContinue)
}

# Install Scoop if missing
if (-not (CommandExists "scoop")) {
    Write-Host "Scoop not found. Installing Scoop..."
    Set-ExecutionPolicy RemoteSigned -Scope CurrentUser -Force
    iwr -useb get.scoop.sh | iex
} else {
    Write-Host "Scoop is already installed."
}

# Add Scoop buckets if missing
Write-Host "Adding Scoop buckets..."
try {
    scoop bucket add java -ErrorAction Stop
} catch {
    Write-Host "Bucket 'java' already exists. Skipping."
}

try {
    scoop bucket add extras -ErrorAction Stop
} catch {
    Write-Host "Bucket 'extras' already exists. Skipping."
}

# Install Temurin 17 OpenJDK
Write-Host "Installing Temurin17 OpenJDK..."
if (-not (CommandExists "java")) {
    scoop install temurin17
} else {
    Write-Host "Java is already installed. Skipping installation."
}

# Install Maven
Write-Host "Installing Maven..."
if (-not (CommandExists "mvn")) {
    scoop install maven
} else {
    Write-Host "Maven is already installed. Skipping installation."
}

# Install Allure
Write-Host "Installing Allure..."
if (-not (CommandExists "allure")) {
    scoop install allure
} else {
    Write-Host "Allure is already installed. Skipping installation."
}

# Verify installations
Write-Host "`nVerifying installations..."

if (CommandExists "java") {
    java -version
    Write-Host "Java installed successfully.`n"
} else {
    Write-Host "Java NOT found!`n"
}

if (CommandExists "mvn") {
    mvn -version
    Write-Host "Maven installed successfully.`n"
} else {
    Write-Host "Maven NOT found!`n"
}

if (CommandExists "allure") {
    allure --version
    Write-Host "Allure installed successfully.`n"
} else {
    Write-Host "Allure NOT found!`n"
}

Write-Host "Setup complete! Please close and reopen your terminal to reload environment variables."
Write-Host "Press any key to exit..."
$x = $host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
