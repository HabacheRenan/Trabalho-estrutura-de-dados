package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.dados.Curso;
import model.estruturas.Fila;
import model.estruturas.ListaSimples;
import model.estruturas.No;
import service.interfaces.IControllers;
import utils.Validar;

public class CursoController implements IControllers<Curso> {

	private ListaSimples<Curso> lista = new ListaSimples<>();
	private Fila<Curso> fila = new Fila<>();

	public CursoController() {
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
	public void gravar(Curso objeto) throws Exception {
		if (lista.isEmpty()) {
			lista.addFirst(objeto);
		} else {
			lista.addLast(objeto);
		}
		cadastroCurso(objeto.toString());
	}

	public Fila<Curso> pesquisar(int itemPesquisa) throws Exception {
		Fila<Curso> cursosEncotrados = new Fila<>();
		Fila<Curso> auxiliar = carregacsv();
		while (auxiliar.tamanho() >= 1) {
			Curso curso = auxiliar.remove();
			if (curso.getCod_Curso() == itemPesquisa) {
				cursosEncotrados.insert(curso);
			}
		}
		if (cursosEncotrados.vazia()) {
			return null;
		}
		return cursosEncotrados;
	}

	private Fila<Curso> carregacsv() throws Exception {
		lista.limpar();
		fila.Limpar();
		String path = System.getProperty("user.home") + File.separator + "ArquivosTrabalhoED";
		File arq = new File(path, "Curso.csv");
		if (arq.exists() && arq.isFile()) {
			FileInputStream fis = new FileInputStream(arq);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();
			while (linha != null) {
				Curso curso = new Curso();
				String[] vetlinha = linha.split(";");
				curso.setCod_Curso(Integer.parseInt(vetlinha[0]));
				curso.setNome_Curso(vetlinha[1]);
				curso.setArea_Conhecimento(vetlinha[2]);
				fila.insert(curso);
				lista.addLast(curso);
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
	public void atualizar(int itemPesquisa, Curso objeto) throws Exception {
		No<Curso> auxiliar = lista.getPrimeiro();
		int posicao = 0;
		boolean validar = true;
		while (auxiliar != null) {
			Curso curso = auxiliar.getDado();
			if (curso.getCod_Curso() == itemPesquisa) {
				lista.setDado(posicao, objeto);
			}
			auxiliar = auxiliar.getProximo();
			posicao++;
		}
		if (validar)
			reescrever();
		Validar.mensagemAviso("Aviso: Falha ao atualizar.",
				"O curso de Codigo: " + itemPesquisa + ", nao foi encontrado.");
	}

	private void reescrever() throws IOException {
		String path = System.getProperty("user.home") + File.separator + "ArquivosTrabalhoED";
		File arq = new File(path, "Curso.csv");
		boolean existe = false;
		FileWriter fw = new FileWriter(arq, existe);
		PrintWriter pw = new PrintWriter(fw);

		No<Curso> aux = lista.getPrimeiro();
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
		No<Curso> auxiliar = lista.getPrimeiro();
		int posicao = 0;
		boolean validar = true;
		while (auxiliar != null) {
			Curso curso = auxiliar.getDado();
			if (curso.getCod_Curso() == itemPesquisa) {
				Validar.mensagemInformacao("Informativo: remocao concluida ",
						"O Curso: \nCodigo: " + curso.getCod_Curso() + "\nNome: " + curso.getNome_Curso()
								+ "\nArea de conhecimento: " + curso.getArea_Conhecimento()
								+ "\nFoi Removido com sucesso.");
				lista.remove(posicao);
				validar = true;
			}

			auxiliar = auxiliar.getProximo();
			posicao++;
			if (validar)
				auxiliar = null;
		}
		Validar.mensagemAviso("Aviso: Falha ao remover.",
				"O curso de Codigo: " + itemPesquisa + ", nao foi encontrado.");
	}

	private void cadastroCurso(String csvCurso) throws IOException {
		String path = System.getProperty("user.home") + File.separator + "ArquivosTrabalhoED";
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File arq = new File(path, "Curso.csv");
		boolean existe = false;
		if (arq.exists()) {
			existe = true;
		}
		FileWriter fw = new FileWriter(arq, existe);
		PrintWriter pw = new PrintWriter(fw);
		pw.write(csvCurso + "\r\n");
		pw.flush();
		pw.close();
		fw.close();
	}
}