package br.com.tanngrisnir.teste;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import br.com.tanngrisnir.logica.Buffer;
import br.com.tanngrisnir.logica.Consumidor;
import br.com.tanngrisnir.logica.Produtor;

/**
 * Classe principal para teste da aplica��o.
 * 
 * @version 1.2
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Fl�vio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 *
 */
public class TestaAplicacao {
	public static void main(String[] args) throws InterruptedException {
		int qtdThreads; // Quantidade de threads a serem executadas.
		Buffer buffer = new Buffer(); // Objeto compartilhado
		Timer timer = new Timer(); // Objeto que vai contar o tempo
		Semaphore semaforo = new Semaphore(1);
		Scanner e = new Scanner(System.in);
		System.out.print("Informe a quantidade de threads: ");
		qtdThreads = e.nextInt();
		// Listas que v�o armazenar as respectivas threads com
		// a quantidade informada.
		List<Thread> threadsProdutoras = new ArrayList<Thread>(qtdThreads);
		List<Thread> threadsConsumidoras = new ArrayList<Thread>(qtdThreads);
		System.out.println("\nInicializando threads. Aguarde...\n");
		for (int i = 0; i < qtdThreads; i++) {
			// Popula as listas de threads
			threadsProdutoras.add(new Thread(new Produtor(buffer, i, semaforo)));
			threadsProdutoras.get(i).start();
			threadsConsumidoras.add(new Thread(new Consumidor(buffer, i, semaforo)));
			threadsConsumidoras.get(i).start();
		}

		/**
		 * O m�todo "schedule" agenda uma tarefa para que seja realizada ap�s um
		 * determinado tempo uma �nica vez. Aqui o m�todo � utilizado para
		 * informar a quantidade de pedidos processados pela aplica��o ap�s um
		 * tempo predeterminado que no caso � 3 minutos (180000 ms).
		 * 
		 * @version 1.0
		 * @author Fl�vio Aparecido Ribeiro
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Total de pedidos processados em 3 min: "
						+ Buffer.getPedidosProcessados());
				timer.cancel(); // Termina a execu��o da tarefa.

			}
		}, 180000); // Par�metro que define o tempo para a execu��o da tarefa
					// (em milisegundos).
		for (int i = 0; i < qtdThreads; i++) {
			// O m�todo join() vai informar quando a thread morrer para a
			// aplica��o, dando continuidade ao fluxo normal desta.
			threadsConsumidoras.get(i).join();
			threadsProdutoras.get(i).join();
		}
	}
}
