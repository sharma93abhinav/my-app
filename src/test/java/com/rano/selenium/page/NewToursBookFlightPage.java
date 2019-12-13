package com.rano.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewToursBookFlightPage {
	
	final WebDriver driver;

	@FindBy(name = "buyFlights")
	WebElement purchaseButton;

	@FindBy(name = "passFirst0")
	WebElement firstNameTextbox;

	@FindBy(name = "passLast0")
	WebElement lastNameTextbox;

	@FindBy(name = "creditnumber")
	WebElement phoneNumberTextbox;

	@FindBy(xpath = "(//b)[4]")
	WebElement toFlight;

	@FindBy(xpath = "(//b)[7]")
	WebElement fromFlight;

	public NewToursBookFlightPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(purchaseButton));
		wait.until(ExpectedConditions.visibilityOf(firstNameTextbox));
	}

	public String getToFlightDetails() {
		return toFlight.getText();
	}

	public String getFromFlightDetails() {
		return fromFlight.getText();
	}

	public void fillFirstName(String firstName) {
		firstNameTextbox.click();
		firstNameTextbox.sendKeys(firstName);
	}

	public void fillLasttName(String lastName) {
		lastNameTextbox.click();
		lastNameTextbox.sendKeys(lastName);
	}

	public void fillPhoneNumber(String number) {
		phoneNumberTextbox.click();
		phoneNumberTextbox.sendKeys(number);
	}

	public void submit() {
		purchaseButton.click();
	}
}
