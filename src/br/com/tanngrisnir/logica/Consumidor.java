package br.com.tanngrisnir.logica;

import java.util.Date;

/**
 * Representa um objeto que vai consumir os dados do buffer da programa. Todo
 * consumidor é executado por uma thread, para que isto seja possível faz-se
 * nescessário a implementação da Interface "Runnable" que através do
 * polimorfismo torna o objeto consumidor uma thread.
 * 
 * @version 1.1
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Flavio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 * 
 */
public class Consumidor implements Runnable {

	private Buffer buffer; // Referência ao objeto compartilhado.
	private Date tempoInicial;
	private int idThread;

	public Consumidor(Buffer buffer, int idThread) {
		this.buffer = buffer;
		this.idThread = idThread;
	}

	/**
	 * Para que se possa transformar o objeto consumidor em uma thread é
	 * necessário implementar o método run da interface Runnable. O código
	 * dentro de run é o código que vai ser executado quando a thread for
	 * invocada.
	 * 
	 * @version 1.1
	 * @author Flavio Aparecido Ribeiro
	 *
	 */
	@Override
	public void run() {
		boolean producao = true;
		while (producao) {
			// Retorna o tempo inicial de acordo com o atual em
			// milisegundos.
			tempoInicial = new Date(System.currentTimeMillis());
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
				System.out.println("A execução da thread falhou.");
				e.printStackTrace();
			}
			buffer.removePedido(idThread, tempoInicial);
		}
	}
}
