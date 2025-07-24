package pages;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import java.util.Map;


public class ResultsPage extends BasePage {
    public ResultsPage(WebDriver driver) throws Exception {
        super(driver);
    }

    public ResultsPage navigateToResultsPage(String pageNumber){
        Allure.step("Navigating to results page: " +pageNumber);
        action().scrollToView("Page", Map.of("PageNumber", pageNumber)).click("Page", Map.of("PageNumber", pageNumber));
        return this;
    }

//    public ResultsPage clickWeb(){
//        Allure.step("Clicking web link");
//        action().click("Web Link");
//        return this;
//    }

    public int getResultsCount() {
        Allure.step("Getting results count");
        return action().getElements("Results Links").size();
    }
}
