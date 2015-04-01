package br.com.tanngrisnir.logica;

import java.util.ArrayList;
import java.util.List;

import br.com.tanngrisnir.modelo.Pedido;

/**Classe que gera um buffer populado com 5000 pedidos.
 * 
 * @author Flavio Aparecido Ribeiro
 * @version 1.0.0
 */
public class Buffer {

	private Produtor produtor = new Produtor();
	private List<Pedido> buffer;
	
	// Cria o objeto buffer que contém uma lista do tipo
	// Pedido com 5000 pedidos
	public Buffer() {
		this.buffer = new ArrayList<Pedido>();
		int i = 0;
		while (i < 5000) {
			this.buffer.add(produtor.getPedido());
			i++;
		}
	}

	public List<Pedido> getBuffer() {
		return this.buffer;
	}
}
