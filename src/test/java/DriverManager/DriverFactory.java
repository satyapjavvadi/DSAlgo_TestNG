package DriverManager;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import utils.ConfigReader;

public class DriverFactory {

	public static ThreadLocal<WebDriver> mydriver = new ThreadLocal<>();
	public static ThreadLocal<String> testNGBrowser = new ThreadLocal<>();
	private static final Logger logger = LogManager.getLogger(DriverFactory.class);

	public static void launchBrowser() {

		String browser = testNGBrowser.get();
		logger.info("Launching browser: {}", browser);

		try {
			if (browser.equalsIgnoreCase("Chrome")) {
				mydriver.set(new ChromeDriver());
				logger.info("Chrome browser launched successfully.");
			} else if (browser.equalsIgnoreCase("Firefox")) {
				mydriver.set(new FirefoxDriver());
				logger.info("Firefox browser launched successfully.");
			} else if (browser.equalsIgnoreCase("Edge")) {
				mydriver.set(new EdgeDriver());
				logger.info("Edge browser launched successfully.");
			} else {
				logger.error("Browser not supported: {}", browser);
				throw new IllegalArgumentException("Browser not supported: " + browser);
			}

			getDriver().get(ConfigReader.getProperty("baseURL"));

			getDriver().manage().deleteAllCookies();
			logger.info("Deleted all browser cookies.");

			getDriver().manage().window().maximize();
			logger.info("Browser window maximized.");

			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			logger.info("Implicit wait set to 10 seconds.");

		} catch (Exception e) {
			logger.error("Error while launching browser: {}", e.getMessage());
			throw e;
		}
	}

	public static synchronized WebDriver getDriver() {
		logger.debug("Fetching WebDriver instance from ThreadLocal.");
		return mydriver.get();
	}

	public static void setBrowser(String browserName) {
		testNGBrowser.set(browserName);
	}

	public static String getBrowser() {
		return testNGBrowser.get();
	}
}
