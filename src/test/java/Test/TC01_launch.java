package Test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import TestPackage.LaunchData;
import utils.ElementUtil;

@Test
public class TC01_launch extends Hooks {

	@Test
	public void verifyTitle() {

		logger.info("Verify Launch page title : {}", ElementUtil.getTitle());
		Assert.assertEquals(ElementUtil.getTitle(), "Numpy Ninja");
	}

	@Test(dataProvider = "launchPageText", dataProviderClass = LaunchData.class)
	public void verifyContentText(String expectedText) {
		logger.info("Verify content text : {}", expectedText);
		Assert.assertTrue(pom.getLaunchPage().doesPageContainText(expectedText),
				"Expected text not found:" + expectedText);

	}

	@Test
	public void verifyButtonCount() {
		logger.info("Verify button count: {}", pom.getLaunchPage().getButtonCount());
		Assert.assertEquals(pom.getLaunchPage().getButtonCount(), 1, "Expected 1 button on LaunchPage");
	}

	@Test
	public void verifyButtonText() {
		List<String> buttonText = pom.getLaunchPage().getButtonText();
		logger.info("Verify Button Text 'Get Started' present: {}", buttonText);
		Assert.assertEquals(buttonText.get(0), "Get Started", "Button text mismatch");
	}

}
