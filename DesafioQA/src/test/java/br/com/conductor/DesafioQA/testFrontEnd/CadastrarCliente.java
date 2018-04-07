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
import org.openqa.selenium.support.ui.WebDriverWait;

import Suporte.Web;
import pages.LoginPage;

/**
 * 
 * Classe que contém os casos de testes para cadastro de clientes.
 * Nela temos testes para validar o cadastro de cliente, cadastro de cliente com dados em branco
 * 
 * Na clase os para agilizar a usabilidade dos dados foi usado do Data Driver Testing, 
 * por não ter tempo hábil não foi possível implementar o PageObject
 * 
 * Por motivo de tempo não foi possível incluir mais testes.
 * 
 * @author JOÃO HENRIQUE
 *
 */

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "CadastrarClientes.csv")
public class CadastrarCliente {

	//Variável global  do navegador e tela de Home.
	private WebDriver navegator;

	@Before
	public void setup() {
		navegator = Web.createChrome();

		LoginPage logarSistema = new LoginPage(navegator);
		logarSistema
				.digitarLogin("admin")
				.digitarSenha("admin")
				.clicarSignIn();
	
	}

	@Test
	//Teste para validar o cadastro do cliente com sucesso
	public void testCadastrarClienteComSucesso(@Param(name="nome")String nome, @Param(name="cpf")String cpf, @Param(name="saldo")String saldo) {
		//Preencher o Cadastro
		preencherCadastro(nome, cpf, saldo);


		//Esperar a mensagem aparecer na tela
		WebDriverWait wait = new WebDriverWait(navegator,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("alertMessage")));

		//Validar se a mensagem de sucesso é exibido 
		String alertMessage = navegator.findElement(By.id("alertMessage")).getText();
		assertTrue(alertMessage.contains("Cliente salvo com sucesso"));

	}


	@Test
	//Test para validar o tratamento do sistema para caso o usuária cadastro o cliente com o nome em branco
	public void testCadastrarClienteComCampoNomeEmBranco(@Param(name="nome")String nome, @Param(name="cpf")String cpf, @Param(name="saldo")String saldo) {
		//Preencher o Cadastro
		preencherCadastro(nome, cpf, saldo);


		//Esperar a mensagem aparecer na tela
		WebDriverWait wait = new WebDriverWait(navegator,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[@data-bv-for=\"nome\"]")));

		//Validar se a mensagem de Campo obrigatório é exibido 
		String alertMessage = navegator.findElement(By.xpath("//small[@data-bv-for=\"nome\"]")).getText();
		assertTrue(alertMessage.contains("Campo Obrigatório"));

	}



	@Test
	//Test para validar o tratamento do sistema para caso o usuária cadastro o cliente com o CPF em branco
	public void testCadastrarClienteComCampoCPFEmBranco(@Param(name="nome")String nome, @Param(name="cpf")String cpf, @Param(name="saldo")String saldo) {
		//Preencher o Cadastro
		preencherCadastro(nome, cpf, saldo);


		//Esperar a mensagem aparecer na tela
		WebDriverWait wait = new WebDriverWait(navegator,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[@data-bv-for=\"cpf\"]")));

		//Validar se a mensagem de Campo obrigatório é exibido 
		String alertMessage = navegator.findElement(By.xpath("//small[@data-bv-for=\"cpf\"]")).getText();
		assertTrue(alertMessage.contains("Campo Obrigatório"));

	}



	//Test para validar o tratamento do sistema para caso o usuário cadastrar o cliente com o Saldo em branco
	@Test
	public void testCadastrarClienteComCampoSaldoEmBranco(@Param(name="nome")String nome, @Param(name="cpf")String cpf, @Param(name="saldo")String saldo) {
		//Preencher o Cadastro
		preencherCadastro(nome, cpf, saldo);

		//Esperar a mensagem aparecer na tela
		WebDriverWait wait = new WebDriverWait(navegator,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[@data-bv-for=\"nome\"]")));

		//Validar se a mensagem de Campo obrigatório é exibido 
		String alertMessage = navegator.findElement(By.xpath("//small[@data-bv-for=\"saldoCliente\"]")).getText();
		assertTrue(alertMessage.contains("Campo Obrigatório"));

	}

	//Método de preenchimento de cadastro que irá se repetir em todos os testes
	public void preencherCadastro(String nome, String cpf, String saldo) {
		//Clicando no Submenu Incluir do Menu Clientes 
		navegator.findElement(By.xpath("//a[@title=\"QA\"]")).click();;
		navegator.findElement(By.xpath("//a[@title=\"Clientes\"]")).click();
		navegator.findElement(By.xpath("//a[@title=\"Incluir\"]")).click();

		//Criar um WebElement para captura o formulário de Incluir
		WebElement formularioIncluir = navegator.findElement(By.xpath("//div[@class=\"well\"]"));

		//Inserindo o nome do cliente no campo Nome
		formularioIncluir.findElement(By.id("nome")).sendKeys(nome);	

		//Inserindo o CPF do cliente no campo CPF
		formularioIncluir.findElement(By.id("cpf")).sendKeys(cpf);	

		//Inserindo o saldo do cliente no campo saldoCliente
		formularioIncluir.findElement(By.id("saldoCliente")).sendKeys(saldo);	


		//Clicando no Botão Salvar
		formularioIncluir.findElement(By.id("botaoSalvar")).click();	
	}

	@After
	public void tearDown() {
		// Fechar o navegador
		navegator.quit();
	}

}
