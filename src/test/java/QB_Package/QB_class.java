package QB_Package;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QB_class {

	public static void main(String[] args) throws InterruptedException {

		// ============================
		// 1) Setup WebDriver and Wait
		// ============================
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.manage().window().maximize();
		driver.get(
				"https://accounts.intuit.com/app/sign-in?app_group=QBO&asset_alias=Intuit.devx.devx&app_environment=prod");

		waitForPageLoad(driver);

		// ========================
		// 2) Login to QuickBooks
		// ========================
		WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//input[@id='iux-identifier-first-international-email-user-id-input']")));
		emailField.sendKeys("vishvaraj.codexlancers@gmail.com");

		WebElement submitButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
		submitButton.click();

		WebElement passwordField = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//input[@id='iux-password-confirmation-password']")));
		passwordField.sendKeys("Welcome@007");

		WebElement continueButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Continue')]")));
		continueButton.click();

		// ==========================
		// 3) OTP Page (Optional)
		// ==========================
		try {
			WebElement otpButton = wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//button[@data-testid='challengePickerOption_SMS_OTP']")));
			if (otpButton.isDisplayed()) {
				System.out.println("OTP page detected.");
				otpButton.click();

				WebElement otpInput = wait.until(ExpectedConditions
						.presenceOfElementLocated(By.xpath("//input[@data-testid='VerifyOtpInput']")));
				System.out.println("Waiting 20 seconds for OTP...");
				Thread.sleep(20000); // ⚠️ Replace with actual OTP handling in future

				WebElement verifyOtpButton = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//button[@data-testid='VerifyOtpSubmitButton']")));
				verifyOtpButton.click();
			}
		} catch (Exception e) {
			System.out.println("OTP page did not appear.");
		}

		waitForPageLoad(driver);

		// Wait until any loading spinner disappears
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//*[@id='web-shell-spinner' or @class='has-background hide-spinner']")));

		// ==============================
		// 4) Navigate to Invoice Module
		// ==============================
		WebElement salesMenu = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Sales']")));
		salesMenu.click();
		System.out.println("Sales selected from side menu.");

		waitForPageLoad(driver);

		WebElement invoicesTab = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("(//span[@class='tabbedNavItemLabel'][normalize-space()='Invoices'])[1]")));
		invoicesTab.click();
		System.out.println("Invoice tab selected.");

		// ==============================
		// 5) Click Create Invoice
		// ==============================
		waitForPageLoad(driver);

		WebElement createInvoiceButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Create invoice']")));
		createInvoiceButton.click();
		System.out.println("Create invoice button clicked.");

		// ==============================
		// 6) Select Customer
		// ==============================
		Thread.sleep(5000); // Optional: wait for modal to fully open
		WebElement customerDropdown = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='idsDropdownTypeaheadTextField2']")));
		customerDropdown.click();

		WebElement customer = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Arav')]")));
		customer.click();
		System.out.println("Customer selected.");

		// ==============================
		// 7) Close Side Panel (if open)
		// ==============================
		WebElement closebar = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='txp-capability-closeBtn-Q9kg2']//button[@aria-label='Close']")));
		closebar.click();
		System.out.println("Sidebar closed.");

		// ==============================
		// 8) Select Item from Dropdown
		// ==============================
		WebElement itemBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//td[@role='cell'])[4]")));
		System.out.println("Hovering over item cell...");

		Actions actions = new Actions(driver);
		actions.moveToElement(itemBox).build().perform();
		itemBox.click();
		System.out.println("Item cell clicked.");

		// Re-locate to avoid stale element reference
		WebElement designItem = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[normalize-space()='Design']")));
		js.executeScript("arguments[0].scrollIntoView(true);", designItem);
		Thread.sleep(500); // Optional short wait
		js.executeScript("arguments[0].click();", designItem);
		System.out.println("Item 'Design' selected.");

		// ==============================
		// 9) Click Review and Send
		// ==============================
		WebElement reviewAndSendBtn = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Review and send']")));
		Thread.sleep(3000); // Optional UI wait
		reviewAndSendBtn.click();
		System.out.println("Review and send button clicked.");

		waitForPageLoad(driver);

		// ==============================
		// 10) Click Send Invoice
		// ==============================
		WebElement sendInvoiceBtn = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Send invoice']")));
		js.executeScript("arguments[0].scrollIntoView(true);", sendInvoiceBtn);
		Thread.sleep(1000); // Optional small wait
		js.executeScript("arguments[0].click()", sendInvoiceBtn);
		System.out.println("Send invoice button clicked using JS.");

		// ==============================
		// 11) End of Process
		// ==============================
		System.out.println("✅ Invoice successfully created.");
		driver.quit();
		System.out.println("Browser closed.");
	}

	// ==============================
	// Utility: Wait for full page load
	// ==============================
	private static void waitForPageLoad(WebDriver driver) {
		new WebDriverWait(driver, Duration.ofSeconds(30)).until(webDriver -> ((JavascriptExecutor) webDriver)
				.executeScript("return document.readyState").equals("complete"));
	}
}
