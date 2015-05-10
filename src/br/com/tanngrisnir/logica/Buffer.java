package br.com.tanngrisnir.logica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.tanngrisnir.modelo.Pedido;

/**
 * Classe que gera um buffer que será o objeto compartilhado no protótipo,
 * populado com 5000 pedidos.
 * 
 * @author Daiana Paula Tizer Parra
 * @author Fábio de Paula dos Anjos
 * @author Flávio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 * @version 1.0
 */
public class Buffer {
	private List<Pedido> pedidos;

	public Buffer() {
		this.pedidos = new ArrayList<Pedido>(5000);
		Produtor produtor = new Produtor();
		for (int i = 0; i < 5000; i++) {
			// Popula todas as posições do buffer com pedidos gerados pelo
			// produtor.
			pedidos.add(produtor.geraPedido());
		}
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	/**
	 * Método responsável por remover os pedidos do buffer.
	 * 
	 * @param int idThread - ID da thread.
	 * @version 1.0
	 * @author Flávio Aparecido Ribeiro
	 */
	public void consomePedido(int idThread) {
		if (!pedidos.isEmpty()) { // Se o buffer estiver vazio não executa a
									// lógica.
			Pedido pedido;
			Date horarioInicio = new Date(System.currentTimeMillis());
			SimpleDateFormat formatoData = new SimpleDateFormat("hh:mm:ss,SSS");
			pedido = pedidos.get(0);
			pedidos.remove(pedido); // Remove o pedido.
			Date horarioTermino = new Date(System.currentTimeMillis());
			System.out.println("consumidor#" + idThread + " consumiu o pedido "
					+ pedido.getId() + " - Inicio: "
					+ formatoData.format(horarioInicio) + " Término: "
					+ formatoData.format(horarioTermino));
		}
	}
}
