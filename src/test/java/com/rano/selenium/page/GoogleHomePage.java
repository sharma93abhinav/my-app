package com.rano.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GoogleHomePage {

	@FindBy(name = "q")
	WebElement searchBar;

	@FindBy(name = "btnK")
	WebElement googleSearch;

	@FindBy(name = "btnI")
	WebElement imFeelingLucky;

	public GoogleHomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void enterSearchTerm(String searchTerm) {
		searchBar.clear();
		searchBar.sendKeys(searchTerm);
	}

	public void submitSearch() {
		googleSearch.submit();
	}

	public void getLucky() {
		imFeelingLucky.click();
	}
}
