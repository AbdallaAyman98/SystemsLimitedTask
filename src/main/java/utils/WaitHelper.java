package utils;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class WaitHelper {
    private final WebDriver driver;
    private final int defaultTimeoutInSeconds;

    public WaitHelper(WebDriver driver, int timeoutInSeconds) {
        this.driver = driver;
        this.defaultTimeoutInSeconds = timeoutInSeconds;
    }

    private WebDriverWait getWait(int timeout) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    private WebDriverWait getDefaultWait() {
        return getWait(defaultTimeoutInSeconds);
    }


    private FluentWait<WebDriver> getFluentWait(int timeout) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    @Step("Wait for page to load completely")

    public void waitForPageToLoad() {
        getDefaultWait().until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public WebElement waitForElementVisible(By locator) {
        return getDefaultWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementVisible(WebElement element) {
        return getDefaultWait().until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForElementClickable(By locator) {
        return getDefaultWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForElementClickable(WebElement element) {
        return getDefaultWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public boolean waitForTextInElement(By locator, String text) {
        return getDefaultWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public boolean waitForUrlToContain(String partialUrl) {
        return getDefaultWait().until(ExpectedConditions.urlContains(partialUrl));
    }

    public boolean waitForTitleToBe(String title) {
        return getDefaultWait().until(ExpectedConditions.titleIs(title));
    }

    public boolean waitForAttributeToBe(By locator, String attribute, String value) {
        return getDefaultWait().until(ExpectedConditions.attributeToBe(locator, attribute, value));
    }

    public boolean waitForElementInvisible(By locator) {
        return getDefaultWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public boolean waitForElementStaleness(WebElement element) {
        return getDefaultWait().until(ExpectedConditions.stalenessOf(element));
    }

    public WebElement fluentWaitForElement(By locator, int timeoutInSeconds) {
        return getFluentWait(timeoutInSeconds).until(driver -> {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed() ? element : null;
        });
    }

    public List<WebElement> waitForElementsVisible(By by) {
        return getDefaultWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

}
