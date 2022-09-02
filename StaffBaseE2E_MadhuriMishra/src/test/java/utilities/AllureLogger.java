package utilities;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

public class AllureLogger {

    public static Logger log = Logger.getLogger("Execution Log");
    private static StepResult result_fail;
    private static StepResult result_pass;
    private static String uuid;
    private static SoftAssert softAssertion;


    public static void startTest() {
        softAssertion = new SoftAssert();
    }

    public static void logStep(String description) {
        log.info(description);
        uuid = UUID.randomUUID().toString();
        result_fail =  new StepResult().setName(description).setStatus(Status.FAILED);
        result_pass = new StepResult().setName(description).setStatus(Status.PASSED);      
    }

    public static void markStepAsFailed(WebDriver driver, String errorMessage) {
        log.fatal(errorMessage);
        System.err.println(errorMessage);
        Allure.getLifecycle().startStep(uuid, result_fail);
        Allure.getLifecycle().addAttachment(errorMessage, "image", "JPEG", ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        Allure.getLifecycle().stopStep(uuid);
        softAssertion.fail(errorMessage);
    }
    public static void markStepAsFailed(String errorMessage) {
    	log.fatal(errorMessage);
    	System.err.println(errorMessage);
    	 result_fail =  new StepResult().setName(errorMessage).setStatus(Status.FAILED); 	 
    	 uuid = UUID.randomUUID().toString();
        Allure.getLifecycle().startStep(uuid, result_fail);
        Allure.getLifecycle().stopStep(uuid);
        softAssertion.fail(errorMessage);
    }


    public static void markStepAsPassed(WebDriver driver, String message) {
        log.info(message);
        System.out.println(message);
        Allure.getLifecycle().startStep(uuid, result_pass);
        Allure.getLifecycle().stopStep(uuid);
    }
    
    public static void markStepAsPassed(String message) {
        log.info(message);
        System.out.println(message);
    	result_pass = new StepResult().setName(message).setStatus(Status.PASSED);
    	uuid = UUID.randomUUID().toString();
        Allure.getLifecycle().startStep(uuid, result_pass);
        Allure.getLifecycle().stopStep(uuid);
    }

    public static void endTest() {
        softAssertion.assertAll();
       /* softAssertion = null;
        startTest();
        softAssertion = new SoftAssert();*/
    }
}
