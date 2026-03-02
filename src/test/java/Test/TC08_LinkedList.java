package Test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import DriverManager.DriverFactory;
import TestPackage.LinkedListData;
import utils.ElementUtil;

@Test(groups = { "LinkedList", "Get Started", "Sign in" })
public class TC08_LinkedList extends Hooks {

	@BeforeClass
	public void openLinkedListPage() {
		pom.getHomePage().clickGetStarted("Linked List");
		logger.info("User is in LinkedList page");
		Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("linked-list"),
				"Navigation to Linked List page failed. Current URL: " + DriverFactory.getDriver().getCurrentUrl());
	}

	@Test(priority = 0, dataProvider = "staticContent", dataProviderClass = LinkedListData.class)
	public void verifyStaticContent(String expectedText) {
		logger.info("Verifying static content: {}", expectedText);
		Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("linked-list"));

		List<String> headings = pom.getLinkedListPage().getheadingtext();
		Assert.assertTrue(headings.contains(expectedText), "Static content missing:" + expectedText);
	}

	@Test(priority = 1)
	public void verifyTopicLinks() {
		List<String> topics = pom.getLinkedListPage().subtopiclinks();
		List<String> expected = List.of("Introduction", "Creating Linked LIst", "Types of Linked List",
				"Implement Linked List in Python", "Traversal", "Insertion", "Deletion");
		Assert.assertEquals(topics, expected, "Topic links mismatch");
		logger.info("Verified all Linked List topic links");
	}

	@Test(priority = 2, dataProvider = "topics", dataProviderClass = LinkedListData.class)
	public void verifyTopicNavigation(String topic) {
		pom.getLinkedListPage().clickTopicLink(topic);

		Assert.assertTrue(pom.getLinkedListPage().getheadingtext().contains(topic),
				"Topic content not visible for: " + topic);

		logger.info("Verified topic navigation: {}", topic);

		DriverFactory.getDriver().navigate().back();
	}

	@Test(priority = 3)
	public void verifyTryHereButton() {
		pom.getLinkedListPage().clickTopicLink("Introduction");

		Assert.assertTrue(pom.getLinkedListPage().checktryherebutton_displayed(), "Try Here button not visible");
		logger.info("Try Here button is visible");

	}

	@Test(priority = 4)
	public void verifyTryHereEditorOpens() {
		pom.getLinkedListPage().clickTryHereButton();

		Assert.assertTrue(ElementUtil.getURL().contains("tryEditor"), "Try Editor did not open");
		logger.info("Try Editor opened successfully");
		DriverFactory.getDriver().navigate().back();
	}

	@Test(priority = 5)
	public void verifyPracticeQuestionsLink() {
		pom.getHomePage().clickGetStarted("Linked List");
		pom.getLinkedListPage().clickTopicLink("Introduction");
		Assert.assertTrue(pom.getLinkedListPage().isPracticeQuestionLinkVisible(),
				"Practice Questions Link not visible");
		logger.info("Practice Questions link is visible");
	}

	@Test(priority = 6)
	public void verifyPracticeQuestionsPage() {
		pom.getLinkedListPage().clickTopicLink("Introduction");
		pom.getLinkedListPage().clickPracticeQuestionsLink();
		Assert.assertTrue(DriverFactory.getDriver().getPageSource().contains("Practice Questions"),
				"Practice Questions page title missing");
		logger.info("Practice questions page opened");
	}

	@Test(priority = 7)
	public void verifyPracticeQuestionList() {
		pom.getLinkedListPage().clickTopicLink("Introduction");
		pom.getLinkedListPage().clickPracticeQuestionsLink();
		List<String> questions = pom.getLinkedListPage().getQuestionsList();
		Assert.assertTrue(!questions.isEmpty(), "Practice question list is empty");
		logger.info("Practice questions displayed: {}", questions);
	}

}
