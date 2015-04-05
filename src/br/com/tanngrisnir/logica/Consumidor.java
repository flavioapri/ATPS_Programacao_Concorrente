package br.com.tanngrisnir.logica;

import java.math.BigInteger;
import java.util.List;

import br.com.tanngrisnir.modelo.Pedido;

/**
 * Representa um objeto que vai consumir os dados do buffer da aplicação. Todo
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

	private List<Pedido> buffer;
	private long tempoInicialPedido;
	private long tempoTotalPedido;
	private int idThread;
	private static int pedidosProcessados = 0;

	public Consumidor(List<Pedido> buffer, int idThread) {
		this.buffer = buffer;
		this.idThread = idThread;
	}

	public static int getPedidosProcessados() {
		return pedidosProcessados;
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
		synchronized (this) {
			while (!buffer.isEmpty() && !Produtor.isTempoEsgotado()) {
				int i = 0;
				BigInteger idPedido = buffer.get(i).getId();
				while (i < 10) {
					if (!buffer.isEmpty()) { // Se o buffer não
												// estiver
												// vazio...
						// Retorna o tempo inicial de acordo com o atual em
						// milisegundos.
						tempoInicialPedido = System.currentTimeMillis();
						// Aqui podemos ver ama das grandes vantagens no uso
						// da interface List.
						// Sempre que for removido o primeiro elemento da lista
						// todos os
						// elementos precedentes serão realocados
						// automaticamente.
						buffer.remove(0);
						try {
							Thread.sleep(10000); // Pausa a thread por um
													// determinado
													// tempo em milisegundos.
						} catch (InterruptedException e) {
							System.out.println("A execução da thread falhou!");
							e.printStackTrace();
						}
						if (!buffer.isEmpty()) {
							tempoTotalPedido = System.currentTimeMillis()
									- tempoInicialPedido;
							System.out.println("\nThread: cosumidor" + idThread
									+ " - Pedido ID: " + idPedido
									+ " - Tempo de processamento: "
									+ tempoTotalPedido + " ms");
							pedidosProcessados++;
						}
					}
					i++;
				}
			}
		}
	}
}
