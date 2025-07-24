Technology Stack: Java + Selenium + Allure

Overview
This project is a modular UI Test Automation Framework built using Java, Selenium WebDriver for browser automation, Allure for detailed test reporting, and supports dynamic locator extraction from external sources like Excel.


Features

Page Object Model (POM) architecture

Dynamic locator management via LocatorExtractor utility

Custom Allure listener for capturing screenshots on test failures

Externalized test data and locators

Automatic Allure report generation

Cross-browser support via TestNG parameterization

Clean utility separation and reusable components

Setup and execution support via setup.ps1 and runner.bat

Tools & Technologies

Java

Selenium WebDriver

Allure Reports

TestNG

Maven

Key Components

BaseTest.java – Handles WebDriver setup and teardown.

LoginPage.java – Sample Page Object using locators from external sources (JSON/Excel).

LocatorExtractor.java – Utility to extract and format locators dynamically with placeholder support.

AllureListener.java – Captures screenshots and logs for Allure on test failures.

testng.xml – Manages test execution configuration, parallelism, and browser settings.

Allure Steps – Uses @Step and Allure.step() for step-level logging in reports.

setup.ps1 – Installs required dependencies and ensures the environment is ready.

runner.bat – One-click script to clean, run tests, and launch Allure report.

Test Execution

Run Tests:
mvn clean test

Generate Allure Report:
allure serve allure-results

Alternative:

Run runner.bat to execute everything in one click.

Use setup.ps1 once to configure your environment (Java, Maven, Allure PATH, etc.).

Note: Make sure Allure is installed and added to your system's PATH if you run manually.

Custom Locators

Supports dynamic locators using placeholders:

Example:
//a[@aria-label="Page {number}"]

Usage in code:
locator("pagination.link", "2") // Replaces {number} with "2"

Supports multiple placeholders:
locator("menu.item", "File", "Download")
// Where locator is: //div[text()='{0}']//a[text()='{1}']

Folder Structure Example

SystemsLimitedTask/
├── allure-report/              # Allure test reports
├── allure-results/             # Allure test results
├── Locators/                   # Excel files containing locators
│   ├── LandingPage.xlsm
│   └── ResultsPage.xlsm
├── recordings/                 # Video recordings of test executions
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── datamodel/       # Data model classes
│   │   │   ├── pages/           # Page object classes
│   │   │   └── utils/           # Utility classes
│   │   └── resources/
│   └── test/
│       ├── java/
│       │   ├── BaseTest/             # Base test class
│       │   ├── DriverManager/        # WebDriver management
│       │   └── GoogleSearchTests/    # Test cases
│       └── resources/
│           ├── TestData/
│           │   └── GoogleSearchTests/
│           │       └── TC_001.JSON   # Test data in JSON format
│           └── Workflows/
│               └── TC_001.xlsm
├── target/
├── setup.ps1                  # PowerShell script to set up dependencies/environment
├── runner.bat                 # Batch script to run tests and open Allure report
├── init.config                # Configuration file containing the URL, waitTime, driver etc
└── .gitignore                 # Git ignore rules
