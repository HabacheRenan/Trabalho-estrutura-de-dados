package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.dados.Inscricoes;
import model.dados.Professor;
import model.estruturas.Fila;
import model.estruturas.ListaSimples;
import model.estruturas.No;
import service.interfaces.IControllers;
import service.ordenacao.OrdenacaoProfessor;
import utils.Validar;

public class InscricoesController<T> implements IControllers<Inscricoes> {

	private ProfessoresController Professor = new ProfessoresController();
	private ListaSimples<Inscricoes> lista = new ListaSimples<>();

	public InscricoesController() {
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
	public void gravar(Inscricoes objeto) throws Exception {
		if (lista.isEmpty()) {
			lista.addFirst(objeto);
		} else {
			lista.addLast(objeto);
		}
		cadastroIncricao(objeto.toString());
	}

	private void cadastroIncricao(String csvInscricao) throws IOException {
		String path = System.getProperty("user.home") + File.separator + "ArquivosTrabalhoED";
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File arq = new File(path, "Inscricao.csv");
		boolean existe = false;
		if (arq.exists()) {
			existe = true;
		}
		FileWriter fw = new FileWriter(arq, existe);
		PrintWriter pw = new PrintWriter(fw);
		pw.write(csvInscricao + "\r\n");
		pw.flush();
		pw.close();
		fw.close();
	}

	public ListaSimples<Inscricoes> pesquisar(int itemPesquisa) throws Exception {
		ListaSimples<Inscricoes> IncricaoEncontradas = new ListaSimples<>();
		ListaSimples<Inscricoes> auxiliar = carregacsv();
		No<Inscricoes> auxNo = auxiliar.getPrimeiro();
		while (auxNo != null) {
			Inscricoes inscritos = auxNo.getDado();
			if (inscritos.getCod_Disciplina() == itemPesquisa) {
				IncricaoEncontradas.addLast(inscritos);
			}
			auxNo = auxNo.getProximo();
		}
		if (IncricaoEncontradas.isEmpty())
			return null;

		return IncricaoEncontradas;
	}

	public ListaSimples<Professor> procurar(ListaSimples<Inscricoes> incricaoEncProfessor) throws Exception {
		ListaSimples<Professor> encontrados = new ListaSimples<Professor>();
		No<Inscricoes> auxNo = incricaoEncProfessor.getPrimeiro();
		while (auxNo != null) {
			Inscricoes inscritos = auxNo.getDado();
			Fila<Professor> professores = Professor.carregacsv();
			while (professores.tamanho() >= 1) {
				Professor professor = professores.remove();
				if (inscritos.getCpf() == professor.getCpf()) {
					encontrados.addLast(professor);
				}
			}
			auxNo = auxNo.getProximo();
		}
		if (encontrados.isEmpty()) {
			return null;
		}

		OrdenacaoProfessor ordenar = new OrdenacaoProfessor();
		encontrados = ordenar.ordenarProfessorPorPontuacao(encontrados);
		return encontrados;
	}

	public ListaSimples<Inscricoes> carregacsv() throws Exception {
		lista.limpar();
		String path = System.getProperty("user.home") + File.separator + "ArquivosTrabalhoED";
		File arq = new File(path, "Inscricao.csv");
		if (arq.exists() && arq.isFile()) {
			FileInputStream fis = new FileInputStream(arq);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();
			while (linha != null) {
				Inscricoes inscricoes = new Inscricoes();
				String[] vetlinha = linha.split(";");
				inscricoes.setCod_Processo(Integer.parseInt(vetlinha[0]));
				inscricoes.setCod_Disciplina(Integer.parseInt(vetlinha[1]));
				inscricoes.setCpf(Integer.parseInt(vetlinha[2]));
				lista.addLast(inscricoes);
				linha = br.readLine();
			}
			br.close();
			isr.close();
			fis.close();
			return lista;
		}
		return null;
	}

	@Override
	public void atualizar(int itemPesquisa, Inscricoes objeto) throws Exception {
		No<Inscricoes> auxiliar = lista.getPrimeiro();
		int posicao = 0;
		boolean validar = true;
		while (auxiliar != null) {
			Inscricoes inscricoes = auxiliar.getDado();
			if (inscricoes.getCod_Processo() == itemPesquisa) {
				lista.setDado(posicao, objeto);
			}
			auxiliar = auxiliar.getProximo();
			posicao++;
		}
		if (validar)
			reescrever();
		Validar.mensagemAviso("Aviso: Falha ao remover.",
				"O codigo de Processo: " + itemPesquisa + ", nao foi encontrado.");
	}
	private void reescrever() throws IOException {
		String path = System.getProperty("user.home") + File.separator + "ArquivosTrabalhoED";
		File arq = new File(path, "Inscricao.csv");
		boolean existe = false;
		FileWriter fw = new FileWriter(arq, existe);
		PrintWriter pw = new PrintWriter(fw);

		No<Inscricoes> aux = lista.getPrimeiro();
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
		No<Inscricoes> auxiliar = lista.getPrimeiro();
		int posicao = 0;
		boolean validar = true;
		while (auxiliar != null) {
			Inscricoes inscricoes = auxiliar.getDado();
			if (inscricoes.getCod_Processo() == itemPesquisa) {
				Validar.mensagemInformacao("Informativo: remocao concluida ",
						"A inscricao: \nCodigo de Processo: " + inscricoes.getCod_Processo() + "\nCPF: "
								+ inscricoes.getCpf() + "\nCodigo da Disciplina: " + inscricoes.getCod_Disciplina()
								+ "\nFoi removida com sucesso.");
				lista.remove(posicao);
				validar = true;
			}
			auxiliar = auxiliar.getProximo();
			posicao++;
			if (validar)
				auxiliar = null;
		}
		Validar.mensagemAviso("Aviso: Falha ao remover.",
				"O codigo de Processo: " + itemPesquisa + ", nao foi encontrado.");
	}
}
