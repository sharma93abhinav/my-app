package com.rano.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewToursRegisterPage {

	final WebDriver driver;

	@FindBy(id = "email")
	WebElement usernameTextbox;

	@FindBy(name = "password")
	WebElement passwordTextbox;

	@FindBy(name = "confirmPassword")
	WebElement confirmPasswordTextbox;

	@FindBy(name = "register")
	WebElement submitButton;

	@FindBy(xpath = "//b[contains(text(),'Note')]")
	WebElement welcomeNote;

	public NewToursRegisterPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(usernameTextbox));
		wait.until(ExpectedConditions.visibilityOf(submitButton));
	}

	public void fillUsername(String username) {
		usernameTextbox.click();
		usernameTextbox.clear();
		usernameTextbox.sendKeys(username);
	}

	public void fillPassword(String password) {
		passwordTextbox.click();
		passwordTextbox.clear();
		passwordTextbox.sendKeys(password);
	}

	public void fillConfirmPassword(String password) {
		confirmPasswordTextbox.click();
		confirmPasswordTextbox.clear();
		confirmPasswordTextbox.sendKeys(password);
	}

	public void submit() {
		submitButton.click();
	}

	public String getTextForWelcomeNote() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(welcomeNote));
		return welcomeNote.getText();
	}

}
