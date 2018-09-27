package com.sephora.utils;

import java.io.File;

import org.openqa.selenium.Platform;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtendManager {
	private static ExtentReports extent;
	private static Platform platform;
	private static String reportFileName = "Data-Platform-Test-Automaton-Report.html";
	private static String macPath = System.getProperty("user.dir") + "/TestReport";
	private static String windowsPath = System.getProperty("user.dir") + "\\TestReport";
	private static String macReportFileLoc = macPath + "\\" + reportFileName;
	private static String winReportFileLoc = windowsPath + "\\" + reportFileName;
	private static ExtentTest logger;

	// Create an extent report instance
	public static ExtentReports createInstance() {
		if (extent == null) {
			platform = getCurrentPlatform();
			String fileName = getReportFileLocation(platform);
			extent = new ExtentReports(fileName);
			// System information
			extent.addSystemInfo("Host Name", "Data Platform").addSystemInfo("Environment", "QA")
					.addSystemInfo("User Name", System.getProperty("user.name"));
			// loading the external xml file (i.e., extent-config.xml) which was placed
			// under the base directory
			// You could find the xml file below. Create xml file in your project and copy
			// past the code mentioned below
			extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
			return extent;
		} else {
			return extent;
		}

	}

	public static ExtentTest getLogger() {
		createInstance();		
		return logger;
	}
	
	public static void closeReport() {
		extent.flush();
	}

	// Select the extent report file location based on platform
	private static String getReportFileLocation(Platform platform) {
		String reportFileLocation = null;
		switch (platform) {
		case MAC:
			reportFileLocation = macReportFileLoc;
			createReportPath(macPath);
			System.out.println("ExtentReport Path for MAC: " + macPath + "\n");
			break;
		case WINDOWS:
			reportFileLocation = winReportFileLoc;
			createReportPath(windowsPath);
			System.out.println("ExtentReport Path for WINDOWS: " + windowsPath + "\n");
			break;
		default:
			System.out.println("ExtentReport path has not been set! There is a problem!\n");
			break;
		}
		return reportFileLocation;
	}
	
	public static String getReportLocation() {
		return winReportFileLoc;
		
	}

	// Create the report path if it does not exist
	private static void createReportPath(String path) {
		File testDirectory = new File(path);
		if (!testDirectory.exists()) {
			if (testDirectory.mkdir()) {
				System.out.println("Directory: " + path + " is created!");
			} else {
				System.out.println("Failed to create directory: " + path);
			}
		} else {
			System.out.println("Directory already exists: " + path);
		}
	}

	// Get current platform
	private static Platform getCurrentPlatform() {
		if (platform == null) {
			String operSys = System.getProperty("os.name").toLowerCase();
			if (operSys.contains("win")) {
				platform = Platform.WINDOWS;
			} else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
				platform = Platform.LINUX;
			} else if (operSys.contains("mac")) {
				platform = Platform.MAC;
			}
		}
		return platform;
	}
}