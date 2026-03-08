package Test;

import TestPackage.LoginData;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TestContext;

import java.util.Arrays;
import java.util.List;

@Test(groups = "Get Started")
public class TC04_Login extends Hooks {

	private String testCase;

	@BeforeClass
	public void openLoginPage() {
		logger.info("User is in Sign in Page");
		pom.getHomePage().clickSignInButton();
	}

	@Test(priority = 0)
	public void verifyInputFieldCount() {
		logger.info("Verify input field count : {}", pom.getLoginPage().getInputFieldCount());
		Assert.assertEquals(pom.getLoginPage().getInputFieldCount(), 2, "Login page should have 2 input fields");
	}

	@Test(priority = 1,dataProvider = "loginLabels", dataProviderClass = LoginData.class)
	public void verifyLoginLabels(String expectedLabel) {
		logger.info("Verify login labels :{}", expectedLabel);
		List<String> labels = pom.getLoginPage().getLoginLabelNames();
		Assert.assertTrue(labels.contains(expectedLabel), "Missing label: " + expectedLabel);

	}

	@Test(priority = 2)
	public void verifyButtonCount() {
		logger.info("Verify button count: {}", pom.getLoginPage().getButtonCount());
		Assert.assertEquals(pom.getLoginPage().getButtonCount(), 1, "Login page should habe 1 button");

	}

	@Test(priority = 3)
	public void verifyLoginButtonText() {
		logger.info("Verify login button text: {}", pom.getLoginPage().getButtonText().contains("Login"));
		Assert.assertTrue(pom.getLoginPage().getButtonText().contains("Login"), "Login button text mismatch");
	}

	@Test(priority = 4,dataProvider = "negativeData", dataProviderClass = LoginData.class)
	public void verifyInvalidLogin(String testCaseType, String submissionMethod, String field) {
		logger.info("Starting negative login test: scenarioType='{}', submissionMethod='{}', field='{}'", testCaseType,
				submissionMethod, field);
		pom.getLoginPage().login(submissionMethod, testCaseType);

		String actualError = pom.getLoginPage().getDisplayedErrorMessage(field);
		String expectedError = TestContext.testData.get("expected_message");
		logger.info("Actual error message: {}", actualError);
		logger.info("Expected error message: {}", expectedError);
		Assert.assertEquals(actualError, expectedError, "Error message mismatch for: " + testCaseType);
		logger.info("Negative login test passed for scenario {}", testCaseType);
	}

	@Test(priority = 5,groups="validLogin",dataProvider = "validLoginData", dataProviderClass = LoginData.class)
	public void verifyValidLogin(String submissionMethod, String testCaseType) {
		logger.info("Staring valid login test: submissionMethod={}, scenarioType={}", submissionMethod, testCaseType);
		testCase = submissionMethod;
		pom.getLoginPage().login(submissionMethod, testCaseType);

		pom.getLoginPage().waitForHomeRedirect();

		String actualMessage = pom.getHomePage().getAlertMessage();
		String expectedMessage = "You are logged in";

		logger.info("Actual success message: {}", actualMessage);
		logger.info("Expected success message: {}", expectedMessage);

		Assert.assertEquals(pom.getHomePage().getAlertMessage(), "You are logged in", "Success message mismatch");

		logger.info("Valid login test passed for scenario :{}", testCaseType);
	}


	@AfterMethod
	public void signOut(ITestResult result) {

		String[] groups = result.getMethod().getGroups();
		List<String> groupList = Arrays.asList(groups);

		if (groupList.contains("validLogin")) {
			logger.info("testCaseName:{} ", testCase);
			if (testCase != null && testCase.contains("Submits")) {
				logger.info("Inside validLogin group");
				pom.getHomePage().clickSignOutButton();
				pom.getHomePage().clickSignInButton();
			}

		}
	}
}
/*=======
package Test;

import TestPackage.LoginData;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TestContext;

import java.util.Arrays;
import java.util.List;

@Test(groups = "Get Started")
public class TC04_Login extends Hooks {

	private String testCase;

	@BeforeClass
	public void openLoginPage() {
		logger.info("User is in Sign in Page");
		pom.getHomePage().clickSignInButton();
	}

	@Test(priority = 0)
	public void verifyInputFieldCount() {
		logger.info("Verify input field count : {}", pom.getLoginPage().getInputFieldCount());
		Assert.assertEquals(pom.getLoginPage().getInputFieldCount(), 2, "Login page should have 2 input fields");
	}

	@Test(priority = 1,dataProvider = "loginLabels", dataProviderClass = LoginData.class)
	public void verifyLoginLabels(String expectedLabel) {
		logger.info("Verify login labels :{}", expectedLabel);
		List<String> labels = pom.getLoginPage().getLoginLabelNames();
		Assert.assertTrue(labels.contains(expectedLabel), "Missing label: " + expectedLabel);

	}

	@Test(priority = 2)
	public void verifyButtonCount() {
		logger.info("Verify button count: {}", pom.getLoginPage().getButtonCount());
		Assert.assertEquals(pom.getLoginPage().getButtonCount(), 1, "Login page should habe 1 button");

	}

	@Test(priority = 3)
	public void verifyLoginButtonText() {
		logger.info("Verify login button text: {}", pom.getLoginPage().getButtonText().contains("Login"));
		Assert.assertTrue(pom.getLoginPage().getButtonText().contains("Login"), "Login button text mismatch");
	}

	@Test(priority = 4,dataProvider = "negativeData", dataProviderClass = LoginData.class)
	public void verifyInvalidLogin(String testCaseType, String submissionMethod, String field) {
		logger.info("Starting negative login test: scenarioType='{}', submissionMethod='{}', field='{}'", testCaseType,
				submissionMethod, field);
		pom.getLoginPage().login(submissionMethod, testCaseType);

		String actualError = pom.getLoginPage().getDisplayedErrorMessage(field);
		String expectedError = TestContext.testData.get("expected_message");
		logger.info("Actual error message: {}", actualError);
		logger.info("Expected error message: {}", expectedError);
		Assert.assertEquals(actualError, expectedError, "Error message mismatch for: " + testCaseType);
		logger.info("Negative login test passed for scenario {}", testCaseType);
	}

	@Test(priority = 5,groups="validLogin",dataProvider = "validLoginData", dataProviderClass = LoginData.class)
	public void verifyValidLogin(String submissionMethod, String testCaseType) {
		logger.info("Staring valid login test: submissionMethod={}, scenarioType={}", submissionMethod, testCaseType);
		testCase = submissionMethod;
		pom.getLoginPage().login(submissionMethod, testCaseType);

		pom.getLoginPage().waitForHomeRedirect();

		String actualMessage = pom.getHomePage().getAlertMessage();
		String expectedMessage = "You are logged in";

		logger.info("Actual success message: {}", actualMessage);
		logger.info("Expected success message: {}", expectedMessage);

		Assert.assertEquals(pom.getHomePage().getAlertMessage(), "You are logged in", "Success message mismatch");

		logger.info("Valid login test passed for scenario :{}", testCaseType);
	}


	@AfterMethod
	public void signOut(ITestResult result) {

		String[] groups = result.getMethod().getGroups();
		List<String> groupList = Arrays.asList(groups);

		if (groupList.contains("validLogin")) {
			logger.info("testCaseName:{} ", testCase);
			if (testCase != null && testCase.contains("Submits")) {
				logger.info("Inside validLogin group");
				pom.getHomePage().clickSignOutButton();
				pom.getHomePage().clickSignInButton();
			}

		}
	}
}
>>>>>>> Master*/
