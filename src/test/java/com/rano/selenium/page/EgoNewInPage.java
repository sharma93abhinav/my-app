package com.rano.selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EgoNewInPage {

	final WebDriver driver;

	@FindBy(xpath = "//img[@title='EGO']")
	WebElement logo;

	@FindBy(css = "h1#page-title-heading>span")
	WebElement newInHeader;

	public EgoNewInPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(logo));
		wait.until(ExpectedConditions.visibilityOf(newInHeader));
	}

	public boolean isProductExists(String productName) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//a[contains(text(),'" + productName + "')]"))));
		WebElement element = driver.findElement(By.xpath("//a[contains(text(),'" + productName + "')]"));
		return element.isDisplayed();
	}

	public void selectProduct(String productName) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//a[contains(text(),'" + productName + "')]"))));
		WebElement element = driver.findElement(By.xpath("//a[contains(text(),'" + productName + "')]"));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}
}
