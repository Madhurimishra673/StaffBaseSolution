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
public class Test_4_ValidateOnlyValidEmailAndPhoneIsAccepted extends Testbase{

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
	@Parameters({"FirstName","LastName","Email","Phone","FileLocation","Resume","CoverLetter","VisaInfo","Repository"})
	@Description("This Test to verify submitting the form by filling all the valid details")
	public void validateEmailAndPhone(String FirstName, String LastName, String Email, String Phone, String FileLocation,String Resume,String CoverLetter, String visaInfo,String Repository) throws Exception, SQLException {
		AllureLogger.startTest();
		try {
			
			
			apply.enterFirstName(FirstName);
			apply.enterLastName(LastName);
			apply.enterEmail(Email);
			apply.enterPhone(Phone);
			softAssert.assertEquals(apply.attachResume(FileLocation, Resume),true,"Attaching Resume");
			softAssert.assertEquals(apply.attachCoverLetter(FileLocation, CoverLetter),true,"Attaching Cover Letter");
			apply.enterVisaSponsorshipInfo(visaInfo);
			softAssert.assertEquals(apply.selectPrivacyNotice("Text", "Yes"), true,"Selecting Privacy Notice");
			apply.enterSolutionRepository(Repository);			
			apply.clickSubmitApplication();
			apply.validateErrorMessageWhenInvalidEmailEntered();
			apply.validateErrorMessageWhenInvalidPhoneEntered();
			
			
			
			
		} catch (Exception e) {
			throw e;
		}
			
			softAssert.assertAll();
			AllureLogger.endTest();
		}
	
	
	
	

}
