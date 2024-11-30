package service.interfaces;

public interface IControllers<T> {

	public int getSize();

	public void gravar(T objeto) throws Exception;

	public void atualizar(int itemPesquisa, T objeto) throws Exception;

	public void remover(int itemPesquisa) throws Exception;

}