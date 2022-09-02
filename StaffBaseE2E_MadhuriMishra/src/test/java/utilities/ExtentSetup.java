package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


import base.Testbase;

public class ExtentSetup extends Testbase{
	
	public static ExtentReports setUpExtentReport() {
		/*SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
		Date date=new Date();
		String actualdate=format.format(date);
		*/
		
		
		String reportpath=System.getProperty("user.dir")+"/target/Reports/index.html";
		ExtentSparkReporter sparkReport=new ExtentSparkReporter(reportpath);
		extent = new ExtentReports();
		extent.attachReporter(sparkReport);
		
		sparkReport.config().setDocumentTitle("Report");
		sparkReport.config().setTheme(Theme.DARK);
		sparkReport.config().setReportName("IQuote Automation Report");
		sparkReport.config().setAutoCreateRelativePathMedia(true);

		extent.setSystemInfo("Executed on Environment", Config.getProperty("testsiteurl"));
		extent.setSystemInfo("Executed by User", System.getProperty("user.name"));
		
		return extent;
	}
}
