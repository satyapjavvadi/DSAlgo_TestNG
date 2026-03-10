package TestPackage;

import org.testng.annotations.DataProvider;

public class LaunchData {

	@DataProvider(name = "launchPageText")
	public Object[][] getContextTextLaunch() {
		return new Object[][] { { "Preparing for the Interviews" }, { "Copyright@NumpyNinja2021" } };

	}

}
