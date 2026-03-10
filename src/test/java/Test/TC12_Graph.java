package Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import TestPackage.GraphData;
import utils.ElementUtil;

@Test(groups = { "Get Started", "Sign in" })
public class TC12_Graph extends Hooks {

	@BeforeMethod()
	public void clickGraphButton(Method method) {
		logger.info("Clicking Graph link in home page");
		pom.getHomePage().clickGetStarted("Graph");
		Test testAnnotation = method.getAnnotation(Test.class);
		List<String> groupList = Arrays.asList(testAnnotation.groups());
		if (groupList.contains("Graph")) {
			pom.getGraphPage().clickTopicLink("Graph");
		}
	}

	@Test(priority = 1)
	public void navigateToGraphPage() {

		logger.debug("Current page title: {}", ElementUtil.getTitle());
		Assert.assertEquals(ElementUtil.getTitle(), "Graph", "Failed to navigate to Graph page");

	}

	@Test(priority = 2, dataProvider = "StaticContent", dataProviderClass = GraphData.class)
	public void verifyStaticContent(String expectedText) {
		List<String> headings = pom.getGraphPage().getheadingtext();
		System.out.println("Headings: " + headings);
		logger.info("Headings: {}", headings);
		boolean found = false;

		for (String heading : headings) {
			if (heading.equalsIgnoreCase(expectedText)) {
				found = true;
				break;
			}
		}
		Assert.assertTrue(found, "Expected text '" + expectedText + "' not found in the headings: " + headings);
	}

	@Test(priority = 3, dataProvider = "SubtopicLink", dataProviderClass = GraphData.class)
	public void verifySubtopic_Link(String expectedSubtopics) {

		List<String> actualSubtopics = pom.getGraphPage().subtopiclinks();
		logger.info("Actual Subtopic links in graph page: " + actualSubtopics);

		logger.info("Expected Subtopic links in Graph page: " + expectedSubtopics);

		Assert.assertTrue(actualSubtopics.contains(expectedSubtopics),
				"Mismatch in subtopic link texts in Graph page: " + expectedSubtopics);
	}

	@Test(priority = 4, dataProvider = "SubtopicLinks", dataProviderClass = GraphData.class)
	public void navigateToSubTopicPage(String topicUrl, String pageurltext) {
		pom.getGraphPage().clickTopicLink(topicUrl);
		logger.info("Current page title: {}", ElementUtil.getTitle());
		String expected = pageurltext.toLowerCase().replace(" ", "-");
		Assert.assertTrue(ElementUtil.getURL().contains(expected),
				"URL does not contain expected text: " + pageurltext);
		ElementUtil.navigateBack();
	}

	@Test(priority = 5, groups = "Graph")
	public void verifyTryHereButtonVisibility() {
		logger.info("Checking Try Here button visibility for Graph page");

		boolean isDisplayed = pom.getGraphPage().checktryherebutton_displayed();
		if (!isDisplayed) {
			logger.error("Try Here button is not visible on Graph page");
		}
		{
			Assert.assertTrue(isDisplayed, "Try Here button is not visible on Graph page");
			logger.info("Verified Try Here button is visible on Graph page");
		}
	}

	@Test(priority = 6, groups = "Graph")
	public void navigateToTryEditorPage() {
		pom.getGraphPage().clickTryHereButton();
		Assert.assertTrue(ElementUtil.getURL().contains("tryEditor"), "user is not on tryeditor screen");
		logger.info("User is on the Try Editor page after clicking Try Here button");

	}

	@Test(priority = 7, groups = "Graph")
	public void navigateToPracticeQuestionsLink() {
		pom.getGraphPage().clickPracticeQuestionsLink();
		List<String> questions = pom.getGraphPage().getQuestionsList();
		Assert.assertFalse(questions.isEmpty(),
				"No questions are displayed in Practice Questions section of Graph module");
	}

	@AfterMethod
	public void backToHome(ITestResult result) {
		String[] groups = result.getMethod().getGroups();
		List<String> groupList = Arrays.asList(groups);

		if (groupList.contains("Graph")) {
			while (!ElementUtil.getURL().contains("home")) {
				ElementUtil.navigateBack();
			}
		}

	}

}
