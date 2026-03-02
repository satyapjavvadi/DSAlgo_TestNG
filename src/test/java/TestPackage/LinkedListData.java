package TestPackage;

import org.testng.annotations.DataProvider;

public class LinkedListData {
	
	@DataProvider(name = "staticContent")
	public Object[][] staticContent()
	{
		return new Object[][] {
			{"Linked List"},
			{"Topics Covered"}
		};
	}
	
	@DataProvider(name = "topics")
	public Object[][] topics()
	{
		return new Object[][] {
			{"Introduction"},
			{"Creating Linked LIst"},
			{"Types of Linked List"},
			{"Implement Linked List in Python"},
			{"Traversal"},
			{"Insertion"},
			{"Deletion"}};
	}

}
