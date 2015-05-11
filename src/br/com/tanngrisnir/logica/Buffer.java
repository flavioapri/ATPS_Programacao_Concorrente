package br.com.tanngrisnir.logica;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import br.com.tanngrisnir.modelo.Pedido;

/**
 * Classe que gera um buffer que será o objeto compartilhado na programa,
 * populado com 5000 pedidos.
 * 
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Flavio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 * @version 1.3
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
	 * Método responsavel por adicionar os pedidos ao buffer. O método esta
	 * utilizando sincronismo entre as threads através das chamadas a wait() e
	 * notifyAll().
	 * 
	 * @version 1.3
	 * @author Flávio Aparecido Ribeiro
	 */
	public synchronized void inserePedido(Pedido pedido, int idThread, long tempoInicial,
			Semaphore semaforo) {
		while (pedidos.size() > 5000) {
			// Se o buffer estiver cheio manda a thread aguradar a liberação do
			// recurso através da chamada a wait.
			try {
				System.out.println("produtor#" + idThread + " aguardando...");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		pedidos.add(pedido);
		System.out.println("produtor#" + idThread + " inseriu o pedido "
				+ pedido.getId() + " - Tempo de processamento "
				+ (System.currentTimeMillis() - tempoInicial) + " ms\n");
	}

	/**
	 * Método responsável por remover os pedidos do buffer. O método esta
	 * utilizando sincronismo entre as threads através das chamadas a wait() e
	 * notifyAll().
	 * 
	 * @version 1.3
	 * @author Flávio Aparecido Ribeiro
	 */
	public synchronized void removePedido(int idThread, long tempoInicial, Semaphore semaforo) {
		while (pedidos.isEmpty()) {
			// Se o buffer estiver vazio manda a thread aguardar um produtor
			// inserir um pedido, para assim, poder ser consumido.
			try {
				System.out.println("consumidor#" + idThread + " aguardando...");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Pedido pedido;
		pedido = pedidos.get(0);
		pedidos.remove(pedido); // Remove o pedido.
		pedidosProcessados++; // Foi considerado como pedido processado todo
								// o
								// pedido que foi produzido e depois
								// consumido.
		System.out.println("consumidor#" + idThread + " removeu o pedido "
				+ pedido.getId() + " - Tempo de processamento "
				+ (System.currentTimeMillis() - tempoInicial) + " ms\n");
		tempoTotalDeProcessamento += System.currentTimeMillis() - tempoInicial;
	}
}
