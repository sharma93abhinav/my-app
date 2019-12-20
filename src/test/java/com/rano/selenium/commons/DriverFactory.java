package com.rano.selenium.commons;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	private static final Logger LOGGER = Logger.getLogger(DriverFactory.class.getName());

	private static WebDriver driver;

	private static final String IE_BROWSER = "IE";

	private static final String CHROME_BROWSER = "CHROME";

	private static final String FIREFOX_BROWSER = "FIREFOX";

	private static final String EDGE_BROWSER = "EDGE";

	public WebDriver getDriver(String browserType) {
		try {
			if (browserType.equalsIgnoreCase(IE_BROWSER)) {
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				driver.manage().window().maximize();
			} else if (browserType.equalsIgnoreCase(CHROME_BROWSER)) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				driver.manage().window().maximize();
			} else if (browserType.equalsIgnoreCase(FIREFOX_BROWSER)) {

				/*
				 * WebDriverManager.firefoxdriver().setup(); driver = new FirefoxDriver();
				 * driver.manage().window().maximize();
				 */
				// temporary work around for firefox driver as web driver manager is not able to
				// download correct greko driver
				System.setProperty("webdriver.gecko.driver", "./Resources/geckodriver.exe");
				driver = new FirefoxDriver();
				driver.manage().window().maximize();

			} else if (browserType.equalsIgnoreCase(EDGE_BROWSER)) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				driver.manage().window().maximize();
			} else {
				throw new IllegalArgumentException("Unknown driver specified");
			}

		} catch (WebDriverException e) {
			LOGGER.info("Unable to launch web browser " + e.getMessage());
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
		return driver;
	}

	public void exitBrowser() {
		driver.close();
	}
}
