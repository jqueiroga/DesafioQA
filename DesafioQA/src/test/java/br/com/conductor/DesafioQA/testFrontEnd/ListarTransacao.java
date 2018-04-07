package br.com.conductor.DesafioQA.testFrontEnd;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
 * Classe que contém os casos de testes para validação da tela de listagem de transação.
 * Nela temos testes para validar a tela de listagem, lista todos os transação, pesquisa transação por cliente,
 * 
 * Por não ter tempo hábil não foi possível implementar o PageObject e nem o DDT
 * 
 * Por motivo de tempo não foi possível incluir mais testes.
 * 
 * @author JOÃO HENRIQUE
 *
 */


public class ListarTransacao {

	//Variável global  do navegador e tela de Home.
	private WebDriver navegator;

	@Before
	public void setup() {


		//Setando o local do chromeWebDriver
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\driver\\chromedriver.exe");

		//Como pré-condição o usuário deverá está locado
		navegator = Web.createChrome();
		
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
		formularioIncluir.findElement(By.id("nome")).sendKeys("Cliente 005");	
		formularioIncluir.findElement(By.id("cpf")).sendKeys("05005005055");	
		formularioIncluir.findElement(By.id("saldoCliente")).sendKeys("100");	
		formularioIncluir.findElement(By.id("botaoSalvar")).click();

		
		
		//Pré-condição do Caso de Testes querer ao menos 1 transação realizada
		navegator.findElement(By.xpath("//a[@title=\"QA\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Transações\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Dashboard\"]")).click();
		WebElement content = navegator.findElement(By.id("content"));
		WebDriverWait wait = new WebDriverWait(navegator,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class=\"page-title txt-color-blueDark\"]")));
		WebElement campoType = content.findElement(By.id("cliente"));
		new Select(campoType).selectByVisibleText("Cliente 005");
		content.findElement(By.id("valorTransacao")).sendKeys("100");
		content.findElement(By.id("botaoSalvar")).click();
		
		//Ir para a tela inicial do sistema
		navegator.findElement(By.xpath("//a[@title=\"Inicio\"]")).click();

	}


	@Test
	public void testAbrirTelaDeListarTransação() {
		//Clicando no Submenu Incluir do Menu Clientes 
		navegator.findElement(By.xpath("//a[@title=\"QA\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Transações\"]")).click();
		navegator.findElement(By.xpath("//a[@href=\"/desafioqa/listarVenda\"]")).click();

		//Criar um WebElement para captura o formulário de Incluir
		WebElement content = navegator.findElement(By.id("content"));

		//Esperar o título da tela aparecer
		WebDriverWait wait = new WebDriverWait(navegator,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class=\"page-title txt-color-blueDark\"]")));

		//Validar se a o usuário está na tela de listar Transações
		String tituloListarTransacoes = content.findElement(By.xpath("//h1[@class=\"page-title txt-color-blueDark\"]")).getText();
		System.out.println(tituloListarTransacoes);
		assertTrue(tituloListarTransacoes.contains("Listar Transações"));

	}
	
	@Test
	public void testPesquisarTodasTransacoesCadastradas() {
		//Clicando no Submenu Incluir do Menu Clientes 
		navegator.findElement(By.xpath("//a[@title=\"QA\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Transações\"]")).click();
		navegator.findElement(By.xpath("//a[@href=\"/desafioqa/listarVenda\"]")).click();

		//Clicando no botão pesquisar, para trazer todos os clientes cadastrados
		navegator.findElement(By.xpath("//input[@value=\"Pesquisar\"]")).click();

		WebElement table = navegator.findElement(By.xpath("//table/tbody"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertTrue(rows.size() > 0);

	}
	
	@Test
	public void testPesquisarUmaTransacaoEspecifica() {
		//Clicando no Submenu Incluir do Menu Clientes 
		navegator.findElement(By.xpath("//a[@title=\"QA\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Transações\"]")).click();
		navegator.findElement(By.xpath("//a[@href=\"/desafioqa/listarVenda\"]")).click();

		//Filtrar a lista pelo cliente adicionado na Pré-Condição
		WebElement campoType = navegator.findElement(By.id("cliente"));
		new Select(campoType).selectByVisibleText("Cliente 005");

		//Clicando no botão pesquisar, para trazer todos os clientes cadastrados
		navegator.findElement(By.xpath("//input[@value=\"Pesquisar\"]")).click();

		//Verificando que a tabela seja adicionada pelas transações conforme filtro de pesquisa
		WebElement table = navegator.findElement(By.xpath("//table/tbody"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertTrue(rows.size() > 0);

	}
	
	@After
	public void tearDown() {
		// Fechar o navegador
		navegator.quit();
	}

}
