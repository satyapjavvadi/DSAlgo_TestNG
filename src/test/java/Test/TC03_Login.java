package Test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import DriverManager.DriverFactory;
import TestPackage.LoginData;
import utils.TestContext;

public class TC03_Login extends Hooks {

	@BeforeMethod
	public void openLoginPage() {
		String url = prop.getProperty("baseURL") + "home";
		DriverFactory.getDriver().get(url);

		if (pom.getHomePage().isSignOutVisible()) {
			pom.getHomePage().clickSignOutButton();
		}

		pom.getHomePage().clickSignInButton();
	}

	@Test
	public void verifyInputFieldCount() {
		logger.info("Verify input field count : {}", pom.getLoginPage().getInputFieldCount());
		Assert.assertEquals(pom.getLoginPage().getInputFieldCount(), 2, "Login page should have 2 input fields");
	}

	@Test(dataProvider = "loginLabels", dataProviderClass = LoginData.class)
	public void verifyLoginLabels(String expectedLabel) {
		logger.info("Verify login labels :{}", expectedLabel);
		List<String> labels = pom.getLoginPage().getLoginLabelNames();
		Assert.assertTrue(labels.contains(expectedLabel), "Missing label: " + expectedLabel);

	}

	@Test
	public void verifyButtonCount() {
		logger.info("Verify button count: {}", pom.getLoginPage().getButtonCount());
		Assert.assertEquals(pom.getLoginPage().getButtonCount(), 1, "Login page should habe 1 button");

	}

	@Test
	public void verifyLoginButtonText() {
		logger.info("Verify login button text: {}", pom.getLoginPage().getButtonText().contains("Login"));
		Assert.assertTrue(pom.getLoginPage().getButtonText().contains("Login"), "Login button text mismatch");
	}

	@Test(dataProvider = "negativeData", dataProviderClass = LoginData.class)
	public void verifyInvalidLogin(String testCaseType, String submissionMethod, String field) {
		logger.info("Starting negative login test: scenarioType='{}', submessionMethod='{}', field='{}'", testCaseType,
				submissionMethod, field);
		pom.getLoginPage().login(submissionMethod, testCaseType);

		String actualError = pom.getLoginPage().getDisplayedErrorMessage(field);
		String expectedError = TestContext.testData.get("expected_message");
		logger.info("Actual error message: {}", actualError);
		logger.info("Expected error message: {}", expectedError);
		Assert.assertEquals(actualError, expectedError, "Error message mismatch for: " + testCaseType);
		logger.info("Negative login test passed for scenario {}", testCaseType);
	}

	@Test(dataProvider = "validLoginData", dataProviderClass = LoginData.class)
	public void verifyValidLogin(String submissionMethod, String testCaseType) {
		logger.info("Staring valid login test: submissionMethod={}, scenarioType={}", submissionMethod, testCaseType);
		pom.getLoginPage().login(submissionMethod, testCaseType);

		pom.getLoginPage().waitForHomeRedirect();

		String actualMessage = pom.getHomePage().getAlertMessage();
		String expectedMessage = "You are logged in";

		logger.info("Actual success message: {}", actualMessage);
		logger.info("Expected success message: {}", expectedMessage);

		Assert.assertEquals(pom.getHomePage().getAlertMessage(), "You are logged in", "Success message mismatch");

		logger.info("Valid login test passed for scenario :{}", testCaseType);
	}

}
