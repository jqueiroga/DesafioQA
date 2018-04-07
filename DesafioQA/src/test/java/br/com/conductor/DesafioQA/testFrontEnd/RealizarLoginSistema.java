package br.com.conductor.DesafioQA.testFrontEnd;

import static org.junit.Assert.assertEquals;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import Suporte.Web;
import pages.LoginPage;

/**
 * 
 * Esse método foi desenvolvido utilizando a metodologia de PageObject
 * um método que deixa o código mais limpo e mais fácil de aplicar modificações.
 * Também utilizamos o Data Driver Testing, para facilitar no tratamento dos
 * dados, sendo esses parametrizados.
 * 
 * @author Jeremias Araujo
 *
 */


@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "RealizarLoginSistema.csv")
public class RealizarLoginSistema {
	//Variável global  do navegador.
	private WebDriver navigator;

	@Before
	public void setup() {
		//Criando o navegado do chrome, que encontra na class WEB
		navigator = Web.createChrome();
	}


	@Test
	//Efetuando o login no sistema com dados válidos
	public void testEfetuarloginSistemaComUsuarioValido(
			@Param(name="username")String login,
            @Param(name="password")String senha,
            @Param(name="message")String messagemEsperada
            ) {

		
		//Gerando a mensagem de erro, a partir do PageObject LoginPage
		String messageUsuarioLogado = new LoginPage(navigator)
				.digitarLogin(login)
				.digitarSenha(senha)
				.clicarSignIn()
				.capturarMessageLogado();
		
		assertEquals(messagemEsperada, messageUsuarioLogado);
	}
	
	@Test
	//Tentando efetuar o login no sistema com dados inválidos
	public void testEfetuarloginSistemaComUsuarioInvalido(
			@Param(name="username")String login,
            @Param(name="password")String senha,
            @Param(name="message")String messagemEsperada
            ) {

		
		//Gerando a mensagem de erro, a partir do PageObject LoginPage
		String messageErro = new LoginPage(navigator)
				.digitarLogin(login)
				.digitarSenha(senha)
				.clicarSignIn()
				.capturarMessageErro();
		
		assertEquals(messagemEsperada, messageErro);
	}
		

	@After
    public void tearDown() {
        // Fechar o navegador
        navigator.quit();
    }


}
