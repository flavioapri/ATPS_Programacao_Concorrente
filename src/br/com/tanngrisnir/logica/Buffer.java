package br.com.tanngrisnir.logica;

import java.util.ArrayList;
import java.util.List;

import br.com.tanngrisnir.modelo.Pedido;

/**Classe que gera um buffer populado com 5000 pedidos.
 * @deprecated
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Flavio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 * @version 1.1
 */
public class Buffer {

	private List<Pedido> buffer;
	
	// Cria o objeto buffer que contém uma lista do tipo
	public Buffer() {
		this.buffer = new ArrayList<Pedido>();
	}

	public List<Pedido> getBuffer() {
		return this.buffer;
	}
}
