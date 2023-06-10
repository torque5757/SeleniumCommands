package seleniumbasics;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DemoWebShop {
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
	public void tc_002_verifyLogin()
	{
		driver.get("https://demowebshop.tricentis.com/");
		WebElement login = driver.findElement(By.xpath("//a[text()='Log in']"));
		login.click();
		String email ="urbanetorq@gmail.com";
		WebElement emailField = driver.findElement(By.id("Email"));
		emailField.sendKeys(email);
		WebElement password = driver.findElement(By.id("Password"));
		password.sendKeys("rak1234");
		WebElement submitbtn = driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
		submitbtn.click();
		WebElement emailsubmitted = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String actMsg = emailsubmitted.getText();
		String expMsg =email;
		Assert.assertEquals(actMsg, expMsg, "Invalid Message");
		
	}
	@Test
	public void tc_003_verifyRegistration()
	{
		driver.get("https://demowebshop.tricentis.com/");
	WebElement register=driver.findElement(By.xpath("//a[text()='Register']"));
	register.click();
	List<WebElement> gender = driver.findElements(By.xpath("//input[@name='Gender']"));
	selectGender("M",gender);
	
	WebElement firstName = driver.findElement(By.id("FirstName"));
	firstName.sendKeys("rakesh");
	WebElement lastName = driver.findElement(By.id("LastName"));
	lastName.sendKeys("mohan");
	String email = "rakesh2825@gmail.com";
	WebElement emailField = driver.findElement(By.id("Email"));
	emailField.sendKeys(email);
	WebElement password = driver.findElement(By.id("Password"));
	password.sendKeys("rak1234");
	WebElement confirmPassword = driver.findElement(By.id("ConfirmPassword"));
	confirmPassword.sendKeys("rak1234");
	WebElement registerBtn = driver.findElement(By.id("register-button"));
	registerBtn.click();
	WebElement emailsubmitted = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
	String actMsg = emailsubmitted.getText();
	String expMsg =email;
	Assert.assertEquals(actMsg, expMsg, "Invalid Message");
	}
	public void selectGender(String gen,List<WebElement> gender)
	{
		for(int i=0;i<gender.size();i++)
		{
			String genderValue = gender.get(i).getAttribute("value");
			if(genderValue.equals(gen))
			{
				gender.get(i).click();
			}
		}
	}
	@Test
	public void tc_004_verifyTitleFromExcelSheet() throws IOException
	{
		driver.get("https://demowebshop.tricentis.com/");
		String actualTitle = driver.getTitle();
		String excelPath = "\\src\\test\\resources\\TestData.xlsx";
		String sheetName = "HomePage";
		String expTitle = ExcelUtility.readStringData(excelPath, sheetName, 0, 1);
		Assert.assertEquals(actualTitle, expTitle, "Invalid title");
	}
	@Test
	public void tc_005_verifyregistrationFromExcelSheet() throws IOException
	{
		driver.get("https://demowebshop.tricentis.com/");
		String excelPath = "\\src\\test\\resources\\TestData.xlsx";
		String sheetName = "RegisterPage";
		String expTitle = ExcelUtility.readStringData(excelPath, sheetName, 0, 1);
		WebElement register=driver.findElement(By.xpath("//a[text()='Register']"));
		register.click();
		String actualTitle =driver.getTitle();
		Assert.assertEquals(actualTitle, expTitle, "Invalid title");
		List<WebElement> gender = driver.findElements(By.xpath("//input[@name='Gender']"));
		selectGender(ExcelUtility.readStringData(excelPath, sheetName, 6, 1),gender);
		WebElement firstName = driver.findElement(By.id("FirstName"));
		firstName.sendKeys(ExcelUtility.readStringData(excelPath, sheetName, 1, 1));
		
	    WebElement lastName = driver.findElement(By.id("LastName"));
		lastName.sendKeys(ExcelUtility.readStringData(excelPath, sheetName, 2, 1));	
		WebElement email = driver.findElement(By.id("Email"));
		email.sendKeys(ExcelUtility.readStringData(excelPath, sheetName, 3, 1));
		WebElement password = driver.findElement(By.id("Password"));
		password.sendKeys(ExcelUtility.readStringData(excelPath, sheetName, 4, 1));
		WebElement confirmPassword = driver.findElement(By.id("ConfirmPassword"));
		confirmPassword.sendKeys(ExcelUtility.readStringData(excelPath, sheetName, 5, 1));
		WebElement registerBtn = driver.findElement(By.id("register-button"));
		registerBtn.click();
		WebElement emailsubmitted = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String actMsg = emailsubmitted.getText();
		Assert.assertEquals(actMsg,ExcelUtility.readStringData(excelPath, sheetName, 3, 1), "Invalid Email");
	}
	@Test(dataProvider="InvalidCredentials")
	public void tc_006_verifyLoginWithInvalidData(String email,String psword )
	{
		driver.get("https://demowebshop.tricentis.com/");
		WebElement login = driver.findElement(By.xpath("//a[text()='Log in']"));
		login.click();
		WebElement emailField = driver.findElement(By.id("Email"));
		emailField.sendKeys(email);
		WebElement password = driver.findElement(By.id("Password"));
		password.sendKeys(psword);
		WebElement submitbtn = driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
		submitbtn.click();
		WebElement errorMsg = driver.findElement(By.xpath("//div[@class='validation-summary-errors']//span"));
		String actMsg = errorMsg.getText();
		String expMsg ="Login was unsuccessful. Please correct the errors and try again.";
		Assert.assertEquals(actMsg, expMsg, "Invalid Message");	
	}
	@DataProvider(name= "InvalidCredentials")
	public Object[][] userCredentials()
	{
		Object[][] data = {{"rakesh.221643@gmail.com","rak123"},{"rakesh.2216@gmail.com","rak12345"},{"rakesh.2211643@gmail.com","rak123"}};
		return data;
	}
	@Test
	public void tc_007_verifyRegistrationUsingRandomGenerator()
	{
		driver.get("https://demowebshop.tricentis.com/");
		WebElement register=driver.findElement(By.xpath("//a[text()='Register']"));
		register.click();
		String fName= RandomDataUtility.getfName();
		String lName = RandomDataUtility.getlName();
		String email = RandomDataUtility.getRandomEmail();
		String psword = fName+"@123";
		List<WebElement> gender = driver.findElements(By.xpath("//input[@name='Gender']"));
		selectGender("M",gender);
		WebElement firstName = driver.findElement(By.id("FirstName"));
		firstName.sendKeys(fName);
		WebElement lastName = driver.findElement(By.id("LastName"));
		lastName.sendKeys(lName);
		WebElement emailField = driver.findElement(By.id("Email"));
		emailField.sendKeys(email);
		WebElement password = driver.findElement(By.id("Password"));
		password.sendKeys(psword);
		WebElement confirmPassword = driver.findElement(By.id("ConfirmPassword"));
		confirmPassword.sendKeys(psword);
		WebElement registerBtn = driver.findElement(By.id("register-button"));
		registerBtn.click();
		WebElement emailsubmitted = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String actMsg = emailsubmitted.getText();
		String expMsg =email;
		Assert.assertEquals(actMsg, expMsg, "Invalid Message");
		
	}
	@Test(dataProvider="ValidCredentials")
	public void tc_008_verifyLoginWithValidDatas(String email,String psword )
	{
		driver.get("https://demowebshop.tricentis.com/");
		WebElement login = driver.findElement(By.xpath("//a[text()='Log in']"));
		login.click();
		WebElement emailField = driver.findElement(By.id("Email"));
		emailField.sendKeys(email);
		WebElement password = driver.findElement(By.id("Password"));
		password.sendKeys(psword);
		WebElement submitbtn = driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
		submitbtn.click();
		WebElement emailsubmitted = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String actMsg = emailsubmitted.getText();
		String expMsg =email;
		Assert.assertEquals(actMsg, expMsg, "Invalid Message");
		
		//WebElement msgDisplayed = driver.findElement(By.xpath("//div[@class='topic-html-content-title']//h2"));
		//String actMsg = msgDisplayed.getText();
		//String expMsg ="Welcome to our store";
		//Assert.assertEquals(actMsg, expMsg, "Invalid Message");	
	}
	@DataProvider(name= "ValidCredentials")
	public Object[][] userCredentials1()
	{
		Object[][] data = {{"rakesh5757@gmail.com","rak12345"},{"rakesh9656@gmail.com","rocky123"}};
		return data;
	}
	}

