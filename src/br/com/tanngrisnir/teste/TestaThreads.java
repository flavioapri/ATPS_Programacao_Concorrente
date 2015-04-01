package br.com.tanngrisnir.teste;

import java.util.ArrayList;
import java.util.List;

import br.com.tanngrisnir.logica.Buffer;
import br.com.tanngrisnir.logica.Consumidor;

/**
 * Classe de testes para a execução das threads.
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

		// Foram usadas duas listas para o teste porque a lógica de cálculo da
		// media do tempo de execução de cada thread esta encapsulado na classe
		// Consumidor, após a execução de uma thread não é possível acessar
		// de alguma maneira o atributo com este valor.
		// As duas listas contém referências para os mesmos objetos, então toda
		// a alteração feita em tempo de execução em cada thread pode ser
		// acessada através da referência em consumidores.
		List<Consumidor> consumidores = new ArrayList<Consumidor>();
		List<Thread> threads = new ArrayList<Thread>();

		int i = 0;
		while (i < qtdThreads) {
			// Cria uma lista de consumidores de acordo com a respectiva
			// quantidade de
			// threads informadas.
			consumidores.add(new Consumidor(new Buffer(), i));
			// Cria uma lista de threads que recebe referências para execução
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
			// O método join serve para bloquear as threads, para que
			// o restante no código nesta classe de testes possa ser
			// executado.
			// Deve ser executado em todas as threads em execução.
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

		// Calcula o tempo médio de execução de cada thread
		tempoMedioExecucaoQtdThreads = tempoTotalExecucaoQtdThreads
				/ qtdThreads;

		System.out.println("\nTempo total de execução: "
				+ tempoTotalExecucaoQtdThreads + " ms");

		System.out.println("\nTempo médio de execução para " + qtdThreads
				+ " threads: " + tempoMedioExecucaoQtdThreads + " ms");
	}
}
