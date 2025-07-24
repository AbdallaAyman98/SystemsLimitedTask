package utils;



import io.qameta.allure.internal.shadowed.jackson.databind.DeserializationFeature;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class JSONDataReader {

    public static <T> T read(String testCaseId, String moduleName, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (!testCaseId.endsWith(".json")) {
            testCaseId += ".json";
        }
        String filePath = "TestData/" + moduleName + "/" + testCaseId;
        try (InputStream is = new FileInputStream(new File(filePath))) {
            return mapper.readValue(is, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data from: " + filePath, e);
        }
    }
}
