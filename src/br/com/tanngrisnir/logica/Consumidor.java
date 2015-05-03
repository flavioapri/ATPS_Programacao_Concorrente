package br.com.tanngrisnir.logica;

import java.util.concurrent.Semaphore;

/**
 * Representa um objeto que vai consumir os dados do buffer da aplicação. Todo
 * consumidor é executado por uma thread, para que isto seja possível faz-se
 * nescessário a implementação da Interface "Runnable" que através do
 * polimorfismo torna o objeto consumidor uma thread.
 * 
 * @version 1.2
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Flavio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 * 
 */
public class Consumidor implements Runnable {

	private Buffer buffer; // Referência ao objeto compartilhado.
	private long tempoInicial;
	private int idThread;
	private Semaphore semaforo;

	public Consumidor(Buffer buffer, int idThread, Semaphore semaforo) {
		this.buffer = buffer;
		this.idThread = idThread;
		this.semaforo = semaforo;
	}

	/**
	 * Para que se possa transformar o objeto consumidor em uma thread é
	 * necessário implementar o método run da interface Runnable. O código
	 * dentro de run é o código que vai ser executado quando a thread for
	 * invocada.
	 * 
	 * @version 1.2
	 * @author Flavio Aparecido Ribeiro
	 *
	 */
	@Override
	public void run() {
		int i = 0;
		while (i < 10) { // 10 execuções para cada thread, definido na
							// atividade.
			// Retorna o tempo inicial de acordo com o atual em
			// milisegundos.
			tempoInicial = System.currentTimeMillis();
			// Aqui podemos ver ama das grandes vantagens no uso
			// da interface List.
			// Sempre que for removido o primeiro elemento da lista
			// todos os
			// elementos precedentes serão realocados
			// automaticamente.
			try {
				Thread.sleep(10000); // Pausa a thread por um
										// determinado
										// tempo em milisegundos.
			} catch (InterruptedException e) {
				System.out.print("A execução da thread falhou.");
				e.printStackTrace();
			}
			try {
				semaforo.acquire();
				buffer.removePedido(idThread, tempoInicial, semaforo);
			} catch (InterruptedException e) {
				System.out.print("Falha ao requisitar a trava para o semáforo.");
				e.printStackTrace();
			} finally {
				semaforo.release();
			}
			i++;
		}
	}
}
