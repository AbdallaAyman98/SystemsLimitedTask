package utils;

import org.openqa.selenium.By;
import java.util.*;

public class ByFetcher {
    private final LocatorExtractor locatorExtractor;

    public ByFetcher(String workbookName) throws Exception {
        this.locatorExtractor = new LocatorExtractor(workbookName);
    }

    /**
     * Fetches locators with placeholder replacement.
     */
    public List<By> fetchLocators(String locatorLabel, Map<String, String> replacements) {
        try {
            List<Locator> locatorList = locatorExtractor.extractLocator(locatorLabel);
            List<By> byList = new ArrayList<>();

            for (Locator locator : locatorList) {
                String type = locator.getType().toLowerCase();
                String value = locator.getValue();

                // Replace placeholders like {number}
                if (replacements != null && !replacements.isEmpty()) {
                    for (Map.Entry<String, String> entry : replacements.entrySet()) {
                        value = value.replace("{" + entry.getKey() + "}", entry.getValue());
                    }
                }

                // check for unresolved placeholders
                if (value.matches(".*\\{.+?}.*")) {
                    throw new IllegalArgumentException("Unresolved placeholder in locator: " + value);
                }

                switch (type) {
                    case "xpath": byList.add(By.xpath(value)); break;
                    case "css": byList.add(By.cssSelector(value)); break;
                    case "id": byList.add(By.id(value)); break;
                    case "name": byList.add(By.name(value)); break;
                    case "class": byList.add(By.className(value)); break;
                    case "tag": byList.add(By.tagName(value)); break;
                    case "linktext": byList.add(By.linkText(value)); break;
                    case "partiallinktext": byList.add(By.partialLinkText(value)); break;
                    default: throw new IllegalArgumentException("Unsupported locator type: " + type);
                }
            }

            return byList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract locators for label: " + locatorLabel, e);
        }
    }

    /**
     * Overloaded method — fetches locators without replacements.
     */
    public List<By> fetchLocators(String locatorLabel) {
        return fetchLocators(locatorLabel, null);
    }
}
