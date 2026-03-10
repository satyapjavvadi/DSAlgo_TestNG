package TestPackage;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.testng.annotations.DataProvider;

public class GraphData {

	@DataProvider(name = "StaticContent")
	public Object[][] getStaticContent() {
		return new Object[][] { { "Graph" }, { "Topics Covered" }};
	}
	
	@DataProvider(name = "SubtopicLink")
	public Object[][] getsubtopicLink() {
		return new Object[][] { { "Graph" }, { "Graph Representations" }};
	}
	

	@DataProvider(name = "SubtopicLinks")
	public Object[][] getSubtopicLinks() {
	    return new Object[][] {
	        {"Graph", "Graph"},
	        {"Graph Representations", "Graph Representations"}
	    };
	}
//	@DataProvider(name = "SubtopicLink")
	//public Object[] getSubtopicLink() {
		//return new Object[] { Arrays.asList("Graph", "Graph Representations") };	
	//}
}
