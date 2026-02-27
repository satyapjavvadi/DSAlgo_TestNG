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
	
	@BeforeSuite(alwaysRun = true)
	@Parameters("browserName")
	public void setBrowser(@Optional("chrome") String browserName) {
		
		logger.info("Before Suite : set Browser : {}",browserName);
		DriverFactory.setBrowser(browserName);
		
		pom= new PageObjectManager();

		Properties prop = ConfigReader.initializeProperties();
		logger.debug("Loaded configuration properties");

		ExcelReader.readDataFromExcel(prop.getProperty("loginsheetName"));
		ExcelReader.readDataFromExcel(prop.getProperty("RegisterPage"));
		ExcelReader.readDataFromExcel(prop.getProperty("ArrayPractice"));
		logger.info("Excel test data loaded");
	}
	
	@BeforeClass
	public void setUp() {
		logger.info("Before Class : Initilize Browser ");
				DriverFactory.launchBrowser();
				
		Test testAnnotationTest = getClass().getAnnotation(Test.class);
		
		if(testAnnotationTest != null) {
			List<String> groups = Arrays.asList(testAnnotationTest.groups());
			if(groups.contains("Get Started")) {
				pom.getLaunchPage().clickGetStartedButton();
			}
			if(groups.contains("Sign in")) {
				pom.getHomePage().clickSignInButton();
				logger.info("Clicked Sign In button");

				pom.getLoginPage().login("Submits the login form", "valid_login");
				logger.info("Performed login with valid credentials");
			}
			
					}
		
		
	}
	
	
	@AfterClass
	public void tearDown() {
		if (DriverFactory.getDriver() != null) {
			logger.info("Tearing down WebDriver and closing browser");
			DriverFactory.getDriver().quit();
			DriverFactory.mydriver.remove();
			logger.info("Driver removed from ThreadLocal");
		}
	}
}