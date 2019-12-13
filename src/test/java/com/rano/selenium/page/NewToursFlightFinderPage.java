package com.rano.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewToursFlightFinderPage {

	final WebDriver driver;
	
	@FindBy(name = "findFlights")
	WebElement continueButton;

	@FindBy(name = "fromPort")
	WebElement fromSelect;

	@FindBy(name = "toPort")
	WebElement toSelect;

	@FindBy(xpath = "//input[@value='Business']")
	WebElement businessClassRadioButton;

	public NewToursFlightFinderPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(continueButton));
		wait.until(ExpectedConditions.visibilityOf(businessClassRadioButton));
	}

	public String selectFromPort(String source) {
		Select fromPort = new Select(fromSelect);
		fromPort.selectByVisibleText(source);
		return fromPort.getFirstSelectedOption().getText();
	}

	public String selectToPort(String dest) {
		Select toPort = new Select(toSelect);
		toPort.selectByVisibleText(dest);
		return toPort.getFirstSelectedOption().getText();
	}

	public void selectBusinessClassRadioButton() {
		businessClassRadioButton.click();
	}

	public void clickContinue() {
		continueButton.click();
	}
}
