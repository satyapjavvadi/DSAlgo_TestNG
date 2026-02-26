package utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import DriverManager.DriverFactory;

public class WaitUtils {
	private final WebDriverWait wait;
	private final WebDriver driver;

	public WaitUtils() {
		this.driver = DriverFactory.getDriver();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public WebElement waitForClickable(WebElement element) {
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitForPageLoad() {
		wait.until(
				driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
	}

	public boolean waitForVisibilityOfAll(List<WebElement> elements) {

		try {
			wait.until(ExpectedConditions.visibilityOfAllElements(elements));
			return !elements.isEmpty();
		} catch (TimeoutException e) {
			return false;
		}
	}

	public static void waitForUrlContains(WebDriver driver, String fragment, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
		wait.until(ExpectedConditions.urlContains(fragment));
	}

	public static WebElement waitForVisibility(WebDriver driver, WebElement element, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static String getVisibleText(WebDriver driver, WebElement loginAlert, int timeoutSeconds) {
		return waitForVisibility(driver, loginAlert, timeoutSeconds).getText().trim();
	}

	public static boolean isVisible(WebDriver driver, WebElement element, int timeoutSeconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String waitForCodeMirrorOutput(String elementId, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

		WebElement output = wait.until(driver -> {
			WebElement el = driver.findElement(By.id(elementId));
			String text = el.getAttribute("textContent").trim();
			return !text.isEmpty() ? el : null;
		});

		return output.getAttribute("textContent").trim();
	}

}
