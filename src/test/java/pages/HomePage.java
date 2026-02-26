package pages;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DriverManager.DriverFactory;
import utils.WaitUtils;

public class HomePage {

	private WebDriver driver;
	private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

	@FindBy(linkText = "NumpyNinja")
	WebElement companyName;

	@FindBy(xpath = "//a[text()=' Register ']")
	WebElement registerLink;

	@FindBy(xpath = "//a[text()='Sign in']")
	WebElement logInLink;

	@FindBy(xpath = "//a[text()='Data Structures']")
	WebElement dataStructureDropdown;

	// all options in data structure dropdown
	@FindBy(xpath = "//a[@class='dropdown-item']")
	List<WebElement> dataStructureOptions;

	@FindBy(tagName = "h4")
	WebElement headingTitle;

	@FindBy(xpath = "//div[@role='alert']")
	WebElement ErrMsg;

	@FindBy(xpath = "//div[@class='card-body d-flex flex-column']")
	private List<WebElement> parentCard;

	@FindBy(css = "div.alert[role='alert']")
	private WebElement loginAlert;

	@FindBy(xpath = "//a[@href='/login']")
	private WebElement loginButton;

	@FindBy(xpath = "//a[text()=' ValidUser ']")
	WebElement loggedInUser;

	@FindBy(xpath = "//a[text()='Sign out']")
	WebElement signOutLink;

	@FindBy(xpath = "//ul")
	private WebElement parent;

	@FindBy(css = "div.alert[role='alert']")
	private WebElement alert;

	public HomePage() {
		this.driver = DriverFactory.getDriver();
		PageFactory.initElements(driver, this);
	}

	public String getLoginSuccessMessage() {
		return WaitUtils.getVisibleText(driver, loginAlert, 0);
	}

	public void clickSignInButton() {
		loginButton.click();
	}

	public String getCompanyName() {
		return companyName.getText();
	}

	public void clickDataStructureDropdown() {
		dataStructureDropdown.click();
	}

	public String getRightCornerLink(String linkText) {

		if (linkText == null) {
			logger.warn("Link text is null");
			return "Invalid Link Text";
		}
		String value;
		switch (linkText.toLowerCase()) {
		case "sign out":
			value = signOutLink.getText();
			break;

		case "validuser":
			value = loggedInUser.getText();
			break;

		default:
			logger.warn("Invalid Link Text: {}", linkText);
			return "Invalid Link Text";
		}

		logger.info("Getting text of '{}' link: {}", linkText, value);
		return value;
	}

	public List<String> getDataStructureOptionsText() {
		List<WebElement> options = dataStructureOptions;
		for (WebElement option : options) {
			logger.info("Data Structure Option: " + option.getText());
		}
		return options.stream().map(WebElement::getText).collect(Collectors.toList());

	}

	public String getErrMsg() {
		return ErrMsg.getText();
	}

	public void selectOption(String option) {
		dataStructureDropdown.click();
		for (WebElement opt : dataStructureOptions) {
			if (opt.getText().equals(option)) {
				opt.click();
				break;
			}
		}
	}

	public String getLinkName(String linkText) {
		if (linkText.equalsIgnoreCase("Register")) {
			logger.info("Getting text of Register link" + registerLink.getText());
			return registerLink.getText();
		} else if (linkText.equalsIgnoreCase("Sign in")) {
			logger.info("Getting text of Sign in link" + logInLink.getText());
			return logInLink.getText();
		} else {
			return "Invalid Link Text";
		}
	}

	public String getPageHeading(String optionName) {

		logger.info("Getting page heading for: {}", optionName);

		String heading = headingTitle.getText();
		logger.info("Page Heading is: {}", heading);

		return heading;
	}

	public void clickTitlePage(String ExpectedTitle) {
		String topicHeading;
		for (WebElement child : parentCard) {
			List<WebElement> grandChild = child.findElements(By.xpath(".//h5"));

			for (WebElement element : grandChild) {
				topicHeading = element.getText();
				if (topicHeading.equalsIgnoreCase(ExpectedTitle)) {
					element.findElement(By.xpath("..//a")).click();
					return;
				}
			}
		}
	}

	public void navigatetoPages(String pageInfo) {
		WaitUtils.waitForVisibility(driver, parent, 10);
		List<WebElement> childLink = parent.findElements(By.xpath(".//a"));
		for (WebElement eachLink : childLink) {
			String linkText = eachLink.getText();
			logger.info("Checking link: " + linkText);
			if (linkText.toLowerCase().contains(pageInfo.toLowerCase())) {
				logger.info("in line 57");
				WaitUtils.waitForVisibility(driver, eachLink, 10);
				eachLink.click();
				break;
			}
		}
	}

	public String getAlertMessage() {
		return alert.getText().trim();
	}

	public void clickGetStarted(String cardTitle) {
		logger.info("Clicking Get Started for card: " + cardTitle);
		for (WebElement child : parentCard) {
			List<WebElement> grandChild = child.findElements(By.xpath(".//h5"));

			for (WebElement element : grandChild) {
				element.getText();
				logger.info("In home" + element.getText());
				if (element.getText().equalsIgnoreCase(cardTitle)) {
					child.findElement(By.xpath(".//a")).click();
					return;
				}

			}
		}
	}
}