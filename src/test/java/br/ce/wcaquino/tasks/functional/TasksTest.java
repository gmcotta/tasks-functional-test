package br.ce.wcaquino.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {
	public WebDriver accessApplication() throws MalformedURLException {
		ChromeOptions cap = new ChromeOptions();
		WebDriver driver = new RemoteWebDriver(new URL(" http://192.168.0.102:4444/wd/hub"), cap);
		driver.navigate().to("http://192.168.0.102:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	@Test
	public void shouldSaveTaskSuccessfully() throws MalformedURLException {
		WebDriver driver = accessApplication();
		
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("20/10/2020");
			driver.findElement(By.id("saveButton")).click();
			
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", message);			
		} finally {
			driver.quit();			
		}
		
	}
	
	@Test
	public void shouldNotCreateTaskWithoutDescription() throws MalformedURLException {
		WebDriver driver = accessApplication();
		
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("dueDate")).sendKeys("20/10/2010");
			driver.findElement(By.id("saveButton")).click();
			
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", message);
			
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void shouldNotCreateTaskWithoutDate() throws MalformedURLException {
		WebDriver driver = accessApplication();
		
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Erro");
			driver.findElement(By.id("saveButton")).click();
			
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", message);			
		} finally {
			driver.quit();			
		}
	}
	
	@Test
	public void shouldNotCreateTaskWithPastDate() throws MalformedURLException {
		WebDriver driver = accessApplication();
		
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Erro");
			driver.findElement(By.id("dueDate")).sendKeys("20/10/2010");
			driver.findElement(By.id("saveButton")).click();
			
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", message);			
		} finally {
			driver.quit();			
		}	
	}
}
