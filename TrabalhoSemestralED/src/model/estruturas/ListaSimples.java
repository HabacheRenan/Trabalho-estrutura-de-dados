package model.estruturas;

public class ListaSimples<T> {

	No<T> primeiro;

	public ListaSimples() {
		this.primeiro = null;
	}

	public void limpar() {
		primeiro = null;
	}

	public No<T> getPrimeiro() {
		return primeiro;
	}

	public void setDado(int posicao, T valor) throws Exception {
		No<T> elemento = getNo(posicao);
		elemento.setDado(valor);
	}

	public boolean isEmpty() {
		return primeiro == null;
	}

	public int size() {
		int contador = 0;

		if (!isEmpty()) {
			No<T> auxiliar = primeiro;
			while (auxiliar != null) {
				contador++;
				auxiliar = auxiliar.proximo;
			}
		}

		return contador;
	}

	public void addFirst(T valor) {
		No<T> elemento = new No<T>();
		elemento.setDado(valor);
		elemento.proximo = primeiro;
		primeiro = elemento;
	}

	public void addLast(T valor) throws Exception {
		if (isEmpty()) {
			addFirst(valor);
		} else {
			int tamanho = size();
			No<T> elemento = new No<T>();
			elemento.setDado(valor);

			No<T> ultimo = getNo(tamanho - 1);
			ultimo.proximo = elemento;

		}
	}

	public void add(T valor, int posicao) throws Exception {
		int tamanho = size();
		if (posicao < 0 || posicao > tamanho) {
			throw new Exception("Posicao invalida");
		}

		if (posicao == 0) {
			addFirst(valor);
		} else if (posicao == tamanho) {
			addLast(valor);
		} else {
			No<T> elemento = new No<T>();
			elemento.setDado(valor);

			No<T> anterior = getNo(posicao);
			elemento.proximo = anterior.proximo;
			anterior.proximo = elemento;
		}

	}

	public void removeFirst() throws Exception {
		if (isEmpty()) {
			throw new Exception("Lista vazia");
		}
		primeiro = primeiro.proximo;
	}

	public void removeLast() throws Exception {
		if (isEmpty()) {
			throw new Exception("Lista vazia");
		}

		int tamanho = size();

		if (tamanho == 1) {
			removeFirst();
		} else {
			No<T> penultimo = getNo(tamanho - 2);
			penultimo.proximo = null;
		}
	}

	public void remove(int posicao) throws Exception {
		if (isEmpty()) {
			throw new Exception("Lista vazia");
		}

		int tamanho = size();
		if (posicao < 0 || posicao > tamanho) {
			throw new Exception("Posicao invalida");
		}

		if (posicao == 0) {
			removeFirst();
		} else if (posicao == tamanho - 1) {
			removeLast();
		} else {
			No<T> anterior = getNo(posicao - 1);
			No<T> atual = getNo(posicao);
			anterior.proximo = atual.proximo;
		}
	}

	public T get(int posicao) throws Exception {
		if (isEmpty()) {
			throw new Exception("Lista vazia");
		}
		int tamanho = size();
		if (posicao < 0 || posicao > tamanho - 1) {
			throw new Exception("Posicao invalida");
		}
		No<T> auxiliar = getNo(posicao);
		return auxiliar.getDado();
	}

	private No<T> getNo(int posicao) throws Exception {
		if (isEmpty()) {
			throw new Exception("Lista Vazia");
		}

		int tamanho = size();

		if (posicao < 0 || posicao > tamanho - 1) {
			throw new Exception("Posicao Invalida");
		}

		No<T> auxiliar = primeiro;
		int contador = 0;

		while (contador < posicao) {
			auxiliar = auxiliar.proximo;
			contador++;
		}

		return auxiliar;
	}
}