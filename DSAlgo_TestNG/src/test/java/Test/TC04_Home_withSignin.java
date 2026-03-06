package Test;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import TestPackage.HomeData;
import utils.ElementUtil;

import java.util.Arrays;
import java.util.List;

@Test(groups = { "Get Started", "Sign in" })
public class TC04_Home_withSignin extends Hooks {
	@BeforeMethod
	public void navigationBack(ITestResult result) {
		String[] groups = result.getMethod().getGroups();
		List<String> groupList = Arrays.asList(groups);

		if (groupList.contains("Selection")) {
			logger.info("Group List :{} ", groupList);
			ElementUtil.navigateBack();
		}
	}
	@Test(priority =0 )
	public void verifyHomeLinkText() {
		logger.info("Link name displayed: {}", pom.getHomePage().getRightCornerLink("Sign out"));
		Assert.assertEquals(pom.getHomePage().getRightCornerLink("Sign out"), "Sign out", "Link name mismatch");
	}
	
	@Test(priority =1,groups ="Selection",dataProvider = "TabName", dataProviderClass = HomeData.class)
	public void verifyPageDetails_OnClickingOptions(String OptionName) {
		String selectCard = null;

		if(OptionName.equalsIgnoreCase("Data Structures-Introduction")){
			logger.info("Selection for DS : {} is not possible",OptionName);
		}else {
			if (OptionName.contains("Array")) {
				selectCard = OptionName + "s";
			} else {
				selectCard = OptionName;
			}
			pom.getHomePage().selectOption(selectCard);
			logger.info("Page heading displayed from dropdown: {}", pom.getHomePage().getPageHeading(OptionName));
			Assert.assertEquals(pom.getHomePage().getPageHeading(OptionName), OptionName, "Page heading mismatch");
		}

		
	}	

	@Test(priority =2,groups ="Selection",dataProvider = "TabName", dataProviderClass = HomeData.class)
	public void verifyPageDetails_OnClickingTabs(String TabName) {

		pom.getHomePage().clickTitlePage(TabName);
		logger.info("Page heading displayed: {}", pom.getHomePage().getPageHeading(TabName));
		Assert.assertEquals(pom.getHomePage().getPageHeading(TabName), TabName, "Page heading mismatch");


	}


}
