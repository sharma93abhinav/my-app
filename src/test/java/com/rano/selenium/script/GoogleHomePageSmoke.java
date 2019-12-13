package com.rano.selenium.script;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.rano.selenium.DriverBase;
import com.rano.selenium.page.GoogleHomePage;

import junit.framework.Assert;

public class GoogleHomePageSmoke extends DriverBase {

	private ExpectedCondition<Boolean> pageTitleStartsWith(final String searchString) {
		return driver -> driver.getTitle().toLowerCase().startsWith(searchString.toLowerCase());
	}

	@Test
	public void googleCheeseExample() {
		WebDriver driver = getDriver();
		driver.get("http://www.google.com");

		GoogleHomePage googleHomePage = new GoogleHomePage(driver);

		googleHomePage.enterSearchTerm("cheese");
		googleHomePage.submitSearch();

		WebDriverWait wait = new WebDriverWait(driver, 10, 100);
		wait.until(pageTitleStartsWith("Cheese"));

		String title = driver.getTitle();
		Assert.assertEquals("cheese - Google Search", title);
	}

}
