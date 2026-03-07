
package Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import TestPackage.QueueData;
import utils.ElementUtil;

@Test(groups = { "Get Started", "Sign in" })
public class TC10_Queue extends Hooks {

	@BeforeMethod
	public void navigateToQueuemainpage(Method method) {

		logger.info("Clicking get sarted button of Queue module in home page");
		pom.getHomePage().clickGetStarted("Queue");
		Test testAnnotation = method.getAnnotation(Test.class);
		List<String> groupList = Arrays.asList(testAnnotation.groups());
		if (groupList.contains("Implementation of Queue in Python")) {
			pom.getQueuePage().clickTopicLink("Implementation of Queue in Python");
		}
	}

	@Test(priority = 1, dataProvider = "Queuetext", dataProviderClass = QueueData.class)
	public void getQueuePage(String text) {
		logger.info("Navigated to DS module: {}", text);

		Assert.assertEquals(ElementUtil.getTitle(), "Queue", "Navigation failed to Queue page");
	}

	@Test(priority = 2, dataProvider = "QueueUIcontent", dataProviderClass = QueueData.class)
	public void Verifycontent(String content) {

		List<String> headings = pom.getQueuePage().getHeadingText();
		logger.info("Headings on Queue page: {}", headings);
		Assert.assertTrue(headings.contains(content), "Queue UI heading content missing:" + content);

	}

	@Test(priority = 3, dataProvider = "Queuetopics", dataProviderClass = QueueData.class)
	public void verifyQueuetopics(String expectedtopics) {
		List<String> actualSubtopics = pom.getQueuePage().subTopicLinks();
		logger.info("Validating Queue subtopics");

		Assert.assertTrue(actualSubtopics.contains(expectedtopics), "Missing topics in Queue UI: " + expectedtopics);

	}

	@Test(priority = 4, dataProvider = "Queuepageurl", dataProviderClass = QueueData.class)
	public void verifyQueuetopicurl(String topic, String topicurl) {
		pom.getQueuePage().clickTopicLink(topic);

		Assert.assertTrue(ElementUtil.getURL().contains(topicurl),
				"Queue page URL does not contain expected text: " + topicurl);
		logger.info("Verified URL contains '{}'", topicurl);

		ElementUtil.navigateBack();
	}

	@Test(priority = 5, groups = "Implementation of Queue in Python")
	public void checkTryHereButton() {

		Assert.assertTrue(pom.getQueuePage().checkTryhereButton_Display(), "Try Here button not visible");
		logger.info("Verified Try Here button is visible");

	}

	@Test(priority = 6, groups = "Implementation of Queue in Python")
	public void opencodeeditor() {

		pom.getQueuePage().clickTryHereButton();
		logger.info("Clicked '{}' button", "Try here>>>");
		Assert.assertTrue(ElementUtil.getURL().contains("tryEditor"), "User is not on tryEditor screen");
		logger.info("User navigated to code editor page");

	}

	@Test(priority = 7, groups = "Implementation of Queue in Python")
	public void checkpracticequestionlink() {

		boolean isLinkVisible = pom.getQueuePage().isPracticeQuestionLinkVisible();
		Assert.assertTrue(isLinkVisible, "Practice Questions link not present ");
		logger.info("Practice Questions link is visible in Queue subtopic page");

	}

	@Test(priority = 8, groups = "Implementation of Queue in Python")
	public void viewpracticequestions() {

		pom.getQueuePage().clickPracticeQuestionsLink();

		Assert.assertTrue(ElementUtil.getURL().contains("practice"), "User is not on Queue practice questions page");
		logger.info("Navigated to Practice Questions UI from Queue page");
		List<String> questionList = pom.getQueuePage().getQuestionsList();

		logger.info("Actual Questions: {}", questionList);

		Assert.assertTrue(!questionList.isEmpty(), "Practice question list is empty");

		logger.info("Verified Queue practice questions page");

	}

	@AfterMethod
	public void backToHome(ITestResult result) {
		String[] groups = result.getMethod().getGroups();
		List<String> groupList = Arrays.asList(groups);

		if (groupList.contains("Implementation of Queue in Python")) {
			while (!ElementUtil.getURL().contains("home")) {
				ElementUtil.navigateBack();
			}
		}
	}

}
