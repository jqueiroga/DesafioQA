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
import org.openqa.selenium.support.ui.WebDriverWait;

import Suporte.Web;
import pages.LoginPage;

/**
 * 
 * Classe que contém os casos de testes para validação da tela de listagem de clientes.
 * Nela temos testes para validar a tela de listagem, lista todos os clietes, pesquisa cliente,
 * pesquisar cliente não cadastrado e visualizar cliente cadastrado.
 * 
 * Por não ter tempo hábil não foi possível implementar o PageObject e nem o DDT
 * 
 * Por motivo de tempo não foi possível incluir mais testes.
 * 
 * @author JOÃO HENRIQUE
 *
 */

public class ListarClientes {

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
		formularioIncluir.findElement(By.id("nome")).sendKeys("Cliente 001");	
		formularioIncluir.findElement(By.id("cpf")).sendKeys("01001001011");	
		formularioIncluir.findElement(By.id("saldoCliente")).sendKeys("100");	
		formularioIncluir.findElement(By.id("botaoSalvar")).click();

		navegator.findElement(By.xpath("//a[@title=\"Inicio\"]")).click();

	}

	@Test
	public void testAbrirTelaDeListarCliente() {
		//Clicando no Submenu Incluir do Menu Clientes 
		navegator.findElement(By.xpath("//a[@title=\"QA\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Clientes\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Listar\"]")).click();

		//Criar um WebElement para captura o formulário de Incluir
		WebElement content = navegator.findElement(By.id("content"));

		//Esperar o título da tela aparecer
		WebDriverWait wait = new WebDriverWait(navegator,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class=\"page-title txt-color-blueDark\"]")));

		//Validar se a o usuário está na tela de listar Cliente
		String tituloListarTransacoes = content.findElement(By.xpath("//h1[@class=\"page-title txt-color-blueDark\"]")).getText();
		assertTrue(tituloListarTransacoes.contains("Listar Clientes"));

	}

	@Test
	public void testListarTodosCliente() {
		//Clicando no Submenu Incluir do Menu Clientes 
		navegator.findElement(By.xpath("//a[@title=\"QA\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Clientes\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Listar\"]")).click();

		//Clicando no botão pesquisar, para trazer todos os clientes cadastrados
		navegator.findElement(By.xpath("//input[@value=\"Pesquisar\"]")).click();

		//Validando o preenchimento da tabela com todos os clientes cadastrados
		WebElement table = navegator.findElement(By.xpath("//table/tbody"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertTrue(rows.size() > 0);
	}


	@Test
	public void testPesquisarUmEspecíficoCliente() {
		//Clicando no Submenu Incluir do Menu Clientes 
		navegator.findElement(By.xpath("//a[@title=\"QA\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Clientes\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Listar\"]")).click();

		//Filtrar a lista pelo cliente adicionado na Pré-Condição
		navegator.findElement(By.xpath("//input[@class=\"form-control\"]")).sendKeys("Clinte");

		//Clicando no botão pesquisar, para trazer todos os clientes cadastrados
		navegator.findElement(By.xpath("//input[@value=\"Pesquisar\"]")).click();

		//Valindando que a tabela foi preenchido após pesquisa
		WebElement table = navegator.findElement(By.xpath("//table/tbody"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertTrue(rows.size() > 0);

	}

	@Test
	public void testPesquisarUmEspecíficoClienteNaoCadastrado() {
		//Clicando no Submenu Incluir do Menu Clientes 
		navegator.findElement(By.xpath("//a[@title=\"QA\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Clientes\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Listar\"]")).click();

		//Filtrar a lista pelo cliente adicionado na Pré-Condição
		navegator.findElement(By.xpath("//input[@class=\"form-control\"]")).sendKeys("ClienteInexistente");

		//Clicando no botão pesquisar, para trazer todos os clientes cadastrados
		navegator.findElement(By.xpath("//input[@value=\"Pesquisar\"]")).click();

		//Verificando que a tabela não tem registros adicionados
		WebElement table = navegator.findElement(By.xpath("//table/tbody"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertTrue(rows.size() == 0);

	}

	@Test
	public void testVisualizarClienteCadastrado() {
		//Clicando no Submenu Incluir do Menu Clientes 
		navegator.findElement(By.xpath("//a[@title=\"QA\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Clientes\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Listar\"]")).click();

		//Filtrar a lista pelo cliente adicionado na Pré-Condição
		navegator.findElement(By.xpath("//input[@class=\"form-control\"]")).sendKeys("Clinte");

		//Clicando no botão pesquisar, para trazer todos os clientes cadastrados
		navegator.findElement(By.xpath("//input[@value=\"Pesquisar\"]")).click();

		WebElement table = navegator.findElement(By.xpath("//table/tbody"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));

		//Clicando no ícone para visualiar o cliente cadastrado
		rows.get(0).findElement(By.xpath("//a[@class=\"btn btn-sm btn-primary\"]")).click();

		//Esperar a mensagem aparecer na tela
		WebDriverWait wait = new WebDriverWait(navegator,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class=\"page-title txt-color-blueDark\"]")));

		String tituloListarTransacoes = navegator.findElement(By.xpath("//h1[@class=\"page-title txt-color-blueDark\"]")).getText();
		assertTrue(tituloListarTransacoes.contains("Visualizar Cliente"));

	}

	@After
	public void tearDown() {
		// Fechar o navegador
		navegator.quit();
	}
}
