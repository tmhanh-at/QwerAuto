package com.qwer.listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.qwer.driver.DriverFactory;
import com.qwer.utils.ReportManager;
import com.qwer.utils.ScreenshotUtils;

public class TestListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		String className = result.getTestClass().getName();
		className = className.replace("com.qwer.tests.ui.", "");
		String methodName = result.getMethod().getMethodName();
		String description = result.getMethod().getDescription();
		ReportManager.startTest(className, methodName, description);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ReportManager.getTest().pass(result.getMethod().getDescription());
		ReportManager.getTest().log(Status.PASS, "✅ Test passed");

	}

	@Override
	public void onTestFailure(ITestResult result) {
		ReportManager.getTest().fail(result.getMethod().getDescription());
		ReportManager.getTest().log(Status.FAIL, result.getThrowable());

		try {
			WebDriver driver = DriverFactory.getDriver();
			if (driver != null) {
				String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getMethod().getMethodName());
				if (screenshotPath != null && screenshotPath != "") {
					ReportManager.getTest().fail("Screenshot:", com.aventstack.extentreports.MediaEntityBuilder
							.createScreenCaptureFromPath(screenshotPath).build());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ReportManager.getTest().skip(result.getMethod().getDescription());
		ReportManager.getTest().log(Status.SKIP, "⚠ Test skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		ReportManager.flush();
	}
}
