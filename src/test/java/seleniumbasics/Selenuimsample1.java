package seleniumbasics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

public class Selenuimsample1 {
	public static void main(String[] args) {
		WebDriver driver;
		ChromeOptions ops=new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
		 driver=new ChromeDriver(ops);
		 driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			
		driver.get("https://selenium.obsqurazone.com/simple-form-demo.php");
		String title =driver.getTitle();
    	System.out.println(title);
		WebElement entermessagefield =	driver.findElement(By.id("single-input-field"));
		entermessagefield.sendKeys("Hi Hello");
        WebElement showmessage= driver.findElement(By.id("button-one"));
        showmessage.click();
        WebElement entervaluea = driver.findElement(By.id("value-a"));
        entervaluea.sendKeys("3");
        WebElement entervalueb = driver.findElement(By.id("value-b"));
        entervalueb.sendKeys("4");
        WebElement gettotal = driver.findElement(By.id("button-two"));
        gettotal.click();
        
        WebElement totaltext = driver.findElement(By.id("message-two"));
  String message2=      totaltext.getText();
        
        System.out.println(message2);
       
        
      //  String pagesource= driver.getPageSource();
    	//System.out.println(pagesource);
    
    	

    	driver.close();
}
}
