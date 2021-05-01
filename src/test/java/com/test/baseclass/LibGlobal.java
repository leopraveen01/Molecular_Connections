package com.test.baseclass;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LibGlobal {
	public static WebDriver driver;
	String gettext;
	Actions ac;
	Robot robot;
	String value;

	public static void getDriver() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	public static void launchUrl(String url) {
		driver.get(url);
	}

	public void enterText(WebElement element, String value) {
		element.sendKeys(value);
	}

	public void buttonClick(WebElement element) {
		element.click();
	}

	public void getTitle() {
		driver.getTitle();
	}

	public void getText(WebElement element) {
		gettext = element.getText();
	}

	public void getAttribute(WebElement element) {
		element.getAttribute("value");
	}

	public void actionMoveCursor(WebElement element) {
		ac = new Actions(driver);
		ac.moveToElement(element).perform();
	}

	public void actionClick(WebElement elements) {
		ac.click(elements).perform();
	}

	public void actionRightClick(WebElement elements) {
		ac.contextClick(elements).perform();
	}

	public void actionDoubleClick(WebElement elements) {
		ac.doubleClick(elements).perform();
	}

	public void actionDragAndDrop(WebElement Source, WebElement Destination) {
		ac.dragAndDrop(Source, Destination);
	}

	public void KeyboardPress(int keycode) throws AWTException {
		robot = new Robot();
		robot.keyPress(keycode);
	}

	public void KeyboardRelease(int keycode) {
		robot.keyRelease(keycode);
	}

	public void javaScriptExecutor(String arg, WebElement elements) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(arg, elements);
	}

	public void acceptAlert() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public void declineAlert() {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}

	public void promtAlert(String arg) {
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(arg);
		alert.accept();
	}

	public void switchTwoWindowHandling() {
		String parWin = driver.getWindowHandle();
		Set<String> allWondows = driver.getWindowHandles();
		for (String string : allWondows) {
			if (!(string.equals(parWin))) {
				driver.switchTo().window(string);
			}
		}
	}

	public void switchMultiWindowHandling(int index) {
		Set<String> allWindows = driver.getWindowHandles();
		List<String> li = new LinkedList<String>();
		li.addAll(allWindows);
		driver.switchTo().window(li.get(index));
	}

	
	public void dropDownSingleselection(WebElement element, String arg) {
		Select se = new Select(element);
		se.selectByVisibleText(arg);
	}

	public String getExcelData(String pathName, String sheet, int row, int cell) throws IOException {
		File file = new File(pathName);
		FileInputStream stream = new FileInputStream(file);
		Workbook wb = new XSSFWorkbook(stream);
		Sheet sheet1 = wb.getSheet(sheet);
		Row row1 = sheet1.getRow(row);
		Cell cell2 = row1.getCell(cell);
		int cellType = cell2.getCellType();
		if (cellType == 1) {
			value = cell2.getStringCellValue();
		} else if (DateUtil.isCellDateFormatted(cell2)) {
			Date dateCellValue = cell2.getDateCellValue();
			SimpleDateFormat sd = new SimpleDateFormat();
			value = sd.format(dateCellValue);
		} else {
			double numericCellValue = cell2.getNumericCellValue();
			long l = (long) numericCellValue;
			value = String.valueOf(l);
		}
		return value;
	}

	public void screenShot(String Data) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcfile = ts.getScreenshotAs(OutputType.FILE);
		System.out.println(srcfile);
		File desfile = new File(System.getProperty("user.dir") + "\\target\\" + Data + ".png");
		FileUtils.copyFile(srcfile, desfile);
	}

	public void WriteExcel(String sheet, int rownum, int colnum,String attribute) throws IOException {
		File file = new File("C:\\Users\\leo\\eclipse-workspace\\MoleculerConnections\\Excel\\DiseaseReport.xlsx");
		FileInputStream stream = new FileInputStream(file);
		Workbook wb = new XSSFWorkbook(stream);
		Sheet sheetnew = wb.getSheet(sheet);
		Row row = sheetnew.createRow(rownum);
		for (int i = 0; i < sheetnew.getPhysicalNumberOfRows(); i++) {
			Cell cell = row.createCell(colnum);
			cell.setCellValue(attribute);
		}

		FileOutputStream filesave = new FileOutputStream(file);
		wb.write(filesave);
	}

	public String getAttribute(WebElement element, String value) {
		String attribute = element.getAttribute(value);

		return attribute;
	}

}
