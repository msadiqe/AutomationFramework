package com.workflows;

import java.awt.AWTException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sephora.utils.ExtendManager;
public class Cosmos_Sephora_Cust_Details{

	static String processing_time;
	static String date_time;
	static String activity;
	static String message;
	static String error_log;
	static WebDriver driver;
	static String url_home="https://sephora-mkt-stage2.campaign.adobe.com/nl/jsp/logon.jsp?target=%2Fview%2Fhome";
	ExtentReports extent = new ExtendManager().createInstance();
    ExtentTest logger = new ExtendManager().getLogger();

	@BeforeTest
	public void fileUploadToSFTPLocation() throws IOException {
		logger=extent.startTest("File Upload To SFTPLocation");
		FileUpload_SFTP.send(System.getProperty("user.dir")+ "\\TestData\\Cosmos_Sephora_Cust_Details_.txt.pgp");
		logger.log(LogStatus.PASS, "File Created successfully");
	}

	@Test
	public void runWorkFlow() throws InterruptedException, AWTException, ParseException {
		// TODO Auto-generated method stub

		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, 40);
		driver.manage().window().maximize();
		logger=extent.startTest("login to Adobe Campaign");
		driver.get(url_home);		
		logger.log(LogStatus.PASS, "Application Launch successfully");
		driver.findElement(By.id("login")).sendKeys("Sandeep");
		logger.log(LogStatus.PASS, "Username entered successfully");
		driver.findElement(By.id("password")).sendKeys("123456");
		logger.log(LogStatus.PASS, "Password entered successfully");
		driver.findElement(By.id("validate")).click();
		logger.log(LogStatus.PASS, "Home Page open successfully");
		//Thread.sleep(5000);
		//Click on Mon
		logger=extent.startTest("Cosmos Sephora Customer Identity Data");
		wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//span[contains(text(), 'Monitoring')]"))));
		driver.findElement(By.xpath("//span[contains(text(), 'Monitoring')]")).click();
		logger.log(LogStatus.PASS, "Click on Monitoring Linking successfully");
		Thread.sleep(3000);
		wait.until(ExpectedConditions
				.elementToBeClickable(
		driver.findElement(By.xpath("(//span[@class='nlui-dropdown-header-text-wrapper'])[1]"))));
		driver.findElement(By.xpath("(//span[@class='nlui-dropdown-header-text-wrapper'])[1]")).click();
		
		wait.until(ExpectedConditions
				.elementToBeClickable(
		driver.findElement(By.xpath("(//span[@class='nlui-dropdown-item-label-wrapper'][contains(text(),'All')])[3]"))));
		driver.findElement(By.xpath("(//span[@class='nlui-dropdown-item-label-wrapper'][contains(text(),'All')])[3]")).click();
		
		Thread.sleep(1500);		
		driver.findElement(By.xpath("(//input[@placeholder='Search...'])[1]")).sendKeys("AA_Copy of Cosmos Sephora Customer Identity Data");
		Thread.sleep(1500);	
		wait.until(ExpectedConditions
				.elementToBeClickable(
		driver.findElement(By.xpath("(//span[contains(text(), 'Copy of Cosmos Sephora Customer Identity Data')])[1]"))));
		driver.findElement(By.xpath("(//span[contains(text(), 'Copy of Cosmos Sephora Customer Identity Data')])[1]")).click();
		logger.log(LogStatus.PASS, "Searching Cosmos Sephora Customer Identity Data WF successfully");
		Thread.sleep(1500);		
		wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//span[contains(text(),'Start the workflow')]"))));
		driver.findElement(By.xpath("//span[contains(text(),'Start the workflow')]")).click();
		logger.log(LogStatus.PASS, "WorkFlow started successfully");
		Thread.sleep(2000);
		wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//span[contains(text(),'OK')][@class='label']"))));
		driver.findElement(By.xpath("//span[contains(text(),'OK')][@class='label']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(
				driver.findElement(By.xpath("//span[contains(text(),'Pause')][@class='label']"))));
		if (driver.findElement(By.xpath("//span[contains(text(),'Pause')][@class='label']")).isDisplayed()) {
			processing_time = driver
					.findElement(By.xpath("//td[contains(text(), 'Last processing: ')]//following-sibling::td"))
					.getText();
			// Click to display logs
			int x=0;
			do {
				Thread.sleep(2000);
				driver.navigate().refresh();
				driver.findElement(By.xpath("//a[@class='entityLink']")).click();
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//table[@class='content']/tbody/tr[1]/td[3]")));
				x=x+1;
			} while (!driver.findElement(By.xpath("//table[@class='content']/tbody/tr[1]/td[3]")).getText()
					.contains("Workflow finished") || x==3);

			List<WebElement> aduitLogs = driver.findElements(By.xpath("//table[@class='content']/tbody/tr"));
			// Reading Audit Logs
			GetAuditLogs.getAuditLogs(driver, processing_time, aduitLogs,logger);

		}
		driver.navigate().refresh();
		
	}

	@AfterTest
	public void closeBrowser() {
		ExtendManager.closeReport();
		System.out.println("Workflow Finished");
		driver.get(ExtendManager.getReportLocation());
		
	}
}
