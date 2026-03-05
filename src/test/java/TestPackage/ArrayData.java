package TestPackage;

import java.util.Arrays;

import org.testng.annotations.DataProvider;

public class ArrayData {

	@DataProvider(name = "Arraytext")
	public Object[][] getLinkData() {
		return new Object[][] { { "Array" } };
	}

	@DataProvider(name = "ArrayUIcontent")
	public Object[][] UIContent() {
		return new Object[][] { { "Array" }, { "Topics Covered" } };
	}

	@DataProvider(name = "Arraytopics")
	public Object[][] getArraytopics() {
		return new Object[][] { { "Arrays in Python" }, { "Arrays Using List" }, { "Basic Operations in Lists" },
				{ "Applications of Array" } };
	}

	@DataProvider(name = "Arraypageurl")
	public Object[][] verifyArraypageurl() {
		return new Object[][] { { "Arrays in Python", "arrays-in-python" },
				{ "Arrays Using List", "arrays-using-list" },
				{ "Basic Operations in Lists", "basic-operations-in-lists" },
				{ "Applications of Array", "applications-of-array" }

		};
	}

	@DataProvider(name = "ArrayPracticequestions")
	public Object[][] verifyPracticequestions() {
		return new Object[][] {

				{ "Search the array" }, { "Max Consecutive Ones" }, { "Find Numbers with Even Number of Digits" },
				{ "Squares of a Sorted Array" }

		};

	}

	@DataProvider(name = "TryEditorQuestion")
	public Object[][] getquestion() {
		return new Object[][] { { "Question1" } };
	}

}
