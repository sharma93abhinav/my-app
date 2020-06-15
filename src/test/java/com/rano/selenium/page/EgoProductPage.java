package com.rano.selenium.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EgoProductPage {

	final WebDriver driver;

	@FindBy(xpath = "//img[@title='EGO']")
	WebElement logo;

	@FindBy(id = "product-addtocart-button")
	WebElement addToBagButton;

	@FindBy(xpath = "//select[@class='swatch-select size']")
	WebElement sizeSelect;

	@FindBy(id = "qty")
	WebElement qtySelect;

	@FindBy(css = "a.action.showcart")
	WebElement bagIcon;

	public EgoProductPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(logo));
		wait.until(ExpectedConditions.visibilityOf(addToBagButton));
	}

	public void clickOnAddToBag() {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", addToBagButton);
	}

	public void selectSize(String size) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(sizeSelect));
		Select select = new Select(sizeSelect);
		select.selectByVisibleText(size);
	}

	public void selectQty(int qty) {
		Select select = new Select(qtySelect);
		select.selectByIndex(qty);
	}

	public void clickOnBagIcon() {
		bagIcon.click();
	}
}
