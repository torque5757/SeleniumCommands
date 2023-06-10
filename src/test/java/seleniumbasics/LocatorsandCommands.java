package seleniumbasics;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LocatorsandCommands {
	WebDriver driver;

	public void testInitialise(String browser) {
		if (browser.equals("Chrome")) {
			ChromeOptions ops = new ChromeOptions();
			ops.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(ops);
		} else if (browser.equals("Firefox")) {
			driver = new FirefoxDriver();
		} else if (browser.equals("Edge")) {
			driver = new EdgeDriver();
		} else {
			try {
				throw new Exception("Invalid browser");
			} catch (Exception e) {
				throw new RuntimeException(e);

			}
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

	@BeforeMethod
	public void setup() {
		testInitialise("Chrome");

	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File("./Screenshots/" + result.getName() + ".png"));
		}
		// driver.close();
		driver.quit();
	}

	@Test
	public void tc_001_verifyObsquraTitle() {
		driver.get("https://selenium.obsqurazone.com/index.php");
		String actualTitle = driver.getTitle();
		System.out.println("Title :" + actualTitle);
		String expectedTitle = "Obsqura Testing";
		Assert.assertEquals(actualTitle, expectedTitle, "Invalid title found");

	}

	@Test
	public void tc_002_verifySingleInputfieldMessage() {
		driver.get("https://selenium.obsqurazone.com/simple-form-demo.php");
		WebElement entermessagefield = driver.findElement(By.id("single-input-field"));
		entermessagefield.sendKeys("Hi Hello");
		WebElement showmessage = driver.findElement(By.id("button-one"));
		showmessage.click();
		WebElement message = driver.findElement(By.id("message-one"));
		String actMessageText = message.getText();
		String expMessage = "Your Message : Hi Hello";
		Assert.assertEquals(actMessageText, expMessage, "Invalid Message");
	}

	@Test
	public void tc_003_verifyTwoInputfieldMessage() {
		driver.get("https://selenium.obsqurazone.com/simple-form-demo.php");
		WebElement entervaluea = driver.findElement(By.id("value-a"));
		entervaluea.sendKeys("3");
		WebElement entervalueb = driver.findElement(By.id("value-b"));
		entervalueb.sendKeys("4");
		WebElement gettotal = driver.findElement(By.id("button-two"));
		gettotal.click();
		WebElement totaltext = driver.findElement(By.id("message-two"));
		String acttotal = totaltext.getText();
		String exptotal = "Total A + B : 7";
		Assert.assertEquals(acttotal, exptotal, "Invalid Message");

	}

	@Test
	public void tc_004_verifyEmptyFieldValidation() {
		driver.get("https://selenium.obsqurazone.com/form-submit.php");
		String expectedFnameMessage = "Please enter First name.";
		String expectedLnameMessage = "Please enter Last name.";
		String expectedUnameMessage = "Please choose a username.";
		String expectedCityMessage = "Please provide a valid city.";
		String expectedStatemessage = "Please provide a valid state.";
		String expectedZipMessage = "Please provide a valid zip.";
		String expectedAgreeMessage = "You must agree before submitting.";

		WebElement submitbtn = driver.findElement(By.xpath("//button[@type='submit']"));
		submitbtn.click();
		WebElement firstNameValidation = driver
				.findElement(By.xpath("//input[@id='validationCustom01']//following-sibling::div[1]"));
		WebElement lastNameValidation = driver
				.findElement(By.xpath("//input[@id='validationCustom02']//following-sibling::div[1]"));
		WebElement userNameValidation = driver
				.findElement(By.xpath("//input[@id='validationCustomUsername']//following-sibling::div[1]"));
		WebElement cityValidation = driver
				.findElement(By.xpath("//input[@id='validationCustom03']//following-sibling::div[1]"));
		WebElement stateValidation = driver
				.findElement(By.xpath("//input[@id='validationCustom04']//following-sibling::div[1]"));
		WebElement zipValidation = driver
				.findElement(By.xpath("//input[@id='validationCustom05']//following-sibling::div[1]"));
		WebElement agreeValidation = driver
				.findElement(By.xpath("//input[@id='invalidCheck']//following-sibling::div[1]"));
		String actualFnameMessage = firstNameValidation.getText();
		String actualLnameMessage = lastNameValidation.getText();
		String actualUsernameMessage = userNameValidation.getText();
		String actualCityMessage = cityValidation.getText();
		String actualStateMessage = stateValidation.getText();
		String actualZipMessage = zipValidation.getText();
		String actualAgreeMessage = agreeValidation.getText();
		Assert.assertEquals(actualFnameMessage, expectedFnameMessage, "Invalid Message");
		Assert.assertEquals(actualLnameMessage, expectedLnameMessage, "Invalid Message");
		Assert.assertEquals(actualUsernameMessage, expectedUnameMessage, "Invalid Message");
		Assert.assertEquals(actualCityMessage, expectedCityMessage, "Invalid Message");
		Assert.assertEquals(actualStateMessage, expectedStatemessage, "Invalid Message");
		Assert.assertEquals(actualZipMessage, expectedZipMessage, "Invalid Message");
		Assert.assertEquals(actualAgreeMessage, expectedAgreeMessage, "Invalid Message");
	}

	@Test
	public void tc_005_verifyEmptyStateAndZipcodeValidation() {
		driver.get("https://selenium.obsqurazone.com/form-submit.php");
		WebElement submitbtn = driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
		submitbtn.click();
		WebElement firstNameValidation = driver.findElement(By.xpath("//input[@id='validationCustom01']"));
		firstNameValidation.sendKeys("Rakesh");
		// String expectedFnameMessage = "Rakesh";
		// String actualFnameMessage = firstNameValidation.getText();
		// Assert.assertEquals(actualFnameMessage, expectedFnameMessage, "Invalid
		// Message");
		WebElement lastNameValidation = driver.findElement(By.xpath("//input[@id='validationCustom02']"));
		lastNameValidation.sendKeys("Mohan");
		// String expectedLnameMessage = "Mohan";
		// String actualLnameMessage = lastNameValidation.getText();
		// Assert.assertEquals(actualLnameMessage, expectedLnameMessage, "Invalid
		// Message");
		WebElement userNameValidation = driver.findElement(By.xpath("//input[@id='validationCustomUsername']"));
		userNameValidation.sendKeys("rm");
		// String expectedUnameMessage = "rm";
		// String actualUsernameMessage = userNameValidation.getText();
		// Assert.assertEquals(actualUsernameMessage, expectedUnameMessage, "Invalid
		// Message");
		WebElement cityValidation = driver.findElement(By.xpath("//input[@id='validationCustom03']"));
		cityValidation.sendKeys("Trivandrum");
		// String expectedCityMessage = "Trivandrum";
		// String actualCityMessage = cityValidation.getText();
		// Assert.assertEquals(actualCityMessage, expectedCityMessage, "Invalid
		// Message");
		WebElement stateValidation = driver
				.findElement(By.xpath("//input[@id='validationCustom04']//following-sibling::div[1]"));
		String expectedStatemessage = "Please provide a valid state.";
		String actualStateMessage = stateValidation.getText();
		Assert.assertEquals(actualStateMessage, expectedStatemessage, "Invalid Message");
		WebElement zipValidation = driver
				.findElement(By.xpath("//input[@id='validationCustom05']//following-sibling::div[1]"));
		String expectedZipMessage = "Please provide a valid zip.";
		String actualZipMessage = zipValidation.getText();
		Assert.assertEquals(actualZipMessage, expectedZipMessage, "Invalid Message");

	}

	@Test
	public void tc_006_verifySubmitSuccessfulValidation() {
		driver.get("https://selenium.obsqurazone.com/form-submit.php");
		WebElement firstNameValidation = driver.findElement(By.xpath("//input[@id='validationCustom01']"));
		firstNameValidation.sendKeys("Rakesh");
		// String expectedFnameMessage = "Rakesh";
		// String actualFnameMessage = firstNameValidation.getText();
		// Assert.assertEquals(actualFnameMessage, expectedFnameMessage, "Invalid
		// Message");
		WebElement lastNameValidation = driver.findElement(By.xpath("//input[@id='validationCustom02']"));
		lastNameValidation.sendKeys("Mohan");
		// String expectedLnameMessage = "Mohan";
		// String actualLnameMessage = lastNameValidation.getText();
		// Assert.assertEquals(actualLnameMessage, expectedLnameMessage, "Invalid
		// Message");
		WebElement userNameValidation = driver.findElement(By.xpath("//input[@id='validationCustomUsername']"));
		userNameValidation.sendKeys("rm");
		// String expectedUnameMessage = "rm";
		// String actualUsernameMessage = userNameValidation.getText();
		// Assert.assertEquals(actualUsernameMessage, expectedUnameMessage, "Invalid
		// Message");
		WebElement cityValidation = driver.findElement(By.xpath("//input[@id='validationCustom03']"));
		cityValidation.sendKeys("Trivandrum");
		// String expectedCityMessage = "Trivandrum";
		// String actualCityMessage = cityValidation.getText();
		// Assert.assertEquals(actualCityMessage, expectedCityMessage, "Invalid
		// Message");
		WebElement stateValidation = driver.findElement(By.xpath("//input[@id='validationCustom04']"));
		stateValidation.sendKeys("Kerala");
		WebElement zipValidation = driver.findElement(By.xpath("//input[@id='validationCustom05']"));
		zipValidation.sendKeys("695015");
		WebElement agreeBoxValidation = driver.findElement(By.xpath("//input[@type='checkbox']"));
		agreeBoxValidation.click();
		WebElement submitbtn = driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
		submitbtn.click();
		WebElement submitMessage = driver.findElement(By.xpath("//div[@id='message-one']"));
		String actMsg = submitMessage.getText();
		String expMsg = "Form has been submitted successfully!";
		Assert.assertEquals(actMsg, expMsg, "Invalid Message");
	}

	@Test
	public void tc_007_verifyNewsletterSubscription() {
		driver.get("https://demowebshop.tricentis.com");
		WebElement newsletterEmail = driver.findElement(By.cssSelector("input#newsletter-email"));
		newsletterEmail.sendKeys("rakesh@gmail.com");
		WebElement subscibeButton = driver.findElement(By.cssSelector("input#newsletter-subscribe-button"));
		subscibeButton.click();

	}

	@Test
	public void tc_007_verifyPhpTravelsFormValidation() throws InterruptedException {
		driver.get("https://phptravels.com/demo/");
		WebElement firstNameValidation = driver.findElement(By.cssSelector("input.first_name.input.mb1"));
		firstNameValidation.sendKeys("Rakesh");
		WebElement lastNameValidation = driver.findElement(By.cssSelector("input.last_name.input.mb1"));
		lastNameValidation.sendKeys("Mohan");
		WebElement businessNameValidation = driver.findElement(By.cssSelector("input.business_name.input.mb1"));
		businessNameValidation.sendKeys("Urbane");
		WebElement emailValidation = driver.findElement(By.cssSelector("input.email.input.mb1"));
		emailValidation.sendKeys("rakesh@gmail.com");
		WebElement number1 = driver.findElement(By.cssSelector("span#numb1"));
		WebElement number2 = driver.findElement(By.cssSelector("span#numb2"));
		String actnum1 = number1.getText();
		String actnum2 = number2.getText();
		int num1 = Integer.parseInt(actnum1);
		int num2 = Integer.parseInt(actnum2);
		int sum = num1 + num2;
		String sumText = String.valueOf(sum);
		WebElement result = driver.findElement(By.cssSelector("input#number"));
		result.sendKeys(sumText);
		WebElement submitBtn = driver.findElement(By.cssSelector("button#demo"));
		submitBtn.click();
		Thread.sleep(5000);
		WebElement completedBox = driver.findElement(By.cssSelector("div.completed"));
		boolean status = completedBox.isDisplayed();
		System.out.println(status);
	}

	@Test
	public void tc_008_verifyQuitandClose() {
		driver.get("https://demo.guru99.com/popup.php");
		WebElement clickHereBtn = driver.findElement(By.xpath("//a[text()='Click Here']"));
		clickHereBtn.click();
	}

	@Test
	public void tc_009_verifyNavigateTo() {
		// driver.get("https://demowebshop.tricentis.com");
		driver.navigate().to("https://demowebshop.tricentis.com");

	}

	@Test
	public void tc_010_verifyRefresh() {
		driver.get("https://demowebshop.tricentis.com");
		WebElement newsletterField = driver.findElement(By.xpath("//input[@id='newsletter-email']"));
		newsletterField.sendKeys("Test@gmail.com");
		driver.navigate().refresh();
	}

	@Test
	public void tc_011_verifyForwardNBackwardNavigation() throws InterruptedException {
		driver.get("https://demowebshop.tricentis.com");
		WebElement loginMenu = driver.findElement(By.xpath("//a[@class='ico-login']"));
		loginMenu.click();
		Thread.sleep(2000);
		driver.navigate().back();
		Thread.sleep(2000);
		driver.navigate().forward();
	}

	@Test
	public void tc_012_verifyWebElementCommands() throws InterruptedException {
		driver.get("https://selenium.obsqurazone.com/ajax-form-submit.php");
		WebElement subjectField = driver.findElement(By.xpath("//input[@id='subject']"));
		subjectField.sendKeys("selenium");
		WebElement descriptionField = driver.findElement(By.xpath("//textarea[@id='description']"));
		descriptionField.sendKeys("automation testing");
		subjectField.clear(); // to clear an input field
		String classAttributeValue = subjectField.getAttribute("class");
		System.out.println(classAttributeValue);
		String tagnameValue = subjectField.getTagName();
		System.out.println(tagnameValue);
		subjectField.sendKeys("selenium automation");
		WebElement submitBtn = driver.findElement(By.xpath("//input[@class='btn btn-primary']"));
		Thread.sleep(2000);
		submitBtn.click();
		Thread.sleep(2000);
		WebElement formSubmittedSuccessfully = driver.findElement(By.xpath("//div[@id='message-one']"));
		String actMsg = formSubmittedSuccessfully.getText();
		String expMsg = "Form has been submitted successfully!";
		Assert.assertEquals(actMsg, expMsg, "Invalid Message");
	}

	@Test
	public void tc_013_verifyIsDisplayed() {
		driver.get("https://selenium.obsqurazone.com/ajax-form-submit.php");
		WebElement subjectField = driver.findElement(By.xpath("//input[@id='subject']"));
		subjectField.sendKeys("selenium");
		boolean status = subjectField.isDisplayed();
		System.out.println(status);
		Assert.assertTrue(status, "subject field is not displayed");
	}

	@Test

	public void tc_014_verifyIsSelected()

	{
		driver.get("https://selenium.obsqurazone.com/check-box-demo.php");
		WebElement singleClick = driver.findElement(By.xpath("//input[@id='gridCheck']"));
		boolean status = singleClick.isSelected();
		System.out.println(status);
		Assert.assertFalse(status, "checkbox is selected");
		singleClick.click();
		boolean statusafterClick = singleClick.isSelected();
		System.out.println(statusafterClick);
		Assert.assertTrue(statusafterClick, "checkbox is not selected");

	}

	@Test
	public void tc_014_verifyIsEnabled() {
		driver.get("https://selenium.obsqurazone.com/ajax-form-submit.php");
		WebElement submitBtn = driver.findElement(By.xpath("//input[@class='btn btn-primary']"));
		boolean status = submitBtn.isEnabled();
		System.out.println(status);
		Assert.assertTrue(status, "submit button is not selected");
		Point point = submitBtn.getLocation();
		System.out.println(point.x + "," + point.y);
		Dimension dim = submitBtn.getSize();
		System.out.println(dim.height + "," + dim.width);
		String bgColour = submitBtn.getCssValue("background-color");
		System.out.println(bgColour);
		WebElement element = driver.findElement(By.tagName("input"));
		System.out.println(element);
		List<WebElement> elements = driver.findElements(By.tagName("Input"));
		System.out.println(elements);
	}

	@Test
	public void tc_015_verifyThemessageDisplayedInNewTab() {
		driver.get("https://demoqa.com/browser-windows");
		WebElement newTab = driver.findElement(By.xpath("//button[@id='tabButton']"));
		boolean status = newTab.isEnabled();
		System.out.println(status);
		newTab.click();
		driver.navigate().to("https://demoqa.com/sample");
		WebElement samplePage = driver.findElement(By.xpath("//h1[@id='sampleHeading']"));
		String actMsg = samplePage.getText();
		String expMsg = "This is a sample page";
		Assert.assertEquals(actMsg, expMsg, "Invalid Message");

	}

	@Test
	public void tc_016_verifyTheMessageDisplayedInNewWindow() {
		driver.get("https://demoqa.com/browser-windows");
		String parentWindow = driver.getWindowHandle();
		System.out.println("parent windowid =" + parentWindow);
		WebElement newWindow = driver.findElement(By.xpath("//button[@id='windowButton']"));
		newWindow.click();
		Set<String> handles = driver.getWindowHandles();
		System.out.println(handles);
		Iterator<String> handleids = handles.iterator();
		while (handleids.hasNext()) {
			String childWindow = handleids.next();
			if (!childWindow.equals(parentWindow)) {
				driver.switchTo().window(childWindow);
				WebElement samplePage = driver.findElement(By.xpath("//h1[@id='sampleHeading']"));
				String actMsg = samplePage.getText();
				String expMsg = "This is a sample page";
				Assert.assertEquals(actMsg, expMsg, "Invalid Message");
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
	}

	@Test
	public void tc_017_verifySimpleAlert() {
		driver.get("https://selenium.obsqurazone.com/javascript-alert.php");
		WebElement clickMe = driver.findElement(By.xpath("//button[@class='btn btn-success']"));
		clickMe.click();
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		System.out.println(alertText);
		alert.accept();

	}

	@Test
	public void tc_018_verifyConfirmAlert() {
		driver.get("https://selenium.obsqurazone.com/javascript-alert.php");
		WebElement clickMe = driver.findElement(By.xpath("//button[@class='btn btn-warning']"));
		clickMe.click();
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		System.out.println(alertText);
		alert.dismiss();
	}

	@Test
	public void tc_019_verifyPromptAlert() {
		driver.get("https://selenium.obsqurazone.com/javascript-alert.php");
		WebElement clickMe = driver.findElement(By.xpath("//button[@class='btn btn-danger']"));
		clickMe.click();
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		System.out.println(alertText);
		alert.sendKeys("Rakesh");
		alert.accept();
	}

	@Test
	public void tc_020_verifyTextInAFrame() {
		driver.get("https://demoqa.com/frames");
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		int numberOfFrames = frames.size();
		System.out.println(numberOfFrames);
		// driver.switchTo().frame(3); //using index
		// driver.switchTo().frame("frame1");//using name or id
		WebElement frameOne = driver.findElement(By.id("frame1"));// using webelement
		driver.switchTo().frame(frameOne);
		WebElement sampleHeading = driver.findElement(By.id("sampleHeading"));
		String headingText = sampleHeading.getText();
		System.out.println(headingText);
		// driver.switchTo().parentFrame();
		driver.switchTo().defaultContent();

	}

	@Test
	public void tc_021_verifyRightClick() {
		driver.get("https://demo.guru99.com/test/simple_context_menu.html");
		WebElement rightClickMe = driver.findElement(By.xpath("//span[@class='context-menu-one btn btn-neutral']"));
		Actions action = new Actions(driver);
		action.contextClick(rightClickMe).build().perform();

	}

	@Test
	public void tc_022_verifyDoubleClick() {
		driver.get("https://demo.guru99.com/test/simple_context_menu.html");
		WebElement doubleClick = driver.findElement(By.xpath("//button[text()='Double-Click Me To See Alert']"));
		Actions actions = new Actions(driver);
		actions.doubleClick(doubleClick).build().perform();
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	@Test
	public void tc_023_verifyMouseHover() {
		driver.get("https://demoqa.com/menu/");
		WebElement MainItem1 = driver.findElement(By.xpath("//a[text()='Main Item 1']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(MainItem1).build().perform();
	}

	@Test
	public void tc_024_verifyDragAndDrop() {
		driver.get("https://demoqa.com/droppable");
		WebElement dragMe = driver.findElement(By.id("draggable"));
		WebElement dropHere = driver.findElement(By.id("droppable"));
		Actions actions = new Actions(driver);
		actions.dragAndDrop(dragMe, dropHere).build().perform();
	}

	@Test
	public void tc_025_verifyClickAndHold() {
		driver.get("https://demoqa.com/resizable");
		// WebElement dragBox = driver.findElement(By.xpath("//div[text()='Resizable
		// box, starting at 200x200. Min size is 150x150, max is 500x300.']"));
		WebElement dragBox = driver
				.findElement(By.xpath("//span[@class='react-resizable-handle react-resizable-handle-se']"));
		Actions action = new Actions(driver);
		action.clickAndHold(dragBox).build().perform();
		action.dragAndDropBy(dragBox, 100, 100).build().perform();

	}

	@Test
	public void tc_026_verifyDropDown() {
		driver.get("https://demo.guru99.com/test/newtours/register.php");
		WebElement country = driver.findElement(By.xpath("//select[@name='country']"));
		Select select = new Select(country);
		// select.selectByVisibleText("INDIA");
		// select.selectByIndex(5);
		select.selectByValue("GUINEA");

	}

	@Test
	public void tc_027_verifyMultipleDropDown() {
		driver.get("https://www.softwaretestingmaterial.com/sample-webpage-to-automate/");
		WebElement multipleSelectValues = driver.findElement(By.xpath("//select[@class='spTextField']"));
		Select select = new Select(multipleSelectValues);
		boolean status = select.isMultiple();
		System.out.println(status);
		select.selectByVisibleText("Performance Testing");
		select.selectByValue("msmanual");
	}

	@Test
	public void tc_028_verifyFileUploadInSelenium() {
		driver.get("https://demo.guru99.com/test/upload/");
		WebElement chooseFile = driver.findElement(By.id("uploadfile_0"));
		chooseFile.sendKeys("D:\\Selenium\\test.txt");
		WebElement terms = driver.findElement(By.id("terms"));
		terms.click();
		WebElement submitBtn = driver.findElement(By.id("submitbutton"));
		submitBtn.click();
	}

	@Test
	public void tc_029_verifyClickAndSendkeysusingJavascriptexecutor() {
		driver.get("https://demowebshop.tricentis.com/");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementById('newsletter-email').value='test@gmail.com'");
		js.executeScript("document.getElementById('newsletter-subscribe-button').click()");
	}

	@Test
	public void tc_030_verifyScrolldownOfAWebelement() {
		driver.get("https://demo.guru99.com/test/guru99home/");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
	}

	@Test
	public void tc_031_verifyScrollIntoViewOfAWebelement() {
		driver.get("https://demo.guru99.com/test/guru99home/");
		WebElement linuxElement = driver.findElement(By.linkText("Linux"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", linuxElement);
	}

	@Test
	public void tc_032_verifyHorizontalScroll() {
		driver.get("https://demo.guru99.com/test/guru99home/");
		WebElement VBScriptElement = driver.findElement(By.linkText("VBScript"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", VBScriptElement);
	}

	@Test
	public void tc_033_verifyTable() throws IOException {
		driver.get("https://www.w3schools.com/html/html_tables.asp");
		List<WebElement> rowElements = driver.findElements(By.xpath("//table[@id='customers']//tbody//tr"));
		List<WebElement> columnElements = driver.findElements(By.xpath("//table[@id='customers']//tbody//tr//td"));
		List<ArrayList<String>> actGridData = TableUtility.get_Dynamic_TwoDimension_TablElemnts(rowElements,
				columnElements);
		List<ArrayList<String>> expGridData = ExcelUtility.excelDataReader("\\src\\test\\resources\\TestData.xlsx",
				"Table");
		System.out.println(actGridData);
		System.out.println(expGridData);
		Assert.assertEquals(actGridData, expGridData, "Invalid data Found");
	}

	@Test
	public void tc_034_verifyFileUploadUsingRobotClass() throws AWTException, InterruptedException 
	{
		driver.get("https://www.foundit.in/seeker/registration");
		StringSelection s = new StringSelection("D:\\Selenium\\test.docx");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
		WebElement uploadFile = driver.findElement(By.xpath("//div[@class='uploadResume']"));
		uploadFile.click();
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_V);
		Thread.sleep(2000);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
	}
	@Test
	public void tc_035_verifyTableSort() throws IOException, InterruptedException
	{
	driver.get("https://selenium.obsqurazone.com/table-sort-search.php");
	WebElement search = driver.findElement(By.xpath("//input[@type='search']"));
	search.sendKeys("caesar");
	Thread.sleep(2000);
	List<WebElement> rowElements = driver.findElements(By.xpath("//table[@id='dtBasicExample']//tr"));
	List<WebElement> columnElements = driver.findElements(By.xpath("//table[@id='dtBasicExample']//tr//td"));
	List<ArrayList<String>> actGridData = TableUtility.get_Dynamic_TwoDimension_ObscuraTablElemnts(rowElements, columnElements);
	List<ArrayList<String>> expGridData = ExcelUtility.excelDataReader("\\src\\test\\resources\\TestData.xlsx","Table2");
	System.out.println(actGridData);
	System.out.println(expGridData);
	Assert.assertEquals(actGridData, expGridData, "Invalid data Found");
	}
	@Test
	public void tc_036_verifyWaitsInSelenium()
	{
		driver.get("https://demowebshop.tricentis.com/");
		/* pageload wait*/
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		/* Implicit wait */
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement newsLetter= driver.findElement(By.xpath("//input[@id='newsletter-email']")) ;
		newsLetter.sendKeys("test@gmail.com");
		/* Explicit wait */
		WebElement subscribeBtn = driver.findElement(By.xpath("//input[@id='newsletter-subscribe-button']"));
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(subscribeBtn));
		/* Fluent wait */
		FluentWait fwait = new FluentWait<WebDriver>(driver);
		fwait.withTimeout(Duration.ofSeconds(10));
		fwait.pollingEvery(Duration.ofSeconds(1));
		fwait.until(ExpectedConditions.visibilityOf(subscribeBtn));
		subscribeBtn.click();
	}
	@Test
	public void tc_037_verify()
	{
		
		
	}
}
