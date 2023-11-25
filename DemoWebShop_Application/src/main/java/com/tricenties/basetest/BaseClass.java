package com.tricenties.basetest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.tricenties.fileutility.ExcelUtility;
import com.tricenties.fileutility.FileUtility;
import com.tricenties.javautility.JavaUtility;
import com.tricenties.objectrepository.HomePage;
import com.tricenties.objectrepository.LoginPage;
import com.tricenties.objectrepository.WelComePage;

public class BaseClass {
	public static WebDriver driver;
	public static WebDriverWait wait;
	public JavaUtility jLib=new JavaUtility();
	public FileUtility fLib=new FileUtility();
	public ExcelUtility eLib=new ExcelUtility();
	public static ExtentReports report;
	public static ExtentTest test ;
	public WelComePage wp;
	public LoginPage lp;
	public HomePage hp;
	
	@BeforeSuite(alwaysRun = true)
	public void configReports() {
		String time = jLib.getSystemTime().toString().replace(":", "-");
		ExtentSparkReporter spark=new ExtentSparkReporter("./HTML_reports/ExtentReport"+time+".html");
		report=new ExtentReports();
		report.attachReporter(spark);
	}
	@BeforeClass(alwaysRun = true)
	public void browserSetup() throws IOException {
		String BROWSER = fLib.getDataFromPropertyFile("browser");
		String URL = fLib.getDataFromPropertyFile("url");
		if (BROWSER.equals("chrome")) {
			driver=new ChromeDriver();
		}else if(BROWSER.equals("firefox")) {
			driver=new FirefoxDriver();
		}else if(BROWSER.equals("edge")) {
			driver=new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		wait=new WebDriverWait(driver, Duration.ofSeconds(20));
		driver.get(URL);
	}
	@BeforeMethod(alwaysRun = true)
	public void login(Method method) throws EncryptedDocumentException, IOException {
		test = report.createTest(method.getName());
		String EMAIL = eLib.getDataFromExcel("Login", 1, 0);
		String PASSWORD = eLib.getDataFromExcel("Login", 1, 1);
		wp=new WelComePage(driver);
		wp.getLoginLink().click();
		lp=new LoginPage(driver);
		lp.getEmailTextField().sendKeys(EMAIL);
		lp.getPasswordTextField().sendKeys(PASSWORD);
		lp.getLoginButton().click();
	}
	@AfterMethod(alwaysRun = true)
	public void logout() {
		hp=new HomePage(driver);
		hp.getLogoutLink().click();
	}
	@AfterClass(alwaysRun = true)
	public void closeBrowser() {
		driver.quit();
	}
	
	@AfterSuite(alwaysRun = true)
	public void reportBackup() {
		report.flush();
	}
}
