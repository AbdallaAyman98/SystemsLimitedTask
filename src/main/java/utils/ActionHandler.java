package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class ActionHandler {

    private final ByFetcher byFetcher;
    private final WaitHelper waitHelper;
    private final WebDriver driver;

    public ActionHandler(WebDriver driver, String workbookName, int timeoutInSeconds) throws Exception {
        this.driver = driver;
        this.byFetcher = new ByFetcher(workbookName);
        this.waitHelper = new WaitHelper(driver, timeoutInSeconds);
    }

    // Helper method to highlight element
    private void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll into view first
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);

        // Highlight
        String originalStyle = element.getAttribute("style");
        String script =  "const el = arguments[0];" +
                "el.style.transition = 'all 0.5s ease';" +
                "el.style.border = '4px solid #8B0000';" +
                "el.style.backgroundColor = 'transparent';" +
                "el.style.color = '#FF4500';" +
                "el.style.fontWeight = 'bold';" +
                "el.style.padding = '6px';" +
                "el.style.borderRadius = '12px';" +
                "el.style.boxShadow = '0 0 20px 4px rgba(139, 0, 0, 0.6)';";
        js.executeScript(script, element);

        try {
            Thread.sleep(400); // Make highlight visible
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Restore original style
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, originalStyle);
    }

    // =================== CLICK ===================
    public ActionHandler click(String locatorLabel) {
        return click(locatorLabel, null);
    }

    public ActionHandler click(String locatorLabel, Map<String, String> replacements) {
        return attemptWithLocators(locatorLabel, replacements, by -> {
            WebElement element = waitHelper.waitForElementClickable(by);
            highlightElement(element);
            element.click();
            Allure.step("Clicked element using: " + by);
            return null;
        });
    }

    // =================== TYPE ===================
    public ActionHandler type(String locatorLabel, String text) {
        return type(locatorLabel, text, null);
    }

    public ActionHandler type(String locatorLabel, String text, Map<String, String> replacements) {
        return attemptWithLocators(locatorLabel, replacements, by -> {
            WebElement element = waitHelper.waitForElementVisible(by);
            highlightElement(element);
            element.clear();
            element.sendKeys(text);
            Allure.step("Typed into element: " + locatorLabel + " | Text: " + text);
            return null;
        });
    }

    public ActionHandler typeLikeHuman(String locatorLabel, String text, long minDelayMillis, long maxDelayMillis) {
        return typeLikeHuman(locatorLabel, text, null, minDelayMillis, maxDelayMillis);
    }

    public ActionHandler typeLikeHuman(String locatorLabel, String text, Map<String, String> replacements, long minDelayMillis, long maxDelayMillis) {
        return attemptWithLocators(locatorLabel, replacements, by -> {
            WebElement element = waitHelper.waitForElementVisible(by);
            highlightElement(element);
            element.clear();
            for (char c : text.toCharArray()) {
                element.sendKeys(Character.toString(c));
                try {
                    long randomDelay = ThreadLocalRandom.current().nextLong(minDelayMillis, maxDelayMillis + 1);
                    Thread.sleep(randomDelay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted while typing", e);
                }
            }
            Allure.step("Typed like human into element: " + locatorLabel + " | Text: " + text);
            return null;
        });
    }

    // =================== SEND KEYS ===================
    public ActionHandler sendKeys(String locatorLabel, Keys keys) {
        return sendKeys(locatorLabel, keys, null);
    }

    public ActionHandler sendKeys(String locatorLabel, Keys keys, Map<String, String> replacements) {
        return attemptWithLocators(locatorLabel, replacements, by -> {
            WebElement element = waitHelper.waitForElementVisible(by);
            highlightElement(element);
            element.sendKeys(keys);
            Allure.step("Sent keys to element: " + locatorLabel + " | Keys: " + keys.name());
            return null;
        });
    }

    // =================== GET TEXT ===================
    public String getText(String locatorLabel) {
        return getText(locatorLabel, null);
    }

    public String getText(String locatorLabel, Map<String, String> replacements) {
        final StringBuilder result = new StringBuilder();
        attemptWithLocators(locatorLabel, replacements, by -> {
            WebElement element = waitHelper.waitForElementVisible(by);
            highlightElement(element);
            String text = element.getText();
            Allure.step("Retrieved text from element: " + locatorLabel + " | Text: " + text);
            result.append(text);
            return null;
        });
        return result.toString();
    }

    // =================== GET ELEMENTS ===================
    public List<WebElement> getElements(String locatorLabel) {
        return getElements(locatorLabel, null);
    }

    public List<WebElement> getElements(String locatorLabel, Map<String, String> replacements) {
        List<By> locators;

        try {
            locators = byFetcher.fetchLocators(locatorLabel, replacements);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch locators for label: " + locatorLabel, e);
        }

        for (By by : locators) {
            try {
                List<WebElement> elements = waitHelper.waitForElementsVisible(by);
                if (!elements.isEmpty()) {
                    // highlight all found elements
                    for (WebElement el : elements) {
                        highlightElement(el);
                    }
                    Allure.step("Found total " + elements.size() + " elements for label: " + locatorLabel);
                    return elements;
                }
            } catch (Exception e) {
                System.out.println("Could not get elements for: " + by + " - " + e.getMessage());
            }
        }

        throw new RuntimeException("No elements found for label: " + locatorLabel);
    }

    // =================== CHECK EXISTENCE ===================
    public boolean checkElementExistence(String locatorLabel) {
        return checkElementExistence(locatorLabel, null);
    }

    public boolean checkElementExistence(String locatorLabel, Map<String, String> replacements) {
        List<By> locators;
        try {
            locators = byFetcher.fetchLocators(locatorLabel, replacements);
        } catch (Exception e) {
            System.out.println("Failed to fetch locators for label: " + locatorLabel + " - " + e.getMessage());
            return false;
        }

        for (By by : locators) {
            try {
                List<WebElement> elements = waitHelper.waitForElementsVisible(by);
                if (!elements.isEmpty()) {
                    // highlight first element found
                    highlightElement(elements.get(0));
                    Allure.step("Element exists for label: " + locatorLabel + " using locator: " + by);
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Locator check failed for: " + by + " - " + e.getMessage());
            }
        }

        Allure.step("Element does not exist for label: " + locatorLabel);
        return false;
    }

    // =================== CORE LOGIC ===================
    private ActionHandler attemptWithLocators(String locatorLabel, Map<String, String> replacements, Function<By, Void> action) {
        List<By> locators;
        try {
            locators = byFetcher.fetchLocators(locatorLabel, replacements);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch locators for label: " + locatorLabel, e);
        }

        for (By by : locators) {
            try {
                action.apply(by);
                return this;
            } catch (Exception e) {
                System.out.println("Action failed for: " + by + " - " + e.getMessage());
            }
        }

        throw new RuntimeException("All locator attempts failed for label: " + locatorLabel);
    }

    // Overload for backward compatibility
    private ActionHandler attemptWithLocators(String locatorLabel, Function<By, Void> action) {
        return attemptWithLocators(locatorLabel, null, action);
    }

    public ActionHandler scrollToView(String locatorLabel) {
        return scrollToView(locatorLabel, null);
    }

    public ActionHandler scrollToView(String locatorLabel, Map<String, String> replacements) {
        return attemptWithLocators(locatorLabel, replacements, by -> {
            WebElement element = waitHelper.waitForElementVisible(by);
            highlightElement(element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            Allure.step("Scrolled to element: " + locatorLabel + " using locator: " + by);
            return null;
        });
    }
}
