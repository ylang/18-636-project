package edu.cmu.ece.tester;

import org.browsermob.core.har.Har;
import org.browsermob.proxy.ProxyServer;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class Driver {
	
	private static String USER_EMAIL = "email_here";
	private static String USER_PASSWORD = "password_here\n";
	
	public static void main(String[] args) throws Exception {
		// start the proxy
		ProxyServer server = new ProxyServer(14444);
		server.start();

		// get the Selenium proxy object
		Proxy proxy = server.seleniumProxy();

		// configure it as a desired capability
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.PROXY, proxy);

		// start the browser up
		WebDriver driver = new FirefoxDriver(capabilities);

		// create a new HAR with the label "yahoo.com"
		server.newHar("yahoo.com");

		// open yahoo.com
		driver.get("http://yahoo.com");

		// get the HAR data
		Har har = server.getHar();
		har.writeTo(System.out);
		// The Firefox driver supports javascript
		/*
		WebDriver driver = new FirefoxDriver();

		// Go to the Google Suggest home page
		driver.get("http://www.twitter.com");
		driver.findElement(By.id("signin-email")).clear();
		driver.findElement(By.id("signin-email")).sendKeys(USER_EMAIL);
		driver.findElement(By.id("signin-password")).clear();
		driver.findElement(By.id("signin-password")).sendKeys(USER_PASSWORD);
		// Enter the query string "Cheese"
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy somewhere
		FileUtils.copyFile(scrFile, new File("screenshot.png"));

		// Sleep until the div we want is visible or 5 seconds is over
		long end = System.currentTimeMillis() + 5000;
		while (System.currentTimeMillis() < end) {
			WebElement resultsDiv = driver.findElement(By.className("gssb_e"));

			// If results have been returned, the results are displayed in a
			// drop down.
			if (resultsDiv.isDisplayed()) {
				break;
			}
		}

		// And now list the suggestions
		List<WebElement> allSuggestions = driver.findElements(By
				.xpath("//td[@class='gssb_a gbqfsf']"));

		for (WebElement suggestion : allSuggestions) {
			System.out.println(suggestion.getText());
		}

		WebElement ads = driver.findElement(By.id("tads"));
		*/
	}
}
