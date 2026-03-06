package Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import DriverManager.DriverFactory;
import TestPackage.LinkedListData;
import utils.ElementUtil;

@Test(groups = { "LinkedList", "Get Started", "Sign in" })
public class TC08_LinkedList extends Hooks {

	@BeforeMethod
	public void openLinkedListPage(Method method) {
		logger.info("Clicking get started button of Linked List module in home page");
		pom.getHomePage().clickGetStarted("Linked List");
		Test testAnnotation = method.getAnnotation(Test.class);
		List<String> groupList = Arrays.asList(testAnnotation.groups());
		if (groupList.contains("Introduction")) {
			pom.getLinkedListPage().clickTopicLink("Introduction");
		} else if (groupList.contains("Creating Linked LIst")) {
			pom.getLinkedListPage().clickTopicLink("Creating Linked List");
		} else if (groupList.contains("Types of Linked List")) {
			pom.getLinkedListPage().clickTopicLink("Types of Linked List");
		} else if (groupList.contains("Implement Linked List in Python")) {
			pom.getLinkedListPage().clickTopicLink("Implement Linked List in Python");
		} else if (groupList.contains("Traversal")) {
			pom.getLinkedListPage().clickTopicLink("Traversal");
		} else if (groupList.contains("Insertion")) {
			pom.getLinkedListPage().clickTopicLink("Insertion");
		} else if (groupList.contains("Deletion")) {
			pom.getLinkedListPage().clickTopicLink("Deletion");
		}
	}

	@Test(priority = 0, dataProvider = "staticContent", dataProviderClass = LinkedListData.class)
	public void verifyStaticContent(String expectedText) {
		logger.info("Verifying static content: {}", expectedText);
		Assert.assertTrue(ElementUtil.getURL().contains("linked-list"));

		List<String> headings = pom.getLinkedListPage().getheadingtext();
		Assert.assertTrue(headings.contains(expectedText), "Static content missing:" + expectedText);
	}

	@Test(priority = 1, dataProvider = "LLSubTopics", dataProviderClass = LinkedListData.class)
	public void verifyTopicLinks(String expectedTopics) {
		List<String> topics = pom.getLinkedListPage().subtopiclinks();
		Assert.assertTrue(topics.contains(expectedTopics), "Topic link missing in Linked List page: " + expectedTopics);
		logger.info("Verified all Linked List topic links");
	}

	@Test(priority = 2, dataProvider = "topics", dataProviderClass = LinkedListData.class)
	public void verifyTopicNavigation(String topic) {
		pom.getLinkedListPage().clickTopicLink(topic);

		Assert.assertTrue(pom.getLinkedListPage().getheadingtext().contains(topic),
				"Topic content not visible for: " + topic);

		logger.info("Verified topic navigation: {}", topic);

		ElementUtil.navigateBack();
	}

	@Test(priority = 3, groups = "Introduction")
	public void verifyTryHereButton() {

		Assert.assertTrue(pom.getLinkedListPage().checktryherebutton_displayed(), "Try Here button not visible");
		logger.info("Try Here button is visible");

	}

	@Test(priority = 4, groups = "Introduction")
	public void verifyTryHereEditorOpens() {
		pom.getLinkedListPage().clickTryHereButton();

		Assert.assertTrue(ElementUtil.getURL().contains("tryEditor"), "Try Editor did not open");
		logger.info("Try Editor opened successfully");
		DriverFactory.getDriver().navigate().back();
	}

	@Test(priority = 5, groups = "Introduction")
	public void verifyPracticeQuestionsLink() {
		Assert.assertTrue(pom.getLinkedListPage().isPracticeQuestionLinkVisible(),
				"Practice Questions Link not visible");
		logger.info("Practice Questions link is visible");
	}

	@Test(priority = 6, groups = "Introduction")
	public void verifyPracticeQuestionsPage() {
		pom.getLinkedListPage().clickPracticeQuestionsLink();
		Assert.assertTrue(DriverFactory.getDriver().getPageSource().contains("Practice Questions"),
				"Practice Questions page title missing");
		logger.info("Practice questions page opened");
	}

	@Test(priority = 7, groups = "Introduction")
	public void verifyPracticeQuestionList() {
		pom.getLinkedListPage().clickPracticeQuestionsLink();
		List<String> questions = pom.getLinkedListPage().getQuestionsList();
		Assert.assertTrue(!questions.isEmpty(), "Practice question list is empty");
		logger.info("Practice questions displayed: {}", questions);
	}

	@AfterMethod
	public void backToHome(ITestResult result) {
		String[] groups = result.getMethod().getGroups();
		List<String> groupList = Arrays.asList(groups);

		if (groupList.contains("Introduction")) {
			while (!ElementUtil.getURL().contains("home")) {
				ElementUtil.navigateBack();
			}
		}
	}

}
