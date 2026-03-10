package Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import TestPackage.ArrayData;
import utils.ElementUtil;

@Test(groups = { "Get Started", "Sign in" })
public class TC07_Array extends Hooks {

	@BeforeMethod
	public void navigateToArraymainpage(Method method) {

		logger.info("Clicking get sarted buton of Array module in home page");
		pom.getHomePage().clickGetStarted("Array");
		Test testAnnotation = method.getAnnotation(Test.class);
		List<String> groupList = Arrays.asList(testAnnotation.groups());
		if (groupList.contains("Arrays in Python")) {
			pom.getArrayPage().clickTopicLink("Arrays in Python");
		}
	}

	@Test(priority = 1, dataProvider = "Arraytext", dataProviderClass = ArrayData.class)
	public void getArrayPage(String text) {
		logger.info("Navigated to DS module: {}", text);

		Assert.assertEquals(ElementUtil.getTitle(), "Array", "Navigation failed to Array page");
	}

	@Test(priority = 2, dataProvider = "ArrayUIcontent", dataProviderClass = ArrayData.class)
	public void Verifycontent(String content) {

		List<String> headings = pom.getArrayPage().getheadingtext();
		logger.info("Headings on Array page: {}", headings);
		Assert.assertTrue(headings.contains(content), "Array UI heading content missing:" + content);

	}

	@Test(priority = 3, dataProvider = "Arraytopics", dataProviderClass = ArrayData.class)
	public void verifyArraytopics(String expectedtopics) {
		List<String> actualSubtopics = pom.getArrayPage().subtopiclinks();
		logger.info("Validating Array subtopics");

		Assert.assertTrue(actualSubtopics.contains(expectedtopics), "Missing topics in Array UI: " + expectedtopics);

	}

	@Test(priority = 4, dataProvider = "Arraypageurl", dataProviderClass = ArrayData.class)
	public void verifyArraytopicurl(String topic, String topicurl) {
		pom.getArrayPage().clickTopicLink(topic);

		Assert.assertTrue(ElementUtil.getURL().contains(topicurl),
				"Array page URL does not contain expected text: " + topicurl);
		logger.info("Verified URL contains '{}'", topicurl);

		ElementUtil.navigateBack();
	}

	@Test(priority = 5, groups = "Arrays in Python")
	public void checkTryHereButton() {

		Assert.assertTrue(pom.getArrayPage().checktryherebutton_displayed(), "Try Here button not visible");
		logger.info("Verified Try Here button is visible");

	}

	@Test(priority = 6, groups = "Arrays in Python")
	public void opencodeeditor() {

		pom.getArrayPage().clickTryHereButton();
		logger.info("Clicked '{}' button", "Try here>>>");
		Assert.assertTrue(ElementUtil.getURL().contains("tryEditor"), "User is not on tryEditor screen");
		logger.info("User navigated to code editor page");

	}

	@Test(priority = 7, groups = "Arrays in Python")
	public void checkpracticequestionlink() {

		boolean isLinkVisible = pom.getArrayPage().isPracticeQuestionLinkVisible();
		Assert.assertTrue(isLinkVisible, "Practice Questions link not present ");
		logger.info("Practice Questions link is visible in Array subtopic page");

	}

	@Test(priority = 8, groups = "Arrays in Python", dataProvider = "ArrayPracticequestions", dataProviderClass = ArrayData.class)
	public void viewpracticequestions(String expectedlist) {

		pom.getArrayPage().clickPracticeQuestionsLink();

		Assert.assertTrue(ElementUtil.getURL().contains("practice"), "User is not on Array practice questions page");
		logger.info("Navigated to Practice Questions UI from Array page");
		List<String> actualList = pom.getArrayPage().getQuestionsList();

		logger.info("Actual Questions: {}", actualList);

		Assert.assertTrue(actualList.contains(expectedlist),
				"Question missing: '" + expectedlist + "' | Actual list: " + actualList);

		logger.info("Verified list of questions in Array practice questions page");

	}

	@Test(priority = 9, groups = "Arrays in Python", dataProvider = "ArrayPracticequestions", dataProviderClass = ArrayData.class)
	public void viewquestionpage(String expectedList) {

		pom.getArrayPage().clickPracticeQuestionsLink();
		pom.getArrayPage().clickProblemLink(expectedList);
		logger.info("Clicked on problem link '{}'", expectedList);
		logger.info("Actual page title: {} | Expected page name: {}", ElementUtil.getTitle(), "Assessment");
		Assert.assertTrue(ElementUtil.getTitle().contains("Assessment"));

	}

	@Test(priority = 10, groups = "Arrays in Python", dataProvider = "ArrayPracticequestions", dataProviderClass = ArrayData.class)
	public void viewrunbutton(String expectedList) {

		pom.getArrayPage().clickPracticeQuestionsLink();
		pom.getArrayPage().clickProblemLink(expectedList);
		logger.info("Clicked on problem link '{}'", expectedList);
		boolean runbuttoncheck = pom.getArrayPage().getButtonTextAssessmentPage("Run");
		Assert.assertTrue(runbuttoncheck, "Run button is not present in UI for this Question: " + expectedList);

	}

	@Test(priority = 11, groups = "Arrays in Python", dataProvider = "ArrayPracticequestions", dataProviderClass = ArrayData.class)
	public void viewsubmitbutton(String expectedList) {

		pom.getArrayPage().clickPracticeQuestionsLink();
		pom.getArrayPage().clickProblemLink(expectedList);
		logger.info("Clicked on problem link '{}'", expectedList);
		boolean submitbuttoncheck = pom.getArrayPage().isSubmitButtonPresent();
		Assert.assertTrue(submitbuttoncheck, "Submit button is not present in UI for this Question: " + expectedList);

	}

	@Test(priority = 12, groups = "Arrays in Python", dataProvider = "ArrayPracticequestions", dataProviderClass = ArrayData.class)
	public void runemptycode(String expectedList) {

		pom.getArrayPage().clickPracticeQuestionsLink();
		pom.getArrayPage().clickProblemLink(expectedList);
		logger.info("Clicked on problem link '{}'", expectedList);
		pom.getArrayPage().submitProblem();
		logger.info("Submitted problem '{}'", expectedList);
		String actualOutput = pom.getArrayPage().getConsoleOutput();
		Assert.assertTrue(actualOutput.contains("Error occurred during submission"),
				"Expected output not found. Actual output: " + actualOutput);
		logger.info("Verified output contains '{}'", "Error occurred during submission");

	}

	@Test(priority = 13, groups = "Arrays in Python", dataProvider = "TryEditorQuestion", dataProviderClass = ArrayData.class)
	public void runtryeditor(String question) {

		pom.getArrayPage().clickTryHereButton();
		logger.info("User is on Try Editor page");
		pom.getTryEditorPage().runCode(question);
		logger.info("Executed code in Try Editor: {}", question);
		String actualOutput = pom.getArrayPage().getConsoleOutput();
		Assert.assertTrue(actualOutput.contains("Element Found"),
				"Expected output not found. Actual output: " + actualOutput);
		logger.info("Verified output contains '{}'", "Element Found");
	}

	@AfterMethod
	public void backToHome(ITestResult result) {
		String[] groups = result.getMethod().getGroups();
		List<String> groupList = Arrays.asList(groups);

		if (groupList.contains("Arrays in Python")) {
			while (!ElementUtil.getURL().contains("home")) {
				ElementUtil.navigateBack();
			}
		}
	}

}
