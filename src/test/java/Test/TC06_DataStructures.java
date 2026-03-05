package Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Test;

import TestPackage.DataStrData;
import utils.ElementUtil;

@Test(groups = {"Get Started", "Sign in"})
public class TC06_DataStructures extends Hooks {

    @BeforeMethod
    public void clickDSButton(Method method) {
        logger.info("Clicking Data Structures-Introduction link in home page");
        pom.getHomePage().clickGetStarted("Data Structures-Introduction");
		Test testAnnotation = method.getAnnotation(Test.class);
		List<String> groupList = Arrays.asList(testAnnotation.groups());
		if (groupList.contains("Time Complexity")) {
			pom.getDataStructurePage().clickTopicLink("Time Complexity");
		}


    }

    @Test(priority = 1)
    public void navigateToDataStructuresPage() {

        logger.debug("Current page title: {}", ElementUtil.getTitle());
        Assert.assertEquals(ElementUtil.getTitle(), "Data Structures-Introduction",
                "Failed to navigate to Data Structures page");

    }

    @Test(priority = 2, dataProvider = "StaticContent", dataProviderClass = DataStrData.class)
    public void verifyStaticContent(String expectedText) {
        List<String> headings = pom.getDataStructurePage().getheadingtext();
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

    @Test(priority = 3, groups = "Time Complexity")
    public void navigateToTimeComplexityPage() {

        logger.info("Current page title: {}", ElementUtil.getTitle());
        String pageUrlText = "Data Structures Introduction";
        String expected = pageUrlText.toLowerCase().replace(" ", "-");
        logger.info("User is on the expected page: {}", pageUrlText);
        Assert.assertTrue(ElementUtil.getURL().contains(expected),
                "URL does not contain expected text: " + pageUrlText);


    }

    @Test(priority = 4, groups = "Time Complexity")
    public void verifyTryHereButtonVisibility() {

        logger.info("Checking Try Here button visibility on Time Complexity page");
        boolean isDisplayed = pom.getDataStructurePage().checktryherebutton_displayed();
        if (!isDisplayed) {
            logger.error("Try Here button is not visible on Time Complexity page");
        }
        {
            Assert.assertTrue(isDisplayed, "Try Here button is not visible on Time Complexity page");
            logger.info("Verified Try Here button is visible on Time Complexity page");
        }
    }

    @Test(priority = 5, groups = "Time Complexity")
    public void navigateToTryEditorPage() {

        pom.getDataStructurePage().clickTryHereButton();
        Assert.assertTrue(ElementUtil.getURL().contains("tryEditor"), "user is not on tryeditor screen");
        logger.info("User is on the Try Editor page after clicking Try Here button");
    }

    @Test(priority = 6, groups = "Time Complexity")
    public void navigateToPracticeQuestionsLink() {

        pom.getDataStructurePage().clickPracticeQuestionsLink();
        List<String> questions = pom.getDataStructurePage().getQuestionsList();
        Assert.assertFalse(questions.isEmpty(),
                "No questions are displayed in Practice Questions section of DataStructures module");
    }

    @AfterMethod
    public void backToHome(ITestResult result) {
        String[] groups = result.getMethod().getGroups();
        List<String> groupList = Arrays.asList(groups);

        if (groupList.contains("Time Complexity")) {
            while (!ElementUtil.getURL().contains("home")) {
                ElementUtil.navigateBack();
            }
        }
    }


}
