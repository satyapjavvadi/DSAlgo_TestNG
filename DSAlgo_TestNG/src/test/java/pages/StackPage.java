package pages;

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

public class StackPage {

	private static final Logger logger = LoggerFactory.getLogger(StackPage.class);

	private final WebDriver driver;
	private final WaitUtils wait;

	@FindBy(xpath = "//*[@class='bg-secondary text-white']")
	private List<WebElement> headings;

	@FindBy(xpath = "//a[@class='list-group-item']")
	private List<WebElement> topicLinks;

	@FindBy(xpath = "//a[contains(text(),'Try')]")
	private WebElement tryHereButton;

	@FindBy(xpath = "//a[contains(text(),'Practice Questions')]")
	private WebElement practiceQuestionsLink;

	@FindBy(xpath = "//div[@class='list-group']")
	private List<WebElement> questionsList;

	public StackPage() {
		this.driver = DriverFactory.getDriver();
		PageFactory.initElements(driver, this);
		this.wait = new WaitUtils();
		logger.info("StackPage initialized successfully.");
	}

	public List<String> getHeadingText() {
		logger.info("Fetching Stack page headings.");
		wait.waitForVisibilityOfAll(headings);

		List<String> headingTexts = headings.stream()
				.map(e -> e.getText().trim())
				.collect(Collectors.toList());

		logger.debug("Headings found: {}", headingTexts);
		return headingTexts;
	}

	public List<String> getSubtopicLinks() {
		logger.info("Fetching Stack subtopic links.");
		wait.waitForVisibilityOfAll(topicLinks);

		List<String> subtopics = topicLinks.stream()
				.map(e -> e.getText().trim())
				.collect(Collectors.toList());

		logger.debug("Subtopics found: {}", subtopics);
		return subtopics;
	}

	public void clickTopicLink(String topicName) {

		logger.info("Attempting to click topic link: {}", topicName);

		logger.debug("Available topic links:");
		topicLinks.forEach(link ->
				logger.debug("-> [{}]", link.getText().trim())
		);

		for (WebElement link : topicLinks) {
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

	public boolean isTryHereButtonDisplayed() {
		boolean visible = WaitUtils.isVisible(driver, tryHereButton, 10);
		logger.info("Try Here button visible: {}", visible);
		return visible;
	}

	public void clickTryHereButton() {
		logger.info("Clicking Try Here button.");
		JSUtils.scrollIntoView(tryHereButton);
		wait.waitForClickable(tryHereButton).click();
	}

	public boolean isPracticeQuestionLinkVisible() {
		logger.info("Checking Practice Questions link visibility.");
		JSUtils.scrollIntoView(practiceQuestionsLink);

		boolean visible = WaitUtils.isVisible(driver, practiceQuestionsLink, 10);
		logger.info("Practice Questions link visible: {}", visible);
		return visible;
	}

	public boolean isPracticeQuestionLinkEnabled() {
		boolean enabled = practiceQuestionsLink.isEnabled();
		logger.info("Practice Questions link enabled: {}", enabled);
		return enabled;
	}

	public void clickPracticeQuestionsLink() {
		logger.info("Clicking Practice Questions link.");
		JSUtils.scrollIntoView(practiceQuestionsLink);
		wait.waitForClickable(practiceQuestionsLink).click();
	}

	public List<String> getQuestionsList() {
		try {
			logger.info("Fetching Stack practice questions.");
			wait.waitForVisibilityOfAll(questionsList);

			List<String> questions = questionsList.stream()
					.map(e -> e.getText().trim())
					.collect(Collectors.toList());

			logger.debug("Questions found: {}", questions);
			return questions;

		} catch (Exception e) {
			logger.error("Questions list not found: {}", e.getMessage());
			return Collections.emptyList();
		}
	}
}