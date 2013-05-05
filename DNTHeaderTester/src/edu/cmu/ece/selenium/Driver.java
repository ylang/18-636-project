package edu.cmu.ece.selenium;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Driver {

	private static String USER_EMAIL = "facebookpwdcracking@gmail.com";
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

		driver.get("http://www.twitter.com");
		driver.findElement(By.id("signin-email")).clear();
		driver.findElement(By.id("signin-email")).sendKeys(USER_EMAIL);
		driver.findElement(By.id("signin-password")).clear();
		driver.findElement(By.id("signin-password")).sendKeys(USER_PASSWORD);
		new WebDriverWait(driver, 30).until(ExpectedConditions
				.presenceOfElementLocated(By
						.xpath("//*[@id='page-container']")));
		driver.get("http://www.google.com");
		WebElement query = driver.findElement(By.name("q"));
		query.sendKeys("european trip");
		query.submit();
		new WebDriverWait(driver, 30).until(ExpectedConditions
				.presenceOfElementLocated(By
						.xpath("//*[@id='nav']/tbody/tr/td")));
		
		List<WebElement> pages = driver.findElements(
				By.xpath("//*[@id='nav']/tbody/tr/td/a"));
		List<String> pageList = new ArrayList<String>(11);
		for (WebElement page : pages ) {
			String pageUrl = page.getAttribute("href");
			pageList.add(pageUrl);
		}
		int cnt = 1;
		for (String page : pageList) {
			driver.get(page);
			new WebDriverWait(driver, 30).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath("//*[@id='rso']/li/div/h3")));
			System.out.println("loaded completely");
			List<String> results = new ArrayList<String>();
			List<WebElement> searchResults = driver.findElements(By
					.xpath("//*[@id='rso']/li/div/h3/a"));
			System.out.println("size of result = " + searchResults.size());
			try {
				for (WebElement a : searchResults) {
					String link = a.getAttribute("href");
					results.add(link);
					System.out.printf("%d\t%s\n", cnt, a.getText());
					cnt++;
				}
				System.out.println("size of result = " + results.size());
				// Output each link and click on each link
				for (String link : results) {
					System.out.println(link);

					// Step 4
					driver.get(link);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		driver.get("http://www.twitter.com");
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		FileUtils.copyFile(scrFile, new File("screenshot_twitter.png"));
		// driver.close();
		driver.get("https://twitter.com/who_to_follow/web_personalized");
		scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		FileUtils.copyFile(scrFile, new File("screenshot_twitter_tailored.png"));
		// driver.close();
		driver.get("http://ie.microsoft.com/testdrive/browser/donottrack/");
		// driver.close();
	}
}
