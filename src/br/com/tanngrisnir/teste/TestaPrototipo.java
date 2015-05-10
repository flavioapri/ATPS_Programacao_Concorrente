package br.com.tanngrisnir.teste;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import br.com.tanngrisnir.logica.Buffer;
import br.com.tanngrisnir.logica.Consumidor;
import br.com.tanngrisnir.logica.Produtor;

/**
 * Classe principal para teste da programa.
 * 
 * @version 1.1
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Fl�vio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 *
 */
public class TestaPrototipo {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException {
		int qtdThreads; // Quantidade de threads a serem executadas.
		Buffer buffer = new Buffer(); // Objeto compartilhado
		Timer timer = new Timer(); // Objeto que vai contar o tempo
		Scanner e = new Scanner(System.in);
		System.out.print("Informe a quantidade de threads: ");
		qtdThreads = e.nextInt();
		// Listas que v�o armazenar as respectivas threads com
		// a quantidade informada.
		List<Thread> threadsProdutoras = new ArrayList<Thread>(qtdThreads);
		List<Thread> threadsConsumidoras = new ArrayList<Thread>(qtdThreads);
		System.out.println("\nInicializando threads. Aguarde...\n");
		// Inicia a contagem do tempo de processamento da programa.
		for (int i = 0; i < qtdThreads; i++) {
			// Popula as listas de threads e as inicializa atrav�s do m�todo
			// start().
			threadsProdutoras.add(new Thread(new Produtor(buffer, i)));
			threadsProdutoras.get(i).start();
			threadsConsumidoras.add(new Thread(new Consumidor(buffer, i)));
			threadsConsumidoras.get(i).start();
		}

		/**
		 * O m�todo "schedule" agenda uma tarefa para que seja realizada ap�s um
		 * determinado tempo uma �nica vez. Aqui o m�todo � utilizado para
		 * interromper a execu��o de todas as threads e informar informar a
		 * quantidade de pedidos processados pela programa ap�s tempo
		 * predeterminado de 3 minutos (180000 ms) que � o especificado na
		 * atividade.
		 * 
		 * @version 1.0
		 * @author Fl�vio Aparecido Ribeiro
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				for (int i = 0; i < qtdThreads; i++) { // Interrompe todas as
														// threads.
					threadsConsumidoras.get(i).interrupt();
					threadsProdutoras.get(i).interrupt();
				}
				System.out
						.println("Total de pedidos processados no tempo limite de 3 min: "
								+ Buffer.getPedidosProcessados());
				timer.cancel(); // Termina a execu��o da tarefa.
				System.exit(0); // Encerra o programa.
			}
		}, 180000); // Par�metro que define o tempo para a execu��o da tarefa
					// (em milisegundos).
	}
}
