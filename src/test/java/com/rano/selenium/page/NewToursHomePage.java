package com.rano.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewToursHomePage {

	final WebDriver driver;

	@FindBy(linkText = "REGISTER")
	WebElement registerLinkText;

	@FindBy(linkText = "Flights")
	WebElement flightsLinkText;

	@FindBy(xpath = "(//b)[1]")
	WebElement welcomeText;

	public NewToursHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(registerLinkText));
		wait.until(ExpectedConditions.visibilityOf(flightsLinkText));
	}

	public void clickOnRegisterLink() {
		registerLinkText.click();
	}

	public void clickOnFlightsLink() {
		flightsLinkText.click();
	}

	public String getWelcomeText() {
		return welcomeText.getText();
	}
}
