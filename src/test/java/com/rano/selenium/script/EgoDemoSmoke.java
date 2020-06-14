package com.rano.selenium.script;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.rano.selenium.DriverBase;
import com.rano.selenium.page.EgoCheckOutPage;
import com.rano.selenium.page.EgoHomePage;
import com.rano.selenium.page.EgoMyBagSmoke;
import com.rano.selenium.page.EgoNewInPage;
import com.rano.selenium.page.EgoProductPage;

public class EgoDemoSmoke extends DriverBase {

	private static WebDriver driver;

	@Test(priority = 1)
	@Parameters({ "url2" })
	public void navigateToEgo(String url) {
		logger = extent.createTest("Navigate to Ego Staging Website");
		driver = getDriver();
		driver.navigate().to(url);
		String actualTitle = driver.getTitle();
		String expTitle = "EGO Shoes: Step Into Autumn/Winter | New Season Shoes";
		Assert.assertEquals(actualTitle, expTitle, "Verify the title of page");
		logger.log(Status.PASS, verificationDetails("Verify the title of page.", expTitle, actualTitle));
		getScreenshotInLogger("e1");
	}

	@Test(priority = 2)
	@Parameters({ "productName" })
	public void navigateToNewIn(String productName) {
		logger = extent.createTest("Navigate to New In Page");

		EgoHomePage egoHomePage = new EgoHomePage(driver);
		egoHomePage.clickOnNewIn();

		EgoNewInPage egoNewInPage = new EgoNewInPage(driver);
		boolean actProductName = egoNewInPage.isProductExists(productName);

		Assert.assertEquals(actProductName, true, "Veriy that product exists on New In Page.");
		logger.log(Status.PASS, verificationDetails("Veriy that product exists on New In Page.", "true",
				String.valueOf(actProductName)));
		getScreenshotInLogger("e2");
	}

	@Test(priority = 3)
	@Parameters({ "productName", "size" })
	public void addToBag(String productName, String size) {
		logger = extent.createTest("Select Product");

		EgoNewInPage egoNewInPage = new EgoNewInPage(driver);
		egoNewInPage.selectProduct(productName);

		EgoProductPage egoProductPage = new EgoProductPage(driver);
		egoProductPage.selectSize(size);
		egoProductPage.selectQty(2);
		egoProductPage.clickOnAddToBag();

		EgoMyBagSmoke egoMyBagSmoke = new EgoMyBagSmoke(driver);
		String actProductName = egoMyBagSmoke.getProductName();

		Assert.assertEquals(actProductName, productName, "Veriy that product is added to Bag.");
		logger.log(Status.PASS,
				verificationDetails("Veriy that product is added to Bag.", productName, actProductName));
		getScreenshotInLogger("e3");
	}

	@Test(priority = 4)
	@Parameters({ "productName" })
	public void proceedToCheckout(String productName) {
		logger = extent.createTest("Navigate to Checkout Page");

		EgoMyBagSmoke egoMyBagSmoke = new EgoMyBagSmoke(driver);
		egoMyBagSmoke.clickOnCheckout();

		EgoCheckOutPage egoCheckOutPage = new EgoCheckOutPage(driver);
		String actProductName = egoCheckOutPage.getProductName();

		Assert.assertEquals(actProductName, productName, "Veriy that product name is correct on checkout page.");
		logger.log(Status.PASS, verificationDetails("Veriy that product name is correct on checkout page", productName,
				actProductName));
		getScreenshotInLogger("e4");
	}

	@Test(priority = 9, alwaysRun = true)
	public void tearDown() {
		close();
	}

}
