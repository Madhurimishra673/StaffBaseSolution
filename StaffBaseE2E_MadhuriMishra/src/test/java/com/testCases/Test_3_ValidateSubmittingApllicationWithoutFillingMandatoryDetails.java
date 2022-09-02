package com.testCases;

import java.awt.AWTException;
import java.sql.SQLException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import base.Testbase;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.StaffBaseApplyPage;
import pages.StaffBaseJobSearchPage;
import utilities.AllureLogger;
import utilities.CommonFunctions;

@Listeners(utilities.ExtentListeners.class)
@Owner(value="Madhuri Mishra")
public class Test_3_ValidateSubmittingApllicationWithoutFillingMandatoryDetails extends Testbase{

	StaffBaseApplyPage apply;
	StaffBaseJobSearchPage JobSearch;
	
	@BeforeMethod(alwaysRun = true)
	public void init() throws AWTException {
		apply=new StaffBaseApplyPage();
		JobSearch=new StaffBaseJobSearchPage();
	   
	    }
	
	@BeforeMethod(alwaysRun = true)
	@Description("login to Application")
	public void navigateToJobApplyPage() {

		AllureLogger.startTest();
		try {
			softAssert.assertEquals(JobSearch.clickApplyBtn(), true,"Click on apply button in Job search page");
		} catch (Exception e) {

			e.printStackTrace();
		}
		AllureLogger.endTest();
		}
	

	
	
	@Test
	@Description("This Test to verify submitting the form by submitting the form without filling mandatory details")
	public void validateFormSubmissionWithEmptyDetails() throws Exception, SQLException {
		AllureLogger.startTest();
		try {
			
			
		    apply.validateformSubmissionIsUnsuccessfull();
			
						
			
		} catch (Exception e) {
			throw e;
		}
			
			softAssert.assertAll();
			AllureLogger.endTest();
		}
	
	
	
	

}
