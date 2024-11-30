package model.estruturas;

public class Fila<T> {
	No<T> inicio;
	No<T> fim;

	public boolean vazia() {
		if (inicio == null && fim == null) {
			return true;
		}
		return false;
	}

	public void Limpar() {
		while (inicio != null) {
			inicio = inicio.proximo;
		}
		fim = null;
	}

	public void insert(T valor) {
		No<T> elemento = new No<>();
		elemento.setDado(valor);
		;
		elemento.proximo = null;
		if (vazia()) {
			inicio = elemento;
			fim = inicio;
		} else {
			fim.proximo = elemento;
			fim = elemento;
		}
	}

	public T remove() throws Exception {
		if (vazia()) {
			new Exception("Não ha elementos na fila");
		}
		T valor = inicio.getDado();
		if (inicio == fim && inicio != null) {
			inicio = null;
			fim = inicio;
		} else {
			inicio = inicio.proximo;
		}
		return valor;
	}

	public int tamanho() {
		int tamanho = 0;
		if (!vazia()) {
			No<T> auxiliar = inicio;
			while (auxiliar != null) {
				tamanho++;
				auxiliar = auxiliar.proximo;
			}
		}
		return tamanho;
	}
}
