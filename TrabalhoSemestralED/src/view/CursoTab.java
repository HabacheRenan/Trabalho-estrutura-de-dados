package view;

import controller.CursoController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.dados.Curso;
import model.estruturas.Fila;
import service.interfaces.ITabs;
import utils.Validar;

public class CursoTab implements ITabs<Curso> {

	// Os campos rensponsaveis pela entrada de dados.
	private TextField txtcod_Curso;
	private TextField txtnome_Curso;
	private ComboBox<String> txtarea_Conhecimento;

	// O controle, responsaveis pelos CRUDs
	private CursoController controll = new CursoController();

	public CursoTab() {
		txtcod_Curso = new TextField();
		txtnome_Curso = new TextField();
		txtarea_Conhecimento = new ComboBox<>();

		txtarea_Conhecimento.getItems().addAll("Ciencias Exatas e da Terra", "Ciencias Biologicas", "Engenharias",
				"Ciencias da Saude", "Ciencias Agrarias", "Linguistica, Letras e Artes", "Ciencias Sociais Aplicadas",
				"Ciencias Humanas");
	}

	// Funcão que retornará a tela (Aba) a ser criada na tela Principal.
	@Override
	public BorderPane getTela() {

		// Controla os tamanhos de espaçamento das colonas
		ColumnConstraints coluna1 = new ColumnConstraints();
		ColumnConstraints coluna2 = new ColumnConstraints();
		coluna1.setPercentWidth(30);
		coluna2.setPercentWidth(60);

		/*
		 * 3 tipos de "mini-telas": BorderPane é a tela "Principal", ela tem 5 entradas
		 * para adicioanr itens, que retorna para a Main. GridPane é onde se adicionara
		 * os campos de entrada e os botões, é adicionado no BorderPane. Hbox uma caixa
		 * que "guarda" os botões, ela é adicionada ao GridPane.
		 */
		BorderPane telaCurso = new BorderPane();
		GridPane cursoForm = new GridPane();
		ScrollPane scrollPesquisa = new ScrollPane(null);
		HBox caixaBotoes = new HBox(60);
		Label tamanhoItens = new Label("Existem atualmente " + controll.getSize() + " Cursos salvos no total.");

		scrollPesquisa.setFitToWidth(true);
		scrollPesquisa.setPannable(true);

		cursoForm.getColumnConstraints().addAll(coluna1, coluna2);
		cursoForm.setHgap(5);
		cursoForm.setVgap(5);
		cursoForm.setPadding(new Insets(15));

		// Adiciona no GridPane os textos e os campos de entrada de dados.
		cursoForm.add(new Label("Codigo do Curso:"), 0, 0);
		cursoForm.add(txtcod_Curso, 1, 0);

		cursoForm.add(new Label("Nome do Curso"), 0, 1);
		cursoForm.add(txtnome_Curso, 1, 1);

		cursoForm.add(new Label("Area de Conhecimento:"), 0, 2);
		cursoForm.add(txtarea_Conhecimento, 1, 2);

		// Adiciona os botões e suas funcionalidades.
		// Adciona um novo item na lista.
		Button bntGravar = new Button("Gravar");
		bntGravar.setOnAction(e -> {
			Curso curso = boundaryToEntity();
			if (curso != null) {
				try {
					controll.gravar(curso);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Validar.mensagemInformacao("Informativo: gravacao concluida.", "O curso foi gravado com sucesso.");
				limparCampos();
				tamanhoItens.setText("Existem atualmente " + controll.getSize() + " Cursos salvos no total.");
			}
		});

		// Atualiza um valor da lista (Nao parece possivel mudar a chave primaria)
		Button bntAtualizar = new Button("Atualizar");
		bntAtualizar.setOnAction(e -> {
			Curso curso = boundaryToEntity();
			if (curso != null) {
				try {
					int cod_Curso_pesquisa = Integer.parseInt(txtcod_Curso.getText());
					controll.atualizar(cod_Curso_pesquisa, curso);
					limparCampos();
				} catch (Exception e1) {
					Validar.mensagemAviso("Aviso: Falha ao atualizar.", "Ocorreu uma falha.", e1);
				}
			}
		});

		// Remove itens existentes na lista.
		Button bntRemover = new Button("Remover");
		bntRemover.setOnAction(e -> {
			try {
				int cod_Curso_pesquisa = Integer.parseInt(txtcod_Curso.getText());
				controll.remover(cod_Curso_pesquisa);
				limparCampos();
				tamanhoItens.setText("Existem atualmente " + controll.getSize() + " Cursos salvos no total.");
			} catch (Exception e1) {
				Validar.mensagemAviso("Aviso: asmfka", "sadsadsa", e1);
			}
		});

		// Pesquisa e mostra os dados de um item da lista.
		Button bntPesquisar = new Button("Pesquisar");
		bntPesquisar.setOnAction(e -> {
			int cod_Disciplana_Pesquisa = Integer.parseInt(txtcod_Curso.getText()); // nao seia curso?
			try {
				Fila<Curso> curso = controll.pesquisar(cod_Disciplana_Pesquisa); // nao seria curso

				VBox conteudo = new VBox(10);
				Curso cursoConteudo = new Curso();
				while (curso.tamanho() >= 1) {
					try {
						cursoConteudo = curso.remove();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					conteudo.getChildren().add(new Label(cursoConteudo.getNome_Curso() + " - "
							+ cursoConteudo.getCod_Curso() + " - " + cursoConteudo.getArea_Conhecimento()));
				}
				scrollPesquisa.setContent(conteudo);
			} catch (Exception e2) {
				Validar.mensagemAviso("Aviso: campo de pesquisa invalido.",
						"O campo de pesquisa (CODIGO Curso) deve conter apenas numeros.", e2); // teste
			}
		});
		// Adiciona os botões na Hbox e adiciona a Hbox na Grid.
		caixaBotoes.getChildren().addAll(bntGravar, bntAtualizar, bntRemover, bntPesquisar);
		cursoForm.add(caixaBotoes, 0, 3, 2, 1);

		telaCurso.setTop(cursoForm);
		telaCurso.setCenter(scrollPesquisa);
		telaCurso.setBottom(tamanhoItens);
		return telaCurso;
	}

	@Override
	public Curso boundaryToEntity() {
		// GRAVAR

		if (Validar.isEmptyCampos(txtcod_Curso.getText(), txtnome_Curso.getText(), txtarea_Conhecimento.getValue())) {
			return null;
		}

		Integer codigoCurso = Validar.validarParseInt(txtcod_Curso.getText(), "Codigo do Curso");
		if (codigoCurso == null) {
			return null;
		}

		Curso curso = new Curso();
		curso.setCod_Curso(codigoCurso);
		curso.setNome_Curso(txtnome_Curso.getText());
		curso.setArea_Conhecimento(txtarea_Conhecimento.getValue());
		return curso;
	}

	@Override
	public void entityToBoundary(Curso objeto) {
		// PESQUISAR

		if (objeto != null) {
			txtcod_Curso.setText(Integer.toString(objeto.getCod_Curso()));
			txtnome_Curso.setText(objeto.getNome_Curso());
			txtarea_Conhecimento.setValue(objeto.getArea_Conhecimento());
		}
	}

	private void limparCampos() {
		txtcod_Curso.setText("");
		txtnome_Curso.setText("");
		txtarea_Conhecimento.setValue(null);
	}

}
