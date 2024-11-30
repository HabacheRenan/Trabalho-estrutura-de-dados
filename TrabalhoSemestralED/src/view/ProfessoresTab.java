package view;

import controller.ProfessoresController;
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
import model.dados.Professor;
import model.estruturas.Fila;
import service.interfaces.ITabs;
import utils.Validar;

public class ProfessoresTab implements ITabs<Professor> {

	private TextField txtcpf;
	private TextField txtnome;
	private ComboBox<String> txtarea_Conhecimento;
	private TextField txtqnt_Pontos;

	private ProfessoresController controll = new ProfessoresController();

	public ProfessoresTab() {

		txtcpf = new TextField();
		txtnome = new TextField();

		txtarea_Conhecimento = new ComboBox<>();
		txtarea_Conhecimento.getItems().addAll("Ciencias Exatas e da Terra", "Ciencias Biologicas", "Engenharias",
				"Ciencias da Saude", "Ciencias Agrarias", "Linguistica, Letras e Artes", "Ciencias Sociais Aplicadas",
				"Ciencias Humanas");

		txtqnt_Pontos = new TextField();
	}

	@Override
	public BorderPane getTela() {
		ColumnConstraints coluna1 = new ColumnConstraints();
		ColumnConstraints coluna2 = new ColumnConstraints();
		coluna1.setPercentWidth(30);
		coluna2.setPercentWidth(60);

		BorderPane telaProfessores = new BorderPane();
		GridPane professoresForm = new GridPane();
		ScrollPane scrollPesquisa = new ScrollPane(null);
		HBox caixaBotoes = new HBox(60);
		Label tamanhoItens = new Label(
				"Existem atualmente " + controll.getSize() + " Professores(as) salvas no total.");

		scrollPesquisa.setFitToWidth(true);
		scrollPesquisa.setPannable(true);

		professoresForm.getColumnConstraints().addAll(coluna1, coluna2);
		professoresForm.setHgap(5);
		professoresForm.setVgap(5);
		professoresForm.setPadding(new Insets(15));

		professoresForm.add(new Label("CPF: "), 0, 0);
		professoresForm.add(txtcpf, 1, 0);

		professoresForm.add(new Label("Nome: "), 0, 1);
		professoresForm.add(txtnome, 1, 1);

		professoresForm.add(new Label("Area de Conhecimento: "), 0, 2);
		professoresForm.add(txtarea_Conhecimento, 1, 2);

		professoresForm.add(new Label("Quantidade de Pontos: "), 0, 3);
		professoresForm.add(txtqnt_Pontos, 1, 3);

		Button bntGravar = new Button("Gravar");
		bntGravar.setOnAction(e -> {
			Professor professor = boundaryToEntity();
			if (professor != null) {
				try {
					controll.gravar(professor);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Validar.mensagemInformacao("Informativo: gravacao concluida.",
						"O Cadastro do(a) Professor(a) \nfoi gravado com sucesso.");
				limparCampos();
				tamanhoItens.setText("Existem atualmente " + controll.getSize() + " Professores(as) salvas no total.");
			}
		});

		Button bntAtualizar = new Button("Atualizar");
		bntAtualizar.setOnAction(e -> {
			Professor professor = boundaryToEntity();
			if (professor != null) {
				try {
					int cod_Processo_Pesquisa = Integer.parseInt(txtcpf.getText());
					controll.atualizar(cod_Processo_Pesquisa, professor);
					limparCampos();
				} catch (Exception e1) {
					Validar.mensagemAviso("Aviso: Falha ao atualizar.", "Ocorreu uma falha.", e1);
				}
			}
		});

		Button bntRemover = new Button("Remover");
		bntRemover.setOnAction(e -> {
			try {
				int cpf_Pesquisa = Integer.parseInt(txtcpf.getText());
				controll.remover(cpf_Pesquisa);
				limparCampos();
				tamanhoItens.setText("Existem atualmente " + controll.getSize() + " Professores(as) salvas no total.");
			} catch (Exception e1) {
				Validar.mensagemAviso("Aviso: asmfka", "sadsadsa", e1);
			}
		});

		Button bntPesquisar = new Button("Pesquisar");
		bntPesquisar.setOnAction(e -> {
			int cod_Professor_Pesquisa = Integer.parseInt(txtcpf.getText()); // nao seia generico?
			try {
				Fila<Professor> professor = controll.pesquisar(cod_Professor_Pesquisa); // nao seria Generico

				VBox conteudo = new VBox(10);
				Professor professorConteudo = new Professor();
				while (professor.tamanho() >= 1) {
					try {
						professorConteudo = professor.remove();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					conteudo.getChildren()
							.add(new Label(professorConteudo.getCpf() + " - " + professorConteudo.getNome() + " - "
									+ professorConteudo.getArea_Conhecimento() + " - "
									+ professorConteudo.getQnt_Pontos()));

				}
				scrollPesquisa.setContent(conteudo);
			} catch (Exception e2) {
				Validar.mensagemAviso("Aviso: campo de pesquisa invalido.",
						"O campo de pesquisa (CODIGO Curso) deve conter apenas numeros.", e2); // teste
			}
		});
		caixaBotoes.getChildren().addAll(bntGravar, bntAtualizar, bntRemover, bntPesquisar);
		professoresForm.add(caixaBotoes, 0, 4, 2, 1);

		telaProfessores.setTop(professoresForm);
		telaProfessores.setCenter(scrollPesquisa);
		telaProfessores.setBottom(tamanhoItens);
		return telaProfessores;
	}

	@Override
	public Professor boundaryToEntity() { // GRAVAR

		if (Validar.isEmptyCampos(txtcpf.getText(), txtnome.getText(), txtarea_Conhecimento.getValue(),
				txtqnt_Pontos.getText())) {
			return null;
		}

		Integer cpf = Validar.validarParseInt(txtcpf.getText(), "cpf");
		if (cpf == null) {
			return null;
		}

		Integer qntPontos = Validar.validarParseInt(txtqnt_Pontos.getText(), "Qnt pontos");
		if (qntPontos == null) {
			return null;
		}

		Professor professor = new Professor();
		professor.setCpf(cpf);
		professor.setNome(txtnome.getText());
		professor.setArea_Conhecimento(txtarea_Conhecimento.getValue());
		professor.setQnt_Pontos(qntPontos);
		return professor;
	}

	@Override
	public void entityToBoundary(Professor objeto) { // PESQUISAR
		if (objeto != null) {
			txtcpf.setText(Integer.toString(objeto.getCpf()));
			txtnome.setText(objeto.getNome());
			txtarea_Conhecimento.setValue(objeto.getArea_Conhecimento());
			txtqnt_Pontos.setText(Integer.toString(objeto.getQnt_Pontos()));
		}
	}

	private void limparCampos() {
		txtcpf.setText("");
		txtnome.setText("");
		txtarea_Conhecimento.setValue(null);
		txtqnt_Pontos.setText("");
	}

}
