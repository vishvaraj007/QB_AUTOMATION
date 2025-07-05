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

		// 1️⃣ Initialize WebDriver and Wait
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.manage().window().maximize();
		driver.get(
				"https://accounts.intuit.com/app/sign-in?app_group=QBO&asset_alias=Intuit.devx.devx&app_environment=prod");
		waitForPageLoad(driver);

		// 2️⃣ Login to QuickBooks
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("iux-identifier-first-international-email-user-id-input")))
				.sendKeys("vishvaraj.codexlancers@gmail.com");

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("iux-password-confirmation-password")))
				.sendKeys("Welcome@007");

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Continue')]"))).click();

		// 3️⃣ OTP Handling (if appears)
		try {
			WebElement otpBtn = wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//button[@data-testid='challengePickerOption_SMS_OTP']")));
			if (otpBtn.isDisplayed()) {
				System.out.println("OTP page detected.");
				otpBtn.click();

				wait.until(ExpectedConditions
						.presenceOfElementLocated(By.xpath("//input[@data-testid='VerifyOtpInput']")));
				System.out.println("Waiting 20 seconds for OTP entry...");
				Thread.sleep(20000); // ⚠️ Replace with automated OTP fetch if needed

				wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//button[@data-testid='VerifyOtpSubmitButton']"))).click();
			}
		} catch (Exception e) {
			System.out.println("OTP page did not appear.");
		}

		waitForPageLoad(driver);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//*[@id='web-shell-spinner' or @class='has-background hide-spinner']")));

		// 4️⃣ Navigate to Invoice Section
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Sales']"))).click();
		System.out.println("Sales selected.");

		waitForPageLoad(driver);
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("(//span[@class='tabbedNavItemLabel'][normalize-space()='Invoices'])[1]"))).click();
		System.out.println("Invoice tab opened.");

		// 5️⃣ Click "Create Invoice"
		waitForPageLoad(driver);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Create invoice']")))
				.click();
		System.out.println("Create Invoice clicked.");

		// 6️⃣ Select Customer
		Thread.sleep(5000); // Ensure modal is fully loaded
		WebElement customerDropdown = wait
				.until(ExpectedConditions.elementToBeClickable(By.id("idsDropdownTypeaheadTextField2")));
		customerDropdown.click();

		WebElement customer = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Arav')]")));
		customer.click();
		System.out.println("Customer selected.");

		// 7️⃣ Close Right Sidebar if Visible
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[@class='txp-capability-closeBtn-Q9kg2']//button[@aria-label='Close']"))).click();
		System.out.println("Sidebar closed.");

		// 8️⃣ Select Item (e.g., 'Design')
		WebElement itemCell = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//td[@role='cell'])[4]")));
		new Actions(driver).moveToElement(itemCell).click().perform();
		System.out.println("Item cell clicked.");

		WebElement designItem = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[normalize-space()='Design']")));
		js.executeScript("arguments[0].scrollIntoView(true);", designItem);
		Thread.sleep(500); // Short delay
		js.executeScript("arguments[0].click();", designItem);
		System.out.println("Item 'Design' selected.");

		// 9️⃣ Save
		Thread.sleep(2000); // Optional UI buffer
		WebElement reviewAndSend = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//button[@class='idsTSButton idsF Button-button-47fde43 Button-size-medium-6e55314 Button-purpose-standard-09f745b Button-priority-secondary-f585bb5 SplitButton-buttonWrapper-3d5c01c SplitButton-medium-50be0d0']")));
		reviewAndSend.click();
		System.out.println("Invoice Save.");

		// 🔟 Close the Invoice Page after Save
		Thread.sleep(4000); // Let invoice submit before closing
		driver.findElement(By.xpath("//button[@aria-label='Close']")).click();
		System.out.println("Invoice page closed.");

		// ✅ Process Completed
		waitForPageLoad(driver);
		System.out.println("✅ Invoice created successfully.");
		Thread.sleep(4000);
		driver.quit();
		System.out.println("🔒 Browser closed.");
	}

	// 📌 Utility Method: Wait for JS page load
	private static void waitForPageLoad(WebDriver driver) {
		new WebDriverWait(driver, Duration.ofSeconds(30)).until(webDriver -> ((JavascriptExecutor) webDriver)
				.executeScript("return document.readyState").equals("complete"));
	}
}
