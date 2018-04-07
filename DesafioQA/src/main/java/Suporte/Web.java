package Suporte;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/** 
 * 
 * Classe de suporte para configurações padrões de WebDriver, reduzindo a quantidade repetitiva
 * de código usadas em todas as class, ao instanciar uma nova WebDriver
 * 
 * @author Jeremias Araújo
 *
 */


public class Web {
	public static WebDriver createChrome() {
		//Setando o local do chromeWebDriver
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\driver\\chromedriver.exe");

		//Como pré-condição o usuário deverá está locado
		WebDriver navigator = new ChromeDriver();
		navigator.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		navigator.manage().window().maximize();
		navigator.get("http://desafioqa.marketpay.com.br:9084/desafioqa/login");
		
		return navigator;

	}

}
