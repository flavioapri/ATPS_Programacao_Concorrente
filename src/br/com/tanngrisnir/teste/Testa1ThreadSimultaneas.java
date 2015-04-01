package br.com.tanngrisnir.teste;

import java.io.IOException;

import br.com.tanngrisnir.logica.Buffer;
import br.com.tanngrisnir.logica.Consumidor;

/**
 * Classe de teste para a execução de uma única thread.
 * 
 * @version 1.0.0
 * @author Flavio Aparecido Ribeiro
 *
 */
public class Testa1ThreadSimultaneas {

	public static void main(String[] args) throws InterruptedException,
			IOException {
		 
		Thread thread1 = new Thread(new Consumidor(new Buffer()));
		thread1.start();
	}
}
