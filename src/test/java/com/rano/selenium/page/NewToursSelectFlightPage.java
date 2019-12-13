package com.rano.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewToursSelectFlightPage {

	final WebDriver driver;
	
	@FindBy(xpath = "(//td[@class='frame_action_xrows'])[2]/input")
	WebElement flightRadioButton;

	@FindBy(xpath = "(//td[@class='frame_action_xrows'])[7]/input")
	WebElement returnFlightRadioButton;

	@FindBy(xpath = "(//td[@class='title'])[3]")
	WebElement toTitle;

	@FindBy(xpath = "(//td[@class='title'])[7]")
	WebElement fromTitle;

	@FindBy(name = "reserveFlights")
	WebElement continueButton;

	public NewToursSelectFlightPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(flightRadioButton));
		wait.until(ExpectedConditions.visibilityOf(returnFlightRadioButton));
	}

	public void selectFlight() {
		flightRadioButton.click();
	}

	public void selectReturnFlight() {
		returnFlightRadioButton.click();
	}

	public String toText() {
		return toTitle.getText();
	}

	public String fromText() {
		return fromTitle.getText();
	}

	public void clickContinue() {
		continueButton.click();
	}
}
