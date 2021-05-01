package com.test.exe;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.test.baseclass.LibGlobal;

public class GetDiseaseSummery extends LibGlobal {

	@BeforeMethod
	public void tc0() {
		getDriver();
		launchUrl("https://rarediseases.info.nih.gov/diseases/categories");
	}

	@Test
	public void tc1() throws InterruptedException, AWTException, IOException {
		driver.manage().timeouts().implicitlyWait(8000, TimeUnit.MICROSECONDS);
		WebElement catBtnClk = driver.findElement(By.xpath("(//ul[@class='hidden-print']//child::li[@class='selected  ']//child::ul//li//a[@href=\"/diseases/diseases-by-category/38/autoimmune-autoinflammatory-diseases\"])[2]"));
		catBtnClk.click();

		WebElement tableOfDisease = driver.findElement(By.xpath("//div[@class='body-container']"));
		List<WebElement> btnAllDisease = tableOfDisease.findElements(By.xpath("//div[@class='body-container']//child::a"));
		Robot r = new Robot();
		for (int i = 0; i < btnAllDisease.size(); i++) {
			WebElement btnclk = btnAllDisease.get(i);
			actionMoveCursor(btnclk);
			actionRightClick(btnclk);
			r.keyPress(KeyEvent.VK_DOWN);
			r.keyRelease(KeyEvent.VK_DOWN);
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
			switchTwoWindowHandling();
			try {
				WebElement diseaseSummery = driver.findElement(By.id("readSpeaker_Summary"));
				String text = diseaseSummery.getText();
				WriteExcel("Specification", i + 1, 1,  text);
				System.out.println(text);
			} catch (Exception e) {
				System.out.println("summary not found");
			}
			
			switchMultiWindowHandling(0);
		}

	}

	@Test
	public void tc2() throws InterruptedException, AWTException, IOException {
		driver.manage().timeouts().implicitlyWait(4000, TimeUnit.MICROSECONDS);
		WebElement catBtnClk = driver.findElement(By.xpath("(//ul[@class='hidden-print']//child::li[@class='selected  ']//child::ul//li//a[@href=\"/diseases/diseases-by-category/38/autoimmune-autoinflammatory-diseases\"])[2]"));
		catBtnClk.click();
		

		WebElement tableOfDisease = driver.findElement(By.xpath("//div[@class='body-container']"));
		List<WebElement> btnAllDisease = tableOfDisease
				.findElements(By.xpath("//div[@class='body-container']//child::a"));
		Robot r = new Robot();
		for (int i = 0; i < btnAllDisease.size(); i++) {

			WebElement btnclk = btnAllDisease.get(i);
			actionMoveCursor(btnclk);
			actionRightClick(btnclk);
			r.keyPress(KeyEvent.VK_DOWN);
			r.keyRelease(KeyEvent.VK_DOWN);
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
			switchTwoWindowHandling();
			try {
				WebElement subTable = driver.findElement(By.xpath("//div[@class='table-responsive']"));
				try {
					WebElement viewAllBtn = driver
							.findElement(By.xpath("(//button[@class='btn btn-link phenotypeTableToggler'])[1]"));
					viewAllBtn.click();
				} catch (Exception e) {
					System.out.println("view all button not available");
				}
				List<WebElement> medicalterms = subTable.findElements(
						By.xpath("//div[@class='table-responsive']//child::tr//preceding-sibling::td[3]"));
				
				for (int j = 0; j < medicalterms.size(); j++) {

					String text1 = medicalterms.get(j).getText();
					System.out.println(text1);
				
					WriteExcel("Result", i ,i, text1);
				}
				
			} catch (Exception e) {
				System.out.println("table not available");
			}
			switchMultiWindowHandling(0);

		}
	}

}
