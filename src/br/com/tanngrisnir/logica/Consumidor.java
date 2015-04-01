package br.com.tanngrisnir.logica;

/**
 * Representa um objeto que vai consumir os dados do buffer da aplica��o. Todo
 * consumidor � executado por uma thread, para que isto seja poss�vel faz-se
 * nescess�rio a implementa��o da Interface "Runnable" que atrav�s do
 * polimorfismo torna o objeto consumidor uma thread.
 * 
 * @version 1.0.0
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Flavio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 * 
 */
public class Consumidor implements Runnable {

	private Buffer buffer;
	private long tempoInicialPedido;
	private long tempoTotalPedido;
	private long tempoInicialThread;
	private long tempoTotalThread;
	private long tempoMedioExecucoes;
	private int execucoes;
	private int idThread;

	public Consumidor(Buffer buffer, int idThread) {
		this.buffer = buffer;
		this.idThread = idThread;
	}

	public double getTempoMedioExecucoes() {
		return tempoMedioExecucoes;
	}

	/**
	 * Para que se possa transformar o objeto consumidor em uma thread �
	 * necess�rio implementar o m�todo run da interface Runnable. O c�digo
	 * dentro de run � o c�digo que vai ser executado quando a thread for
	 * invocada.
	 * 
	 * @version 1.0.0
	 * @author Flavio Aparecido Ribeiro
	 *
	 */
	@Override
	public void run() {
		execucoes = 0;
		tempoInicialThread = System.currentTimeMillis();
		while (!buffer.getBuffer().isEmpty()) {
			int i = 0;
			while (i < 10) {
				if (!buffer.getBuffer().isEmpty()) { // Se o buffer n�o estiver
														// vazio...
					System.out.print("\nThread: thread" + idThread);
					System.out.print(" - Pedido ID: "
							+ buffer.getBuffer().get(0).getId());
					// Retorna o tempo inicial de acordo com o atual em
					// milisegundos.
					tempoInicialPedido = System.currentTimeMillis();
					// Aqui podemos ver ama das grandes vantagens no uso
					// da interface List.
					// Sempre que for removido o primeiro elemento da lista
					// todos os
					// elementos precedentes ser�o realocados automaticamente.
					buffer.getBuffer().remove(0);
					try {
						Thread.sleep(10000); // Pausa a thread por um determinado
											// tempo em milisegundos.
					} catch (InterruptedException e) {
						System.out.println("A execu��o da thread falhou!");
						e.printStackTrace();
					}
					tempoTotalPedido = System.currentTimeMillis()
							- tempoInicialPedido;
					System.out.print(" - Tempo de processamento: "
							+ tempoTotalPedido + " ms\n");
				}
				i++;
				System.out.println(this.buffer.getBuffer().size());
			}
			execucoes++;
		}
		// Calcula o tempo total dos 10 pedidos processados na thread
		tempoTotalThread = System.currentTimeMillis() - tempoInicialThread;
		// Calcula o tempo medio dos 10 pedidos processados na thread;
		tempoMedioExecucoes = tempoTotalThread / execucoes;
	}
}
