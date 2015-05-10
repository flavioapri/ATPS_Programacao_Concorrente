package br.com.tanngrisnir.teste;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.tanngrisnir.logica.Buffer;
import br.com.tanngrisnir.logica.Consumidor;

/**
 * Classe principal para teste do protótipo.
 * 
 * @version 1.0
 * @author Daiana Paula Tizer Parra
 * @author Fábio de Paula dos Anjos
 * @author Flávio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 *
 */
public class TestaPrototipo {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException {
		int qtdThreads;
		long tempoInicial;
		Buffer buffer = new Buffer();
		List<Thread> consumidores;
		Scanner s = new Scanner(System.in);
		System.out.print("Informe a quantidade de threads: ");
		qtdThreads = s.nextInt();
		consumidores = new ArrayList<Thread>(qtdThreads);
		System.out.println("Inicializando as threads. Aguarde...");
		tempoInicial = System.currentTimeMillis();
		// Pega o tempo em que a execução das threads começou.
		for (int i = 0; i < qtdThreads; i++) {
			consumidores.add(new Thread(new Consumidor(buffer, i)));
			// Inicializa as posições na lista de com threads do tipo
			// consumidora.
			consumidores.get(i).start(); // Inicia a execução de cada thread.
		}
		for (int i = 0; i < qtdThreads; i++) {
			consumidores.get(i).join();
			// Manda aguardar todas as threads morrerem para só assim continuar
			// o fluxo normal do programa.
		}
		System.out.println("Tempo total de execução de " + qtdThreads
				+ " threads: " + (System.currentTimeMillis() - tempoInicial)
				+ " ms");
	}
}
