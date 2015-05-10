package br.com.tanngrisnir.logica;

/**
 * Representa um objeto que vai consumir os dados do buffer da programa. Todo
 * consumidor é executado como uma thread, para que isto seja possível faz-se
 * necessário a implementação da Interface "Runnable" que através do
 * polimorfismo torna o objeto consumidor uma thread.
 * 
 * @version 1.0
 * @author Daiana Paula Tizer Parra
 * @author Fábio de Paula dos Anjos
 * @author Flávio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 * 
 */
public class Consumidor implements Runnable {

	private Buffer buffer; // Referência ao objeto compartilhado.
	private int idThread;

	public Consumidor(Buffer buffer, int idThread) {
		this.buffer = buffer;
		this.idThread = idThread;
	}

	/**
	 * Todo o código que vai ser executado por uma thread deve estar contido
	 * dentro do método "run" da interface "Runnable". Aqui, o método consome
	 * pedidos do buffer previamente preenchido.
	 * 
	 * @version 1.0
	 * @author Flávio Aparecido Ribeiro
	 *
	 */
	@Override
	public void run() {
		while (!buffer.getPedidos().isEmpty()) {
			int i = 0;
			while (i < 10) { // 10 execuções para cada thread, definido na
								// atividade.
				if (buffer.getPedidos().isEmpty()) {
					break;
				}
				try {
					Thread.sleep(10000);
					// Pausa a thread por um determinado tempo em milisegundos.
				} catch (InterruptedException e) {
					System.out.println("A execução da thread falhou.");
					e.printStackTrace();
				}
				buffer.consomePedido(idThread);
				i++;
			}
		}
		Thread.currentThread().interrupt(); // Interrompe a thread após sua
		// execução.
	}
}
