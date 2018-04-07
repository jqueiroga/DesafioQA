package br.com.conductor.DesafioQA.testBackEnd;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

public class testBackEndAPI {

	
	public testBackEndAPI() {
		baseURI = "http://52.191.254.38/desafio/produtos";
	}
	
	@Test
	public void testValidacaoListaProdutos() {
		System.out.println(">>>>> Realizar Validação da API Listar Produtos <<<<<");
		
		given()
			.headers("Token","desafio")
		.when()
			.get(baseURI)
		.then()
			.statusCode(200)
			.body("id", hasItems("1"))
			.body("nome", hasItems("Chocolate"))
			.body("descricao", hasItems("Chocolate ao leite"))
			.body("valor_venda", hasItems("2.00"))
			.body("estoque", hasItems("10.00"))
			.body("endereco_foto", hasItems("teste"))
			.body("codigo_barras", hasItems("78912344412"));
			
	
		//Se o Caso de Teste chegar a esse ponto é que foi executado com sucesso.
		System.out.println("Caso de Teste executado com SUCESSO !!!");
		
	}

}
