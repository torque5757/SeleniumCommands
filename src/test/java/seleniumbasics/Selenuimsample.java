package seleniumbasics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Selenuimsample {

	public static void main(String[] args) {
		WebDriver driver;
		ChromeOptions ops=new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
		 driver=new ChromeDriver(ops);
		//driver = new FirefoxDriver();
		 driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
		driver.get("https://selenium.obsqurazone.com/simple-form-demo.php");
	WebElement entermessagefield =	driver.findElement(By.id("single-input-field"));
	entermessagefield.sendKeys("Test");
	//String url =driver.getCurrentUrl();
//	System.out.println(url);
String pagesource= driver.getPageSource();
	System.out.println(pagesource);
String title =driver.getTitle();
	System.out.println(title);
	

	driver.close();
		
		//*[@id="single-input-field"]
		//#single-input-field
		

	}

}
