package com.rano.selenium.script;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.rano.selenium.DriverBase;
import com.rano.selenium.page.NewToursBookFlightPage;
import com.rano.selenium.page.NewToursFlightConfirmationPage;
import com.rano.selenium.page.NewToursFlightFinderPage;
import com.rano.selenium.page.NewToursHomePage;
import com.rano.selenium.page.NewToursRegisterPage;
import com.rano.selenium.page.NewToursSelectFlightPage;

public class NewToursSmoke extends DriverBase {

	private static WebDriver driver;

	@Test(priority = 1)
	@Parameters({ "url" })
	public void navigateToNewTours(String url) {
		logger = extent.createTest("Navigate to New Tours Website");
		driver = getDriver();
		driver.navigate().to(url);
		String actualTitle = driver.getTitle();
		String expTitle = "Welcome: Mercury Tours";
		Assert.assertEquals(actualTitle, expTitle, "Verify the title of page");
		logger.log(Status.PASS, verificationDetails("Verify the title of page", expTitle, actualTitle));
		getScreenshotInLogger("Title Validation");
	}

	@Test(priority = 2)
	@Parameters({ "username", "password" })
	public void registerUser(String username, String password) {
		logger = extent.createTest("Register User");
		NewToursHomePage newToursHomePage = new NewToursHomePage(driver);
		newToursHomePage.clickOnRegisterLink();

		NewToursRegisterPage newToursRegisterPage = new NewToursRegisterPage(driver);
		newToursRegisterPage.fillUsername(username);
		newToursRegisterPage.fillPassword(password);
		newToursRegisterPage.fillConfirmPassword(password);
		newToursRegisterPage.submit();

		String actualWelcomeNote = newToursRegisterPage.getTextForWelcomeNote();
		String expWelcomeNote = "Note: Your user name is dummyuser.";
		Assert.assertEquals(actualWelcomeNote, expWelcomeNote, "Verify that user got registered successfully");
		logger.log(Status.PASS,
				verificationDetails("Verify that user got registered successfully", expWelcomeNote, actualWelcomeNote));
		getScreenshotInLogger("User Registration Validation");
		newToursHomePage.clickOnFlightsLink();
	}

	@Test(priority = 3)
	public void findFlights() {
		logger = extent.createTest("Find Flights");
		NewToursFlightFinderPage newToursFlightFinderPage = new NewToursFlightFinderPage(driver);
		String expFromFlight = "Paris";
		String expToFlight = "Zurich";
		String actualFromFlight = newToursFlightFinderPage.selectFromPort(expFromFlight);
		String actualToFlight = newToursFlightFinderPage.selectToPort(expToFlight);
		newToursFlightFinderPage.selectBusinessClassRadioButton();

		Assert.assertEquals(actualFromFlight, expFromFlight, "Verify that departure destination selected successfully");
		logger.log(Status.PASS, verificationDetails("Verify that departure destination selected successfully",
				expFromFlight, actualFromFlight));
		getScreenshotInLogger("Departure Destination Validation");

		Assert.assertEquals(actualToFlight, expToFlight, "Verify that TO destination selected successfully");
		logger.log(Status.PASS,
				verificationDetails("Verify that TO destination selected successfully", expToFlight, actualToFlight));
		getScreenshotInLogger("Return Destination Validation");

		newToursFlightFinderPage.clickContinue();
	}

	@Test(priority = 4)
	public void selectFlights() {
		logger = extent.createTest("Select Flights");
		NewToursSelectFlightPage newToursSelectFlightPage = new NewToursSelectFlightPage(driver);
		newToursSelectFlightPage.selectFlight();
		newToursSelectFlightPage.selectReturnFlight();

		String expDepartDetails = "Paris to Zurich";
		String expReturnDetails = "Zurich to Paris";
		String actualDepartDetails = newToursSelectFlightPage.toText();
		String actualReturnDetails = newToursSelectFlightPage.fromText();

		Assert.assertEquals(actualDepartDetails, expDepartDetails, "Verify that depart details are correct");
		logger.log(Status.PASS,
				verificationDetails("Verify that depart details are correct", expDepartDetails, actualDepartDetails));
		getScreenshotInLogger("Depart Details Validation");

		Assert.assertEquals(actualReturnDetails, expReturnDetails, "Verify that return details are correct");
		logger.log(Status.PASS,
				verificationDetails("Verify that return details are correct", expReturnDetails, actualReturnDetails));
		getScreenshotInLogger("Return Details Validation");

		newToursSelectFlightPage.clickContinue();

	}

	@Test(priority = 5)
	public void bookFlight() {
		logger = extent.createTest("Book Flights");
		NewToursBookFlightPage newToursBookFlightPage = new NewToursBookFlightPage(driver);
		newToursBookFlightPage.fillFirstName("DUMMY");
		newToursBookFlightPage.fillLasttName("USER");
		newToursBookFlightPage.fillPhoneNumber("00000");

		String expToFlight = "Blue Skies Airlines 361";
		String expFromFlight = "Pangea Airlines 632";
		String actualToFlight = newToursBookFlightPage.getToFlightDetails();
		String actualFromFlight = newToursBookFlightPage.getFromFlightDetails();

		Assert.assertEquals(actualToFlight, expToFlight, "Verify that depart flight is correct");
		logger.log(Status.PASS,
				verificationDetails("Verify that depart flight is correct", expToFlight, actualToFlight));
		getScreenshotInLogger("Depart Flight Validation");

		Assert.assertEquals(actualFromFlight, expFromFlight, "Verify that return flight is correct");
		logger.log(Status.PASS,
				verificationDetails("Verify that return flight is correct", expFromFlight, actualFromFlight));
		getScreenshotInLogger("Return Flight Validation");

		newToursBookFlightPage.submit();
	}

	@Test(priority = 6)
	public void verifyBookingAndLogoutWebsite() {
		logger = extent.createTest("Verify Booking");
		NewToursFlightConfirmationPage newToursFlightConfirmationPage = new NewToursFlightConfirmationPage(driver);
		String expBookingMessage = "Your itinerary has been booked!";
		String actualBookingMessage = newToursFlightConfirmationPage.getBookingMessage();

		Assert.assertEquals(actualBookingMessage, expBookingMessage, "Verify that booking message is correct");
		logger.log(Status.PASS,
				verificationDetails("Verify that booking message is correct", expBookingMessage, actualBookingMessage));
		getScreenshotInLogger("Booking Message Validation");

		newToursFlightConfirmationPage.navToLogout();
	}

	//@Test(priority = 7)
	public void checkforWelcomeBackText() {
		logger = extent.createTest("Verify Welcome Text");
		NewToursHomePage newToursHomePage = new NewToursHomePage(driver);
		String expWelcomeText = "Welcome back to Mercury Tours";
		String actualWelcomeText = newToursHomePage.getWelcomeText();
		Assert.assertEquals(actualWelcomeText, expWelcomeText, "Verify that Welcome Text is correct");
		logger.log(Status.PASS,
				verificationDetails("Verify that Welcome Text is correct", expWelcomeText, actualWelcomeText));
		getScreenshotInLogger("Welcome Text Validation");
	}

	@Test(priority = 9, alwaysRun = true)
	public void tearDown() {
		close();
	}

}
