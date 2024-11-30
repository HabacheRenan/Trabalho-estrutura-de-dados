package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.dados.Disciplina;
import model.dados.Inscricoes;
import model.estruturas.Fila;
import model.estruturas.ListaSimples;
import model.estruturas.No;
import service.interfaces.IControllers;
import utils.Validar;

public class DisciplinaController implements IControllers<Disciplina> {

	private ListaSimples<Disciplina> lista = new ListaSimples<>();
	private Fila<Disciplina> fila = new Fila<>();
	private InscricoesController<Inscricoes> Inscricoes = new InscricoesController<>();

	public DisciplinaController() {
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
	public void gravar(Disciplina objeto) throws Exception {
		if (lista.isEmpty()) {
			lista.addFirst(objeto);
		} else {
			lista.addLast(objeto);
		}
		cadastroDisciplina(objeto.toString());
	}

	private void cadastroDisciplina(String csvDisciplina) throws IOException {
		String path = System.getProperty("user.home")  + File.separator + "ArquivosTrabalhoED";
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File arq = new File(path, "Disciplina.csv");
		boolean existe = false;
		if (arq.exists()) {
			existe = true;
		}
		FileWriter fw = new FileWriter(arq, existe);
		PrintWriter pw = new PrintWriter(fw);
		pw.write(csvDisciplina + "\r\n");
		pw.flush();
		pw.close();
		fw.close();
	}

	public Fila<Disciplina> pesquisar(int itemPesquisa) throws Exception {
		Fila<Disciplina> diciplinaEncotradas = new Fila<>();
		Fila<Disciplina> auxiliar = carregacsv();
		while (auxiliar.tamanho() >= 1) {
			Disciplina disciplina = auxiliar.remove();
			if (disciplina.getCod_Disciplina() == itemPesquisa) {
				diciplinaEncotradas.insert(disciplina);
			}
		}
		if (diciplinaEncotradas.vazia()) {
			return null;
		}
		return diciplinaEncotradas;
	}

	private Fila<Disciplina> carregacsv() throws Exception {
		lista.limpar();
		fila.Limpar();
		String path = System.getProperty("user.home") + File.separator + "ArquivosTrabalhoED";
		File arq = new File(path, "Disciplina.csv");
		if (arq.exists() && arq.isFile()) {
			FileInputStream fis = new FileInputStream(arq);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();
			DateTimeFormatter formatoExibicao = DateTimeFormatter.ofPattern("HH:mm");

			while (linha != null) {
				Disciplina disciplina = new Disciplina();
				String[] vetlinha = linha.split(";");
				disciplina.setCod_Disciplina(Integer.parseInt(vetlinha[0]));
				disciplina.setCod_Processo(Integer.parseInt(vetlinha[1]));
				disciplina.setNome_Diciplina(vetlinha[2]);
				disciplina.setDia_Semana_Ministrada(vetlinha[3]);

				LocalTime Horario_Inicial = LocalTime.parse(vetlinha[4], formatoExibicao);
				LocalTime Qnt_Horas_Diarias = LocalTime.parse(vetlinha[5], formatoExibicao);
				disciplina.setHorario_Inicial(Horario_Inicial);
				disciplina.setQnt_Horas_Diarias(Qnt_Horas_Diarias);

				disciplina.setCod_Curso(Integer.parseInt(vetlinha[6]));
				fila.insert(disciplina);
				lista.addLast(disciplina);
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
	public void atualizar(int itemPesquisa, Disciplina objeto) throws Exception {
		No<Disciplina> auxiliar = lista.getPrimeiro();
		int posicao = 0;
		boolean validar = false;
		while (auxiliar != null) {
			Disciplina disciplina = auxiliar.getDado();
			if (disciplina.getCod_Disciplina() == itemPesquisa) {
				lista.setDado(posicao, objeto);
				validar = true;
			}
			auxiliar = auxiliar.getProximo();
			posicao++;
		}
		if (validar) {
			reescrever();
		} else {
			Validar.mensagemAviso("Aviso: Falha ao atualizar.",
					"O codigo da Discipla: " + itemPesquisa + ", nao foi encontrado.");
		}
	}

	@Override
	public void remover(int itemPesquisa) throws Exception {
		No<Disciplina> auxiliar = lista.getPrimeiro();
		int posicao = 0;
		boolean validar = false;
		while (auxiliar != null) {
			Disciplina disciplina = auxiliar.getDado();
			if (disciplina.getCod_Disciplina() == itemPesquisa) {
				Validar.mensagemInformacao("Informativo: remocao concluida.",
						"A Disciplina: \nCodigo da Disciplina: " + disciplina.getCod_Curso() + "\nCodigo de Processo: "
								+ disciplina.getCod_Processo() + "\nNome: " + disciplina.getNome_Diciplina()
								+ "\nCodigo do Curso: " + disciplina.getCod_Curso() + "\nFoi Removida com sucesso.");
				lista.remove(posicao);
				validar = true;
				removerInscricoes(itemPesquisa);
			}
			auxiliar = auxiliar.getProximo();
			posicao++;
			if (validar)
				auxiliar = null;
		}
		
		if (validar) {
			reescrever();
		} else {
			Validar.mensagemAviso("Aviso: Falha ao remover.",
					"O codigo da Disciplina: " + itemPesquisa + ", nao foi encontrado.");
		}
	}

	private void removerInscricoes(int itemPesquisa) throws Exception {
		ListaSimples<Inscricoes> lisinscricoes = Inscricoes.carregacsv();
		No<Inscricoes> auxiliar = lisinscricoes.getPrimeiro();
		ListaSimples<Inscricoes> auxlista = new ListaSimples<>();
		
		int posicao = 0;
		boolean validar = false;
		while (auxiliar != null) {
			Inscricoes Inscricoes = auxiliar.getDado();
			if (Inscricoes.getCod_Disciplina() == itemPesquisa) {
				validar = true;
			} else {
				auxlista.add(Inscricoes, posicao);
				posicao++;
			}
			auxiliar = auxiliar.getProximo();
		}
		if (validar) {
			reescreverInscricao(auxlista);
		} else {
			Validar.mensagemAviso("Aviso: Falha ao remover.",
					"O codigo da Disciplina: " + itemPesquisa + ", nao foi encontrado.");
		}
	}

	private void reescreverInscricao(ListaSimples<Inscricoes> lisinscricoes) throws IOException {
		String path = System.getProperty("user.home") + File.separator + "ArquivosTrabalhoED";
		File arq = new File(path, "Inscricao.csv");
		boolean existe = false;
		FileWriter fw = new FileWriter(arq, existe);
		PrintWriter pw = new PrintWriter(fw);

		No<Inscricoes> aux = lisinscricoes.getPrimeiro();
		while (aux != null) {
			pw.write(aux.getDado().toString() + "\r\n");
			aux = aux.getProximo();
		}
		pw.flush();
		pw.close();
		fw.close();
	}

	private void reescrever() throws IOException {
		String path = System.getProperty("user.home") + File.separator + "ArquivosTrabalhoED";
		File arq = new File(path, "Disciplina.csv");
		boolean existe = false;
		FileWriter fw = new FileWriter(arq, existe);
		PrintWriter pw = new PrintWriter(fw);

		No<Disciplina> aux = lista.getPrimeiro();
		while (aux != null) {
			pw.write(aux.getDado().toString() + "\r\n");
			aux = aux.getProximo();
		}
		pw.flush();
		pw.close();
		fw.close();
	}

}