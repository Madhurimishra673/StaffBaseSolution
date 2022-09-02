package utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dbunit.Assertion;
import org.dbunit.dataset.excel.XlsDataSet;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import base.DriverManager;
import base.Logger;
import base.Testbase;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;


public class CommonFunctions extends Testbase {
	public static final int minWait = 5;
	public static final int midWait = 20;
	public static final int maxWait = 30;
	private static SecureRandom random = new SecureRandom();
	public static Properties prop;
	public static ResultSet rs;
	public static Properties Query = new Properties();

	public static boolean isElementPresent(WebDriver driver, By by) {

		waitForPageLoad(driver);

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		try {
			driver.findElement(by);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
		}
	}

	
	
	public static Properties initialize_properties(String file) {

		prop = new Properties();

		try {
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")
					+ "\\src\\test\\resources\\properties\\Updated\\VerificationQueries\\" + file + ".properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	public static String unique6LengthNumeric() {
		DateFormat dateFormat = new SimpleDateFormat("hhssmm");
		Date date = new Date();
		String sUniqueNumber = dateFormat.format(date);
		if (String.valueOf(sUniqueNumber.charAt(0)).equals("0")) {
			sUniqueNumber = sUniqueNumber.replaceFirst("0", String.valueOf(random.nextInt(8) + 1));
		}
		return sUniqueNumber;
	}

	public static String getCurrentDateTime() {
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj = new Date();
		Logger.info(df.format(dateobj));

		/*
		 * getting current date time using calendar class An Alternative of above
		 */
		Calendar calobj = Calendar.getInstance();
		Logger.info(df.format(calobj.getTime()));
		String CDate = df.format(calobj.getTime());
		return CDate;
	}

	public static boolean Elementdisplayed_Enabled(WebElement Element) {
		if (Element.isDisplayed()) {
			if (Element.isEnabled()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static String getValue(WebDriver driver, By locator) throws Exception {

		String sValue = "";
		if (isElementPresent(driver, locator)) {
			sValue = driver.findElement(locator).getAttribute("value").trim();
		} else {

			Assert.fail("Not able to get value.Locator is not present " + locator);
		}
		Logger.info("sValue is " + sValue);
		return sValue;
	}

	public static String getAttribute(WebDriver driver, By locator, String attribute) throws Exception {
		String sAttr = "";

		if (isElementPresent(driver, locator)) {
			sAttr = driver.findElement(locator).getAttribute(attribute);
		} else {
			Logger.error("Object doesn't exists");
		}

		return sAttr;
	}

	@Step("Closing tab")
	public void CloseTab(String Tab) throws InterruptedException {

		Logger.info("Closing Tab:" + Tab);
		if (driver.findElements(By.xpath(
				"//label[contains(text(),'" + Tab + "')]/parent::span/parent::li//span[@class='app__tab__close']"))
				.size() > 0) {
			clickElement(By.xpath(
					"//label[contains(text(),'" + Tab + "')]/parent::span/parent::li//span[@class='app__tab__close']"));
		} else if (driver
				.findElements(By.xpath("//span[text()='Loading...']/following-sibling::span[@class='app__tab__close']"))
				.size() > 0) {
			clickElement(By.xpath("//span[text()='Loading...']/following-sibling::span[@class='app__tab__close']"));
		} else

		{
			Logger.info("Tab '" + Tab + "' doesn't exists");
		}
	}

	public void selectCheckbox(WebDriver driver, String lblXpath, String CheckBoxState) throws Exception {
		String btnXpath = lblXpath + "/ancestor::button[@class='ckb ']";
		String CheckboxCurrentstate = driver.findElement(By.xpath(btnXpath)).getAttribute("aria-checked");
		if (CheckBoxState.equals("0")) {

			if (CheckboxCurrentstate.equals("true")) {
				clickElement(By.xpath(btnXpath));
			}

		} else if (CheckBoxState.equals("1")) {
			if (CheckboxCurrentstate.equals("false")) {
				clickElement(By.xpath(btnXpath));
			}

		}

	}

	public static void scrolltoWebElement(WebDriver driver, By locator) throws Exception {
		WebElement element = driver.findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(500);
	}

	public static void waitForPageLoad(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		try {
			WebDriverWait wait = new WebDriverWait(driver, 120);
			wait.until(expectation);
		} catch (Throwable error) {
			Logger.info("Timeout waiting for Page Load Request to complete.");
		} finally {
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
		}
	}

	public static boolean waitUntilElementisClickable(WebDriver driver, By locator, int TimeOut) throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(driver, TimeOut);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			return true;
		} catch (TimeoutException e) {
			Logger.error("Element did not become clickable even after waiting for " + TimeOut + " seconds");
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
		}
	}

	public static void setOriginalWindowHandle(WebDriver driver) {
		String CommonOriginalHandle = null;
		String CommonOrignalwindowTitle = null;
		Logger.info("-----Setting WebDriver Control To original window -----");

		CommonOriginalHandle = driver.getWindowHandle();
		CommonOrignalwindowTitle = driver.getTitle();
	}

	public static void clickElement(WebDriver driver, By Locator) throws Exception {
		try {

			isElementPresent(driver, Locator);
			if (!Elementdisplayed_Enabled(driver.findElement(Locator))) {
				scrolltoWebElement(driver, Locator);
			}

			wait.until(ExpectedConditions.elementToBeClickable(Locator));
			driver.findElement(Locator).click();
			waitForPageLoad(driver);
		} catch (ElementClickInterceptedException ECE) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", driver.findElement(Locator));
		} catch (ElementNotInteractableException e) {

			((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(Locator));
			Thread.sleep(1000);
		} catch (StaleElementReferenceException SERE) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", driver.findElement(Locator));
		}

		catch (WebDriverException e) {
			Logger.error("An exceptional case." + e);
			Assert.assertEquals(false, "An exceptional case." + e);
		}

	}

	public static void sendValue(WebDriver driver, By locator, String sValue) throws Exception {
		if (isElementPresent(driver, locator)) {
			driver.findElement(locator).clear();
			driver.findElement(locator).sendKeys("." + Keys.CONTROL + "a" + Keys.DELETE);
			driver.findElement(locator).sendKeys(sValue);
			driver.findElement(locator).sendKeys(Keys.TAB);
		} else {
			Assert.fail("Element " + locator + " is not present");
		}
	}

	public static boolean waitUntilElementisPresent(WebDriver driver, By locator, int TimeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, TimeOut);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return true;
		} catch (TimeoutException e) {
			Logger.error("Element did not appear even after waiting for " + TimeOut + " seconds");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
		}

	}

	public static boolean waitForElement(WebElement ele, int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		try {
			wait.until(ExpectedConditions.visibilityOf(ele));
			driver.manage().timeouts().implicitlyWait(minWait, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			driver.manage().timeouts().implicitlyWait(minWait, TimeUnit.SECONDS);
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
		}

	}

	public static void enterDataIntoTextField(WebDriver driver, String XpathForLocator, String Fieldvalue)
			throws Exception {
		driver.findElement(By.xpath(XpathForLocator)).clear();
		driver.findElement(By.xpath(XpathForLocator)).click();
		driver.findElement(By.xpath(XpathForLocator)).sendKeys(Fieldvalue + Keys.TAB + Keys.TAB);

	}

	public static void sendValueWithoutClear(WebDriver driver, By locator, String sValue) throws Exception {

		if (isElementPresent(driver, locator)) {
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			clickElement(driver, locator);
			driver.findElement(locator).sendKeys(sValue + Keys.TAB);
		} else {
			Assert.fail("Element " + locator + " is not present");
		}
	}

	public static void selectOptionFromDropdown(WebDriver driver, String XpathForLocator, String Fieldvalue)
			throws Exception {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathForLocator)));
		driver.findElement(By.xpath(XpathForLocator)).click();
		Thread.sleep(2000);
		CommonFunctions.waitUntilElementisPresent(driver, By.xpath("//li/label[contains(text(),'" + Fieldvalue + "')]"),
				180);
		driver.findElement(By.xpath("//li/label[contains(text(),'" + Fieldvalue + "')]")).click();
		Thread.sleep(2000);

		String Selectedval = driver.findElement(By.xpath(XpathForLocator)).getText();
		if (Selectedval.contains(Fieldvalue)) {
			// Logger.info("Selected from dropdown : "+Selectedval);
		} else {
			Logger.info("Correct value is not selected from dropdown please investigate");
		}

	}

	public static void selectTextFromDropdown(WebDriver driver, String XpathForLocator, String Fieldvalue)
			throws Exception {
		driver.findElement(By.xpath(XpathForLocator)).click();
		driver.findElement(By.xpath(XpathForLocator)).clear();
		driver.findElement(By.xpath(XpathForLocator)).click();
		driver.findElement(By.xpath(XpathForLocator)).sendKeys(Fieldvalue);
		Thread.sleep(2000);
		driver.findElement(By.xpath(XpathForLocator)).sendKeys(Keys.DOWN);
		driver.findElement(By.xpath(XpathForLocator)).sendKeys(Keys.ENTER);

		String Selectedval = driver.findElement(By.xpath(XpathForLocator)).getAttribute("value");
		if (Selectedval.equals(Fieldvalue)) {
			// Logger.info("Selected from dropdown : "+Selectedval);
		} else {
			Logger.error("Correct value is not selected from dropdown please investigate");
		}

	}

	public static void WaitFor_ElementVisiblity(WebDriver driver, By Loc) {
		WebDriverWait wait = new WebDriverWait(driver, 180);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Loc));
	}

	public static boolean waitUntilElementisVisible(WebDriver driver, By locator, int TimeOut) throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(driver, TimeOut);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			return true;
		} catch (TimeoutException e) {
			Logger.error("Element did not appear even after waiting for " + TimeOut + " seconds");
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
		}
	}

	public static void selectDropdownByIndex(WebDriver driver, By locator, int index) throws Exception {
		Logger.info("Selecting dropdown with locator " + locator + " by index " + index);
		index += 1;
		if (isElementPresent(driver, locator)) {
			if (index != 0) {
				// select the multiple values from a dropdown
				Select selectByValue = new Select(driver.findElement(locator));
				selectByValue.selectByIndex(index);
			}
			Thread.sleep(1000);
		}
	}

	public static String futureDateinMMddyyyyFormat(int NumberOfdaysToAdd) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, NumberOfdaysToAdd);
		String sfutureDate = sdf.format(c.getTime());
		return sfutureDate;
	}

	public static String pastDateinMMddyyyyFormat(int NumberOfdaysTosubtract) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, -NumberOfdaysTosubtract);
		String sfutureDate = sdf.format(c.getTime());
		return sfutureDate;
	}

	public static int randInt(int min, int max) {

		// Usually this can be a field rather than a method variable
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static void clickEnterKey() {
		try {
			robot = new Robot();

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static WebElement getShadowElement(WebElement element) {
		WebElement ele = (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot",
				element);
		return ele;
	}

	public void ScrollToElement(WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", ele);

	}

	public enum SearchAtrribute {
		EstimateNumber, EstimateTitle, Customer, SalesPerson, Agency, ProductSatisfaction, Status;
	}


	public void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(2000);
			WebDriverWait wait = new WebDriverWait(driver, 160);
			wait.until(expectation);
			System.out.println("page load to ready state is successfull");
		} catch (Throwable error) {
			// Assert.fail("Timeout waiting for Page Load Request to complete.");
		} finally {
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
		}
	}

	public static Boolean isElementScrollable() {
		String execScript = "return document.documentElement.scrollWidth>document.documentElement.clientWidth;";
		JavascriptExecutor scrollBarPresent = (JavascriptExecutor) driver;
		return (Boolean) (scrollBarPresent.executeScript(execScript));

	}

	public static void listComparator(List<String> Original, List<String> Expected) {
		/////// Note: Both the lists should have same sequence
		int expCnt = Expected.size();

		for (int i = 0; i < expCnt; i++) {
			if (Expected.get(i).equals(Original.get(i))) {
				Logger.info(Original.get(i) + " has been updated succesfully");
			} else {
				Logger.error(Original.get(i) + " has NOT been updated succesfully");
			}
		}

	}

	
	public String returnCurrentdatetime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public Date convertStringDateToDate(String date) throws ParseException {
		// This method is used to get the day,date, month and year;
		SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
		return formatter1.parse(date);
	}

	public boolean checkBox(By Locator, String status) {
		boolean flag = false;
		if (status.equals("true") && driver.findElement(Locator).getAttribute("aria-checked").equals("false")) {
			driver.findElement(Locator).click();
			flag = true;
		} else if (status.equals("false") && driver.findElement(Locator).getAttribute("aria-checked").equals("true")) {
			driver.findElement(Locator).click();
			flag = true;
		}
		return flag;
	}

	public void ExecutionSummary(int TotalLines, int Pass, int Fail) {
		Allure.step("****************************** Summary of Test Run | Date : " + returnCurrentdatetime()
				+ "******************************");
		Allure.step("Total Test Lines : " + TotalLines);
		Allure.step("Test Lines Passed are  : " + Pass);
		Allure.step("Test Lines Failed are  : " + Fail);
		Allure.step("Test Lines UnExecuted are  : " + (TotalLines - (Pass + Fail)));
		Allure.step(
				"*********************************************************************************************************");
	}

	
	

	@Owner(value = "Madhuri Mishra")
	public boolean isAttribtutePresent(WebElement element, String attribute) {
		Boolean result = false;
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			String value = element.getAttribute(attribute);
			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
		}

		return result;
	}

	public String readFromExcel(String path, String col) throws IOException {

		Logger.info("--->|readFromExcel : field = " + col + ", path=" + path);
		String val;
		Cell cell = null;
		FileInputStream fis = new FileInputStream(path);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheetAt(0);

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			if (sheet.getRow(i).getCell(0).getStringCellValue().equals(col)) {
				cell = sheet.getRow(i).getCell(1);
				break;
			}
		}

		if (cell == null)
			val = "";
		else
			val = cell.toString();

		Logger.info("<---|readFromExcel  = " + val);
		return val;
	}

	public void writeToExcel(String path, String field, String val)
			throws InvalidFormatException, IOException, InterruptedException {

		Logger.info("--->|writeToExcel : field = " + field + ", val = " + val + ", path=" + path);

		boolean isExists = false;
		int i = 1;

		FileInputStream fis = new FileInputStream(path);
		FileOutputStream fos;
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet = wb.getSheetAt(0);

		for (i = 1; i <= sheet.getLastRowNum(); i++) {
			if (sheet.getRow(i).getCell(0).getStringCellValue().equals(field)) {
				isExists = true;
				sheet.getRow(i).getCell(1).setCellValue(val);
				break;
			}
		}

		// if given field doesnt exists, add new field
		if (!isExists) {
			Row row = sheet.createRow(i);
			row.createCell(0).setCellValue(field);
			row.createCell(1).setCellValue(val);
		}

		fos = new FileOutputStream(path);
		Thread.sleep(5000);
		wb.write(fos);
		fos.flush();
		fos.close();
	}


	

	@Owner(value = "Madhuri Mishra")
	public void clickElement(By Locator) {

		try {
			waitUntilElementisPresent(driver, Locator, 45);
			wait.until(ExpectedConditions.elementToBeClickable(Locator));
			driver.findElement(Locator).click();
			System.out.println("Click On Element " + Locator + " is successfull");
			waitForPageLoaded();
		} catch (ElementClickInterceptedException ECE) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", driver.findElement(Locator));
		} catch (ElementNotInteractableException EIE) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", driver.findElement(Locator));
		}

		catch (StaleElementReferenceException e) {

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", driver.findElement(Locator));
			System.out.println("Click On Element " + Locator + " is successfull");
			waitForPageLoaded();

		} catch (TimeoutException e) {
			Logger.error("Either WebDriver couldn’t locate the element Or Element is not clickable at the moment"
					+ Locator + " exception message " + e);
			Assert.assertEquals(false,
					"Either WebDriver couldn’t locate the element Or Element is not clickable at the moment" + Locator);
		}

		catch (WebDriverException WDE) {
			Logger.error("An exceptional case. " + WDE);
			Assert.assertEquals(false, "An exceptional case. " + WDE);
		}
	}

	@Owner(value = "Madhuri Mishra")
	public void clickElement(WebElement ele) {

	
		try {
			
		
		//	wait.until(ExpectedConditions.visibilityOf(ele));
			wait.until(ExpectedConditions.elementToBeClickable(ele));

			String s = ele.toString();
			ele.click();
			Logger.info("Click On Element " + s + " is successfull");
			waitForPageLoaded();

		} catch (ElementClickInterceptedException ECE) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", ele);
		} catch (ElementNotInteractableException EIE) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", ele);
		} catch (TimeoutException e) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", ele);

		} catch (WebDriverException WDE) {
			Logger.error("An exceptional case." + WDE);
			Assert.assertEquals(false, "An exceptional case." + WDE);
		}

	}

	
	@Owner(value = "Madhuri Mishra")
	public void sendKeys(By Locator, String val) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
		wait.until(ExpectedConditions.elementToBeClickable(Locator));
		driver.findElement(Locator).sendKeys(Keys.CONTROL, "a", Keys.BACK_SPACE);
		Thread.sleep(1000);
		driver.findElement(Locator).sendKeys(val);
		Thread.sleep(1000);
		driver.findElement(Locator).sendKeys(Keys.TAB);
	}

	@Owner(value = "Madhuri Mishra")
	public void sendKeys(WebElement ele, String val) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(ele));
		wait.until(ExpectedConditions.elementToBeClickable(ele));
		ele.sendKeys(Keys.CONTROL, "a", Keys.BACK_SPACE);
		ele.sendKeys(val);
		Thread.sleep(1000);
		ele.sendKeys(Keys.TAB);
	}

	
	public static boolean deleteFilesInFolder(String directoryname) {
		File directory = new File(directoryname);
		File[] files = directory.listFiles();
		for (File file : files) {
			if (!file.delete()) {
				return false;
			}
		}
		return true;
	}

	
	public static void pressKey(String vKey) {
		try {

			robot = new Robot();

			switch (vKey) {
			case "Enter":
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				break;
			case "Right":
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.keyRelease(KeyEvent.VK_RIGHT);
				break;

			case "Tab":
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				break;

			case "Page down":
				robot.keyPress(KeyEvent.VK_PAGE_DOWN);
				robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
				break;

			case "Page up":
				robot.keyPress(KeyEvent.VK_PAGE_UP);
				robot.keyRelease(KeyEvent.VK_PAGE_UP);
				break;

			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static HashMap<String, String> returnValuesFromExcel_HashMap(String path, String sheetName,
			String scenarioName) {
		String fPath = path;
		Logger.info("File Picked from: " + fPath);
		File file;
		FileInputStream fs;
		XSSFWorkbook workBook;
		XSSFSheet sheet;
		XSSFCell cell;
		HashMap<String, String> valsMap = new HashMap<String, String>();

		try {
			int reqColNum = 0;
			int reqRowNum = 0;
			file = new File(fPath);
			fs = new FileInputStream(file);
			workBook = new XSSFWorkbook(fs);
			sheet = workBook.getSheet(sheetName);
			int rowCount = sheet.getLastRowNum();
			int colCount = sheet.getRow(0).getLastCellNum();
			for (int j = 1; j <= rowCount; j++) {
				cell = sheet.getRow(j).getCell(0);
				if (cell.toString().equalsIgnoreCase(scenarioName)) {
					reqRowNum = j;
					break;
				}
			}

			for (int i = 0; i <= colCount; i++) {
				cell = sheet.getRow(0).getCell(i);
				String key = "dummy";
				String val = "";
				try {
					key = cell.toString().trim();
					cell = sheet.getRow(reqRowNum).getCell(i);

					try {
						// sheet.getRow(reqRowNum).getCell(reqColNum).setCellType(Cell.CELL_TYPE_STRING);
						DataFormatter fmt = new DataFormatter();
						val = fmt.formatCellValue(cell);
						// val = cell.toString().trim();
					} catch (Exception e) {
						val = "";
					}
					// Logger.info("ColName: " + key + " Value: " + val);
				} catch (Exception e) {
					break;
				}

				valsMap.put(key, val);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return valsMap;
	}

	public static String returndatetime() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMyyhhmmss");
		String strDate = formatter.format(date);
		return strDate;
	}

	
	public static boolean writeValueToExcel(String filePath, String sheetName, String scenarioName, String columnName,
			String valueToEnter) {
		File file;
		FileInputStream fs;
		XSSFWorkbook workBook;
		XSSFSheet sheet;
		XSSFCell cell;

		try {
			int reqColNum = 0;
			int reqRowNum = 0;
			file = new File(filePath);
			fs = new FileInputStream(file);
			workBook = new XSSFWorkbook(fs);
			sheet = workBook.getSheet(sheetName);
			int rowCount = sheet.getLastRowNum();
			int colCount = sheet.getRow(0).getLastCellNum();

			for (int i = 0; i < colCount; i++) {
				cell = sheet.getRow(0).getCell(i);
				if (cell.toString().equalsIgnoreCase(columnName)) {
					reqColNum = i;
					break;
				}
			}
			if (reqColNum != 0) {
				for (int j = 1; j <= rowCount; j++) {
					// for row with with first column
					cell = sheet.getRow(j).getCell(0);
					if (cell.toString().equalsIgnoreCase(scenarioName)) {
						reqRowNum = j;
						// break;
						// sheet.getRow(reqRowNum).getCell(reqColNum).setCellValue(valueToEnter);
						// Checking whether the cell is null if so creating cell to
						// accept string values
						cell = sheet.getRow(reqRowNum).getCell(reqColNum);
						if (cell == null) {
							cell = sheet.getRow(reqRowNum).createCell(reqColNum);
							// cell.setCellType(Cell.CELL_TYPE_STRING);
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(valueToEnter);
						break;
					}

				}

				FileOutputStream fileOut = new FileOutputStream(file);
				workBook.write(fileOut);
				fileOut.close();

				// workBook.close();

				return true;
			} else {
				Logger.error("Please provide valid test data");
				return false;
			}

		} catch (FileNotFoundException e) {
			Logger.error("File not found or being used by other process");
			return false;
		} catch (Exception e) {
			Logger.error("Exception occured");
			return false;
		}
	}

	public static HashMap<String, String> returnValuesFromExcel_HashMap_secondcolumn(String path, String sheetName,
			String scenarioName) {
		String fPath = path;
		Logger.info("File Picked from: " + fPath);
		File file;
		FileInputStream fs;
		XSSFWorkbook workBook;
		XSSFSheet sheet;
		XSSFCell cell;
		HashMap<String, String> valsMap = new HashMap<String, String>();

		try {
			int reqColNum = 0;
			int reqRowNum = 0;
			file = new File(fPath);
			fs = new FileInputStream(file);
			workBook = new XSSFWorkbook(fs);
			sheet = workBook.getSheet(sheetName);
			int rowCount = sheet.getLastRowNum();
			int colCount = sheet.getRow(0).getLastCellNum();
			for (int j = 1; j <= rowCount; j++) {
				cell = sheet.getRow(j).getCell(1);
				if (cell.toString().equalsIgnoreCase(scenarioName)) {
					reqRowNum = j;
					break;
				}
			}

			for (int i = 0; i <= colCount; i++) {
				cell = sheet.getRow(0).getCell(i);
				String key = "dummy";
				String val = "";
				try {
					key = cell.toString().trim();
					cell = sheet.getRow(reqRowNum).getCell(i);

					try {
						// sheet.getRow(reqRowNum).getCell(reqColNum).setCellType(Cell.CELL_TYPE_STRING);
						DataFormatter fmt = new DataFormatter();
						val = fmt.formatCellValue(cell);
						// val = cell.toString().trim();
					} catch (Exception e) {
						val = "";
					}
					// Logger.info("ColName: " + key + " Value: " + val);
				} catch (Exception e) {
					break;
				}

				valsMap.put(key, val);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return valsMap;
	}

	
	
	@Owner(value = "Madhuri Mishra")
	public String getAttributeValue(WebElement ele, String Attribute) {
		WebElement e = wait.until(ExpectedConditions.visibilityOf(ele));
		return e.getAttribute(Attribute);
	}

	@Owner(value = "Madhuri Mishra")
	public String getTextValue(WebElement ele) {
		WebElement e = wait.until(ExpectedConditions.visibilityOf(ele));
		return e.getText();
	}

	@Owner(value = "Madhuri Mishra")
	public String getTextValue(By Loc) {
		WebElement e = wait.until(ExpectedConditions.presenceOfElementLocated(Loc));
		return e.getText();
	}

	@Owner(value = "Madhuri Mishra")
	public String getAttributeValue(By Loc, String Attribute) {
		WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(Loc));
		return e.getAttribute(Attribute);
	}

	
	
	public static boolean waitUntilElementisPresent(WebDriver driver, WebElement element, int TimeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, TimeOut);
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (TimeoutException e) {
			Logger.error("Element '" + element + "' did not appear even after waiting for " + TimeOut + " seconds");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
		}

	}

	public static boolean waitForElement(WebDriver driver, By elexPath, int timeOut) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elexPath));
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
		}

	}

	// Common MEthods which accepts driver
	@Owner(value = "Madhuri Mishra")
	public void navigateTo(WebDriver driverN, String url) {
		driverN.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driverN.manage().window().maximize();
		driverN.get(url);
		driverN.manage().window().maximize();
		driverN.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),
				TimeUnit.SECONDS);

	}

	@Owner(value = "Madhuri Mishra")
	public void closeBrowser(WebDriver driverN) {
		if (driverN != null) {
			driverN.close();
			driverN.quit();
		}
	}

	
	public static boolean waitForalert(WebDriver driver, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
		}

	}

	public static void selectDropdownByText(WebDriver driver, By locator, String text) throws Exception {
		if (isElementPresent(driver, locator)) {
			Select sele = new Select(driver.findElement(locator));
			try {
				sele.selectByVisibleText(text);
			} catch (NoSuchElementException e) {
				selectDropdownByPartialText(driver, locator, text);
			}
			Thread.sleep(1000);
		}
	}

	public static void selectDropdownByPartialText(WebDriver driver, By locator, String text) throws Exception {
		if (isElementPresent(driver, locator)) {
			Select sele = new Select(driver.findElement(locator));
			List<WebElement> options = sele.getOptions();

			for (WebElement option : options) {
				if (option.getText().contains(text)) {
					sele.selectByVisibleText(option.getText());
					break;
				}
			}
		}
	}

	public static boolean waitUntilElementisAbsent(WebDriver driver, WebElement element, int TimeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, TimeOut);
			wait.until(ExpectedConditions.invisibilityOf(element));
			return true;
		} catch (TimeoutException e) {
			Logger.error("Element did not disappear even after waiting for " + TimeOut + " seconds");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			WebDriverWait wait = new WebDriverWait(driver, 10);
		}

	}

	public static boolean waitUntilElementisAbsent(WebDriver driver, By Locator, int TimeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, TimeOut);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(Locator));
			return true;
		} catch (TimeoutException e) {
			Logger.error("Element did not disappear even after waiting for " + TimeOut + " seconds");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
		}

	}
	
	
}

	