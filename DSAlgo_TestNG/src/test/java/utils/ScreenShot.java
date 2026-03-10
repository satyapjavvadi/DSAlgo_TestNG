package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot {

    // Capture screenshot as byte[] for attaching directly to Cucumber reports
    public static byte[] takeScreenshotAsBytes(WebDriver driver, String scenarioName) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
