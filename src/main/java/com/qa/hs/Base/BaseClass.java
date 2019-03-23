package com.qa.hs.Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseClass {
	
	WebDriver driver;
	Properties prop;
	
	public WebDriver init_Browser_Driver(String browserName){
		if(browserName.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\ASHOK\\workspace\\"
					+ "KeyWordDrivenFramework\\Browser_Drivers\\chromedriver.exe");
			if(prop.getProperty("headless").equalsIgnoreCase("yes")){
				ChromeOptions options=new ChromeOptions();
				options.addArguments("--headless");
				driver=new ChromeDriver(options);
			}else{
				driver=new ChromeDriver();
			}
		}
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		
		return driver;
	}
	
	public Properties init_properties(){
		prop=new Properties();
		try {
			FileInputStream fis=new FileInputStream("C:\\Users\\ASHOK\\workspace\\KeyWordDrivenFramework\\"
					+ "src\\main\\java\\com\\qa\\hs\\config\\config.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}

}
