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

public class GraphPage {

	private WebDriver driver;
	private WaitUtils wait;
	private static final Logger logger = LoggerFactory.getLogger(GraphPage.class);

	@FindBy(xpath = "//*[contains(@class,'text-white')]")
	private List<WebElement> headings;

	@FindBy(xpath = "//a[@class='list-group-item']")
	private List<WebElement> Graph_subtopicslinks;

	@FindBy(xpath = "//a[contains(text(),'Try')]")
	private WebElement tryhere_button;

	@FindBy(xpath = "//button[text()='Run']")
	WebElement run_Button;

	@FindBy(xpath = "//a[contains(text(),'Practice Questions')]")
	private WebElement Practicequestionslink;

	@FindBy(xpath = "//div[@class='list-group']")
	private List<WebElement> questionslist;

	@FindBy(xpath = "//button[text()='Run']")
	WebElement runBtn;

	@FindBy(id = "output")
	WebElement output;

	public GraphPage() {
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

	public List<String> subtopiclinks() {
		wait.waitForVisibilityOfAll(Graph_subtopicslinks);
		List<String> subtopiclinks = new ArrayList<String>();
		for (WebElement topiclink : Graph_subtopicslinks) {
			if (topiclink.isDisplayed() && topiclink.isEnabled()) {
				subtopiclinks.add(topiclink.getText().trim());
			}
		}
		return subtopiclinks;
	}

	public void clickTopicLink(String topicName) {
		wait.waitForVisibilityOfAll(Graph_subtopicslinks);
		for (WebElement link : Graph_subtopicslinks) {
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

	public boolean checkrunbutton_displayed() {
		return WaitUtils.isVisible(driver, run_Button, 10);
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

	public void clickProblemLink(String problemName) {
		for (WebElement eachQuestion : questionslist) {
			String questionName = eachQuestion.getText();
			if (questionName.equalsIgnoreCase(problemName)) {
				eachQuestion.click();
				return;
			}
		}
	}
}