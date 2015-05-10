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
 * Classe principal para teste da programa.
 * 
 * @version 1.2
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Flávio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 *
 */
public class TestaPrototipo {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException {
		int qtdThreads; // Quantidade de threads a serem executadas.
		Semaphore semaforo = new Semaphore(1);
		// Semáforo que fara o controle do acesso aos recursos. O argumento
		// informa o número de acessos simultaneos que podem ser feitos.
		Buffer buffer = new Buffer(); // Objeto compartilhado
		Timer timer = new Timer(); // Objeto que vai contar o tempo
		Scanner e = new Scanner(System.in);
		System.out.print("Informe a quantidade de threads: ");
		qtdThreads = e.nextInt();
		// Listas que vão armazenar as respectivas threads com
		// a quantidade informada.
		List<Thread> threadsProdutoras = new ArrayList<Thread>(qtdThreads);
		List<Thread> threadsConsumidoras = new ArrayList<Thread>(qtdThreads);
		System.out.println("\nInicializando threads. Aguarde...\n");
		// Inicia a contagem do tempo de processamento da programa.
		for (int i = 0; i < qtdThreads; i++) {
			// Popula as listas de threads e as inicializa através do método
			// start().
			threadsProdutoras.add(new Thread(new Produtor(buffer, i, semaforo)));
			threadsProdutoras.get(i).start();
			threadsConsumidoras.add(new Thread(new Consumidor(buffer, i, semaforo)));
			threadsConsumidoras.get(i).start();
		}

		/**
		 * O método "schedule" agenda uma tarefa para que seja realizada após um
		 * determinado tempo uma única vez. Aqui o método é utilizado para
		 * interromper a execução de todas as threads e informar informar a
		 * quantidade de pedidos processados pela programa após tempo
		 * predeterminado de 3 minutos (180000 ms) que é o especificado na
		 * atividade.
		 * 
		 * @version 1.0
		 * @author Flávio Aparecido Ribeiro
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
				timer.cancel(); // Termina a execução da tarefa.
				System.exit(0); // Encerra o programa.
			}
		}, 180000); // Parâmetro que define o tempo para a execução da tarefa
					// (em milisegundos).
	}
}
