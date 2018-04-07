package br.com.conductor.DesafioQA.testFrontEnd;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Suporte.Web;
import pages.LoginPage;

/**
 * 
 * Classe que contém os casos de testes para cadastro de transação de vendas.
 * Nela temos testes para validar a tela de cadastro, cadastro de uma transação com dados válidos
 * cadastro de transação com valor maior que o saldo do cliente.
 * 
 * Por não ter tempo hábil não foi possível implementar o PageObject e nem o DDT
 * 
 * Por motivo de tempo não foi possível incluir mais testes.
 * 
 * @author JOÃO HENRIQUE
 *
 */

public class CadastrarTransicao {

	//Variável global  do navegador e tela de Home.
	private WebDriver navegator;

	@Before
	public void setup() {	

		//Setando o local do chromeWebDriver
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\driver\\chromedriver.exe");

		//Como pré-condição o usuário deverá está locado
		LoginPage logarSistema = new LoginPage(navegator);
		logarSistema
				.digitarLogin("admin")
				.digitarSenha("admin")
				.clicarSignIn();

		//Pré-condição do Caso de Testes querer ao menos 1 usuário cadastrado
		navegator.findElement(By.xpath("//a[@title=\"QA\"]")).click();;
		navegator.findElement(By.xpath("//a[@title=\"Clientes\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Incluir\"]")).click();
		WebElement formularioIncluir = navegator.findElement(By.xpath("//div[@class=\"well\"]"));
		formularioIncluir.findElement(By.id("nome")).sendKeys("Cliente 004");	
		formularioIncluir.findElement(By.id("cpf")).sendKeys("01001001011");	
		formularioIncluir.findElement(By.id("saldoCliente")).sendKeys("1000");	
		formularioIncluir.findElement(By.id("botaoSalvar")).click();

		navegator.findElement(By.xpath("//a[@title=\"Inicio\"]")).click();

	}

	@Test
	public void testAbrirTelaDeCadastrarTransacao() {
		//Clicando no Submenu Incluir do Menu Clientes 
		navegator.findElement(By.xpath("//a[@title=\"QA\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Transações\"]")).click();
		navegator.findElement(By.xpath("//a[@href=\"//desafioqa//incluirVenda\"]")).click();

		//Criar um WebElement para captura o formulário de Incluir
		WebElement content = navegator.findElement(By.id("content"));

		//Esperar o título da tela aparecer
		WebDriverWait wait = new WebDriverWait(navegator,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class=\"page-title txt-color-blueDark\"]")));

		//Validar se a o usuário está na tela de listar Cliente
		String tituloListarTransacoes = content.findElement(By.xpath("//h1[@class=\"page-title txt-color-blueDark\"]")).getText();
		assertTrue(tituloListarTransacoes.contains("Incluir Transação"));

	}


	@Test
	public void testCadastrarTransacao() {
		//Clicando no Submenu Incluir do Menu Clientes 
		navegator.findElement(By.xpath("//a[@title=\"QA\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Transações\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Dashboard\"]")).click();

		//Criar um WebElement para captura o formulário de Incluir
		WebElement content = navegator.findElement(By.id("content"));

		//Esperar o título da tela aparecer
		WebDriverWait wait = new WebDriverWait(navegator,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class=\"page-title txt-color-blueDark\"]")));

		//Selecionando um CLiente no comboBox
		WebElement campoType = content.findElement(By.id("cliente"));
		new Select(campoType).selectByVisibleText("Cliente 004");

		//Preenchendo o valor da transação, no valor limite do saldo
		content.findElement(By.id("valorTransacao")).sendKeys("1000");

		//Clicar no botão Salvar
		content.findElement(By.id("botaoSalvar")).click();

		//Esperar a mensagem aparecer na tela
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("alertMessage")));

		//Validar se a mensagem de sucesso é exibido 
		String alertMessage = navegator.findElement(By.id("alertMessage")).getText();
		assertTrue(alertMessage.contains("Venda realizada com sucesso"));
	}


	@Test
	public void testCadastrarTransacaoMaiorQueSaldoDoCliente() {
		//Clicando no Submenu Incluir do Menu Clientes 
		navegator.findElement(By.xpath("//a[@title=\"QA\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Transações\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Dashboard\"]")).click();

		//Criar um WebElement para captura o formulário de Incluir
		WebElement content = navegator.findElement(By.id("content"));

		//Esperar o título da tela aparecer
		WebDriverWait wait = new WebDriverWait(navegator,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class=\"page-title txt-color-blueDark\"]")));

		//Selecionando um CLiente no comboBox
		WebElement campoType = content.findElement(By.id("cliente"));
		new Select(campoType).selectByVisibleText("Cliente 004");

		//Preenchendo o valor da transação, no valor limite do saldo
		content.findElement(By.id("valorTransacao")).sendKeys("10000");

		//Clicar no botão Salvar
		content.findElement(By.id("botaoSalvar")).click();

		//Esperar a mensagem aparecer na tela
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("alertMessage")));

		//Validar se a mensagem de erro é exibido 
		String alertMessage = navegator.findElement(By.id("alertMessage")).getText();
		assertTrue(alertMessage.contains("Saldo Insuficiente"));
	}

	@After
	public void tearDown() {
		// Fechar o navegador
		navegator.quit();
	}

	
}
