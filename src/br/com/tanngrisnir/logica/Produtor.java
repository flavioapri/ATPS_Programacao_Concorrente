package br.com.tanngrisnir.logica;

import java.math.BigInteger;
import java.util.Random;

import br.com.tanngrisnir.modelo.Pedido;

/**
 * Gera pedidos com ID de 20 algarismos e uma string representando um pacote de
 * dados com 1000 caracteres de texto.
 * 
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Flavio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 * @version 1.0.0
 */
public class Produtor {

	// Objeto gerador de caracteres aleatórios.
	private Random gerador = new Random();
	// Array populado com caracteres aleatórios para a posterior composição do
	// atributo "Id" do pedido.
	private char valoresAleatorios[] = new char[20];
	// Array populado com caracteres aleatórios para a posterior composição do
	// atributo "Pacote de Dados" do pedido.
	private char caracteresAleatorios[] = new char[1000];

	/**
	 * Método que seta valores para os atributos id e pacoteDados para um objeto
	 * do tipo Pedido.
	 * 
	 * @return Objeto do tipo Pedido com Id e Pacote de dados
	 */
	public Pedido getPedido() {
		Pedido pedido = new Pedido();
		int i = 0;
		// Gera valores aleatórios de caracteres para o array, o valor 10 para o
		// método nextInt é utilizado para indicar que seram gerados valores de
		// 0 a 9 e '0' é para indicar que devem ser gerados caracteres.
		while (i < 20) {
			valoresAleatorios[i] = (char) (gerador.nextInt(10) + '0');
			i++;
		}

		pedido.setId(new BigInteger(new String(valoresAleatorios)));

		i = 0;
		// Gera valores aleatórios de caracteres para o array, o valor 26 para o
		// método nextInt é utilizado para indicar que seram gerados valores de
		// 0 a 25 e 'a' é para indicar que devem ser gerados os caracteres que
		// representam letras.
		while (i < 1000) {
			caracteresAleatorios[i] = (char) (gerador.nextInt(26) + 'a');
			i++;
		}
		pedido.setPacoteDados(new String(caracteresAleatorios));
		

		return pedido;
	}

}
