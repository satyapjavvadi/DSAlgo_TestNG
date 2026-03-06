package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DriverManager.DriverFactory;
import utils.JSUtils;
import utils.WaitUtils;

public class LinkedListPage {

	private static final Logger logger = LoggerFactory.getLogger(LinkedListPage.class);

	private WaitUtils wait;
	private WebDriver driver;

	@FindBy(xpath = "//*[contains(@class,'text-white')]")
	private List<WebElement> headings;

	@FindBy(xpath = "//a[@class='list-group-item']")
	private List<WebElement> LinkedList_subtopicslinks;

	@FindBy(xpath = "//a[contains(text(),'Try')]")
	private WebElement tryhere_button;

	@FindBy(xpath = "//a[contains(text(),'Practice Questions')]")
	private WebElement Practicequestionslink;

	@FindBy(xpath = "//div[@class='list-group']")
	private List<WebElement> questionslist;

	public LinkedListPage() {
		this.driver = DriverFactory.getDriver();
		PageFactory.initElements(this.driver, this);
		wait = new WaitUtils();
		logger.info("LinkedListPage initialized successfully.");
	}

	public List<String> getheadingtext() {
		logger.info("Fetching LinkedList headings.");
		wait.waitForVisibilityOfAll(headings);

		List<String> headingtexts = new ArrayList<>();
		for (WebElement heading : headings) {
			headingtexts.add(heading.getText().trim());
		}

		logger.debug("Headings found: {}", headingtexts);
		return headingtexts;
	}

	public List<String> subtopiclinks() {
		logger.info("Fetching LinkedList subtopic links.");
		wait.waitForVisibilityOfAll(LinkedList_subtopicslinks);

		List<String> subtopiclinks = new ArrayList<>();
		for (WebElement topiclink : LinkedList_subtopicslinks) {
			if (topiclink.isDisplayed() && topiclink.isEnabled()) {
				subtopiclinks.add(topiclink.getText().trim());
			}
		}

		logger.debug("Subtopics found: {}", subtopiclinks);
		return subtopiclinks;
	}

	public void clickTopicLink(String topicName) {
		logger.info("Attempting to click topic: {}", topicName);
		wait.waitForVisibilityOfAll(LinkedList_subtopicslinks);

		for (WebElement link : LinkedList_subtopicslinks) {
			if (link.getText().trim().equalsIgnoreCase(topicName)) {
				JSUtils.scrollIntoView(link);
				wait.waitForClickable(link).click();
				logger.info("Successfully clicked topic: {}", topicName);
				return;
			}
		}

		logger.error("Topic link not found: {}", topicName);
		throw new NoSuchElementException("Topic link not found: " + topicName);
	}

	public boolean checktryherebutton_displayed() {
		boolean visible = WaitUtils.isVisible(driver, tryhere_button, 10);
		logger.info("Try Here button visible: {}", visible);
		return visible;
	}

	public void clickTryHereButton() {
		logger.info("Clicking Try Here button.");
		JSUtils.scrollIntoView(tryhere_button);
		wait.waitForClickable(tryhere_button).click();
	}

	public boolean isPracticeQuestionLinkVisible() {
		logger.info("Checking Practice Question link visibility.");
		JSUtils.scrollIntoView(Practicequestionslink);
		boolean visible = WaitUtils.isVisible(driver, Practicequestionslink, 10);
		logger.info("Practice Question link visible: {}", visible);
		return visible;
	}

	public boolean isPracticeQuestionLinkEnabled() {
		boolean enabled = Practicequestionslink.isEnabled();
		logger.info("Practice Question link enabled: {}", enabled);
		return enabled;
	}

	public void clickPracticeQuestionsLink() {
		logger.info("Clicking Practice Questions link.");
		JSUtils.scrollIntoView(Practicequestionslink);
		wait.waitForClickable(Practicequestionslink).click();
	}

	public List<String> getQuestionsList() {
		try {
			logger.info("Fetching LinkedList practice questions.");
			wait.waitForVisibilityOfAll(questionslist);

			List<String> questions = questionslist.stream()
					.map(WebElement::getText)
					.collect(Collectors.toList());

			logger.debug("Questions found: {}", questions);
			return questions;

		} catch (Exception e) {
			logger.error("Questions list not found: {}", e.getMessage());
			return Collections.emptyList();
		}
	}

	public void clickProblemLink(String problemName) {
		logger.info("Attempting to click problem: {}", problemName);

		for (WebElement eachQuestion : questionslist) {
			String questionName = eachQuestion.getText();
			if (questionName.equalsIgnoreCase(problemName)) {
				eachQuestion.click();
				logger.info("Successfully clicked problem: {}", problemName);
				return;
			}
		}

		logger.warn("Problem link not found: {}", problemName);
	}
}