import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;
import utils.SimpleVideoRecorder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {
        SimpleVideoRecorder recorder = new SimpleVideoRecorder();
        @BeforeMethod
        public void setup(Method method) throws Exception {
            // Start Video Recording
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            // Format: TC_001_ClassName_timestamp
            String videoFileName = method.getDeclaringClass().getSimpleName() + "_" + method.getName() + "_" + timestamp;
            recorder.startRecording(videoFileName);

            // Init WebDriver
            DriverManager.setDriver(ConfigReader.get("browser"));
            DriverManager.getDriver().manage().deleteAllCookies();
            DriverManager.getDriver().manage().window().maximize();
            DriverManager.getDriver().get(ConfigReader.get("base.url"));

        }

        @AfterMethod
        public void tearDown(ITestResult result) throws Exception {
            // Stop Video Recording
            recorder.stopRecording();

            // Attach Screenshot if Test Fails
            if (result.getStatus() == ITestResult.FAILURE) {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                Allure.getLifecycle().addAttachment("Screenshot", "image/png", "png", screenshot);
            }

            // Attach Video
            File videoFile = new File("./videos/" + result.getMethod().getMethodName() + ".avi");
            if (videoFile.exists()) {
                try (InputStream is = new FileInputStream(videoFile)) {
                    Allure.addAttachment("Test Video", "video/avi", is, ".avi");
                }
            }

            // Quit WebDriver
            DriverManager.quitDriver();
        }
    }

