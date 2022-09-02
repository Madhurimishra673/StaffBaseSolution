package pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import base.Logger;
import base.Testbase;
import io.qameta.allure.Owner;
import utilities.CommonFunctions;

public class StaffBaseJobSearchPage extends Testbase { 

	 CommonFunctions CFS;
		
		@FindBy(how = How.XPATH, using = "//div[contains(@class,'flex flex-col items-center space-y-3 py-4 lg:hidden')]//a[text()='Apply']")
		private WebElement applyBtn;
		
		
		public StaffBaseJobSearchPage() {
			// TODO Auto-generated constructor stu  
			PageFactory.initElements(driver, this);	
			CFS=new CommonFunctions();
		}
		
		
		@Owner("Madhuri Mishra")
		public boolean clickApplyBtn() throws InterruptedException 
		{
			boolean flag=false;
		
			
			
			if(CFS.isElementPresent(driver, By.xpath("//button[@id='onetrust-accept-btn-handler']")))
				CFS.clickElement(By.xpath("//button[@id='onetrust-accept-btn-handler']"));
			
		    CFS.clickElement(applyBtn);
			CFS.waitForPageLoaded();
			
			driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='grnhse_iframe']")));
			
			if(CFS.isElementPresent(driver, By.id("first_name")))
			{
				System.out.println("Job Apply Page Loaded Successfully");
				flag=true;
			}
			
			
		
			
			return flag;
		}
		
		
		
		
		
}
