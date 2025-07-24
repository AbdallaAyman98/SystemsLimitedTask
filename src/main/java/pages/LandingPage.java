package pages;
import utils.Validators;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class LandingPage extends BasePage {
    public LandingPage(WebDriver driver) throws Exception {
        super(driver);
    }
    public ResultsPage search(String text) throws Exception {
        Validators.assertTrue(action().checkElementExistence("Navigation Bar"));
        Allure.step("Searching " +text);
        action().type("Search Field",text).click("Search Button");
        return new ResultsPage(driver);
    }
}
