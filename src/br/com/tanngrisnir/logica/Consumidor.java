package br.com.tanngrisnir.logica;

/**
 * Representa um objeto que vai consumir os dados do buffer da aplica��o. Todo
 * consumidor � executado por uma thread, para que isto seja poss�vel faz-se
 * nescess�rio a implementa��o da Interface "Runnable" que atrav�s do
 * polimorfismo torna o objeto consumidor uma thread.
 * 
 * @version 1.0.0
 * @author Flavio Aparecido Ribeiro
 *
 */
public class Consumidor implements Runnable {

	private Buffer buffer;
	private long tempoInicialPedido;
	private long tempoTotalPedido;
	private long tempoInicialThread;
	private long tempoTotalThread;
	private double tempoMedioExecucoes;
	private int execucoes;

	
	public Consumidor(Buffer buffer) {
		this.buffer = buffer;
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

		System.out.println("Executando a thread. Aguarde...\n");

		execucoes = 0;
		tempoInicialThread = System.currentTimeMillis();
		while (!buffer.getBuffer().isEmpty()) {
			int i = 0;
			while (i < 10) {
				if (!buffer.getBuffer().isEmpty()) { // Se o buffer n�o estiver
														// vazio...
					System.out.println("Thread: consumidor1");
					System.out.println("Pedido ID: "
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
						Thread.sleep(1); // Pausa a thread por um determinado
											// tempo em milisegundos.
					} catch (InterruptedException e) {
						System.out.println("A execu��o da thread falhou!");
						e.printStackTrace();
					}
					tempoTotalPedido = System.currentTimeMillis()
							- tempoInicialPedido;
					System.out.println("Tempo de processamento: "
							+ tempoTotalPedido + " ms\n");
				}
				i++;
			}
			execucoes++;
		}
		tempoTotalThread = System.currentTimeMillis() - tempoInicialThread;
		tempoMedioExecucoes = tempoTotalThread / execucoes;
		System.out.println("Thread: consumidor1");
		System.out.println("Tempo m�dio para 10 execu��es: " + tempoMedioExecucoes
				+ " ms");
	}
}
