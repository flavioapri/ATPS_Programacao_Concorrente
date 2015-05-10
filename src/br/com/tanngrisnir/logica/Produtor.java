package br.com.tanngrisnir.logica;

import java.math.BigInteger;
import java.util.Random;

import br.com.tanngrisnir.modelo.Pedido;

/**
 * Gera pedidos com ID de 20 algarismos e uma string representando um pacote de
 * dados com 1000 caracteres de texto.
 * 
 * @author Daiana Paula Tizer Parra
 * @author F�bio de Paula dos Anjos
 * @author Fl�vio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 * @version 1.0
 */
public class Produtor {

	// Objeto gerador de caracteres aleat�rios.
	private Random gerador = new Random();
	// Array populado com caracteres aleat�rios para a posterior composi��o do
	// atributo "Id" do pedido.
	private char valoresAleatorios[] = new char[20];
	// Array populado com caracteres aleat�rios para a posterior composi��o do
	// atributo "Pacote de Dados" do pedido.
	private char caracteresAleatorios[] = new char[1000];

	public Pedido geraPedido() {
		Pedido pedido = new Pedido();
		// Gera valores aleat�rios de caracteres para o array, o
		// valor 10 para o m�todo nextInt � utilizado para indicar que ser�o
		// gerados valores de 0 a 9 e '0' � para indicar que devem ser
		// gerados caracteres.
		int i = 0;
		while (i < 20) {
			valoresAleatorios[i] = (char) (gerador.nextInt(10) + '0');
			i++;
		}
		pedido.setId(new BigInteger(new String(valoresAleatorios)));
		i = 0;
		// Gera valores aleat�rios de caracteres para o array, o
		// valor 26 para o m�todo nextInt � utilizado para indicar que ser�o
		// gerados valores de 0 a 25 e 'a' � para indicar que devem ser
		// gerados os caracteres que representam letras.
		while (i < 1000) {
			caracteresAleatorios[i] = (char) (gerador.nextInt(26) + 'a');
			i++;
		}
		pedido.setPacoteDados(new String(caracteresAleatorios));
		return pedido;
	}
}
