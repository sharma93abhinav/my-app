package com.rano.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewToursFlightConfirmationPage {
	
	final WebDriver driver;

	@FindBy(xpath = "//font[contains(text(),'Your')]")
	WebElement bookingMessage;

	@FindBy(xpath = "//img[@src='/images/forms/Logout.gif']")
	WebElement logout;

	public NewToursFlightConfirmationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(bookingMessage));
		wait.until(ExpectedConditions.visibilityOf(logout));
	}

	public void navToLogout() {
		logout.click();
	}

	public String getBookingMessage() {
		return bookingMessage.getText();
	}
}
