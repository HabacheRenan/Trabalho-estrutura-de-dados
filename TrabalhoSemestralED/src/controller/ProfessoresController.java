package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.dados.Professor;
import model.estruturas.Fila;
import model.estruturas.ListaSimples;
import model.estruturas.No;
import service.interfaces.IControllers;
import utils.Validar;

public class ProfessoresController implements IControllers<Professor> {

	private ListaSimples<Professor> lista = new ListaSimples<>();
	private Fila<Professor> fila = new Fila<>();

	public ProfessoresController() {
		super();
		try {
			carregacsv();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getSize() {
		return lista.size();
	}

	@Override
	public void gravar(Professor objeto) throws Exception {
		if (lista.isEmpty()) {
			lista.addFirst(objeto);
		} else {
			lista.addLast(objeto);
		}
		cadastroProfessor(objeto.toString());
	}

	private void cadastroProfessor(String csvProfessor) throws IOException {
		String path = System.getProperty("user.home") + File.separator + "ArquivosTrabalhoED";
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File arq = new File(path, "Professor.csv");
		boolean existe = false;
		if (arq.exists()) {
			existe = true;
		}
		FileWriter fw = new FileWriter(arq, existe);
		PrintWriter pw = new PrintWriter(fw);
		pw.write(csvProfessor + "\r\n");
		pw.flush();
		pw.close();
		fw.close();
	}

	public Fila<Professor> pesquisar(int itemPesquisa) throws Exception {
		Fila<Professor> professorEncotrados = new Fila<>();
		Fila<Professor> auxiliar = carregacsv();
		while (auxiliar.tamanho() >= 1) {
			Professor professor = auxiliar.remove();
			if (professor.getCpf() == itemPesquisa) {
				professorEncotrados.insert(professor);
			}
		}
		if (professorEncotrados.vazia()) {
			return null;
		}
		return professorEncotrados;
	}

	public Fila<Professor> carregacsv() throws Exception {
		lista.limpar();
		fila.Limpar();
		String path = System.getProperty("user.home") + File.separator + "ArquivosTrabalhoED";
		File arq = new File(path, "Professor.csv");
		if (arq.exists() && arq.isFile()) {
			FileInputStream fis = new FileInputStream(arq);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();
			while (linha != null) {
				Professor professor = new Professor();
				String[] vetlinha = linha.split(";");
				professor.setCpf(Integer.parseInt(vetlinha[0]));
				professor.setNome(vetlinha[1]);
				professor.setArea_Conhecimento(vetlinha[2]);
				professor.setQnt_Pontos(Integer.parseInt(vetlinha[3]));
				fila.insert(professor);
				lista.addLast(professor);
				linha = br.readLine();
			}
			br.close();
			isr.close();
			fis.close();
			return fila;
		}
		return null;
	}

	@Override
	public void atualizar(int itemPesquisa, Professor objeto) throws Exception {
		No<Professor> auxiliar = lista.getPrimeiro();
		int posicao = 0;
		boolean validar = false;
		while (auxiliar != null) {
			Professor professor = auxiliar.getDado();
			if (professor.getCpf() == itemPesquisa) {
				lista.setDado(posicao, objeto);
			}
			auxiliar = auxiliar.getProximo();
			posicao++;
		}
		if (validar) {
			reescrever();
		} else {
			Validar.mensagemAviso("Aviso: Falha ao remover.",
					"O CPF do professor(a): " + itemPesquisa + ", nao foi encontrado.");
		}
	}

	private void reescrever() throws IOException {
		String path = System.getProperty("user.home") + File.separator + "ArquivosTrabalhoED";
		File arq = new File(path, "Professor.csv");
		boolean existe = false;
		FileWriter fw = new FileWriter(arq, existe);
		PrintWriter pw = new PrintWriter(fw);

		No<Professor> aux = lista.getPrimeiro();
		while (aux != null) {
			pw.write(aux.getDado().toString() + "\r\n");
			aux = aux.getProximo();
		}
		pw.flush();
		pw.close();
		fw.close();
	}

	@Override
	public void remover(int itemPesquisa) throws Exception {
		No<Professor> auxiliar = lista.getPrimeiro();
		int posicao = 0;
		boolean validar = false;
		while (auxiliar != null) {
			Professor professor = auxiliar.getDado();
			if (professor.getCpf() == itemPesquisa) {
				Validar.mensagemInformacao("Informativo: remocao concluida.",
						"O Professor(a): \nCPF: " + professor.getCpf() + "\nNome: " + professor.getNome()
								+ "\nArea de conhecimento: " + professor.getArea_Conhecimento() + "\nQnt Pontos: "
								+ professor.getQnt_Pontos() + "\nFoi Removido com sucesso.");
				lista.remove(posicao);
				validar = true;
			}
			auxiliar = auxiliar.getProximo();
			posicao++;
			if (validar)
				auxiliar = null;
		}
		Validar.mensagemAviso("Aviso: Falha ao remover.",
				"O CPF do professor(a): " + itemPesquisa + ", nao foi encontrado.");
	}
}