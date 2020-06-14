package com.rano.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EgoCheckOutPage {

	final WebDriver driver;

	@FindBy(css = "button.button.action.continue.primary")
	WebElement paymentButton;

	@FindBy(css = "strong.product-item-name")
	WebElement productName;

	public EgoCheckOutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOf(paymentButton));
		wait.until(ExpectedConditions.visibilityOf(productName));
	}

	public String getProductName() {
		Actions action = new Actions(driver);
		action.moveToElement(productName).build().perform();
		return productName.getText();
	}

}
