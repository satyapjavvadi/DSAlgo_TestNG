package TestPackage;

import org.testng.annotations.DataProvider;

public class DataStrData {
	
	@DataProvider(name = "StaticContent")
	public Object[][] getStaticContent() {
		return new Object[][] { { "Data Structures-Introduction" }, { "Topics Covered" }};
	}
	
	
}
