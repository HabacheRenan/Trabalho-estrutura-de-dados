package model.dados;

public class Curso {

	private int cod_Curso;
	private String nome_Curso;
	private String area_Conhecimento;

	public int getCod_Curso() {
		return cod_Curso;
	}

	public void setCod_Curso(int cod_Curso) {
		this.cod_Curso = cod_Curso;
	}

	public String getNome_Curso() {
		return nome_Curso;
	}

	public void setNome_Curso(String nome_Curso) {
		this.nome_Curso = nome_Curso;
	}

	public String getArea_Conhecimento() {
		return area_Conhecimento;
	}

	public void setArea_Conhecimento(String area_Conhecimento) {
		this.area_Conhecimento = area_Conhecimento;
	}

	@Override
	public String toString() {
		return cod_Curso + ";" + nome_Curso + ";" + area_Conhecimento;
	}
}