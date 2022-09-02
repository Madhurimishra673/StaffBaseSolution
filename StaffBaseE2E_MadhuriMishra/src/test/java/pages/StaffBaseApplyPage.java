package pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import base.Logger;
import base.Testbase;
import io.qameta.allure.Owner;
import utilities.CommonFunctions;

public class StaffBaseApplyPage extends Testbase { 

	CommonFunctions CFS;
	
	@FindBy(how = How.XPATH, using = "//span[.='Sign in with LinkedIn']/parent::button")
	private WebElement applyWithLinkdlnBtn;
	
	@FindBy(how = How.XPATH, using = "//input[@id='first_name']")
	private WebElement firstNameTxt;
	
	@FindBy(how = How.ID, using = "last_name")
	private WebElement lastNameTxt;
	
	@FindBy(how = How.ID, using = "email")
	private WebElement emailTxt;
	
	@FindBy(how = How.ID, using = "phone")
	private WebElement phoneTxt;
	
	@FindBy(how = How.ID, using = "job_application_answers_attributes_0_text_value")
	private WebElement visaSponsorshipInfoTxt;
	
	@FindBy(how = How.ID, using = "job_application_answers_attributes_2_text_value")
	private WebElement solutionRepositoryTxt;
		
	@FindBy(how = How.ID, using = "submit_buttons")
	private WebElement submitApplicationBtn;
	
	@FindBy(how = How.ID, using = "resume_chosen")
	private WebElement resumeChosen;
	
	@FindBy(how = How.ID, using = "resume_filename")
	private WebElement resumeFileName;
	
	@FindBy(how = How.ID, using = "resume_text")
	private WebElement resumeTextArea;
	
	@FindBy(how = How.ID, using = "cover_letter_chosen")
	private WebElement coverLetterChoosen;
	
	@FindBy(how = How.ID, using = "cover_letter_filename")
	private WebElement coverLetterFileName;
	
	@FindBy(how = How.ID, using = "cover_letter_text")
	private WebElement coverLetterTextArea;
	
	@FindBy(how = How.XPATH, using = "//fieldset[@id='resume_fieldset']//button[@data-source='attach']")
	private WebElement attachResumeBtn;
	
	@FindBy(how = How.XPATH, using = "//fieldset[@id='resume_fieldset']//a[@data-source='dropbox']")
	private WebElement dropBoxResume;
	
	@FindBy(how = How.XPATH, using = "//fieldset[@id='resume_fieldset']//a[@data-source='paste']")
	private WebElement enterResumeManuallyBtn;
	
	@FindBy(how = How.XPATH, using = "//fieldset[@id='resume_fieldset']//div[@class='attach-or-paste ']")
	private WebElement dataFileResume;
	
	@FindBy(how = How.XPATH, using = "//fieldset[@id='cover_letter_fieldset']//button[@data-source='attach']")
	private WebElement attachCoverLetterBtn;
	
	@FindBy(how = How.XPATH, using = "//fieldset[@id='cover_letter_fieldset']//a[@data-source='dropbox']")
	private WebElement dropBoxCoverLetter;
	
	@FindBy(how = How.XPATH, using = "//fieldset[@id='cover_letter_fieldset']//a[@data-source='paste']")
	private WebElement enterCoverLetterManuallyBtn;
	
	@FindBy(how = How.XPATH, using = "//fieldset[@id='cover_letter_fieldset']//div[@class='attach-or-paste ']")
	private WebElement dataFileCoverLetter;
	
	
	@FindBy(how = How.XPATH, using = "//div[@id='application_confirmation']//h1")
	private WebElement submissionMessage;
	
	@FindBy(how = How.XPATH, using = "//div[@class='field']//span[@class='asterisk']")
	private List<WebElement> mandatoryFields;
	
	@FindBy(how = How.XPATH, using = "//div[@class='field-error-msg']")
	private List<WebElement> errorMessage;
	
	
	
	
	public StaffBaseApplyPage() throws AWTException {
		PageFactory.initElements(driver, this);
		CFS=new CommonFunctions();
		robot=new Robot();
	}
	
	@Owner("Madhuri Mishra")
	public void enterFirstName(String vFirstName) throws InterruptedException 
	{
		
		CFS.ScrollToElement(firstNameTxt);
		CFS.sendKeys(firstNameTxt, vFirstName);
	}
	
	@Owner("Madhuri Mishra")
	public void enterLastName(String vLastName) throws InterruptedException 
	{
		CFS.ScrollToElement(lastNameTxt);
		CFS.sendKeys(lastNameTxt, vLastName);
	}
	
	@Owner("Madhuri Mishra")
	public void enterPhone(String vPhone) throws InterruptedException 
	{
		CFS.ScrollToElement(phoneTxt);
		CFS.sendKeys(phoneTxt, vPhone);
	}
	
	@Owner("Madhuri Mishra")
	public void enterEmail(String vEmail) throws InterruptedException 
	{
		CFS.ScrollToElement(emailTxt);
		CFS.sendKeys(emailTxt, vEmail);
	}
	
	@Owner("Madhuri Mishra")
	public void enterVisaSponsorshipInfo(String vInfo) throws InterruptedException 
	{
		CFS.sendKeys(visaSponsorshipInfoTxt, vInfo);
	}
	
	@Owner("Madhuri Mishra")
	public void enterSolutionRepository(String vRepository) throws InterruptedException 
	{
		CFS.sendKeys(solutionRepositoryTxt, vRepository);
	}
	
	
	
	@Owner("Madhuri Mishra")
	public void clickApplyWithLinkdln(String vUserName, String vPassword) throws InterruptedException 
	{
		CFS.waitUntilElementisPresent(driver, applyWithLinkdlnBtn, 20);
		CFS.clickElement(applyWithLinkdlnBtn);
		String parent=driver.getWindowHandle();

		Set<String>s=driver.getWindowHandles();

		// Now iterate using Iterator
		Iterator<String> I1= s.iterator();

		while(I1.hasNext())
		{

		String child_window=I1.next();


		if(!parent.equals(child_window))
		{
		driver.switchTo().window(child_window);
		}
		}
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(vUserName);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(vPassword);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		CFS.waitForPageLoaded();
		
		CFS.waitUntilElementisPresent(driver, resumeTextArea, 60);
	}
	
	@Owner("Madhuri Mishra")
	public void clickSubmitApplication() throws InterruptedException 
	{
		CFS.ScrollToElement(submitApplicationBtn);
		CFS.clickElement(submitApplicationBtn);
	}
	
	@Owner("Madhuri Mishra")
	public boolean attachResume(String vFileLoaction, String vResumeNameWithExtension) throws InterruptedException 
	{
		boolean flag=false;
		CFS.clickElement(attachResumeBtn);
		
		robot.delay(2000);
		String vFileName=vFileLoaction + vResumeNameWithExtension;
		//1. copy the file file to clipboard
				StringSelection SS=new StringSelection(vFileName);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(SS, null);
				
				//2. press control +v
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				//release control v
				robot.keyRelease(KeyEvent .VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_V);
				
				
				//3. Press enter
			    robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				
				CFS.waitForPageLoaded();
				CFS.waitUntilElementisPresent(driver, resumeChosen, 60);
			    String ActualFileName=	CFS.getTextValue(resumeFileName);
				
				if(vResumeNameWithExtension.equals(ActualFileName))
				{
					flag=true;
				}
				return flag;
	}
	
	
	@Owner("Madhuri Mishra")
	public boolean attachCoverLetter(String vFileLoaction, String vCoverWithExtension) throws InterruptedException 
	{
		boolean flag=false;
		CFS.clickElement(attachCoverLetterBtn);
		String vFileName=vFileLoaction + vCoverWithExtension;
		//1. copy the file file to clipboard
				StringSelection SS=new StringSelection(vFileName);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(SS, null);
				
				//2. press control +v
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				
				robot.keyRelease(KeyEvent .VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_V);
				
				
				//3. Press enter
			    robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				
				CFS.waitForPageLoaded();
				CFS.waitUntilElementisPresent(driver, coverLetterChoosen, 10);
			String ActualFileName=	CFS.getTextValue(coverLetterFileName);
				
				if(vCoverWithExtension.equals(ActualFileName))
				{
					flag=true;
				}
				return flag;
	}
	
	
	@Owner("Madhuri Mishra")
	public void enterResumeMannually(String vText) throws InterruptedException 
	{
		
		CFS.clickElement(enterResumeManuallyBtn);
		CFS.waitForPageLoaded();
		CFS.waitUntilElementisPresent(driver, resumeTextArea, 60);
		CFS.sendKeys(resumeTextArea, vText);
		
	}
	
	@Owner("Madhuri Mishra")
	public void enterCoverLetterMannually(String vText) throws InterruptedException 
	{
		
		CFS.clickElement(enterCoverLetterManuallyBtn);
		CFS.waitForPageLoaded();
		CFS.waitUntilElementisPresent(driver, coverLetterTextArea, 60);
		CFS.sendKeys(coverLetterTextArea, vText);
		
	}
	
	@Owner("Madhuri Mishra")
	public boolean validateIfFieldIsMandatory(String vField) throws InterruptedException 
	{
		boolean flag=false;
		if(CFS.isElementPresent(driver, By.xpath("//label[contains(text(),'"+vField+"')]//span[@class='asterisk']")))
		{
			Logger.info("Field "+vField+" is  mandotory ");
			flag=true;
		}
		return flag;
	}
	
	
	@Owner("Madhuri Mishra")
	public boolean selectPrivacyNotice(String vOpionType,String vOption) throws InterruptedException 
	{
		boolean flag=false;
		CFS.clickElement(By.xpath("//div[@class='select2-container']"));
		//ul//li//div[.='Yes']
		CFS.clickElement(By.xpath("//ul//li//div[.='Yes']"));
		/*
		 * select=new Select(driver.findElement(By.xpath(
		 * "//select[@id='job_application_answers_attributes_1_answer_selected_options_attributes_1_question_option_id']"
		 * ))); String actual="";
		 * 
		 * select.selectByIndex(1);
		 */
	return true;
	
	}
	
	@Owner("Madhuri Mishra")
	public boolean validateformSubmissionIsSuccessfull() throws InterruptedException 
	{
		boolean flag=false;
           clickSubmitApplication();
           CFS.waitForPageLoaded();
           if(CFS.isElementPresent(driver, By.xpath("//div[@id='application_confirmation']//h1")))
           {
        	  String msg= CFS.getTextValue(By.xpath("//div[@id='application_confirmation']//h1"));
        	  if(msg.equals("Thank you for applying."))
        	  {
        		  flag=true;
        	  }
           }else
           {
        	   Logger.error("Failed to submit application successfully, Please investigate");
           }
          
           return flag;
	}
	
	
	@Owner("Madhuri Mishra")
	public void validateformSubmissionIsUnsuccessfull() throws InterruptedException 
	{
	
         clickSubmitApplication();
         
           
         softAssert.assertEquals(errorMessage.size(), mandatoryFields.size(),"Validating all mandatory field show error message when form is submitted without filling mandatory details");  
          
         //first name
         if(CFS.isElementPresent(driver, By.xpath("//input[@id='first_name']/following-sibling::div[@class='field-error-msg']")))
         { String msg= CFS.getTextValue(By.xpath("//input[@id='first_name']/following-sibling::div[@class='field-error-msg']"));
         if(msg.equals("First Name is required."))
         {
        	 softAssert.assertTrue(true,"'First Name is required.' error message is shown if form is submitted without filling first name");
         }
         }else
         {
        	 softAssert.assertFalse(false,"There is no error message shown when form is submitted without filling First Name");
         }
         
        //Last name
         if(CFS.isElementPresent(driver, By.xpath("//input[@id='last_name']/following-sibling::div[@class='field-error-msg']")))
         { String msg= CFS.getTextValue(By.xpath("//input[@id='last_name']/following-sibling::div[@class='field-error-msg']"));
         if(msg.equals("Last Name is required."))
         {
        	 softAssert.assertTrue(true,"'Last Name is required.' error message is shown if form is submitted without filling Last name");
         }
         }else
         {
        	 softAssert.assertFalse(false,"There is no error message shown when form is submitted without filling Last Name");
        	 }
         
         //email
         if(CFS.isElementPresent(driver, By.xpath("//input[@id='email']/following-sibling::div[@class='field-error-msg']")))
         { String msg= CFS.getTextValue(By.xpath("//input[@id='email']/following-sibling::div[@class='field-error-msg']"));
         if(msg.equals("Email is required."))
         {
        	 softAssert.assertTrue(true,"'Email is required.' error message is shown if form is submitted without filling email");
         }
         }else
         {
        	 softAssert.assertFalse(false,"There is no error message shown when form is submitted without filling email");
         }
      
         //phone
         if(CFS.isElementPresent(driver, By.xpath("//input[@id='phone']/following-sibling::div[@class='field-error-msg']")))
         { String msg= CFS.getTextValue(By.xpath("//input[@id='phone']/following-sibling::div[@class='field-error-msg']"));
         if(msg.equals("Phone is required."))
         {
        	 softAssert.assertTrue(true,"'Phone is required.' error message is shown if form is submitted without filling phone");
         }
         }else
         {
        	 softAssert.assertFalse(false,"There is no error message shown when form is submitted without filling phone");
         }
         
         //resume 
         if(CFS.isElementPresent(driver, By.xpath("//fieldset[@id='resume_fieldset']/div[@class='field-error-msg']")))
         { String msg= CFS.getTextValue(By.xpath("//fieldset[@id='resume_fieldset']/div[@class='field-error-msg']"));
         if(msg.equals("Resume/CV is required."))
         {
        	 softAssert.assertTrue(true,"'Resume/CV is required.' error message is shown if form is submitted without attaching CV");
         }
         }else
         {
        	 softAssert.assertFalse(false,"There is no error message shown when form is submitted without filling attaching CV");
         }
         
         //privacy policy
         if(CFS.isElementPresent(driver, By.xpath("//select/following-sibling::div[@class='field-error-msg']")))
         { String msg= CFS.getTextValue(By.xpath("//select/following-sibling::div[@class='field-error-msg']"));
         if(msg.equals("This field is required."))
         {
        	 softAssert.assertTrue(true,"'This field is required.' error message is shown if form is submitted without selecting privacy policy");
         }
         }else
         {
        	 softAssert.assertFalse(false,"There is no error message shown when form is submitted without selecting privacy policy");
         }
         
        //solution repository 
         if(CFS.isElementPresent(driver, By.xpath("//div[@id='custom_fields']//textarea/following-sibling::div[@class='field-error-msg']")))
         { String msg= CFS.getTextValue(By.xpath("//div[@id='custom_fields']//textarea/following-sibling::div[@class='field-error-msg']"));
         if(msg.equals("This field is required."))
         {
        	 softAssert.assertTrue(true,"'This field is required.' error message is shown if form is submitted without filling Solution Repository");
         }
         }else
         {
        	 softAssert.assertFalse(false,"There is no error message shown when form is submitted without filling Solution Reporsitory");
         }
         
     
         
	}
	
	
	
	
	
	@Owner("Madhuri Mishra")
	public boolean validateErrorMessageWhenInvalidEmailEntered() throws InterruptedException 
	{
		boolean flag=false;
		 //email
        if(CFS.isElementPresent(driver, By.xpath("//input[@id='email']/following-sibling::div[@class='field-error-msg']")))
        { String msg= CFS.getTextValue(By.xpath("//input[@id='email']/following-sibling::div[@class='field-error-msg']"));
        if(msg.equals("Please use the format \"text@example.com\""))
        {
       	 softAssert.assertTrue(true,"'Please use the format \"text@example.com\"' error message is shown if form is submitted with invalid email ID");
        }
        }else
        {
       	 softAssert.assertFalse(false,"There is no error message shown when form is submitted with invalid email ID");
        }
           return flag;
	}
	
	
	@Owner("Madhuri Mishra")
	public boolean validateErrorMessageWhenInvalidPhoneEntered() throws InterruptedException 
	{
		boolean flag=false;
		 //email
        if(CFS.isElementPresent(driver, By.xpath("//input[@id='phone']/following-sibling::div[@class='field-error-msg']")))
        { String msg= CFS.getTextValue(By.xpath("//input[@id='phoen']/following-sibling::div[@class='field-error-msg']"));
        if(msg.equals("Please enter valid 10 digit phne number"))
        {
       	 softAssert.assertTrue(true,"'Please enter valid 10 digit phne number' error message is shown if form is submitted with invalid phone");
        }
        }else
        {
       	 softAssert.assertFalse(false,"There is no error message shown when form is submitted with invalid pnone");
        }
           return flag;
	}
	
}
