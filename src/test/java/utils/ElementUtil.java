package utils;

import DriverManager.DriverFactory;

public class ElementUtil {

	public static String getURL() {
		return DriverFactory.getDriver().getCurrentUrl();
	}

	public static String getTitle() {
		return DriverFactory.getDriver().getTitle();
	}
}
