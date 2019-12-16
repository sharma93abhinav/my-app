package com.rano.selenium.script;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.rano.selenium.DriverBase;
import com.rano.selenium.page.GoogleHomePage;

import junit.framework.Assert;

public class GoogleHomePageSmoke extends DriverBase {

	private ExpectedCondition<Boolean> pageTitleStartsWith(final String searchString) {
		return driver -> driver.getTitle().toLowerCase().startsWith(searchString.toLowerCase());
	}

	@Test(priority = 1)
	public void googleCheeseExample() {
		logger = extent.createTest("Navigate to Google and Search");
		WebDriver driver = getDriver();
		driver.get("http://www.google.com");

		GoogleHomePage googleHomePage = new GoogleHomePage(driver);

		googleHomePage.enterSearchTerm("cheese");
		googleHomePage.submitSearch();

		WebDriverWait wait = new WebDriverWait(driver, 10, 100);
		wait.until(pageTitleStartsWith("Cheese"));

		String title = driver.getTitle();
		Assert.assertEquals("cheese - Google Search", title);
		logger.log(Status.PASS, verificationDetails("Verify the title of page", "cheese - Google Search", title));
		getScreenshotInLogger("Title Validation");
	}

	@Test(priority = 11, alwaysRun = true)
	public void tearDown() {
		close();
	}

}
