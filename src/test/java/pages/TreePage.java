
package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import DriverManager.DriverFactory;

import utils.JSUtils;

import utils.WaitUtils;

public class TreePage {
	private WaitUtils wait;
	private WebDriver driver;
	private static final Logger logger = LogManager.getLogger(TreePage.class);
	// locators

	@FindBy(xpath = "//*[@class='bg-secondary text-white']")
	private List<WebElement> headings;

	@FindBy(xpath = "//a[@class='list-group-item']")
	private List<WebElement> tree_subtopicslinks;

	@FindBy(xpath = "//a[contains(text(),'Try')]")
	private WebElement tryhere_button;

	@FindBy(xpath = "//a[contains(text(),'Practice Questions')]")
	private WebElement Practicequestionslink;

	@FindBy(xpath = "//div[@class='list-group']")
	private List<WebElement> questionslist;

	// action

	public TreePage() {
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
		logger.info("Tree Headings found: {}", headingtexts);
		return headingtexts;
	}

	public List<String> subtopiclinks() {
		wait.waitForVisibilityOfAll(tree_subtopicslinks);
		List<String> subtopiclinks = new ArrayList<String>();
		for (WebElement topiclink : tree_subtopicslinks) {
			if (topiclink.isDisplayed() && topiclink.isEnabled()) {
				subtopiclinks.add(topiclink.getText().trim());
			}
		}
		logger.info("Tree Subtopic links found: {}", subtopiclinks);
		return subtopiclinks;
	}

	public void clickTopicLink(String topicName) {
		wait.waitForVisibilityOfAll(tree_subtopicslinks);
		for (WebElement link : tree_subtopicslinks) {
			if (link.getText().trim().equalsIgnoreCase(topicName)) {
				JSUtils.scrollIntoView(link);
				wait.waitForClickable(link).click();
				logger.info("Successfully clicked Tree topic link: {}", topicName);
				return;
			}
		}
		throw new NoSuchElementException("Topic link not found: " + topicName);
	}

	public boolean checktryherebutton_displayed() {
		logger.info("Checking visibility of Try Here button");
		return WaitUtils.isVisible(driver, tryhere_button, 10);
	}

	public void clickTryHereButton() {
		logger.info("Clicking Try Here button");
		JSUtils.scrollIntoView(tryhere_button);
		wait.waitForClickable(tryhere_button).click();
	}

	public boolean isPracticeQuestionLinkVisible() {
		logger.info("Checking if Practice Questions link is visible");
		return WaitUtils.isVisible(driver, Practicequestionslink, 10);
	}

	public boolean isPracticeQuestionLinkEnabled() {
		logger.info("Checking if Practice Questions link is enabled");
		return Practicequestionslink.isEnabled();
	}

	public void clickPracticeQuestionsLink() {
		logger.info("Clicking Practice Questions link");
		JSUtils.scrollIntoView(Practicequestionslink);
		wait.waitForClickable(Practicequestionslink).click();
	}

	public List<String> getQuestionsList() {
		try {
			wait.waitForVisibilityOfAll(questionslist);
			return questionslist.stream().map(WebElement::getText).collect(Collectors.toList());
		} catch (Exception e) {
			logger.info("Questions list not found: {}", e.getMessage());
			return Collections.emptyList();
		}
	}

}
