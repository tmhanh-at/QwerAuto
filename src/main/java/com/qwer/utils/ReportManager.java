package com.qwer.utils;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportManager {

	private static ExtentReports extent;

	private static Map<String, ExtentTest> classTests = new HashMap<>();
	private static ThreadLocal<ExtentTest> methodTest = new ThreadLocal<>();

	public static ExtentReports getExtentReports() {
		if (extent == null) {
			ExtentSparkReporter spark = new ExtentSparkReporter("reports/extent-report.html");
			extent = new ExtentReports();
			extent.attachReporter(spark);
		}
		return extent;
	}

	public static void startTest(String className, String methodName, String description) {
		ExtentTest classTest = classTests.computeIfAbsent(className, k -> getExtentReports().createTest(className));

		ExtentTest extentTest = classTest.createNode(methodName, description);
		methodTest.set(extentTest);
	}

	public static ExtentTest getTest() {
		return methodTest.get();
	}

	public static void flush() {
		if (extent != null) {
			extent.flush();
		}
	}
}
