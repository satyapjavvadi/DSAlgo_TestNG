package Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import TestPackage.StackData;
import utils.ElementUtil;

@Test(groups = { "Stack", "Get Started", "Sign in" })
public class TC09_Stack extends Hooks {

	@BeforeMethod
	public void openStackPage(Method method) {
		logger.info("Clicking get started button of Stack module in home page");
		pom.getHomePage().clickGetStarted("Stack");
		Test testAnnotation = method.getAnnotation(Test.class);
		List<String> groupList = Arrays.asList(testAnnotation.groups());
		if (groupList.contains("Operations in Stack")) {
			pom.getStackPage().clickTopicLink("Operations in Stack");
		}

	}

	@Test(priority = 0, dataProvider = "staticContent", dataProviderClass = StackData.class)
	public void verifyStaticContent(String expectedText) {
		logger.info("Verifying static content: {}", expectedText);
		Assert.assertTrue(ElementUtil.getURL().contains("stack"));

		List<String> headings = pom.getStackPage().getHeadingText();
		Assert.assertTrue(headings.contains(expectedText), "Static content missing:" + expectedText);
	}

	@Test(priority = 1, dataProvider = "StackSubTopics", dataProviderClass = StackData.class)
	public void verifyTopicLinks(String expectedTopics) {
		List<String> topics = pom.getStackPage().getSubtopicLinks();
		Assert.assertTrue(topics.contains(expectedTopics), "Topic link missing in Stack page: " + expectedTopics);
		logger.info("Verified all Stack topic links");
	}

	@Test(priority = 2, dataProvider = "topics", dataProviderClass = StackData.class)
	public void verifyTopicNavigation(String topic) {
		pom.getStackPage().clickTopicLink(topic);

		Assert.assertTrue(pom.getStackPage().getHeadingText().contains(topic),
				"Topic content not visible for: " + topic);

		logger.info("Verified topic navigation: {}", topic);

		ElementUtil.navigateBack();
	}

	@Test(priority = 3, groups = "Operations in Stack")
	public void verifyTryHereButton() {

		Assert.assertTrue(pom.getStackPage().isTryHereButtonDisplayed(), "Try Here button not visible");
		logger.info("Try Here button is visible");

	}

	@Test(priority = 4, groups = "Operations in Stack")
	public void verifyTryHereEditorOpens() {
		pom.getStackPage().clickTryHereButton();

		Assert.assertTrue(ElementUtil.getURL().contains("tryEditor"), "Try Editor did not open");
		logger.info("Try Editor opened successfully");
	}

	@Test(priority = 5, groups = "Operations in Stack")
	public void verifyPracticeQuestionsLink() {
		Assert.assertTrue(pom.getStackPage().isPracticeQuestionLinkVisible(), "Practice Questions Link not visible");
		logger.info("Practice Questions link is visible");
	}

	@Test(priority = 6, groups = "Operations in Stack")
	public void verifyPracticeQuestionsPage() {
		pom.getStackPage().clickPracticeQuestionsLink();
		Assert.assertTrue(ElementUtil.getURL().contains("practice"), "Practice Questions page title missing");
		logger.info("Practice questions page opened");
	}

	@Test(priority = 7, groups = "Operations in Stack")
	public void verifyPracticeQuestionList() {
		List<String> questions = pom.getStackPage().getQuestionsList();
		Assert.assertFalse(questions.isEmpty(),
				"No questions are displayed in Practice Questions section of Stack module");
		logger.info("Practice questions displayed: {}", questions);
	}

	@AfterMethod
	public void backToHome(ITestResult result) {
		String[] groups = result.getMethod().getGroups();
		List<String> groupList = Arrays.asList(groups);

		if (groupList.contains("Operations in Stack")) {
			while (!ElementUtil.getURL().contains("home")) {
				ElementUtil.navigateBack();
			}
		}
	}

}
