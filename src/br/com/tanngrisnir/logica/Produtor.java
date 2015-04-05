package br.com.tanngrisnir.logica;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import br.com.tanngrisnir.modelo.Pedido;

/**
 * Gera pedidos com ID de 20 algarismos e uma string representando um pacote de
 * dados com 1000 caracteres de texto. Agora a classe implementa Runnable para
 * poder trabalhar como thread e foram implementados os loops de repetição para
 * realizar as 10 execuções das threads e o parametro para realizar o controle
 * do tempo de 3 minutos de execução nestas.
 * 
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Flavio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 * @version 1.1
 */
public class Produtor implements Runnable {

	// Objeto gerador de caracteres aleatórios.
	private Random gerador = new Random();
	// Array populado com caracteres aleatórios para a posterior composição do
	// atributo "Id" do pedido.
	private char valoresAleatorios[] = new char[20];
	// Array populado com caracteres aleatórios para a posterior composição do
	// atributo "Pacote de Dados" do pedido.
	private char caracteresAleatorios[] = new char[1000];
	// Objeto compartilhado.
	private List<Pedido> buffer;
	private int idThread;
	private long tempoInicial;
	private long tempoTotal;
	private long tempoInicialThread;
	private long tempoTotalThread;
	private static int pedidosProcessados;
	private static boolean tempoEsgotado;

	public Produtor(List<Pedido> buffer, int idThread) {
		this.buffer = buffer;
		this.idThread = idThread;
	}

	public static int getPedidosProcessados() {
		return pedidosProcessados;
	}
	
	public static boolean isTempoEsgotado() {
		return tempoEsgotado;
	}

	/**
	 * Gera objetos do tipo Pedido
	 */
	public void run() {
		synchronized (this) {
			// Executa o loop enquanto houverem espaços vazios no buffer e
			// o tempo for inferior a 3 minutos que corresponde a 180000
			// milisegundos
			tempoInicialThread = System.currentTimeMillis();
			while (tempoTotalThread < 180000) {
				int j = 0;
				while (j < 10) {
					if (buffer.size() < 5000 && tempoTotalThread < 180000) {
						int i = 0;
						tempoInicial = System.currentTimeMillis();
						Pedido pedido = new Pedido();
						// Gera valores aleatórios de caracteres para o array, o
						// valor
						// 10 para o
						// método nextInt é utilizado para indicar que seram
						// gerados
						// valores de
						// 0 a 9 e '0' é para indicar que devem ser gerados
						// caracteres.
						while (i < 20) {
							valoresAleatorios[i] = (char) (gerador.nextInt(10) + '0');
							i++;
						}
						pedido.setId(new BigInteger(new String(
								valoresAleatorios)));
						i = 0;
						// Gera valores aleatórios de caracteres para o array, o
						// valor
						// 26 para o
						// método nextInt é utilizado para indicar que seram
						// gerados
						// valores de
						// 0 a 25 e 'a' é para indicar que devem ser gerados os
						// caracteres que
						// representam letras.
						while (i < 1000) {
							caracteresAleatorios[i] = (char) (gerador
									.nextInt(26) + 'a');
							i++;
						}
						pedido.setPacoteDados(new String(caracteresAleatorios));
						this.buffer.add(pedido);
						try {
							// Pausa a thread por um determinado tempo em
							// milisegundos
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							System.out.println("A execução da thread falhou!");
							e.printStackTrace();
						}
						if (buffer.size() < 5000) {
							tempoTotal = System.currentTimeMillis()
									- tempoInicial;
							System.out.println("\nThread: produtor" + idThread
									+ " - Pedido ID: " + pedido.getId()
									+ " - Tempo de processamento: "
									+ tempoTotal + " ms");
							pedidosProcessados++;
						} else {
							break;
						}
					}
					tempoTotalThread = System.currentTimeMillis()
							- tempoInicialThread;
					if (tempoTotalThread > 180000) {
						tempoEsgotado = true;
						// Sai do loop se o tempo for superior a 3 minutos
						break;
					}
					j++;
				}
			}
		}
	}
}
