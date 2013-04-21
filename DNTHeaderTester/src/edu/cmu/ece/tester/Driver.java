package edu.cmu.ece.tester;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;

public class Driver {

	private static String USER_EMAIL = "18636test1";
	private static String USER_PASSWORD = "18636test\n";

	public static void main(String[] args) throws Exception {
		/*
		 * // start the proxy ProxyServer server = new ProxyServer(14444);
		 * server.start();
		 * 
		 * // get the Selenium proxy object Proxy proxy =
		 * server.seleniumProxy();
		 * 
		 * // configure it as a desired capability DesiredCapabilities
		 * capabilities = new DesiredCapabilities();
		 * capabilities.setCapability(CapabilityType.PROXY, proxy);
		 * 
		 * // start the browser up WebDriver driver = new
		 * FirefoxDriver(capabilities);
		 * 
		 * // create a new HAR with the label "yahoo.com"
		 * server.newHar("yahoo.com");
		 * 
		 * // open yahoo.com driver.get("http://yahoo.com");
		 * 
		 * // get the HAR data Har har = server.getHar();
		 * har.writeTo(System.out); // The Firefox driver supports javascript
		 */
		WebDriver driver = new FirefoxDriver();
		/*
		 * driver.get("http://www.twitter.com");
		 * driver.findElement(By.id("signin-email")).clear();
		 * driver.findElement(By.id("signin-email")).sendKeys(USER_EMAIL);
		 * driver.findElement(By.id("signin-password")).clear();
		 * driver.findElement(By.id("signin-password")).sendKeys(USER_PASSWORD);
		 */
		driver.get("http://www.google.com");
		WebElement query = driver.findElement(By.name("q"));
		query.sendKeys("europe trip");
		query.submit();

		long end = System.currentTimeMillis() + 5000;
		while (System.currentTimeMillis() < end) {
			WebElement resultsDiv = driver.findElement(By.className("gssb_e"));

			if (resultsDiv.isDisplayed()) {
				break;
			} else {
				System.out.println("wait...");
				Thread.sleep(500);
			}
		}
		System.out.println("loaded completely");
		List<String> results = new ArrayList<String>();
		List<WebElement> searchResults = driver.findElements(edu.cmu.ece.tester.util.FindBy.all(By.tagName("a"), By.className("l")));
		System.out.println("size of result = " + searchResults.size());
		for (WebElement a : searchResults) {
			System.out.println(a.getText());
			String link = a.getAttribute("href");
			results.add(link);
		}

		// Output each link and click on each link
		for (String link : results) {
			System.out.println(link);

			// Step 4
			driver.get(link);
		}
		driver.get("http://www.twitter.com");
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		FileUtils.copyFile(scrFile, new File("screenshot.png"));
	}
}
