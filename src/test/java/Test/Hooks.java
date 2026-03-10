package Test;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import DriverManager.DriverFactory;
import pages.PageObjectManager;
import utils.ConfigReader;
import utils.ExcelReader;

public class Hooks {

	Logger logger = LogManager.getLogger(getClass());
	PageObjectManager pom;
	protected Properties prop;

	@BeforeSuite(alwaysRun = true)
	@Parameters("browserName")
	public void setBrowser(@Optional("chrome") String browserName) {

		logger.info("Before Suite : set Browser : {}", browserName);
		DriverFactory.setBrowser(browserName);

		prop = ConfigReader.initializeProperties();
		logger.debug("Loaded configuration properties");

		ExcelReader.readDataFromExcel(prop.getProperty("loginsheetName"));
		ExcelReader.readDataFromExcel(prop.getProperty("RegisterPage"));
		ExcelReader.readDataFromExcel(prop.getProperty("ArrayPractice"));
		logger.info("Excel test data loaded");
	}

	@BeforeClass(alwaysRun = true)
	@Parameters("browserName")
	public void setUp(@Optional("chrome") String browserName) {

		DriverFactory.setBrowser(browserName);

		logger.info("Before Class : Initialize Browser ");
		DriverFactory.launchBrowser();

		pom = new PageObjectManager();

		Test testAnnotationTest = getClass().getAnnotation(Test.class);

		if (testAnnotationTest != null) {
			List<String> groups = Arrays.asList(testAnnotationTest.groups());
			if (groups.contains("Get Started")) {
				pom.getLaunchPage().clickGetStartedButton();
			}
			if (groups.contains("Sign in")) {
				pom.getHomePage().clickSignInButton();
				logger.info("Clicked Sign In button");

				pom.getLoginPage().login("Submits the login form", "valid_login");
				logger.info("Performed login with valid credentials");
			}
		}
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		if (DriverFactory.getDriver() != null) {
			logger.info("Tearing down WebDriver and closing browser");
			DriverFactory.getDriver().quit();
			DriverFactory.mydriver.remove();
			logger.info("Driver removed from ThreadLocal");
		}
	}
}
