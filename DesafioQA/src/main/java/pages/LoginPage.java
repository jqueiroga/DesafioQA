package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private WebDriver navigator;

	public LoginPage(WebDriver navigator) {
		this.navigator = navigator;
	}

	public LoginPage digitarLogin(String login) {
		navigator.findElement(By.id("login-form")).findElement(By.name("username")).sendKeys(login);

		return this;
	}

	public LoginPage digitarSenha(String password) {
		navigator.findElement(By.id("login-form")).findElement(By.name("password")).sendKeys(password);

		return this;
	}

	public LoginPage clicarSignIn() {
		navigator.findElement(By.xpath("//button[@class=\"btn btn-primary\"]")).click();

		return this;
	}

	public String capturarMessageLogado() {
		return  navigator.findElement(By.id("content")).findElement(By.xpath("//h1[@class=\"page-title txt-color-blueDark\"]")).getText();
	}


	public String capturarMessageErro() {
		//Esperar a mensagem aparecer na tela
		WebDriverWait wait = new WebDriverWait(navigator,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//font[@color=\"red\"]")));

		return navigator.findElement(By.xpath("//font[@color=\"red\"]")).getText();
	}

}
