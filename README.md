**Technology Stack:** *Java + Selenium + Allure*

---

**Overview:**  
A modular UI Test Automation Framework using **Java**, **Selenium WebDriver**, **Allure** for reporting, and *dynamic locator support* from external sources like Excel.

---

**Features:**  
- **Page Object Model (POM)**  
- *Dynamic locator handling with placeholders*  
- *Externalized test data and locators*  
- **Custom Allure listener** for screenshots on failure  
- *Auto Allure report generation*  
- **Cross-browser support** via TestNG  
- *One-click run via* `runner.bat`  
- *Setup script:* `setup.ps1`

---

**Tools & Technologies:**  
**Java**, **Selenium**, **TestNG**, **Maven**, **Allure**

---

**Key Components:**  
- `BaseTest.java`: *WebDriver setup/teardown*  
- `LoginPage.java`: *Sample Page Object*  
- `LocatorExtractor.java`: *Replaces placeholders like `{0}`*  
- `AllureListener.java`: *Screenshots on failure*  
- `testng.xml`: *Test execution config*  
- `setup.ps1`: *Installs Java, Maven, Allure, etc.*  
- `runner.bat`: *Cleans, runs tests, opens Allure report*

---

**Run Tests:**  
```bash
mvn clean test
```

**View Report:**  
```bash
allure serve allure-results
```

Or simply run:  
```bash
runner.bat
```

Run `setup.ps1` once to configure your environment.

---

**Dynamic Locators Example:**  
`//a[@aria-label="Page {number}"]` → `locator("pagination.link", "2")`  
Supports multiple placeholders like:  
`//div[text()='{0}']//a[text()='{1}']` → `locator("menu.item", "File", "Download")`
