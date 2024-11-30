package model.dados;

import java.time.LocalTime;

public class Disciplina {

	private int cod_Disciplina;
	private int cod_Processo;
	private String nome_Diciplina;
	private String dia_Semana_Ministrada;
	private LocalTime horario_Inicial;
	private LocalTime qnt_Horas_Diarias;
	private int cod_Curso;

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

	public String getNome_Diciplina() {
		return nome_Diciplina;
	}

	public void setNome_Diciplina(String nome_Diciplina) {
		this.nome_Diciplina = nome_Diciplina;
	}

	public String getDia_Semana_Ministrada() {
		return dia_Semana_Ministrada;
	}

	public void setDia_Semana_Ministrada(String dia_Semana_Ministrada) {
		this.dia_Semana_Ministrada = dia_Semana_Ministrada;
	}

	public LocalTime getHorario_Inicial() {
		return horario_Inicial;
	}

	public void setHorario_Inicial(LocalTime horario_Inicial) {
		this.horario_Inicial = horario_Inicial;
	}

	public LocalTime getQnt_Horas_Diarias() {
		return qnt_Horas_Diarias;
	}

	public void setQnt_Horas_Diarias(LocalTime qnt_Horas_Diarias) {
		this.qnt_Horas_Diarias = qnt_Horas_Diarias;
	}

	public int getCod_Curso() {
		return cod_Curso;
	}

	public void setCod_Curso(int cod_Curso) {
		this.cod_Curso = cod_Curso;
	}

	@Override
	public String toString() {
		return cod_Disciplina + ";" + cod_Processo + ";" + nome_Diciplina + ";" + dia_Semana_Ministrada + ";"
				+ horario_Inicial + ";" + qnt_Horas_Diarias + ";" + cod_Curso;
	}

}
