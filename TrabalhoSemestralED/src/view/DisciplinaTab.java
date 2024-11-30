package view;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import controller.DisciplinaController;
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
import model.dados.Disciplina;
import model.estruturas.Fila;
import service.interfaces.ITabs;
import utils.Validar;

public class DisciplinaTab implements ITabs<Disciplina> {

	private TextField txtcod_Disciplina;
	private TextField txtcod_Processo;
	private TextField txtnome_Diciplina;
	private ComboBox<String> txtdia_Semana_Ministrada;
	private TextField txthorario_Inicial;
	private TextField txtqnt_Horas_Diarias;
	private TextField txtcod_Curso;

	private DisciplinaController controll = new DisciplinaController();

	public DisciplinaTab() {
		txtcod_Disciplina = new TextField();
		txtcod_Processo = new TextField();
		txtnome_Diciplina = new TextField();

		txtdia_Semana_Ministrada = new ComboBox<>();
		txtdia_Semana_Ministrada.getItems().addAll("Segunda", "Terca", "Quarta", "Quinta", "Sexta", "Sabado",
				"Domingo");

		txthorario_Inicial = new TextField();
		txtqnt_Horas_Diarias = new TextField();
		txtcod_Curso = new TextField();
	}

	@Override
	public BorderPane getTela() {
		ColumnConstraints coluna1 = new ColumnConstraints();
		ColumnConstraints coluna2 = new ColumnConstraints();
		coluna1.setPercentWidth(30);
		coluna2.setPercentWidth(60);

		BorderPane telaDisciplina = new BorderPane();
		GridPane disciplinaForm = new GridPane();
		ScrollPane scrollPesquisa = new ScrollPane(null);
		HBox caixaBotoes = new HBox(60);
		Label tamanhoItens = new Label("Existem atualmente " + controll.getSize() + " Disciplinas salvas no total.");

		scrollPesquisa.setFitToWidth(true);
		scrollPesquisa.setPannable(true);

		disciplinaForm.getColumnConstraints().addAll(coluna1, coluna2);
		disciplinaForm.setHgap(5);
		disciplinaForm.setVgap(5);
		disciplinaForm.setPadding(new Insets(15));

		disciplinaForm.add(new Label("Codigo da Disciplina: "), 0, 0);
		disciplinaForm.add(txtcod_Disciplina, 1, 0);

		disciplinaForm.add(new Label("Codigo do Processo: "), 0, 1);
		disciplinaForm.add(txtcod_Processo, 1, 1);

		disciplinaForm.add(new Label("Nome da Disciplina: "), 0, 2);
		disciplinaForm.add(txtnome_Diciplina, 1, 2);

		disciplinaForm.add(new Label("Dia Ministrado: "), 0, 3);
		disciplinaForm.add(txtdia_Semana_Ministrada, 1, 3);

		disciplinaForm.add(new Label("Horario inicial: "), 0, 4);
		disciplinaForm.add(txthorario_Inicial, 1, 4);

		disciplinaForm.add(new Label("Quantidade de horas diarias: "), 0, 5);
		disciplinaForm.add(txtqnt_Horas_Diarias, 1, 5);

		disciplinaForm.add(new Label("Codigo do curso: "), 0, 6);
		disciplinaForm.add(txtcod_Curso, 1, 6);

		Button bntGravar = new Button("Gravar");
		bntGravar.setOnAction(e -> {
			Disciplina disciplina = boundaryToEntity();
			if (disciplina != null) {
				try {
					controll.gravar(disciplina);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Validar.mensagemInformacao("Informativo: gravacao concluida.",
						"O Cadastro da Disciplina \nfoi gravado com sucesso.");
				limparCampos();
				tamanhoItens.setText("Existem atualmente " + controll.getSize() + " Disciplinas salvas no total.");
			}
		});
		Button bntAtualizar = new Button("Atualizar");
		bntAtualizar.setOnAction(e -> {
			Disciplina disciplina = boundaryToEntity();
			if (disciplina != null) {
				try {
					int cod_Processo_Pesquisa = Integer.parseInt(txtcod_Disciplina.getText());
					controll.atualizar(cod_Processo_Pesquisa, disciplina);
					limparCampos();
				} catch (Exception e1) {
					Validar.mensagemAviso("Aviso: Falha ao atualizar.", "Ocorreu uma falha.", e1);
				}
			}
		});

		Button bntRemover = new Button("Remover");
		bntRemover.setOnAction(e -> {
			try {
				int cod_Disciplana_Pesquisa = Integer.parseInt(txtcod_Disciplina.getText());
				controll.remover(cod_Disciplana_Pesquisa);
				limparCampos();
				tamanhoItens.setText("Existem atualmente " + controll.getSize() + " Disciplinas salvas no total.");
			} catch (Exception e1) {
				Validar.mensagemAviso("Aviso: asmfka", "sadsadsa", e1);
			}
		});

		Button bntPesquisar = new Button("Pesquisar");
		bntPesquisar.setOnAction(e -> {
			int cod_Disciplana_Pesquisa = Integer.parseInt(txtcod_Disciplina.getText());
			try {
				Fila<Disciplina> disciplina = controll.pesquisar(cod_Disciplana_Pesquisa);

				VBox conteudo = new VBox(10);
				Disciplina disciplinaconteudo = new Disciplina();
				while (disciplina.tamanho() >= 1) {
					try {
						disciplinaconteudo = disciplina.remove();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					conteudo.getChildren().add(new Label(disciplinaconteudo.getCod_Disciplina() + " - "
							+ disciplinaconteudo.getCod_Processo() + " - " + disciplinaconteudo.getNome_Diciplina()
							+ " - " + disciplinaconteudo.getDia_Semana_Ministrada() + " - "
							+ disciplinaconteudo.getHorario_Inicial() + " - "
							+ disciplinaconteudo.getQnt_Horas_Diarias() + " - " + disciplinaconteudo.getCod_Curso()));
				}
				scrollPesquisa.setContent(conteudo);
			} catch (Exception e2) {
				Validar.mensagemAviso("Aviso: campo de pesquisa invalido.",
						"O campo de pesquisa (CODIGO DISCIPLINA) deve conter apenas numeros.", e2);
			}
		});

		caixaBotoes.getChildren().addAll(bntGravar, bntAtualizar, bntRemover, bntPesquisar);
		disciplinaForm.add(caixaBotoes, 0, 7, 2, 1);

		telaDisciplina.setTop(disciplinaForm);
		telaDisciplina.setCenter(scrollPesquisa);
		telaDisciplina.setBottom(tamanhoItens);
		return telaDisciplina;
	}

	@Override
	public Disciplina boundaryToEntity() { // GRAVAR

		if (Validar.isEmptyCampos(txtcod_Disciplina.getText(), txtcod_Processo.getText(), txtnome_Diciplina.getText(),
				txtdia_Semana_Ministrada.getValue(), txthorario_Inicial.getText(), txtqnt_Horas_Diarias.getText(),
				txtcod_Curso.getText())) {
			return null;
		}

		Integer codigoDisciplina = Validar.validarParseInt(txtcod_Disciplina.getText(), "Codigo Disciplina");
		if (codigoDisciplina == null) {
			return null;
		}

		Integer codigoProcesso = Validar.validarParseInt(txtcod_Processo.getText(), "Cogido de Processo");
		if (codigoProcesso == null) {
			return null;
		}

		Integer qntHorasDiarias = Validar.validarParseInt(txtqnt_Horas_Diarias.getText(),
				"Aviso: Quantidade de Horas Diarias.",
				"A QUANTIDADE DE HORAS DIARIAS deve ser um numero inteiro. \nNo formato HHMM, sem adicao de pontucao.");
		if (qntHorasDiarias == null) {
			return null;
		}

		Integer codigoCurso = Validar.validarParseInt(txtcod_Curso.getText(), "Codigo do Curso");
		if (codigoCurso == null) {
			return null;
		}

		Integer horarioInicial = Validar.validarParseInt(txthorario_Inicial.getText(), "Aviso: Horario inicial.",
				"O HORARIO INICIAL deve ser um numero inteiro. \nNo formato HHMM, sem adicao de pontucao.");
		if (horarioInicial == null) {

		}

		LocalTime time_Horario_Inicial = LocalTime.of((horarioInicial / 100), (horarioInicial % 100));
		LocalTime time_Qnt_Horas_Diarias = LocalTime.of((qntHorasDiarias / 100), (qntHorasDiarias % 100));

		Disciplina disciplina = new Disciplina();
		disciplina.setCod_Disciplina(codigoDisciplina);
		disciplina.setCod_Processo(codigoProcesso);
		disciplina.setNome_Diciplina(txtnome_Diciplina.getText());
		disciplina.setDia_Semana_Ministrada(txtdia_Semana_Ministrada.getValue());
		disciplina.setHorario_Inicial(time_Horario_Inicial);
		disciplina.setQnt_Horas_Diarias(time_Qnt_Horas_Diarias);
		disciplina.setCod_Curso(codigoCurso);
		return disciplina;
	}

	@Override
	public void entityToBoundary(Disciplina objeto) {
		if (objeto != null) {
			txtcod_Disciplina.setText(Integer.toString(objeto.getCod_Disciplina()));
			txtcod_Processo.setText(Integer.toString(objeto.getCod_Processo()));
			txtnome_Diciplina.setText(objeto.getNome_Diciplina());
			txtdia_Semana_Ministrada.setValue(objeto.getDia_Semana_Ministrada());

			DateTimeFormatter formatoExibicao = DateTimeFormatter.ofPattern("HH:mm");
			String horarioInicial = objeto.getHorario_Inicial().format(formatoExibicao);
			String qntHorasDiarias = objeto.getQnt_Horas_Diarias().format(formatoExibicao);
			txthorario_Inicial.setText(horarioInicial);
			txtqnt_Horas_Diarias.setText(qntHorasDiarias);

			txtcod_Curso.setText(Integer.toString(objeto.getCod_Curso()));
		}
	}

	private void limparCampos() {
		txtcod_Disciplina.setText("");
		txtcod_Processo.setText("");
		txtnome_Diciplina.setText("");
		txtdia_Semana_Ministrada.setValue(null);
		;
		txthorario_Inicial.setText("");
		txtqnt_Horas_Diarias.setText("");
		txtcod_Curso.setText("");
	}
}