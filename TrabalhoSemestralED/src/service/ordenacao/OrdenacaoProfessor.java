package service.ordenacao;

import model.dados.Professor;
import model.estruturas.ListaSimples;
import model.estruturas.No;

public class OrdenacaoProfessor {
	Quicksort quick = new Quicksort();

	public ListaSimples<Professor> ordenarProfessorPorPontuacao(ListaSimples<Professor> encontrados) throws Exception {
		Professor vet[] = new Professor[encontrados.size()];

		int index = 0;
		No<Professor> current = encontrados.getPrimeiro();
		while (current != null) {
			vet[index++] = current.getDado();
			current = current.getProximo();
		}
		vet = quick.ordenar(vet);

		ListaSimples<Professor> ordenada = new ListaSimples<>();
		for (Professor professor : vet) {
			ordenada.addLast(professor);
		}

		return ordenada;
	}
}