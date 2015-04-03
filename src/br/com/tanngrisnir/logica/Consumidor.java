package br.com.tanngrisnir.logica;

/**
 * Representa um objeto que vai consumir os dados do buffer da aplicação. Todo
 * consumidor é executado por uma thread, para que isto seja possível faz-se
 * nescessário a implementação da Interface "Runnable" que através do
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
	 * @version 1.0.0
	 * @author Flavio Aparecido Ribeiro
	 *
	 */
	@Override
	public void run() {
		while (!buffer.getBuffer().isEmpty()) {
			int i = 0;
			while (i < 10) {
				if (!buffer.getBuffer().isEmpty()) { // Se o buffer não estiver
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
					// elementos precedentes serão realocados automaticamente.
					buffer.getBuffer().remove(0);
					try {
						Thread.sleep(10000); // Pausa a thread por um
												// determinado
												// tempo em milisegundos.
					} catch (InterruptedException e) {
						System.out.println("A execução da thread falhou!");
						e.printStackTrace();
					}
					tempoTotalPedido = System.currentTimeMillis()
							- tempoInicialPedido;
					System.out.print(" - Tempo de processamento: "
							+ tempoTotalPedido + " ms\n");
				}
				i++;

				if (!buffer.getBuffer().isEmpty()) {
					System.out.println("Pedido: "
							+ this.buffer.getBuffer().size());
				} else {
					System.out.println("O buffer esta vazio.");
				}
			}
		}
	}
}
