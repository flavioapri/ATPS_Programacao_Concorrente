package br.com.tanngrisnir.logica;

/**
 * Representa um objeto que vai consumir os dados do buffer da programa. Todo
 * consumidor � executado como uma thread, para que isto seja poss�vel faz-se
 * necess�rio a implementa��o da Interface "Runnable" que atrav�s do
 * polimorfismo torna o objeto consumidor uma thread.
 * 
 * @version 1.0
 * @author Daiana Paula Tizer Parra
 * @author F�bio de Paula dos Anjos
 * @author Fl�vio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 * 
 */
public class Consumidor implements Runnable {

	private Buffer buffer; // Refer�ncia ao objeto compartilhado.
	private int idThread;

	public Consumidor(Buffer buffer, int idThread) {
		this.buffer = buffer;
		this.idThread = idThread;
	}

	/**
	 * Todo o c�digo que vai ser executado por uma thread deve estar contido
	 * dentro do m�todo "run" da interface "Runnable". Aqui, o m�todo consome
	 * pedidos do buffer previamente preenchido.
	 * 
	 * @version 1.0
	 * @author Fl�vio Aparecido Ribeiro
	 *
	 */
	@Override
	public void run() {
		while (!buffer.getPedidos().isEmpty()) {
			int i = 0;
			while (i < 10) { // 10 execu��es para cada thread, definido na
								// atividade.
				if (buffer.getPedidos().isEmpty()) {
					break;
				}
				try {
					Thread.sleep(10000);
					// Pausa a thread por um determinado tempo em milisegundos.
				} catch (InterruptedException e) {
					System.out.println("A execu��o da thread falhou.");
					e.printStackTrace();
				}
				buffer.consomePedido(idThread);
				i++;
			}
		}
		Thread.currentThread().interrupt(); // Interrompe a thread ap�s sua
		// execu��o.
	}
}
