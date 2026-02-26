package Test;

import org.testng.Assert;
import org.testng.annotations.Test;

import utils.ElementUtil;

@Test
public class TC01_launch extends Hooks {
	 
	
	@Test
	public void verifyTitle() {
		
		logger.info("Verify Launch page title : {}",ElementUtil.getTitle());
		Assert.assertEquals(ElementUtil.getTitle(), "Numpy Ninja");
	}
	

}
