package com.rano.selenium;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.apache.commons.io.FileUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.rano.selenium.commons.DriverFactory;

public class DriverBase {

	private static final Logger LOGGER = Logger.getLogger(DriverBase.class.getName());

	private static String defaultBrowserType = "CHROME";

	private static WebDriver driver;

	private static String suiteName;

	private static String browserType;

	private static int passedTestCasesCount = 0;

	private static int failedTestCasesCount = 0;

	private static int skippedTestCasesCount = 0;

	protected static ExtentHtmlReporter htmlReporter;

	protected static ExtentReports extent;

	protected static ExtentTest logger;

	public WebDriver getDriver() {
		String browser = System.getProperty("browser");
		browserType = browser.equals("") ? defaultBrowserType : browser;
		LOGGER.info("Browser type is " + browserType.toUpperCase());
		DriverFactory driverFactory = new DriverFactory();
		driver = driverFactory.getDriver(browserType);
		return driver;
	}

	public void close() {
		DriverFactory driverFactory = new DriverFactory();
		driverFactory.exitBrowser();
	}

	@BeforeClass(alwaysRun = true)
	public void startReport(ITestContext ctx) {
		suiteName = ctx.getCurrentXmlTest().getSuite().getName();
		String className = this.getClass().getSimpleName();
		htmlReporter = new ExtentHtmlReporter("./../temp/test-output/" + className + ".html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "New Tours");
		extent.setSystemInfo("Environment", "Automation Testing");
		extent.setSystemInfo("User Name", "Abhinav Sharma");

		htmlReporter.config().setDocumentTitle("Automation Test Report");
		htmlReporter.config().setReportName("Test - " + className);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);

	}

	@AfterMethod(alwaysRun = true)
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL, result.getMethod().getMethodName());
			logger.log(Status.FAIL, result.getThrowable());
			String screenshotPath;
			try {
				screenshotPath = getScreenshot(driver, result.getMethod().getMethodName());
				logger.addScreenCaptureFromPath(screenshotPath);
			} catch (IOException e) {
				LOGGER.info(e.getMessage());
			}
			failedTestCasesCount++;
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP, result.getMethod().getMethodName());
			skippedTestCasesCount++;
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			passedTestCasesCount++;
		}

	}

	@AfterClass(alwaysRun = true)
	public void endReport() {
		extent.flush();
	}

	public String getScreenshot(WebDriver driver, String screenshotName) {
		Date date = new Date();
		Long timeMilli = date.getTime();
		String dateName = timeMilli.toString();
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = "./../temp/TestsScreenshots/" + screenshotName + dateName + ".png";
		String finalDestination = "./../temp/../TestsScreenshots/" + screenshotName + dateName + ".png";
		File file = new File(destination);
		try {
			FileUtils.copyFile(source, file);
		} catch (IOException e) {
			LOGGER.info(e.getMessage());
		}
		return finalDestination;
	}

	public void getScreenshotInLogger(String message) {
		String screenshotPath = getScreenshot(driver, message);
		try {
			logger.addScreenCaptureFromPath(screenshotPath, message);
		} catch (IOException e) {
			LOGGER.info(e.getMessage());
		}
	}

	public String verificationDetails(String title, String expectedResult, String actualResult) {
		return String.format("<b>Test Verification Detail : </b>" + title + "<br />" + "<b>Expected : </b>"
				+ expectedResult + "<br />" + "<b>Actual : </b>" + actualResult);
	}

	@BeforeSuite(alwaysRun = true)
	public void clearData() {
		String source1 = "./../temp/TestsScreenshots";
		File file1 = new File(source1);
		String source2 = "./../temp/Report";
		File file2 = new File(source2);
		String source3 = "./../temp/test-output";
		File file3 = new File(source3);
		String source4 = "./../temp/EmailableReport.zip";
		File file4 = new File(source4);
		try {
			if (file1.exists()) {
				FileUtils.deleteDirectory(file1);
			}
			if (file2.exists()) {
				FileUtils.deleteDirectory(file2);
			}
			if (file3.exists()) {
				FileUtils.deleteDirectory(file3);
			}
			if (file4.exists()) {
				file4.delete();
			}
		} catch (IOException e) {
			LOGGER.info(e.getMessage());
		}

		new File("./../temp/test-output").mkdir();
		new File("./../temp/TestsScreenshots").mkdir();
	}

	@Parameters({ "toEmailId", "fromEmailId", "senderName", "senderPassword" })
	@AfterSuite(alwaysRun = true)
	public void sendMail(String toEmailId, String fromEmailId, String senderName, String senderPassword) {
		String source1 = "./../temp/test-output";
		String source2 = "./../temp/TestsScreenshots";
		String destination = "./../temp/Report";
		String destination1 = "./../temp/Report/test-output";
		String destination2 = "./../temp/Report/TestsScreenshots";

		new File(destination).mkdir();
		File sourceFile1 = new File(source1);
		File sourceFile2 = new File(source2);
		File destinationFile1 = new File(destination1);
		File destinationFile2 = new File(destination2);
		try {
			FileUtils.copyDirectory(sourceFile1, destinationFile1);
			FileUtils.copyDirectory(sourceFile2, destinationFile2);
		} catch (IOException e) {
			LOGGER.info(e.getMessage());
		}

		ZipFolder appZip = new ZipFolder();
		appZip.generateFileList(new File(destination));
		appZip.zipIt();

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmailId, senderPassword);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmailId));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailId));
			message.setSubject("Automation Test Report");

			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(createBodyForTheMail(), "text/html; charset=utf-8");

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			String filename = "./EmailableReport.zip";
			DataSource source = new FileDataSource(filename);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(filename);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);
			message.setContent(multipart);
			Transport.send(message);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}

	}

	private String createBodyForTheMail() {
		StringBuilder sb = new StringBuilder();
		sb.append("Dear Sir/Madam,");
		sb.append("<br />");
		sb.append("<br />");
		sb.append("Your test suite has just finished its execution. Here is the summary report.");
		sb.append("<br />");
		sb.append("<br />");
		sb.append("<table width=\"600\" border=\"1\">");
		sb.append("<tr>");
		sb.append("<td><b>");
		sb.append("Host Name");
		sb.append("</b></td>");
		sb.append("<td>");
		try {
			sb.append(InetAddress.getLocalHost().getHostName().toUpperCase());
		} catch (UnknownHostException e) {
			LOGGER.info(e.getMessage());
		}
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td><b>");
		sb.append("Operating System");
		sb.append("</b></td>");
		sb.append("<td>");
		sb.append(System.getProperty("os.name").toUpperCase());
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td><b>");
		sb.append("Browser Name");
		sb.append("</b></td>");
		sb.append("<td>");
		sb.append(browserType);
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td><b>");
		sb.append("Suite Name");
		sb.append("</b></td>");
		sb.append("<td>");
		sb.append(suiteName);
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td><b>");
		sb.append("Result:-");
		sb.append("</b></td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<font color=\"green\">");
		sb.append("Pass : " + passedTestCasesCount);
		sb.append("</font>");
		sb.append("</td>");
		sb.append("<td>");
		sb.append("<font color=\"red\">");
		sb.append("Fail : " + failedTestCasesCount);
		sb.append("</font>");
		sb.append("</td>");
		sb.append("<td>");
		sb.append("<font color=\"orange\">");
		sb.append("Skip : " + skippedTestCasesCount);
		sb.append("</font>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");

		sb.append("<br />");
		sb.append("This email was sent automatically by Rano System. Please do not reply.");
		sb.append("<br />");
		sb.append("<br />");
		sb.append("Thanks,");
		sb.append("<br />");
		sb.append("Rano");

		String body = String.format(sb.toString());
		return body;
	}

}
