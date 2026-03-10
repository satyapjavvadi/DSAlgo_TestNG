package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import DriverManager.DriverFactory;

public class TestListener implements ITestListener {

	private static final Logger logger = LogManager.getLogger(TestListener.class);

	@Override
	public void onTestStart(ITestResult result) {
		logger.info("Starting Test: " + result.getName());

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		logger.info("Passed: " + result.getName());

	}

	@Override
	public void onTestFailure(ITestResult result) {
		logger.info("Failed: " + result.getName());
		String testName = result.getName();
		WebDriver driver = DriverFactory.getDriver();

		String screenshotPath = ScreenShot.takeScreenshot(driver, testName);
		System.out.println("Screenshot saved at: " + screenshotPath);

	}

	@Override
	public void onTestSkipped(ITestResult result) {

		logger.info("Test skipped: " + result.getName());
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		logger.info("Test failed with timeout: " + result.getName());
	}

	@Override
	public void onStart(ITestContext context) {
		logger.info("Suite started: " + context.getName());

	}

	@Override
	public void onFinish(ITestContext context) {
		logger.info("Suite finished: " + context.getName());

	}

}
