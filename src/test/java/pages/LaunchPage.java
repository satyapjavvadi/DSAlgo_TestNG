package pages;

import java.util.ArrayList;
import java.util.List;

import DriverManager.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.JSUtils;

public class LaunchPage {

    private static final Logger logger = LoggerFactory.getLogger(LaunchPage.class);

    private WebDriver driver;

    // Locators
    @FindBy(xpath = "//*[text()='Preparing for the Interviews']")
    private WebElement interviewText;

    @FindBy(linkText = "Get Started")
    private WebElement getStartedButton;

    @FindBy(xpath = "//*[contains(text(),'Copyright@NumpyNinja2021')]")
    private WebElement copyrightInfo;

    @FindBy(tagName = "button")
    private List<WebElement> buttons;

    // Constructor
    public LaunchPage() {
        this.driver = DriverFactory.getDriver();
        PageFactory.initElements(driver, this);
        logger.info("LaunchPage initialized successfully.");
    }

    // Browser checks
    public void browserIsOpen() {
        logger.info("Checking if browser is initialized.");
        if (driver == null) {
            logger.error("WebDriver is not initialized.");
            throw new IllegalStateException("WebDriver is not initialized");
        }
        logger.info("WebDriver is active.");
    }

    public boolean doesPageContainText(String expectedText) {
        logger.info("Validating page contains text: {}", expectedText);
        boolean result = JSUtils.getPageInnerText().contains(expectedText);
        logger.info("Text validation result: {}", result);
        return result;
    }

    public void clickGetStartedButton() {
        logger.info("Clicking 'Get Started' button.");
        getStartedButton.click();
    }

    public int getButtonCount() {
        int count = buttons.size();
        logger.info("Total buttons found on page: {}", count);
        return count;
    }

    public List<String> getButtonText() {
        logger.info("Fetching all button texts.");
        List<String> buttonTexts = new ArrayList<>();

        for (WebElement button : buttons) {
            buttonTexts.add(button.getText());
        }

        logger.debug("Button texts: {}", buttonTexts);
        return buttonTexts;
    }

    public boolean doesPageContainButtonWithText(String expectedButtonText) {
        logger.info("Checking if button with text '{}' exists.", expectedButtonText);
        boolean exists = getButtonText().contains(expectedButtonText);
        logger.info("Button existence result: {}", exists);
        return exists;
    }

    public void clickButtonByText(String buttonText) {
        logger.info("Attempting to click button with text: {}", buttonText);

        for (WebElement button : buttons) {
            if (button.getText().equalsIgnoreCase(buttonText)) {
                button.click();
                logger.info("Clicked button: {}", buttonText);
                return;
            }
        }

        logger.warn("Button with text '{}' not found.", buttonText);
    }

    // URL
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.info("Current URL: {}", url);
        return url;
    }

    public boolean isButtonEnabled(String buttonText) {
        logger.info("Checking if button '{}' is enabled.", buttonText);

        for (WebElement button : buttons) {
            if (button.getText().equalsIgnoreCase(buttonText)) {
                boolean enabled = button.isEnabled();
                logger.info("Button '{}' enabled status: {}", buttonText, enabled);
                return enabled;
            }
        }

        logger.warn("Button '{}' not found.", buttonText);
        return false;
    }
}