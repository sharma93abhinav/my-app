package com.rano.selenium.commons;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	private static WebDriver driver;

	private static final String IE_BROWSER = "IE";

	private static final String CHROME_BROWSER = "CHROME";

	private static final String FIREFOX_BROWSER = "FIREFOX";

	private static final String EDGE_BROWSER = "EDGE";

	public WebDriver getDriver(String browserType) {

		if (browserType.equalsIgnoreCase(IE_BROWSER)) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
		} else if (browserType.equalsIgnoreCase(CHROME_BROWSER)) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		} else if (browserType.equalsIgnoreCase(FIREFOX_BROWSER)) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
		} else if (browserType.equalsIgnoreCase(EDGE_BROWSER)) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().window().maximize();
		} else {
			throw new IllegalArgumentException("Unknown driver specified");
		}

		return driver;
	}

	public void exitBrowser() {
		driver.close();
	}
}
