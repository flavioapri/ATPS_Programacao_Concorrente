package br.com.tanngrisnir.logica;

import java.util.ArrayList;
import java.util.List;

import br.com.tanngrisnir.modelo.Pedido;

/**
 * Classe que gera um buffer que será o objeto compartilhado na aplicação,
 * populado com 5000 pedidos.
 * 
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Flavio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 * @version 1.2
 */
public class Buffer {

	private List<Pedido> pedidos;
	private static int pedidosProcessados;
	private static long tempoTotalDeProcessamento;

	public Buffer() {
		this.pedidos = new ArrayList<Pedido>(5000);
		Buffer.pedidosProcessados = 0;
		Buffer.tempoTotalDeProcessamento = 0;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public static int getPedidosProcessados() {
		return pedidosProcessados;
	}
	
	public static long getTempoTotalDeProcessamento() {
		return tempoTotalDeProcessamento;
	}

	/**
	 * Método responsavel por adicionar os pedidos ao buffer. A palavra chave
	 * "synchronized" informa que o código de região crítica dentro do método
	 * deve ser realizado de maneira sincronizada, impedindo que outras threads
	 * acessem ao mesmo tempo esta região, impede-se assim a colisão de dados na
	 * aplicação.
	 * 
	 * @version 1.0
	 * @author Flavio Aparecido Ribeiro
	 */
	public synchronized void inserePedido(Pedido pedido, int idThread,
			long tempoInicial) {
		while (pedidos.size() > 5000) { // Se o buffer estiver cheio manda a
										// thread aguardar até que uma thread
										// consumidora libere espaço no buffer
										// para inserir um pedido.
			try {
				System.out.println("produtor" + idThread + " aguardando...");
				wait(); // Manda a thread aguradar até que seja notificada por
						// outra.
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		pedidos.add(pedido);
		System.out.println("produtor" + idThread + " inseriu o pedido "
				+ pedido.getId() + " - Tempo de processamento "
				+ (System.currentTimeMillis() - tempoInicial) + " ms\n");
		tempoTotalDeProcessamento += System.currentTimeMillis() - tempoInicial;
		notifyAll(); // Informa as outras threads que o acesso foi liberado por
						// esta.
	}

	/**
	 * Método responsável por remover os pedidos do buffer. A palavra chave
	 * "synchronized" informa que o código de região crítica dentro do método
	 * deve ser realizado de maneira sincronizada, impedindo que outras threads
	 * acessem ao mesmo tempo esta região, impede-se assim a colisão de dados na
	 * aplicação.
	 * 
	 * @version 1.0
	 * @author Flavio Aparecido Ribeiro
	 */
	public synchronized void removePedido(int idThread, long tempoInicial) {
		Pedido pedido;
		while (pedidos.isEmpty()) { // Enquanto o buffer estiver vazio manda a
									// thread aguardar até que uma thread
									// produtora insira pedidos.
			try {
				System.out.println("consumidor" + idThread + " aguardando...");
				wait(); // Manda a thread aguradar até que seja notificada por
						// outra.
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		pedido = pedidos.get(0);
		pedidos.remove(0); // Remove o pedido.
		pedidosProcessados++; // Foi considerado como pedido processado todo o
								// pedido que foi produzido e depois consumido.
		System.out.println("consumidor" + idThread + " removeu o pedido "
				+ pedido.getId() + " - Tempo de processamento "
				+ (System.currentTimeMillis() - tempoInicial) + " ms\n");
		tempoTotalDeProcessamento += System.currentTimeMillis() - tempoInicial;
		notifyAll(); // Informa as outras threads que o acesso foi liberado por
						// esta.
	}
}
