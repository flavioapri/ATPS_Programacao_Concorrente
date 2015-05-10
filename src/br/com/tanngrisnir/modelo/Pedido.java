package br.com.tanngrisnir.modelo;

import java.math.BigInteger;

/**
 * Classe modelo representando um pedido.
 * 
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Flávio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 * @version 1.0.0
 */
public class Pedido {
	// Foi adotada a classe BigInteger para o id porque porque o tipo long não
	// comporta inteiros de até 20 dígitos
	// Poderia ser adotada uma string ou array de chars mas por questões de
	// performance e do contexto da atividade este tipo não foi adotado
	private BigInteger id;
	// A string representando o pacote de dados em tipo texto.
	private String pacoteDados;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getPacoteDados() {
		return pacoteDados;
	}

	public void setPacoteDados(String pacoteDados) {
		this.pacoteDados = pacoteDados;
	}
}
