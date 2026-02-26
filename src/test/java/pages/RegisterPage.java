package pages;

import java.util.Arrays;
import java.util.List;

import DriverManager.DriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.ExcelReader;
import utils.TestContext;
import utils.WaitUtils;

public class RegisterPage {

	

	private WebDriver driver;
	private static final Logger logger = LoggerFactory.getLogger(RegisterPage.class);

	public RegisterPage() {
		this.driver = DriverFactory.getDriver();
		PageFactory.initElements(driver, this);
		logger.info("RegisterPage initialized successfully.");
	}

	// ===== Locators =====
	@FindBy(xpath = "//a[@class='navbar-brand']")
	private WebElement companyName;

	@FindBy(xpath = "//ul//a")
	private List<WebElement> links;

	@FindBy(id = "id_username")
	private WebElement usernameField;

	@FindBy(id = "id_password1")
	private WebElement passwordField;

	@FindBy(id = "id_password2")
	private WebElement confirmPasswordField;

	@FindBy(xpath = "//input[@type='submit']")
	private List<WebElement> buttonElements;

	@FindBy(xpath = "//label")
	private List<WebElement> labelList;

	@FindBy(xpath = "//input[@value='Register']")
	private WebElement registerBtn;

	@FindBy(xpath = "//div[@role='alert']")
	private WebElement alertMessage;

	@FindBy(xpath = "//ul/li")
	private List<WebElement> passwordReqList;

	// ===== Actions =====

	public void register(String method, String scenarioType) {

		logger.info("Executing register with method: {} and scenario: {}", method, scenarioType);

		TestContext.testData = ExcelReader.getTestData(scenarioType);
		logger.debug("Test data loaded: {}", TestContext.testData);

		WaitUtils.waitForVisibility(driver, usernameField, 10);
		usernameField.sendKeys(TestContext.testData.get("username"));
		logger.info("Username entered.");

		WaitUtils.waitForVisibility(driver, passwordField, 10);
		passwordField.sendKeys(TestContext.testData.get("password"));
		logger.info("Password entered.");

		WaitUtils.waitForVisibility(driver, confirmPasswordField, 10);
		confirmPasswordField.sendKeys(TestContext.testData.get("confirmpassword"));
		logger.info("Confirm password entered.");

		if ("submits the register form".equalsIgnoreCase(method.trim())) {
			logger.info("Clicking Register button.");
			registerBtn.click();
		} else {
			logger.error("Unknown submission method: {}", method);
			throw new IllegalArgumentException("Unknown submission method: " + method);
		}
	}

	public String getRegisterErrorMessage(String scenarioType) {

		logger.info("Fetching register error message for scenario: {}", scenarioType);

		if (scenarioType.toLowerCase().contains("null")) {

			for (WebElement field : Arrays.asList(usernameField, passwordField, confirmPasswordField)) {
				String tooltip = field.getAttribute("validationMessage");
				if (tooltip != null && !tooltip.isEmpty()) {
					logger.info("Validation tooltip found: {}", tooltip.trim());
					return tooltip.trim();
				}
			}
		} else {
			try {
				WaitUtils.waitForVisibility(driver, alertMessage, 10);
				String alertText = alertMessage.getText().trim();
				logger.info("Alert message found: {}", alertText);
				return alertText;

			} catch (Exception e) {
				logger.warn("No alert message displayed.");
				return "NO_ERROR_FOUND";
			}
		}
		logger.info("No validation message found. Returning null.");
		return null;
	}

	// ===== Non-functional Helpers =====

	public int getInputFieldCount() {
		int count = labelList.size();
		logger.info("Total input fields found: {}", count);
		return count;
	}

	public List<String> getRegisterLabelNames() {
		logger.info("Fetching register page label names.");
		return labelList.stream()
				.map(WebElement::getText)
				.map(String::trim)
				.toList();
	}

	public int getButtonCount() {
		int count = buttonElements.size();
		logger.info("Total buttons found: {}", count);
		return count;
	}

	public List<String> getButtonText() {
		logger.info("Fetching register page button texts.");
		return buttonElements.stream()
				.map(btn -> {
					String text = btn.getText();
					if (text == null || text.isEmpty())
						text = btn.getAttribute("value");
					return text != null ? text.trim() : "";
				})
				.filter(s -> !s.isEmpty())
				.toList();
	}

	public List<String> getRegisterPageLinkText() {
		logger.info("Fetching register page links.");
		return links.stream()
				.map(WebElement::getText)
				.map(String::trim)
				.toList();
	}

	public String getCompanyName() {
		String name = companyName.getText().trim();
		logger.info("Company name displayed: {}", name);
		return name;
	}

	public List<String> getPasswordRequirementsText() {
		logger.info("Fetching password requirement text.");
		return passwordReqList.stream()
				.filter(WebElement::isDisplayed)
				.map(WebElement::getText)
				.map(String::trim)
				.map(text -> text
						.replaceAll("[’']", "")
						.replaceAll("\\.$", "")
				)
				.toList();
	}
}