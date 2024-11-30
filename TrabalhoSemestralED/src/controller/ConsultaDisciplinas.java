package controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.HashMap;

public class ConsultaDisciplinas {

	public VBox getTela() {
		VBox layout = new VBox(10);
		layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

		Button btnConsultar = new Button("Consultar");
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setPrefHeight(300);

		layout.getChildren().addAll(btnConsultar, textArea);

		// Caminho dos arquivos
		String arquivoProcessos = System.getProperty("user.home") +  File.separator + "ArquivosTrabalhoED"  + File.separator + "Inscricao.csv";
		String arquivoDisciplinas = System.getProperty("user.home") + File.separator +"ArquivosTrabalhoED"  + File.separator +"Disciplina.csv";

		// Ação do Botão
		btnConsultar.setOnAction(e -> {
			try {
				HashSet<Integer> codigosComProcessos = getProcessosAbertos(arquivoProcessos);
				String resultado = String.join("\n",
						getDisciplinasComProcessos(arquivoDisciplinas, codigosComProcessos));
				textArea.setText(resultado);
			} catch (IOException ex) {
				textArea.setText("Erro ao ler os arquivos CSV: " + ex.getMessage());
				ex.printStackTrace();
			}
		});

		return layout;
	}

	// Lê o arquivo de processos e retorna um conjunto de códigos de disciplinas com
	// processos abertos
	private HashSet<Integer> getProcessosAbertos(String arquivoProcessos) throws IOException {
		HashSet<Integer> codigosComProcessos = new HashSet<>();
		try (BufferedReader br = new BufferedReader(new FileReader(arquivoProcessos))) {
			String linha;
			while ((linha = br.readLine()) != null) {

				// Ignora linhas vazias
				if (linha.trim().isEmpty()) {
					continue;
				}

				String[] partes = linha.split(";");
				if (partes.length < 3) {
					System.out.println("Linha malformada no arquivo de processos: " + linha);
					continue;
				}

				try {
					int codigoDisciplina = Integer.parseInt(partes[2]); // Último número
					codigosComProcessos.add(codigoDisciplina);
				} catch (NumberFormatException e) {
					System.out.println("Erro ao converter número na linha: " + linha);
				}
			}
		}
		return codigosComProcessos;
	}

	// Lê o arquivo de disciplinas e retorna um mapa com código
	private HashMap<Integer, String> getDisciplinas(String arquivoDisciplinas) throws IOException {
		HashMap<Integer, String> disciplinas = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(arquivoDisciplinas))) {
			String linha;
			while ((linha = br.readLine()) != null) {
				String[] partes = linha.split(";");
				int codigo = Integer.parseInt(partes[0]); // Primeiro número
				String nome = partes[2]; // Nome da disciplina
				disciplinas.put(codigo, nome);
			}
		}
		return disciplinas;
	}

	// Retorna as disciplinas que possuem processos abertos
	private List<String> getDisciplinasComProcessos(String arquivoDisciplinas, HashSet<Integer> codigosComProcessos)
			throws IOException {
		List<String> disciplinasComProcessos = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(arquivoDisciplinas))) {
			String linha;
			while ((linha = br.readLine()) != null) {
				// Ignorar linhas vazias ou malformadas
				if (linha.trim().isEmpty())
					continue;

				String[] partes = linha.split(";");
				if (partes.length < 7) { // Se a linha tiver menos de 7 campos no arquivo de disciplinas: Aviso
					System.out.println("Linha malformada no arquivo de disciplinas: " + linha);
					continue;
				}

				try {
					int codigoDisciplina = Integer.parseInt(partes[0]); // Código da disciplina que está no primeiro
																		// campo
					if (codigosComProcessos.contains(codigoDisciplina)) {
						disciplinasComProcessos.add(partes[2]); // Nome da disciplina
					}
				} catch (NumberFormatException e) {
					System.out.println("Erro ao converter número na linha: " + linha);
				}
			}
		}
		return disciplinasComProcessos;
	}

}
