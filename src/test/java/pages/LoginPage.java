package pages;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DriverManager.DriverFactory;
import utils.ConfigReader;
import utils.ExcelReader;
import utils.JSUtils;
import utils.TestContext;
import utils.WaitUtils;

public class LoginPage {

	private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

	private WebDriver driver;

	// Locators
	@FindBy(id = "id_username")
	private WebElement usernameField;

	@FindBy(id = "id_password")
	private WebElement passwordField;

	@FindBy(xpath = "//input[@value='Login']")
	private WebElement loginButton;

	@FindBy(xpath = ("//div[@role='alert']"))
	private WebElement error;

	@FindBy(linkText = "Register")
	private WebElement registerLink;

	@FindBy(xpath = "//label")
	private List<WebElement> labelList;

	@FindBy(xpath = "//input[@type='submit']")
	private List<WebElement> buttonElements;

	// Constructor
	public LoginPage() {
		this.driver = DriverFactory.getDriver();
		PageFactory.initElements(driver, this);
		logger.info("LoginPage initialized successfully.");
	}

	public void enterUsername(String username) {
		logger.info("Entering username.");
		WebElement field = WaitUtils.waitForVisibility(driver, usernameField, 10);
		field.clear();
		field.sendKeys(username);
	}

	public void enterPassword(String password) {
		logger.info("Entering password.");
		if (password != null && !password.isEmpty()) {
			passwordField.clear();
			passwordField.sendKeys(password);
		} else {
			logger.warn("Password provided is null or empty.");
		}
	}

	// Submission
	public void clickLoginButton() {
		logger.info("Clicking Login button.");
		JSUtils.clickElement(loginButton);
	}

	public void pressEnterToSubmit() {
		logger.info("Submitting login using ENTER key.");
		passwordField.sendKeys(Keys.ENTER);
	}

	// Helpers

	public boolean isLoginPageVisible() {
		boolean visible = usernameField.isDisplayed() && passwordField.isDisplayed();
		logger.info("Login page fields visible: {}", visible);
		return visible;
	}

	public boolean isRegisterLinkVisible() {
		boolean visible = registerLink.isDisplayed();
		logger.info("Register link visible: {}", visible);
		return visible;
	}

	public String getDisplayedErrorMessage(String inField) {

		logger.info("Fetching error message for: {}", inField);
		String errorMessage = null;

		switch (inField) {
			case "username field":
				errorMessage = usernameField.getAttribute("validationMessage");
				break;

			case "password field":
				errorMessage = passwordField.getAttribute("validationMessage");
				break;

			case "alert":
				errorMessage = error.getText();
				break;

			default:
				logger.warn("Unknown error field type: {}", inField);
		}

		logger.info("Error message retrieved: {}", errorMessage);
		return errorMessage;
	}

	// Reusable login helper
	public void login(String method, String scenarioType) {

		logger.info("Executing login with method: {} and scenario: {}", method, scenarioType);

		TestContext.testData = ExcelReader.getTestData(scenarioType);

		enterUsername(TestContext.testData.get("username"));
		enterPassword(TestContext.testData.get("password"));

		switch (method.toLowerCase().trim()) {
			case "submits the login form":
			case "initiates login":
			case "submits the login form with mouse click":
				clickLoginButton();
				break;

			case "presses enter":
			case "confirms login using enter":
				pressEnterToSubmit();
				break;

			default:
				logger.error("Unknown submission method: {}", method);
				throw new IllegalArgumentException("Unknown submission method: " + method);
		}
	}

	public void waitForHomeRedirect() {
		logger.info("Waiting for redirect to Home page.");
		WaitUtils.waitForUrlContains(driver, "/home", 10);
	}

	// Non functional testcases

	public int getInputFieldCount() {
		int count = labelList.size();
		logger.info("Total input fields (labels) found: {}", count);
		return count;
	}

	public int getButtonCount() {
		int count = buttonElements.size();
		logger.info("Total buttons found: {}", count);
		return count;
	}

	public List<String> getButtonText() {
		logger.info("Fetching login page button texts.");

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

	public List<String> getLoginLabelNames() {
		logger.info("Fetching login page label names.");

		return labelList.stream()
				.map(WebElement::getText)
				.map(String::trim)
				.toList();
	}
}