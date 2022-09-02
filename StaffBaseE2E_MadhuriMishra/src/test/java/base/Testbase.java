package base;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;



public class Testbase {

    public static WebDriver driver;
    ThreadLocal<WebDriver> driverTL= new ThreadLocal<WebDriver>();
    public static Properties Config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static WebDriverWait wait;
    public static ExtentReports extent;
    public static ExtentTest test;
    public static String browser;
    public static Actions actions;
    public static int Optionqty = 0;
    public static Robot robot;
    public static Select select;
    String nodeURL;
    public SoftAssert softAssert = new SoftAssert();
    public static final int minWait = 2;
    public static final int midWait = 12;
    public static final int maxWait = 20;

  
	
	  @BeforeClass(alwaysRun = true)
	  
	  @Parameters({ "propertyfile", "portNO" })
	 

    public void setUp(@Optional("Configuration") String propertyfile, @Optional("") String portNO) throws Exception {    	//get the host name of the system

		  robot = new Robot();
	        BasicConfigurator.configure();
        try {
            fis = new FileInputStream(
                System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\" + propertyfile + ".properties");
        } catch (Exception e) {
            

        }
        
        Config.load(fis);
     
        
        FileInputStream fis2;
        try {
            fis2 = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
            fis2.close();
        } catch (Exception e) {
                    
        }

     

        driver = new ChromeDriver();
        DriverManager.setDriver(driver);

        wait = new WebDriverWait(driver, 20); 
        System.out.println(Config.getProperty("testsiteurl"));

        DriverManager.getDriver().get(Config.getProperty("testsiteurl"));
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Integer.parseInt
        		(Config.getProperty("implicit.wait")), TimeUnit.SECONDS);

        

          
        }
        
        

    



	@AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) throws Exception {
        int i = result.getStatus();
        if (i == ITestResult.FAILURE || i == ITestResult.SKIP) {

        	
            //driver.quit();

         //driver = null;
        }
    }
    protected static void saveProperties(Properties p, String val) throws Exception {
        FileOutputStream fr = new FileOutputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\" + val + ".properties");
        p.store(fr, "Properties");
        fr.close();
        System.out.println("After saving properties: " + p);
    }

    @AfterClass
    public void tearDown() throws Exception {
        try {

            //Quit all drivers
            if (driver != null) {
            	
            
          //       driver.quit();
                           }

            //Kill All chromedriver.exe before launching
            //Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");

        } catch (Exception e) {
            throw e;
        }

    }
    


}