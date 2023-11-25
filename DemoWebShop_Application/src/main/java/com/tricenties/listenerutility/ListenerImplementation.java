package com.tricenties.listenerutility;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.tricenties.basetest.BaseClass;

public class ListenerImplementation extends BaseClass implements ITestListener{

	@Override
	public void onTestFailure(ITestResult result) {
		String methodName = result.getName();
		TakesScreenshot ts=(TakesScreenshot) driver;
		String screenshot = ts.getScreenshotAs(OutputType.BASE64);
		test.addScreenCaptureFromBase64String(screenshot);
		test.log(Status.FAIL, methodName+" is Failed");
	}
	
}
