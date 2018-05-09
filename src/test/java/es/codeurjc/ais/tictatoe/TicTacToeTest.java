package es.codeurjc.ais.tictatoe;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import es.codeurjc.ais.tictactoe.WebApp;
import io.github.bonigarcia.wdm.ChromeDriverManager;

public class TicTacToeTest {

	private WebDriver browser1;
	private WebDriver browser2;
	
	private String player1 = "Player1";
	private String player2 = "Player2";

	@BeforeClass
	public static void setupClass() {
		ChromeDriverManager.getInstance().setup();
		WebApp.start();
	}
	
	@AfterClass
	public static void teardownClass() {
		WebApp.stop();
	}
	
	@Before
	public void setupTest() throws InterruptedException{
		browser1 = new ChromeDriver();
		browser2 = new ChromeDriver();
		
		browser1.get("http://localhost:8080");
		WebElement searchInput1 = browser1.findElement(By.id("nickname"));
		Thread.sleep(2000);
		searchInput1.sendKeys(player1);
		
		WebElement button1 = browser1.findElement(By.id("startBtn"));
		button1.click();
        
		browser2.get("http://localhost:8080");
		WebElement searchInput2 = browser2.findElement(By.id("nickname"));
		Thread.sleep(2000);
		searchInput2.sendKeys(player2);
		WebElement button2 = browser2.findElement(By.id("startBtn"));
		button2.click();
	}

	@After
	public void teardown() {
		if (browser1 != null) {
			browser1.quit();
		}
		if (browser2 != null) {
			browser2.quit();
		}
	}


	@Test
	public void testFirstPlayerWins() throws InterruptedException {
				
		WebElement cell_0 = browser1.findElement(By.id("cell-0"));
		cell_0.click();
		
		WebElement cell_1 = browser2.findElement(By.id("cell-1"));
		cell_1.click();
		
		WebElement cell_2 = browser1.findElement(By.id("cell-2"));
		cell_2.click();
		
		WebElement cell_3 = browser2.findElement(By.id("cell-3"));
		cell_3.click();
		
		WebElement cell_4 = browser1.findElement(By.id("cell-4"));
		cell_4.click();
		
		WebElement cell_5 = browser2.findElement(By.id("cell-5"));
		cell_5.click();
		
		WebElement cell_6 = browser1.findElement(By.id("cell-6"));
		cell_6.click();
		
		Thread.sleep(2000);
		
		String text = browser2.switchTo().alert().getText();
		
		assertThat(text).isEqualTo(player1 + " wins! " + player2 + " looses.");
				
	}
	
	
	@Test
	public void testSecondPlayerWins() throws InterruptedException {
		
		WebElement cell_0 = browser2.findElement(By.id("cell-0"));
		cell_0.click();
		
		WebElement cell_1 = browser1.findElement(By.id("cell-1"));
		cell_1.click();
		
		WebElement cell_2 = browser2.findElement(By.id("cell-2"));
		cell_2.click();
		
		WebElement cell_3 = browser1.findElement(By.id("cell-3"));
		cell_3.click();
		
		WebElement cell_4 = browser2.findElement(By.id("cell-4"));
		cell_4.click();
		
		WebElement cell_5 = browser1.findElement(By.id("cell-5"));
		cell_5.click();
		
		WebElement cell_6 = browser2.findElement(By.id("cell-6"));
		cell_6.click();
		
		Thread.sleep(2000);
		
		String text = browser2.switchTo().alert().getText();
		
		assertThat(text).isEqualTo(player2 + " wins! " + player1 + " looses.");
				
	}
	

	@Test
	public void testDraw() throws InterruptedException {
		
		WebElement cell_0 = browser1.findElement(By.id("cell-0"));
		cell_0.click();
		
		WebElement cell_3 = browser2.findElement(By.id("cell-3"));
		cell_3.click();
		
		WebElement cell_1 = browser1.findElement(By.id("cell-1"));
		cell_1.click();
		
		WebElement cell_4 = browser2.findElement(By.id("cell-4"));
		cell_4.click();
		
		WebElement cell_6 = browser1.findElement(By.id("cell-6"));
		cell_6.click();
		
		WebElement cell_7 = browser2.findElement(By.id("cell-7"));
		cell_7.click();
		
		WebElement cell_5 = browser1.findElement(By.id("cell-5"));
		cell_5.click();
		
		WebElement cell_2 = browser2.findElement(By.id("cell-2"));
		cell_2.click();
		
		WebElement cell_8 = browser1.findElement(By.id("cell-8"));
		cell_8.click();
						
		Thread.sleep(2000);
		
		String text = browser2.switchTo().alert().getText();
		
		assertThat(text).isEqualTo("Draw!");
				
	}

}







