package br.com.tanngrisnir.teste;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import br.com.tanngrisnir.logica.Buffer;
import br.com.tanngrisnir.logica.Consumidor;
import br.com.tanngrisnir.logica.Produtor;

/**
 * Classe de testes para a execução de threads Produtoras e Consumidoras.
 * 
 * @version 1.1
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Flavio Aparecido Ribeiro5
 * @author Samuel Raul Gennari
 *
 */
public class TestaThreadsProdutorasCosumidoras {
	public static void main(String[] args) throws InterruptedException {
		int qtdThreads; // Quantidade de threads a serem executadas.
		Buffer buffer = new Buffer();
		Timer timer = new Timer();
		Scanner e = new Scanner(System.in);
		System.out.print("Informe a quantidade de threads: ");
		qtdThreads = e.nextInt();
		// Listas que vão armazenar a respectiva quantidade
		// de threads
		List<Thread> threadsProdutoras = new ArrayList<Thread>(qtdThreads);
		List<Thread> threadsConsumidoras = new ArrayList<Thread>(qtdThreads);
		System.out.println("\nInicializando threads. Aguarde...\n");
		for (int i = 0; i < qtdThreads; i++) {
			// Popula as listas de threads
			threadsProdutoras.add(new Thread(new Produtor(i, buffer)));
			threadsProdutoras.get(i).start();
			threadsConsumidoras.add(new Thread(new Consumidor(buffer, i)));
			threadsConsumidoras.get(i).start();
		}
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Total de pedidos processados em 3 min: "
						+ Buffer.getPedidosProcessados());
				timer.cancel();
			}
		}, 180000);
		for (int i = 0; i < qtdThreads; i++) {
			threadsConsumidoras.get(i).join();
			threadsProdutoras.get(i).join();
		}
	}
}
