package pages;

import DriverManager.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.ExcelReader;
import utils.TestContext;
import utils.WaitUtils;

public class TryEditorPage {

   

	private WaitUtils wait;
	private WebDriver driver;
	private static final Logger logger = LoggerFactory.getLogger(TryEditorPage.class);

	@FindBy(xpath = "//button")
	private WebElement run_button;

    @FindBy(xpath = "//span[@role='presentation']//span")
    private WebElement codeArea;

    @FindBy(css = ".CodeMirror")
    private WebElement codeEditor;

    public TryEditorPage() {
        this.driver = DriverFactory.getDriver();
        PageFactory.initElements(this.driver, this);
        wait = new WaitUtils();
        logger.info("TryEditorPage initialized successfully.");
    }

    public void runCode(String scenarioType) {

        logger.info("Executing code for scenario: {}", scenarioType);

        try {
            TestContext.testData = ExcelReader.getTestData(scenarioType);
            String code = TestContext.testData.get("code");

            logger.debug("Code to be executed: {}", code);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            Actions act = new Actions(driver);

            wait.waitForVisibility(driver, codeEditor, 10);

            act.moveToElement(codeArea).click();

            // Clear existing code
            js.executeScript(
                    "document.querySelector('.CodeMirror').CodeMirror.setValue('');");

            // Set new code
            js.executeScript(
                    "document.querySelector('.CodeMirror').CodeMirror.setValue(arguments[0]);",
                    code);

            logger.info("Code injected successfully. Clicking Run button.");

            run_button.click();

        } catch (Exception e) {
            logger.error("Error while executing code for scenario '{}': {}",
                    scenarioType, e.getMessage());
            throw e;
        }
    }
}
