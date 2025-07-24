package pages;
import utils.ActionHandler;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

public class BasePage {
    protected WebDriver driver;
    protected ActionHandler actionHandler;

    public BasePage(WebDriver driver) throws Exception {
        this.driver = driver;
        int waitSecs = Integer.parseInt(ConfigReader.get("timeout"));
        String workbookName = this.getClass().getSimpleName();
        System.out.println("Instantiating page: " + workbookName);
        this.actionHandler = new ActionHandler(driver, workbookName, waitSecs);

    }

    protected ActionHandler action() {
        return actionHandler;
    }
}
