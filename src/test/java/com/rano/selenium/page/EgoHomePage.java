package com.rano.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EgoHomePage {

	final WebDriver driver;

	@FindBy(xpath = "//img[@title='EGO']")
	WebElement logo;

	@FindBy(css = "li#jq-account-dropdown-wrapper>a>span")
	WebElement accountLink;

	@FindBy(xpath = "(//span[contains(text(),'New In')])[1]")
	WebElement newInLink;

	public EgoHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(logo));
		wait.until(ExpectedConditions.visibilityOf(accountLink));
	}

	public void clickOnNewIn() {
		newInLink.click();
	}

}
