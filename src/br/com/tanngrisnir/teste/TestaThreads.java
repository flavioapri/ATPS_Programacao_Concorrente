package br.com.tanngrisnir.teste;

import java.util.ArrayList;
import java.util.List;

import br.com.tanngrisnir.logica.Buffer;
import br.com.tanngrisnir.logica.Consumidor;

/**
 * Classe de testes para a execução das threads.
 * 
 * @version 1.0.0
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Flavio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 *
 */
public class TestaThreads {
	public static void main(String[] args) throws InterruptedException {
		int qtdThreads = 1000; // Quantidade de threads a serem executadas.
		long tempoInicialThread;
		long tempoTotalThread;
		// Lista que vai armazenar a respectiva quantidade
		// de threads
		List<Thread> threads = new ArrayList<Thread>();
		Buffer buffer = new Buffer(); // Buffer com os pedidos
		int i = 0;
		while (i < qtdThreads) {
			// Cria uma lista de threads que recebe a respectiva quantidade
			// de consumidores.
			threads.add(new Thread(new Consumidor(buffer, i)));
			i++;
		}
		tempoInicialThread = System.currentTimeMillis();
		i = 0;
		while (i < threads.size()) {
			// Executa as threads.
			threads.get(i).start();
			i++;
		}
		i = 0;
		while (i < threads.size()) {
			// O método join serve para bloquear as threads, para que
			// o restante no código nesta classe de testes possa ser
			// executado.
			// Deve ser executado em todas as threads em execução.
			threads.get(i).join();
			i++;
		}
		tempoTotalThread = System.currentTimeMillis() - tempoInicialThread;
		System.out.println("\nTempo total de execução: " + tempoTotalThread
				+ " ms");
		System.out.println("\nTempo médio de execução para " + qtdThreads
				+ " threads: " + (tempoTotalThread / qtdThreads) + " ms");
	}
}
