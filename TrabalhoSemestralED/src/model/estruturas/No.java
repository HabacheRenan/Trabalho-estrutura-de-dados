package model.estruturas;

public class No<T> {

	private T dado;
	No<T> proximo;

	@Override
	public String toString() {
		return "No [dado = " + getDado() + "]";
	}

	public T getDado() {
		return dado;
	}

	public No<T> getProximo() {
		return proximo;
	}

	public void setDado(T dado) {
		this.dado = dado;
	}
}
