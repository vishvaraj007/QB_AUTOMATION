package QB_Package;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QB_class {

	public static void main(String[] args) throws InterruptedException {

		// Initialize the WebDriver (Edge in this case)
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Open the URL and maximize the window
		driver.get(
				"https://accounts.intuit.com/app/sign-in?app_group=QBO&asset_alias=Intuit.devx.devx&app_environment=prod");
		driver.manage().window().maximize();

		// Wait for the page to load completely
		waitForPageLoad(driver);

		// 1) Login Process
		WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//input[@id='iux-identifier-first-international-email-user-id-input']")));
		emailField.sendKeys("vishvaraj.codexlancers@gmail.com");

		WebElement submitButton1 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
		submitButton1.click();

		WebElement passwordField = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//input[@id='iux-password-confirmation-password']")));
		passwordField.sendKeys("Welcome@007");

		WebElement continueButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Continue')]")));
		continueButton.click();

		// 2) OTP Page (If Appears)
		try {
			WebElement otpButton = wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//button[@data-testid='challengePickerOption_SMS_OTP']")));
			if (otpButton.isDisplayed()) {
				System.out.println("OTP page detected.\n");
				otpButton.click();

				WebElement otpInput = new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions
						.presenceOfElementLocated(By.xpath("//input[@data-testid='VerifyOtpInput']")));

				System.out.println("Enter OTP in 20 seconds");
				Thread.sleep(20000);

				WebElement verifyOtpButton = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//button[@data-testid='VerifyOtpSubmitButton']")));
				verifyOtpButton.click();
			}
		} catch (Exception e) {
			System.out.println("OTP page did not appear");
		}

		waitForPageLoad(driver);

		// Wait until the spinner disappears (if any)
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//*[@id='web-shell-spinner' or @class='has-background hide-spinner']")));

		// 3) Navigate to Invoices
		WebElement salesMenu = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Sales']")));
		salesMenu.click();
		System.out.println("Sales is selected from side menu");

		waitForPageLoad(driver);
		WebElement invoicesTab = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("(//span[@class='tabbedNavItemLabel'][normalize-space()='Invoices'])[1]")));
		invoicesTab.click();
		System.out.println("Invoice option selected from top menu");

		// 4) Create Invoice
		waitForPageLoad(driver);
		WebElement createInvoiceButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Create invoice']")));
		createInvoiceButton.click();
		System.out.println("Create new invoice button clicked");

		// 5) Select Customer
		waitForPageLoad(driver);
		System.out.println("Customer is checking.");
		WebElement customerDropdown = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='idsDropdownTypeaheadTextField4']")));

		System.out.println("Customer is loeaded.");
		// waitForPageLoad(driver);
		js.executeScript("arguments[0].click()", customerDropdown);

		System.out.println("Customer is clicked.");

		// waitForPageLoad(driver);
		WebElement customer = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Arav')]")));
		customer.click();
		System.out.println("Customer is selected.");

		// 6) Close Side Bar if needed

		WebElement closebar = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='txp-capability-closeBtn-Q9kg2']//button[@aria-label='Close']")));
		closebar.click();
		System.out.println("Side bar is closed.");

		// ======================================================================

		/*
		 * Select selectiteam = new Select(selectdrop);
		 * 
		 * // selected by name selectiteam.selectByVisibleText("Japan");
		 */
		// ======================================================================
		// 7) Select Item from Dropdown
		WebElement iteambox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//td[@role='cell'])[4]")));
		System.out.println("Item dropdown is hovered 0.");

		Actions actions = new Actions(driver);
		actions.moveToElement(iteambox).build().perform();
		System.out.println("Item dropdown is hovered.");

		iteambox.click();
		System.out.println("Item dropdown is hovered is clicked.");

		WebElement designItem = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[normalize-space()='Design']")));
		System.out.println("Item 'Design' is in view.");

		// Optional scroll + JS click
		// js.executeScript("arguments[0].scrollIntoView(true);", designItem);
		// Thread.sleep(1000);
		// designItem.click();
		js.executeScript("arguments[0].click();", designItem);

		/*
		 * // inteam box WebElement iteambox =
		 * driver.findElement(By.xpath("(//td[@role='cell'])[4]"));
		 * System.out.println("Item dropdown is hovered 0.");
		 * 
		 * Actions actions = new Actions(driver);
		 * actions.moveToElement(iteambox).build().perform();
		 * System.out.println("Item dropdown is hovered."); iteambox.click();
		 * System.out.println("Item dropdown is hovered is clicked.");
		 * 
		 * // **Scroll to the item using JavaScript**
		 * 
		 * WebElement designItem = wait
		 * .until(ExpectedConditions.presenceOfElementLocated(By.xpath(
		 * "//span[normalize-space()='Design']"))); //
		 * js.executeScript("arguments[0].scrollIntoView(true);", designItem);
		 * System.out.println("Item 'Design' is in view.");
		 * js.executeScript("arguments[0].click()", designItem);
		 * 
		 * // Click on the item (using the click action directly) // designItem.click();
		 * System.out.println("Item 'Design' is selected.");
		 */
		// waitForPageLoad(driver);

		WebElement reviewAndSendBtn = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Review and send']")));
		Thread.sleep(3000);
		reviewAndSendBtn.click();
		// js.executeScript("arguments[0].click()", reviewAndSendBtn);

		// reviewAndSendBtn.click();
		System.out.println("Review and send button clicked.");

		// Click "Send Invoice" button with retry logic

		// div[@class="txp-capability-actionButton-es8gr
		// txp-capability-sendModeFooter-ivBGF"]//button[1]

		waitForPageLoad(driver);
		WebElement sendInvoiceBtn = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Send invoice']")));
		js.executeScript("arguments[0].click()", sendInvoiceBtn);

		sendInvoiceBtn.click();
		System.out.println("Send button clicked.");

		driver.switchTo().newWindow(WindowType.TAB);

		driver.get("https://testqbic.advintek.com.my/login");
		sendInvoiceBtn.click();

	}

	/**
	 * Utility method to wait for the page to load completely. Uses a lambda
	 * expression with JavascriptExecutor.
	 */
	private static void waitForPageLoad(WebDriver driver) {
		new WebDriverWait(driver, Duration.ofSeconds(30)).until(webDriver -> ((JavascriptExecutor) webDriver)
				.executeScript("return document.readyState").equals("complete"));
	}
}
