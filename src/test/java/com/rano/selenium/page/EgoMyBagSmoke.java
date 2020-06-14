package com.rano.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EgoMyBagSmoke {

	final WebDriver driver;

	@FindBy(id = "minicart-content-wrapper")
	WebElement myBagPopUp;

	@FindBy(id = "top-cart-btn-checkout")
	WebElement checkoutButton;

	@FindBy(css = "strong.product-item-name>a")
	WebElement productName;

	public EgoMyBagSmoke(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(myBagPopUp));
	}

	public void clickOnCheckout() {
		checkoutButton.click();
	}

	public String getProductName() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(productName));
		return productName.getText();
	}

}
