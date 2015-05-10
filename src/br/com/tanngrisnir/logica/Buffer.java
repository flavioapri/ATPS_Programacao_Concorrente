package br.com.tanngrisnir.logica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

import br.com.tanngrisnir.modelo.Pedido;

/**
 * Classe que gera um buffer que ser� o objeto compartilhado na programa,
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
	private SimpleDateFormat formatoData = new SimpleDateFormat("hh:mm:ss,SSS");
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

	/**
	 * M�todo responsavel por adicionar os pedidos ao buffer. O m�todo agora faz
	 * o uso de sem�foros para o controle de acesso ao objeto compartilhado.
	 * 
	 * @version 1.2
	 * @author Fl�vio Aparecido Ribeiro
	 * @param Semaphore
	 *            semaforo
	 * @param Pedido
	 *            pedido
	 * @param int idThread - ID da thread.
	 */
	public void inserePedido(Pedido pedido, int idThread, Date tempoInicial,
			Semaphore semaforo) {
		while (pedidos.size() > 5000) { // Se o buffer estiver cheio manda a
										// thread aguardar at� que uma thread
										// consumidora libere espa�o no buffer
										// para inserir um pedido.
			try {
				System.out.println("produtor#" + idThread + " aguardando...");
				semaforo.wait(); // Manda a thread aguardar at� que seja
				// notificada por
				// outra.
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			semaforo.acquire();
			pedidos.add(pedido);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaforo.release();
		}
		Date tempoFinal = new Date(System.currentTimeMillis());
		System.out.println("produtor#" + idThread + " produziu o pedido "
				+ pedido.getId() + " - In�cio: "
				+ formatoData.format(tempoInicial) + " T�rmino: "
				+ formatoData.format(tempoFinal));
	}

	/**
	 * M�todo respons�vel por remover os pedidos do buffer. O m�todo agora faz o
	 * uso de sem�foros para o controle de acesso ao objeto compartilhado.
	 * 
	 * @version 1.2
	 * @author Flavio Aparecido Ribeiro
	 * @param semaforo
	 */
	public void removePedido(int idThread, Date tempoInicial, Semaphore semaforo) {
		Pedido pedido;
		while (pedidos.isEmpty()) { // Enquanto o buffer estiver vazio manda a
									// thread aguardar at� que uma thread
									// produtora insira pedidos.
			try {
				System.out.println("consumidor#" + idThread + " aguardando...");
				semaforo.wait(); // Manda a thread aguradar at� que seja
				// notificada por outra.
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		pedido = pedidos.get(0);
		try {
			semaforo.acquire();
			pedidos.remove(pedido); // Remove o pedido.
		} catch (Exception e) {
			System.out.println("Falha ao solicitar sem�foro.");
		} finally {
			semaforo.release();
		}
		pedidosProcessados++; // Foi considerado como pedido processado todo o
		// pedido que foi produzido, e ap�s, consumido.
		Date tempoFinal = new Date(System.currentTimeMillis());
		System.out.println("consumidor#" + idThread + " consumiu o pedido "
				+ pedido.getId() + " - In�cio: "
				+ formatoData.format(tempoInicial) + " T�rmino: "
				+ formatoData.format(tempoFinal));
	}
}
