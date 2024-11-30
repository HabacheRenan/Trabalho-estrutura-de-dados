package service.ordenacao;

import model.dados.Professor;

public class Quicksort {

	public Quicksort() {
		super();
	}

	public Professor[] ordenar(Professor[] vetor) {
		quick(vetor, 0, vetor.length - 1);
		return vetor;
	}

	private void quick(Professor[] vetor, int inicio, int fim) {
		if (fim > inicio) {
			int meio = dividir(vetor, inicio, fim);
			quick(vetor, inicio, meio - 1);
			quick(vetor, meio + 1, fim);
		}
	}

	private int dividir(Professor[] vetor, int inicio, int fim) {
		int pivo = vetor[inicio].getQnt_Pontos();
		int esquerda = (inicio + 1);
		int direita = fim;
		while (esquerda <= direita) {

			while (esquerda <= direita && vetor[esquerda].getQnt_Pontos() >= pivo) {
				esquerda++;
				if (esquerda > fim) {
					break;
				}
			}

			while (direita >= esquerda && vetor[direita].getQnt_Pontos() < pivo) {
				direita--;
				if (direita < inicio) {
					break;
				}
			}

			if (esquerda < direita)
				trocar(vetor, direita, esquerda);
		}
		trocar(vetor, inicio, direita);
		return direita;
	}

	private void trocar(Professor[] vetor, int i, int j) {
		Professor aux = vetor[i];
		vetor[i] = vetor[j];
		vetor[j] = aux;
	}

}