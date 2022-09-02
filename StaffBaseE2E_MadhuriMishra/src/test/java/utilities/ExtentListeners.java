package utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.aventstack.extentreports.Status;

import base.DriverManager;

public class ExtentListeners extends ExtentSetup implements ITestListener,IRetryAnalyzer,IAnnotationTransformer{

	public static Logger Log = Logger.getLogger("devpinoyLogger");
	int noofRetries=0;
	int maxRetries=1;
	
	
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
		String methodName = e.getMethodName();
		System.out.println(methodName);
		
		test=extent.createTest(result.getTestClass().getRealClass().getSimpleName());
		System.out.println("****************************************************************************************");
		System.out.println("****************************************************************************************");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$       " + "Test Case started: " + result.getTestClass().getRealClass().getSimpleName()
				+ "    $$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("X");
		System.out.println("X");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		test.log(Status.PASS, "Test case : "+result.getTestClass().getRealClass().getSimpleName()+" is Passed");
		System.out.println("X");
		System.out.println("X");
		System.out.println(
				"XXXXXXXXXXXXXXXXXXXXXX    " + "TEST CASE PASSED:" + result.getTestClass().getRealClass().getSimpleName() + "XXXXXXXXXXXXXXXXXXXXXX");
		//takeScreenShot(result.getName());
		System.out.println(
				"XXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("****************************************************************************************");
		System.out.println("****************************************************************************************");
	}

	@Override
	public  void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		test.log(Status.FAIL, "Test case : "+result.getTestClass().getRealClass().getSimpleName()+" is Failed");
		test.log(Status.FAIL, result.getThrowable());
	
		//add screenshot for failed test
		File src=((TakesScreenshot)DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
		SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
		Date date=new Date();
		String actualdate=format.format(date);
		String reportpath=System.getProperty("user.dir")+"\\target\\Reports\\Screenshots\\ExecutionReport_"+result.getTestClass().getRealClass().getSimpleName()+actualdate+".png";
		
		File dest=new File(reportpath);
		try {
			FileUtils.copyFile(src, dest.getAbsoluteFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			test.addScreenCaptureFromPath(reportpath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//test.addScreenCaptureFromBase64String(reportpath);
		
		System.out.println("X");
		System.out.println("X");
		System.out.println(
				"XXXXXXXXXXXXXXXXXXXXXX    " + "TEST CASE FAILED:" + result.getTestClass().getRealClass().getSimpleName() + "    XXXXXXXXXXXXXXXXXXXXXX");
		//takeScreenShot(result.getName());
		System.out.println(
				"XXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("****************************************************************************************");
		System.out.println("****************************************************************************************");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		test.log(Status.SKIP, "Test case : "+result.getTestClass().getRealClass().getSimpleName()+" is Skipped");
		test.log(Status.SKIP, result.getThrowable());
		System.out.println("X");
		System.out.println("X");
		System.out.println(
				"XXXXXXXXXXXXXXXXXXXXXX    " + "TEST CASE SKIPPED:" + result.getTestClass().getRealClass().getSimpleName() + "    XXXXXXXXXXXXXXXXXXXXXX");
		//takeScreenShot(result.getName());
		System.out.println(
				"XXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("****************************************************************************************");
		System.out.println("****************************************************************************************");
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		extent=ExtentSetup.setUpExtentReport();
	}

	@Override
	public void onFinish(ITestContext testContext) {
		// TODO Auto-generated method stub
		extent.flush();
		

	    }
		
	


	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		// TODO Auto-generated method stub
		IRetryAnalyzer retry = annotation.getRetryAnalyzer();

		if (retry == null)	{
			annotation.setRetryAnalyzer(ExtentListeners.class);
		}
	}

	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		/*if(noofRetries < maxRetries) {
			System.out.println(result.getTestClass().getRealClass().getSimpleName() + " no of Retry is "+(noofRetries+1));
			noofRetries+=1;
			return true;
		}*/
		return false;
	}
	
	public static byte[] takeScreenShot(String methodName) {

		Log.info("SCREENSHOT of the following test is taken: " + methodName);
		Log.info("----- ***** -----\n");
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}
	
	
	
}
