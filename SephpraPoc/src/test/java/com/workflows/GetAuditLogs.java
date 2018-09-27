package com.workflows;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class GetAuditLogs{
	
	public static void getAuditLogs(WebDriver driver, String processing_time, List<WebElement> aduitLogs,ExtentTest logger) {
		System.out.println("Last processing: " + processing_time);
		logger.log(LogStatus.PASS, "WorkFlow processing start time" + processing_time);
		String workflowName = driver.findElement(By.xpath("//div[@class='nlui-section-base-header-title']")).getText();
		System.out.println(workflowName);
		String error_log;
		String arr[] = processing_time.split(":");
		String processing_date = arr[0] + ":" + arr[1];
		int max_val = 0;
		for (int i = aduitLogs.size(); i >= 1; i--) {
			String f = "//table[@class='content']/tbody/tr[" + i + "]/td[1]";
			String date_time1 = driver.findElement(By.xpath(f)).getText();
			if (processing_time.trim().contains(date_time1.trim())) {
				max_val = i;
				break;
			}
		}

		System.out.println("Alert Type\tDate\tActivity\tMessage");
		for (int i = max_val; i >= 1; i--) {
			String f = "//table[@class='content']/tbody/tr[" + i + "]/td[1]";
			String s = "//table[@class='content']/tbody/tr[" + i + "]/td[2]";
			String th = "//table[@class='content']/tbody/tr[" + i + "]/td[3]";
			String log = "//table[@class='content']/tbody/tr[" + i + "]/td[1]//img";
			String date_time = driver.findElement(By.xpath(f)).getText();
			String activity = driver.findElement(By.xpath(s)).getText();
			String message = driver.findElement(By.xpath(th)).getText();
			String alt = driver.findElement(By.xpath(log)).getAttribute("src");
			String wfStartText = "Workflow 'WKF1012' is being run".replaceAll("\\P{L}", "");

			if (alt.contains("logerr")) {
				error_log = "Error";
			} else if (alt.contains("loginfo")) {
				error_log = "Info";
			} else if (alt.contains("logwarn")) {
				error_log = "Warn";
			} else {
				error_log = "Unknow";
			}

			if (error_log.equals("Info")) {
				if (wfStartText.equals(message.replaceAll("\\P{L}", ""))) {
					System.out.println(error_log + "\t" + date_time + "\t" + activity + "\t" + message);
					logger.log(LogStatus.INFO, message);
				}
				if (message.contains("[5/5] record(s) processed (step '--S8 Insert/Update data --')")) {
					System.out.println(error_log + "\t" + date_time + "\t" + activity + "\t" + message);
					Assert.assertTrue(message.contains("[5/5] record(s) processed (step '--S8 Insert/Update data --')"));
					logger.log(LogStatus.PASS, message);
				}
			}		
						
			if (error_log.equals("Info") && message.contains("Workflow finished")) {
				System.out.println(error_log + "\t" + date_time + "\t" + activity + "\t" + message);
				Assert.assertTrue(message.contains("Workflow finished"),"Workflow finished");
				System.out.println("Test Case Pass");
				logger.log(LogStatus.PASS, message);
			}
		}

	}

}
