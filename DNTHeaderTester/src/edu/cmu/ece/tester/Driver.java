package edu.cmu.ece.tester;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Driver {
	
	private static String USER_EMAIL = "email_here";
	private static String USER_PASSWORD = "password_here";
	
	public static void main(String[] args) {
		// The Firefox driver supports javascript
		WebDriver driver = new FirefoxDriver();

		// Go to the Google Suggest home page
		driver.get("http://www.twitter.com");
		driver.findElement(By.id("signin-email")).clear();
		driver.findElement(By.id("signin-email")).sendKeys(USER_EMAIL);
		driver.findElement(By.id("signin-password")).clear();
		driver.findElement(By.id("signin-password")).sendKeys(USER_PASSWORD);
		driver.findElement(By.className("submit btn primary-btn flex-table-btn js-submit")).click();
		// Enter the query string "Cheese"
		WebElement query = driver.findElement(By.name("q"));
		query.sendKeys("italy flight");

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
		
	}
}
