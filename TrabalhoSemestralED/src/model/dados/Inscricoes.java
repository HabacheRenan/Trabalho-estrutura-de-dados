package model.dados;

public class Inscricoes {

	private int cpf;
	private int cod_Disciplina;
	private int cod_Processo;

	public int getCpf() {
		return cpf;
	}

	public void setCpf(int cpf) {
		this.cpf = cpf;
	}

	public int getCod_Disciplina() {
		return cod_Disciplina;
	}

	public void setCod_Disciplina(int cod_Disciplina) {
		this.cod_Disciplina = cod_Disciplina;
	}

	public int getCod_Processo() {
		return cod_Processo;
	}

	public void setCod_Processo(int cod_Processo) {
		this.cod_Processo = cod_Processo;
	}

	@Override
	public String toString() {
		return cpf + ";" + cod_Disciplina + ";" + cod_Processo;
	}

}