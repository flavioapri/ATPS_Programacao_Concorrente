package br.com.tanngrisnir.teste;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import br.com.tanngrisnir.logica.Buffer;
import br.com.tanngrisnir.logica.Consumidor;
import br.com.tanngrisnir.logica.Produtor;

/**
 * Classe principal para teste da programa.
 * 
 * @version 1.2
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Fl�vio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 *
 */
public class TestaAplicacao {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException {
		int qtdThreads; // Quantidade de threads a serem executadas.
		long tempoInicialDeProcessamento;
		Date tempo;
		DateFormat formatoTempo;
		String tempoTotalDeProcessamento;
		Buffer buffer = new Buffer(); // Objeto compartilhado
		Timer timer = new Timer(); // Objeto que vai contar o tempo
		// O par�metro recebido no construtor informa quantas threads podem
		// acessar a regi�o cr�tica do objeto compartilhado simult�neamente.
		Semaphore semaforo = new Semaphore(1);
		Scanner e = new Scanner(System.in);
		System.out.print("Informe a quantidade de threads: ");
		qtdThreads = e.nextInt();
		// Listas que v�o armazenar as respectivas threads com
		// a quantidade informada.
		List<Thread> threadsProdutoras = new ArrayList<Thread>(qtdThreads);
		List<Thread> threadsConsumidoras = new ArrayList<Thread>(qtdThreads);
		System.out.println("\nInicializando threads. Aguarde...\n");
		// Inicia a contagem do tempo de processamento da programa.
		tempoInicialDeProcessamento = System.currentTimeMillis();
		for (int i = 0; i < qtdThreads; i++) {
			// Popula as listas de threads e as inicializa atrav�s do m�todo
			// start().
			threadsProdutoras
					.add(new Thread(new Produtor(buffer, i, semaforo)));
			threadsProdutoras.get(i).start();
			threadsConsumidoras.add(new Thread(new Consumidor(buffer, i,
					semaforo)));
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
		 * @version 1.1
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
			}
		}, 120000); // Par�metro que define o tempo para a execu��o da tarefa
					// (em milisegundos).
		for (int i = 0; i < qtdThreads; i++) {
			// Manda o programa aguardar at� que todas a threads morram para
			// continuar o fluxo normal.
			threadsConsumidoras.get(i).join();
			threadsProdutoras.get(i).join();
		}
		tempo = new Date(System.currentTimeMillis()
				- tempoInicialDeProcessamento);
		formatoTempo = new SimpleDateFormat("mm:ss,SSS");
		tempoTotalDeProcessamento = formatoTempo.format(tempo);
		System.out.println("Tempo total de processamento: "
				+ tempoTotalDeProcessamento);
	}
}
