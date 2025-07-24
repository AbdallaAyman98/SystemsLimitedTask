package utils;

import com.codoid.products.fillo.*;
import io.qameta.allure.Step;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

public class LocatorExtractor {
    private final Connection connection;


    public LocatorExtractor(String workbookName) throws Exception {
        String filePath = System.getProperty("user.dir") + File.separator +
                "Locators" + File.separator +
                workbookName + ".xlsm";

        Fillo fillo = new Fillo();
        this.connection = fillo.getConnection(filePath);
    }


    @Step("Extracting locator for label: {locatorLabel}")
    public List<Locator> extractLocator(String locatorLabel) throws Exception {
        List<Locator> locatorList = new ArrayList<>();

        // Step 1: Get LOCATOR ID from LOCATORS sheet
        String locatorsQuery = "SELECT * FROM LOCATORS WHERE `LOCATOR LABEL` = '" + locatorLabel + "'";
        Recordset locatorsRecordset = connection.executeQuery(locatorsQuery);

        String locId = null;
        if (locatorsRecordset.next()) {
            locId = locatorsRecordset.getField("LOCATOR ID");
        }
        locatorsRecordset.close();

        if (locId == null || locId.isEmpty()) {
            throw new Exception("LOCATOR ID not found for label: '" + locatorLabel + "'");
        }

        // Step 2: Lookup all locator entries from the sheet named after LOCATOR ID
        String locatorSheetQuery = "SELECT * FROM `" + locId + "`";
        Recordset locatorRecordset = connection.executeQuery(locatorSheetQuery);

        while (locatorRecordset.next()) {
            String type = locatorRecordset.getField("LOCATOR TYPE");
            String value = locatorRecordset.getField("LOCATOR VALUE");

            if (type != null && value != null && !type.trim().isEmpty() && !value.trim().isEmpty()) {
                locatorList.add(new Locator(type.trim(), value.trim()));
            }
        }
        locatorRecordset.close();

        if (locatorList.isEmpty()) {
            throw new Exception("No locator data found in sheet '" + locId + "' for label '" + locatorLabel + "'");
        }

        System.out.println("Extracted locators for '" + locatorLabel + "':");
        for (Locator locator : locatorList) {
            System.out.println("Type: " + locator.getType() + ", Value: " + locator.getValue());
        }

        return locatorList;
    }
    @Step("Closing locator connection")
    public void close() throws SQLException {
        connection.close();
    }
}
