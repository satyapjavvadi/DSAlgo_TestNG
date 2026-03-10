package TestPackage;

import org.testng.annotations.DataProvider;

public class StackData {
	
	@DataProvider(name = "staticContent")
	public Object[][] staticContent()
	{
		return new Object[][] {
			{"Stack"},
			{"Topics Covered"}
		};
	}
	
	@DataProvider(name = "StackSubTopics")
	public Object[][] stackPagetopics()
	{
		return new Object[][] {
			{"Operations in Stack"},
			{"Implementation"},
			{"Applications"}};
	}
	
	@DataProvider(name = "topics")
	public Object[][] topics()
	{
		return new Object[][] {
			{"Operations in Stack"},
			{"Implementation"},
			{"Applications"}};	}

}
