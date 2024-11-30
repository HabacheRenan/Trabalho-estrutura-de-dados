package model.dados;

public class Professor {

	private int cpf;
	private String nome;
	private String area_Conhecimento;
	private int qnt_Pontos;

	public int getCpf() {
		return cpf;
	}

	public void setCpf(int cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getArea_Conhecimento() {
		return area_Conhecimento;
	}

	public void setArea_Conhecimento(String area_Conhecimento) {
		this.area_Conhecimento = area_Conhecimento;
	}

	public int getQnt_Pontos() {
		return qnt_Pontos;
	}

	public void setQnt_Pontos(int qnt_Pontos) {
		this.qnt_Pontos = qnt_Pontos;
	}

	@Override
	public String toString() {
		return cpf + ";" + nome + ";" + area_Conhecimento + ";" + qnt_Pontos;
	}
}