package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DriverManager.DriverFactory;
import utils.JSUtils;
import utils.WaitUtils;

public class DataStructurePage {
	private WebDriver driver;
	private WaitUtils wait;
	private static final Logger logger = LoggerFactory.getLogger(DataStructurePage.class);

	@FindBy(xpath = "//*[contains(@class,'text-white')]")
	private List<WebElement> headings; // static content

	@FindBy(xpath = "//a[@class='list-group-item']")
	private List<WebElement> dataStr_subtopicslinks; // subtopic links under topics covered

	@FindBy(xpath = "//a[contains(text(),'Try')]")
	private WebElement tryhere_button;

	@FindBy(xpath = "//button[text()='Run']")
	WebElement run_Button;

	@FindBy(xpath = "//a[contains(text(),'Practice Questions')]")
	private WebElement Practicequestionslink;

	@FindBy(xpath = "//div[@class='list-group']")
	private List<WebElement> questionslist;

	public DataStructurePage() {
		this.driver = DriverFactory.getDriver();
		PageFactory.initElements(this.driver, this);
		wait = new WaitUtils();
	}

	public List<String> getheadingtext() {
		wait.waitForVisibilityOfAll(headings);
		List<String> headingtexts = new ArrayList<String>();
		for (WebElement heading : headings) {
			headingtexts.add(heading.getText().trim());
		}
		return headingtexts;
	}

	// used
	public void clickTopicLink(String topicName) {
		wait.waitForVisibilityOfAll(dataStr_subtopicslinks);
		for (WebElement link : dataStr_subtopicslinks) {
			if (link.getText().trim().equalsIgnoreCase(topicName)) {
				JSUtils.scrollIntoView(link);
				wait.waitForClickable(link).click();
				return;
			}
		}
		throw new NoSuchElementException("Topic link not found: " + topicName);
	}

	public boolean checktryherebutton_displayed() {
		return WaitUtils.isVisible(driver, tryhere_button, 10);
	}

	public void clickTryHereButton() {
		JSUtils.scrollIntoView(tryhere_button);
		wait.waitForClickable(tryhere_button).click();
	}

	public void clickPracticeQuestionsLink() {
		JSUtils.scrollIntoView(Practicequestionslink);
		wait.waitForClickable(Practicequestionslink).click();
	}

	public List<String> getQuestionsList() {
		try {
			wait.waitForVisibilityOfAll(questionslist);
			return questionslist.stream().map(WebElement::getText).collect(Collectors.toList());
		} catch (Exception e) {
			logger.info("Questions list not found: " + e.getMessage());
			return Collections.emptyList();
		}
	}

	public String getErrAlert() {
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		alert.accept();
		return alertText;
	}

	public void clickrunBtn() {
		run_Button.click();
	}

	public String getRunBtnText() {
		return run_Button.getText();
	}

}