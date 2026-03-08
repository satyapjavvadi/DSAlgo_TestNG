package TestPackage;

import org.testng.annotations.DataProvider;

public class QueueData {
	@DataProvider(name = "Queuetext")
	public Object[][] getLinkData() {
		return new Object[][] { { "Queue" } };
	}

	@DataProvider(name = "QueueUIcontent")
	public Object[][] uiContent() {
		return new Object[][] { { "Queue" }, { "Topics Covered" } };
	}

	@DataProvider(name = "Queuetopics")
	public Object[][] getQueueTopics() {
		return new Object[][] { { "Implementation of Queue in Python" }, { "Implementation using collections.deque" },
				{ "Implementation using array" }, { "Queue Operations" } };
	}

	@DataProvider(name = "Queuepageurl")
	public Object[][] verifyQueuePageUrl() {
		return new Object[][] { { "Implementation of Queue in Python", "implementation-lists" },
				{ "Implementation using collections.deque", "implementation-collections" },
				{ "Implementation using array", "Implementation-array" }, { "Queue Operations", "QueueOp" }

		};
	}
}
