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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import DriverManager.DriverFactory;
import utils.JSUtils;
import utils.WaitUtils;

public class ArrayPage {

	private static final Logger logger = LogManager.getLogger(ArrayPage.class);

	private final WaitUtils wait;
	private final WebDriver driver;


	@FindBy(xpath = "//*[@class='bg-secondary text-white']")
	private List<WebElement> headings;

	@FindBy(xpath = "//a[@class='list-group-item']")
	private List<WebElement> array_subtopicslinks;

	@FindBy(xpath = "//a[contains(text(),'Try')]")
	private WebElement tryhere_button;

	@FindBy(xpath = "//button")
	private WebElement run_button;

	@FindBy(xpath = "//pre[@id='output']")
	private WebElement outputconsole;

	@FindBy(xpath = "//form[@id='answer_form']")
	private WebElement coding_area;

	@FindBy(xpath = "//a[contains(text(),'Practice Questions')]")
	private WebElement Practicequestionslink;

	@FindBy(xpath = "//div[@class='list-group']")
	private List<WebElement> questionslist;

	@FindBy(xpath = "//*[@type='submit']")
	private List<WebElement> submit;

	@FindBy(xpath = "//div[@align='left']")
	private WebElement output;


	public ArrayPage() {
		this.driver = DriverFactory.getDriver();
		PageFactory.initElements(this.driver, this);
		wait = new WaitUtils();
		logger.info("ArrayPage initialized successfully.");
	}

	public List<String> getheadingtext() {
		logger.info("Fetching heading texts from Array page.");
		wait.waitForVisibilityOfAll(headings);

		List<String> headingtexts = new ArrayList<>();
		for (WebElement heading : headings) {
			headingtexts.add(heading.getText().trim());
		}
		logger.info("Headings found: {}", headingtexts);
		return headingtexts;
	}

	public List<String> subtopiclinks() {
		logger.info("Fetching Array subtopic links.");
		wait.waitForVisibilityOfAll(array_subtopicslinks);

		List<String> subtopiclinks = new ArrayList<>();
		for (WebElement topiclink : array_subtopicslinks) {
			if (topiclink.isDisplayed() && topiclink.isEnabled()) {
				subtopiclinks.add(topiclink.getText().trim());
			}
		}
		logger.info("Subtopic links found: {}", subtopiclinks);
		return subtopiclinks;
	}

	public void clickTopicLink(String topicName) {
		logger.info("Clicking on topic link: {}", topicName);
		wait.waitForVisibilityOfAll(array_subtopicslinks);

		for (WebElement link : array_subtopicslinks) {
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
		logger.info("Clicking Try Here button");
		JSUtils.scrollIntoView(tryhere_button);

		wait.waitForClickable(tryhere_button).click();
	}

	public boolean isPracticeQuestionLinkVisible() {
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
		logger.info("Clicking Practice Questions link");
		logger.info("Clicking Practice Questions link.");
		JSUtils.scrollIntoView(Practicequestionslink);
		wait.waitForClickable(Practicequestionslink).click();
	}

	public List<String> getQuestionsList() {
		try {
			logger.info("Fetching practice questions list.");
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
		logger.info("Clicking problem link: {}", problemName);

		for (WebElement eachQuestion : questionslist) {
			String questionName = eachQuestion.getText();
			if (questionName.equalsIgnoreCase(problemName)) {
				eachQuestion.click();
				logger.info("Successfully clicked problem: {}", problemName);
				logger.info("Successfully clicked problem: {}", problemName);
				return;
			}
		}

		logger.warn("Problem link not found: {}", problemName);
	}

	public boolean getButtonTextAssessmentPage(String buttonText) {
		String button = run_button.getText();
		boolean match = button.equalsIgnoreCase(buttonText);
		logger.info("Run button text match with '{}': {}", buttonText, match);
		return match;
	}

	public boolean isSubmitButtonPresent() {
		boolean present = !submit.isEmpty();
		logger.info("Submit button present: {}", present);
		return present;
	}

	public void submitProblem() {
		logger.info("Clicking submit button.");
		for (WebElement eachType : submit) {
			eachType.click();
			return;
		}
		logger.warn("Submit button not found.");
	}

	public String getConsoleOutput() {
		logger.info("Fetching console output.");
		wait.waitForPageLoad();

		String result = wait.waitForCodeMirrorOutput("output", 120);
		logger.info("Submission result: {}", result);

		return result;
	}
}