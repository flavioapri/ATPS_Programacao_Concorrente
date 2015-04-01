package br.com.tanngrisnir.logica;

/**
 * Representa um objeto que vai consumir os dados do buffer da aplicação. Todo
 * consumidor é executado por uma thread, para que isto seja possível faz-se
 * nescessário a implementação da Interface "Runnable" que através do
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
	 * Para que se possa transformar o objeto consumidor em uma thread é
	 * necessário implementar o método run da interface Runnable. O código
	 * dentro de run é o código que vai ser executado quando a thread for
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
				if (!buffer.getBuffer().isEmpty()) { // Se o buffer não estiver
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
					// elementos precedentes serão realocados automaticamente.
					buffer.getBuffer().remove(0);
					try {
						Thread.sleep(1); // Pausa a thread por um determinado
											// tempo em milisegundos.
					} catch (InterruptedException e) {
						System.out.println("A execução da thread falhou!");
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
		System.out.println("Tempo médio para 10 execuções: " + tempoMedioExecucoes
				+ " ms");
	}
}
