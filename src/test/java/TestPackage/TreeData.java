
package TestPackage;


import org.testng.annotations.DataProvider;

public class TreeData {

	@DataProvider(name = "Treetext")
	public Object[][] getLinkData() {
		return new Object[][] { { "Tree" } };
	}

	@DataProvider(name = "TreeUIcontent")
	public Object[][] uiContent() {
		return new Object[][] { { "Tree" }, { "Topics Covered" } };
	}

	@DataProvider(name = "Treetopics")
	public Object[][] getTreeTopics() {
		return new Object[][] { { "Overview of Trees" }, { "Terminologies" }, { "Types of Trees" },
				{ "Tree Traversals" }, { "Traversals-Illustration" }, { "Binary Trees" }, { "Types of Binary Trees" },
				{ "Implementation in Python" }, { "Binary Tree Traversals" }, { "Implementation of Binary Trees" },
				{ "Applications of Binary trees" }, { "Binary Search Trees" }, { "Implementation Of BST" } };
	}

	@DataProvider(name = "Treepageurl")
	public Object[][] verifyTreePageurl() {
		return new Object[][] { { "Overview of Trees", "overview-of-trees" }, { "Terminologies", "terminologies" },
				{ "Types of Trees", "types-of-trees" }, { "Tree Traversals", "tree-traversals" },
				{ "Traversals-Illustration", "traversals-illustration" }, { "Binary Trees", "binary-trees" },
				{ "Types of Binary Trees", "types-of-binary-trees" },
				{ "Implementation in Python", "implementation-in-python" },
				{ "Binary Tree Traversals", "binary-tree-traversals" },
				{ "Implementation of Binary Trees", "implementation-of-binary-trees" },
				{ "Applications of Binary trees", "applications-of-binary-trees" },
				{ "Binary Search Trees", "binary-search-trees" },
				{ "Implementation Of BST", "implementation-of-bst" } };
	}
}
