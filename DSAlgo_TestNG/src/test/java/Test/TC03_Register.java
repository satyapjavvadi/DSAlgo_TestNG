package Test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import TestPackage.RegisterData;
import utils.ElementUtil;

@Test(groups = "Get Started")
public class TC03_Register extends Hooks {

	@BeforeMethod
	public void clickRegisterButton(){
		logger.info("Clicking Register link in home page");
	
		pom.getHomePage().navigatetoPages("Register");
	}

	@Test(priority = 1, dataProvider = "Registertext", dataProviderClass = RegisterData.class)
	public void clickRegister_Home(String text) {

		logger.info("Check title for the page");
		pom.getHomePage().navigatetoPages(text);
		logger.info("Clicked link '{}' on Home Page", text);
		Assert.assertEquals(ElementUtil.getTitle(), "Register", "Navigation failed to Register page");

	}

	@Test(priority = 2)
	public void verifyNumberOfInputFields() {
		int fieldCount = pom.getRegisterPage().getInputFieldCount();
		logger.info("Verifying number of input fields in Register UI : {}", fieldCount);
		Assert.assertEquals(fieldCount, 3, "Register page does not have 3 input fields");
	}

	@Test(priority = 3, dataProvider = "LabelNames", dataProviderClass = RegisterData.class)
	public void verifyLabelsText(String expectedLabels) {
		List<String> actualLinks = pom.getRegisterPage().getRegisterLabelNames();
		logger.info("Validating Register page labels");

		Assert.assertTrue(actualLinks.contains(expectedLabels), "Missing label: " + expectedLabels);

	}

	@Test(priority = 4)
	public void getButtonCount() {
		int actualCount = pom.getRegisterPage().getButtonCount();
		logger.info("Validating button count in Register UI");
		Assert.assertEquals(actualCount, 1, "Button count mismatch in Register page");

	}

	@Test(priority = 5, dataProvider = "Registertext", dataProviderClass = RegisterData.class)
	public void getButtonText(String registerText) {
		List<String> actualButton = pom.getRegisterPage().getButtonText();
		logger.info("Checking button text in Register UI");
		Assert.assertTrue(actualButton.contains(registerText),
				"Button text mismatch. Expected: " + registerText + " | Actual: " + actualButton);

	}

	@Test(priority = 6, dataProvider = "LinkNames", dataProviderClass = RegisterData.class)
	public void getLinkText(String expectedLinks) {
		List<String> actualLinks = pom.getRegisterPage().getRegisterPageLinkText();
		logger.info("Validating Register page linkText");
		Assert.assertTrue(actualLinks.contains(expectedLinks), "Missing label: " + expectedLinks);

	}

	@Test(priority = 7)
	public void verifyCompanyName() {
		String actualCompany = pom.getRegisterPage().getCompanyName().trim();
		Assert.assertEquals(actualCompany, "NumpyNinja");

	}

	@Test(priority = 8, dataProvider = "passwordrules", dataProviderClass = RegisterData.class)
	public void verifyPasswordRules(String expectedPasswordRules) {
		List<String> actualList = pom.getRegisterPage().getPasswordRequirementsText();
		logger.info("Validating Register page password rules");
		Assert.assertTrue(actualList.contains(expectedPasswordRules), "Missing rules: " + expectedPasswordRules);

	}

	@Test(priority = 9, dataProvider = "Negativeregisterdata", dataProviderClass = RegisterData.class)
	public void verifyRegisterInvalid(String scenarioType, String submissionMethod, String message) {
		logger.info("checking negative register data: scenarioType='{}', submissionMethod='{}', message='{}'",
				scenarioType, submissionMethod, message);

		pom.getRegisterPage().register(submissionMethod, scenarioType);
		String actualMessage = pom.getRegisterPage().getRegisterErrorMessage(scenarioType);
		logger.info("Validating error message for scenario '{}'. Expected: '{}', Actual: '{}'", scenarioType, message,
				actualMessage);

		Assert.assertEquals(actualMessage.trim(), message.trim(), "Validation message mismatch");

	}

	@Test(priority = 10, dataProvider = "Validregisterdata", dataProviderClass = RegisterData.class)
	public void verifyRegisterValid(String scenarioType, String submissionMethod, String message) {
		logger.info("checking valid register data: scenarioType='{}', submissionMethod='{}', message='{}'",
				scenarioType, submissionMethod, message);
		pom.getRegisterPage().register(submissionMethod, scenarioType);
		String actualMessage = pom.getHomePage().getAlertMessage();

		logger.info("Verifying redirect message. Expected: '{}', Actual: '{}'", message, actualMessage);

		Assert.assertTrue(actualMessage.contains(message),
				"Expected message to contain: [" + message + "] but found [" + actualMessage + "]");

	}

}
