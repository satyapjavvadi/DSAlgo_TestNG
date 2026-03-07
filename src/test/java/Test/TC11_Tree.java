
package Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import TestPackage.TreeData;
import utils.ElementUtil;

@Test(groups = { "Get Started", "Sign in" })
public class TC11_Tree extends Hooks {

	@BeforeMethod
	public void navigateToTreemainpage(Method method) {

		logger.info("Clicking get sarted button of Tree module in home page");
		pom.getHomePage().clickGetStarted("Tree");
		Test testAnnotation = method.getAnnotation(Test.class);
		List<String> groupList = Arrays.asList(testAnnotation.groups());
		if (groupList.contains("Overview of Trees")) {
			pom.getTreePage().clickTopicLink("Overview of Trees");
		}
	}

	@Test(priority = 1, dataProvider = "Treetext", dataProviderClass = TreeData.class)
	public void getTreePage(String text) {
		logger.info("Navigated to DS module: {}", text);

		Assert.assertEquals(ElementUtil.getTitle(), "Tree", "Navigation failed to Tree page");
	}

	@Test(priority = 2, dataProvider = "TreeUIcontent", dataProviderClass = TreeData.class)
	public void Verifycontent(String content) {

		List<String> headings = pom.getTreePage().getheadingtext();
		logger.info("Headings on Tree page: {}", headings);
		Assert.assertTrue(headings.contains(content), "Tree UI heading content missing:" + content);

	}

	@Test(priority = 3, dataProvider = "Treetopics", dataProviderClass = TreeData.class)
	public void verifyTreetopics(String expectedtopics) {
		List<String> actualSubtopics = pom.getTreePage().subtopiclinks();
		logger.info("Validating Tree subtopics");

		Assert.assertTrue(actualSubtopics.contains(expectedtopics), "Missing topics in Queue UI: " + expectedtopics);

	}

	@Test(priority = 4, dataProvider = "Treepageurl", dataProviderClass = TreeData.class)
	public void verifyTreetopicurl(String topic, String topicurl) {
		pom.getTreePage().clickTopicLink(topic);

		Assert.assertTrue(ElementUtil.getURL().contains(topicurl),
				"Tree page URL does not contain expected text: " + topicurl);
		logger.info("Verified URL contains '{}'", topicurl);

		ElementUtil.navigateBack();
	}

	@Test(priority = 5, groups = "Overview of Trees")
	public void checkTryHereButton() {

		Assert.assertTrue(pom.getTreePage().checktryherebutton_displayed(), "Try Here button not visible");
		logger.info("Verified Try Here button is visible");

	}

	@Test(priority = 6, groups = "Overview of Trees")
	public void opencodeeditor() {

		pom.getTreePage().clickTryHereButton();
		logger.info("Clicked '{}' button", "Try here>>>");
		Assert.assertTrue(ElementUtil.getURL().contains("tryEditor"), "User is not on tryEditor screen");
		logger.info("User navigated to code editor page");

	}

	@Test(priority = 7, groups = "Overview of Trees")
	public void checkpracticequestionlink() {

		boolean isLinkVisible = pom.getTreePage().isPracticeQuestionLinkVisible();
		Assert.assertTrue(isLinkVisible, "Practice Questions link not present ");
		logger.info("Practice Questions link is visible in Tree subtopic page");

	}

	@Test(priority = 8, groups = "Overview of Trees")
	public void viewpracticequestions() {

		pom.getTreePage().clickPracticeQuestionsLink();

		Assert.assertTrue(ElementUtil.getURL().contains("practice"), "User is not on Tree practice questions page");
		logger.info("Navigated to Practice Questions UI from Tree page");
		List<String> questionList = pom.getTreePage().getQuestionsList();

		logger.info("Actual Questions: {}", questionList);

		Assert.assertTrue(!questionList.isEmpty(), "Practice question list is empty");

		logger.info("Verified Tree practice questions page");

	}

	@AfterMethod
	public void backToHome(ITestResult result) {
		String[] groups = result.getMethod().getGroups();
		List<String> groupList = Arrays.asList(groups);

		if (groupList.contains("Overview of Trees")) {
			while (!ElementUtil.getURL().contains("home")) {
				ElementUtil.navigateBack();
			}
		}
	}

}
