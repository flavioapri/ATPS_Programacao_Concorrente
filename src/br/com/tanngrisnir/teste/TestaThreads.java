package br.com.tanngrisnir.teste;

import java.util.ArrayList;
import java.util.List;

import br.com.tanngrisnir.logica.Buffer;
import br.com.tanngrisnir.logica.Consumidor;

/**
 * Classe de testes para a execu��o das threads.
 * 
 * @version 1.0.0
 * @author Daiana Paula Tizer Parra
 * @author Fabio de Paula dos Anjos
 * @author Flavio Aparecido Ribeiro
 * @author Samuel Raul Gennari
 *
 */
public class TestaThreads {

	public static void main(String[] args) throws InterruptedException {

		int tempoTotalExecucaoQtdThreads = 0;
		double tempoMedioExecucaoQtdThreads = 0;
		int qtdThreads = 1; // Quantidade de threads a serem executadas.

		// Foram usadas duas listas para o teste porque a l�gica de c�lculo da
		// media do tempo de execu��o de cada thread esta encapsulado na classe
		// Consumidor, ap�s a execu��o de uma thread n�o � poss�vel acessar
		// de alguma maneira o atributo com este valor.
		// As duas listas cont�m refer�ncias para os mesmos objetos, ent�o toda
		// a altera��o feita em tempo de execu��o em cada thread pode ser
		// acessada atrav�s da refer�ncia em consumidores.
		List<Consumidor> consumidores = new ArrayList<Consumidor>();
		List<Thread> threads = new ArrayList<Thread>();

		int i = 0;
		while (i < qtdThreads) {
			// Cria uma lista de consumidores de acordo com a respectiva
			// quantidade de
			// threads informadas.
			consumidores.add(new Consumidor(new Buffer(), i));
			// Cria uma lista de threads que recebe refer�ncias para execu��o
			// para os respectivos objetos em consumidores
			threads.add(new Thread(consumidores.get(i)));
			i++;
		}

		i = 0;
		while (i < threads.size()) {
			// Executa as threads.
			threads.get(i).start();
			i++;
		}

		i = 0;
		while (i < threads.size()) {
			// O m�todo join serve para bloquear as threads, para que
			// o restante no c�digo nesta classe de testes possa ser
			// executado.
			// Deve ser executado em todas as threads em execu��o.
			threads.get(i).join();
			i++;
		}

		i = 0;
		while (i < consumidores.size()) {
			// Soma o tempo total de todas as threads
			tempoTotalExecucaoQtdThreads += consumidores.get(i)
					.getTempoMedioExecucoes();
			i++;
		}

		// Calcula o tempo m�dio de execu��o de cada thread
		tempoMedioExecucaoQtdThreads = tempoTotalExecucaoQtdThreads
				/ qtdThreads;

		System.out.println("\nTempo total de execu��o: "
				+ tempoTotalExecucaoQtdThreads + " ms");

		System.out.println("\nTempo m�dio de execu��o para " + qtdThreads
				+ " threads: " + tempoMedioExecucaoQtdThreads + " ms");
	}
}
