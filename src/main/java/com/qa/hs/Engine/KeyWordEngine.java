package com.qa.hs.Engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.hs.Base.BaseClass;

public class KeyWordEngine {

	public WebDriver driver;
	public Properties prop;
	public BaseClass base;
	public WebElement element;

	public static Workbook workbook;
	public static Sheet sheet;

	public final String SCENARIO_PATH = "E:\\SeleniumPract\\ExcelData\\hubSpotTest.xlsx";

	public void startExecution(String sheetName) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(SCENARIO_PATH);
		} catch (FileNotFoundException e) {
			System.out.println("File not Found");
			// e.printStackTrace();
		}

		try {
			workbook = WorkbookFactory.create(fis);
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = workbook.getSheet(sheetName);
		int lastRow=sheet.getLastRowNum();
		int k = 0;
		for (int i = 0; i < lastRow; i++) {

			try {
				String locatorName = sheet.getRow(i + 1).getCell(k + 1).toString().trim();
				String locatorValue = sheet.getRow(i + 1).getCell(k + 2).toString().trim();
				String action = sheet.getRow(i + 1).getCell(k + 3).toString().trim();
				String value = sheet.getRow(i + 1).getCell(k + 4).toString().trim();

				switch (action) {
				case "open browser":
					base = new BaseClass();
					prop = base.init_properties();
					if (value.isEmpty() || value.equals("NA")) {
						driver = base.init_Browser_Driver(prop.getProperty("browser"));
					} else {
						driver = base.init_Browser_Driver(value);
					}
					break;

				case "enter url":
					if (value.isEmpty() || value.equals("NA")) {
						driver.get(prop.getProperty("url"));
					} else {
						driver.get(value);
					}
					break;

				case "quit":
					driver.quit();
					break;

				default:
					break;
				}

				switch (locatorName) {
				case ("id"):
					element = driver.findElement(By.id(locatorValue));
					if (action.equalsIgnoreCase("sendkeys")) {
						element.clear();
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("click")) {
						element.click();
					} else if (action.equalsIgnoreCase("isDisplayed")) {
						element.isDisplayed();
					} else if (action.equalsIgnoreCase("getText")) {
						String expectedText = sheet.getRow(i + 1).getCell(k).toString().trim();
						String elementText = element.getText();
						System.out.println(expectedText + ": " + elementText);
					}
					locatorName = null;
					break;

				case ("xpath"):
					element = driver.findElement(By.xpath(locatorValue));
					if (action.equalsIgnoreCase("sendkeys")) {
						element.clear();
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("click")) {
						element.click();
					} else if (action.equalsIgnoreCase("isDisplayed")) {
						element.isDisplayed();
					} else if (action.equalsIgnoreCase("getText")) {
						String expectedText = sheet.getRow(i + 1).getCell(k).toString().trim();
						String elementText = element.getText();
						System.out.println(expectedText + ": " + elementText);
					}
					locatorName = null;
					break;

				case ("className"):
					element = driver.findElement(By.className(locatorValue));

					if (action.equalsIgnoreCase("sendkeys")) {
						element.clear();
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("click")) {
						element.click();
					} else if (action.equalsIgnoreCase("isDisplayed")) {
						element.isDisplayed();
					} else if (action.equalsIgnoreCase("getText")) {
						String expectedText = sheet.getRow(i + 1).getCell(k).toString();
						String elementText = element.getText();
						System.out.println(expectedText + ": " + elementText);
					}
					locatorName = null;
					break;

				default:
					break;
				}

			} catch (Exception e) {

			}
		}

	}
}
