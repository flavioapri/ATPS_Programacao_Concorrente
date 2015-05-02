package br.com.tanngrisnir.logica;

import java.util.ArrayList;
import java.util.List;

import br.com.tanngrisnir.modelo.Pedido;

/**
 * Classe que gera um buffer populado com 5000 pedidos.
 * 
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Flavio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 * @version 1.1
 */
public class Buffer {

	private List<Pedido> pedidos;
	private static int pedidosProcessados;

	public Buffer() {
		this.pedidos = new ArrayList<Pedido>(5000);
		Buffer.pedidosProcessados = 0;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public static int getPedidosProcessados() {
		return pedidosProcessados;
	}

	public synchronized void set(Pedido pedido, int idThread, long tempoInicial) {
		while (pedidos.size() > 5000) {
			try {
				System.out.println("produtor" + idThread + " aguardando...");
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		pedidos.add(pedido);
		System.out.println("produtor" + idThread + " inseriu o pedido "
				+ pedido.getId() + " - Tempo de processamento "
				+ (System.currentTimeMillis() - tempoInicial) + " ms\n");
		notifyAll();
	}

	public synchronized void get(int idThread, long tempoInicial) {
		Pedido pedido;
		while (pedidos.isEmpty()) {
			try {
				System.out.println("consumidor" + idThread + " aguardando...");
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		pedido = pedidos.get(0);
		pedidos.remove(0);
		pedidosProcessados++;
		System.out.println("consumidor" + idThread + " removeu o pedido "
				+ pedido.getId() + " - Tempo de processamento "
				+ (System.currentTimeMillis() - tempoInicial) + " ms\n" + "Buffer size: " + pedidos.size());
		notifyAll();
	}
}
