package br.com.tanngrisnir.teste;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.tanngrisnir.logica.Buffer;
import br.com.tanngrisnir.logica.Consumidor;
import br.com.tanngrisnir.logica.Produtor;
import br.com.tanngrisnir.modelo.Pedido;

/**
 * Classe de testes para a execução de threads Produtoras e Consumidoras.
 * 
 * @version 1.1
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Flavio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 *
 */
public class TestaThreadsProdutorasCosumidoras {
	public static void main(String[] args) throws InterruptedException {
		int qtdThreads = 0; // Quantidade de threads a serem executadas.
		Scanner e = new Scanner(System.in);
		// Listas que vão armazenar a respectiva quantidade
		// de threads
		List<Thread> threadsProdutoras = new ArrayList<Thread>(qtdThreads);
		List<Thread> threadsConsumidoras = new ArrayList<Thread>(qtdThreads);
		Buffer buffer = new Buffer();
		System.out.print("Informe a quantidade de threads: ");
		qtdThreads = e.nextInt();
		System.out.println("\nInicializando threads. Aguarde...");
		int i = 0;
		while (i < qtdThreads) {
			// Popula as listas de threads
			threadsProdutoras.add(new Thread(new Produtor(i, buffer)));
			threadsProdutoras.get(i).start();
			threadsConsumidoras.add(new Thread(new Consumidor(buffer, i)));
			threadsConsumidoras.get(i).start();
			i++;
		}
	}
}
