package view;

import controller.InscricoesController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.dados.Inscricoes;
import model.dados.Professor;
import model.estruturas.ListaSimples;
import model.estruturas.No;
import service.interfaces.ITabs;
import utils.Validar;

public class InscricoesTab implements ITabs<Inscricoes> {

	private TextField txtcod_Processo;
	private TextField txtcpf;
	private TextField txtcod_Disciplina;

	private InscricoesController<Inscricoes> controll = new InscricoesController<>();

	public InscricoesTab() {
		txtcod_Processo = new TextField();
		txtcpf = new TextField();
		txtcod_Disciplina = new TextField();
	}

	@Override
	public BorderPane getTela() {
		ColumnConstraints coluna1 = new ColumnConstraints();
		ColumnConstraints coluna2 = new ColumnConstraints();
		coluna1.setPercentWidth(30);
		coluna2.setPercentWidth(60);

		BorderPane telaInscricoes = new BorderPane();
		GridPane inscricoesForm = new GridPane();
		ScrollPane scrollPesquisa = new ScrollPane(null);
		HBox caixaBotoes = new HBox(60);
		Label tamanhoItens = new Label("Existem atualmente " + controll.getSize() + " Inscricoes salvas no total.");

		scrollPesquisa.setFitToWidth(true);
		scrollPesquisa.setPannable(true);

		inscricoesForm.getColumnConstraints().addAll(coluna1, coluna2);
		inscricoesForm.setHgap(5);
		inscricoesForm.setVgap(5);
		inscricoesForm.setPadding(new Insets(15));

		inscricoesForm.add(new Label("Codigo do Processo: "), 0, 0);
		inscricoesForm.add(txtcod_Processo, 1, 0);

		inscricoesForm.add(new Label("CPF: "), 0, 1);
		inscricoesForm.add(txtcpf, 1, 1);

		inscricoesForm.add(new Label("Codigo da Disciplina: "), 0, 2);
		inscricoesForm.add(txtcod_Disciplina, 1, 2);

		Button bntGravar = new Button("Gravar");
		bntGravar.setOnAction(e -> {
			Inscricoes inscricoes = boundaryToEntity();
			if (inscricoes != null) {
				try {
					controll.gravar(inscricoes);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Validar.mensagemInformacao("Informativo: gravacao concluida.", "A inscricao foi gravada com sucesso.");
				limparCampos();
				tamanhoItens.setText("Existem atualmente " + controll.getSize() + " Inscricoes salvas no total.");
			}
		});

		Button bntAtualizar = new Button("Atualizar");
		bntAtualizar.setOnAction(e -> {
			Inscricoes inscricoes = boundaryToEntity();
			if (inscricoes != null) {
				try {
					int cod_Processo_Pesquisa = Integer.parseInt(txtcod_Processo.getText());
					controll.atualizar(cod_Processo_Pesquisa, inscricoes);
					limparCampos();
				} catch (Exception e1) {
					Validar.mensagemAviso("Aviso: Falha ao atualizar.", "Ocorreu uma falha.", e1);
				}
			}
		});

		Button bntRemover = new Button("Remover");
		bntRemover.setOnAction(e -> {
			try {
				int cod_Processo_Pesquisa = Integer.parseInt(txtcod_Processo.getText());
				controll.remover(cod_Processo_Pesquisa);
				limparCampos();
				tamanhoItens.setText("Existem atualmente " + controll.getSize() + " Inscricoes salvas no total.");
			} catch (Exception e1) {
				Validar.mensagemAviso("Aviso: asmfka", "sadsadsa", e1);
			}
		});

		Button bntPesquisar = new Button("Pesquisar");
		bntPesquisar.setOnAction(e -> {
			try {
				int cod_Pesquisa = Integer.parseInt(txtcod_Disciplina.getText());

				ListaSimples<Inscricoes> inscricoes = controll.pesquisar(cod_Pesquisa);
				if (inscricoes == null) {
					Validar.mensagemAviso("Aviso: Nenhuma inscrição encontrada.",
							"Não foram encontradas inscrições para o código de disciplina informado.");
					return;
				}

				ListaSimples<Professor> professores = controll.procurar(inscricoes);
				if (professores == null) {
					Validar.mensagemAviso("Aviso: Nenhum professor encontrado.",
							"Nenhum professor encontrado para as inscrições desta disciplina.");
					return;
				}

				VBox conteudoInscricoes = new VBox();
				No<Inscricoes> noInscricoes = inscricoes.getPrimeiro();
				while (noInscricoes != null) {
					No<Professor> noProfessores = professores.getPrimeiro();
					Inscricoes inscricaoAtual = noInscricoes.getDado();

					conteudoInscricoes.getChildren()
							.add(new Label("Inscrição - Código de Disciplina: " + inscricaoAtual.getCod_Disciplina()
									+ ", Código de Processo: " + inscricaoAtual.getCod_Processo() + ", "));

					while (noProfessores != null) {
						Professor professorAtual = noProfessores.getDado();

						conteudoInscricoes.getChildren()
								.add(new Label("Professor - Nome: " + professorAtual.getNome()
										+ ", Área de Conhecimento: " + professorAtual.getArea_Conhecimento() + ", CPF: "
										+ professorAtual.getCpf() + ", Pontos: " + professorAtual.getQnt_Pontos()));

						conteudoInscricoes.getChildren().add(new Label("\n"));

						noInscricoes = noInscricoes.getProximo();
						noProfessores = noProfessores.getProximo();
					}
				}
				scrollPesquisa.setContent(conteudoInscricoes);

			} catch (NumberFormatException ex) {
				Validar.mensagemAviso("Aviso: Campo de pesquisa inválido.",
						"O campo de pesquisa (Código de Disciplina) deve conter apenas números.", ex);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});

		caixaBotoes.getChildren().addAll(bntGravar, bntAtualizar, bntRemover, bntPesquisar);
		inscricoesForm.add(caixaBotoes, 0, 3, 2, 1);

		telaInscricoes.setTop(inscricoesForm);
		telaInscricoes.setCenter(scrollPesquisa);
		telaInscricoes.setBottom(tamanhoItens);
		return telaInscricoes;
	}

	@Override
	public Inscricoes boundaryToEntity() {

		if (Validar.isEmptyCampos(txtcod_Processo.getText(), txtcpf.getText(), txtcod_Disciplina.getText())) {
			return null;
		}

		Integer cod_Processo = Validar.validarParseInt(txtcod_Processo.getText(), "Codigo do Processo");
		if (cod_Processo == null) {
			return null;
		}

		Integer cpf = Validar.validarParseInt(txtcpf.getText(), "cpf");
		if (cpf == null) {
			return null;
		}

		Integer cod_Disciplina = Validar.validarParseInt(txtcod_Disciplina.getText(), "Codigo da Disciplina");
		if (cod_Disciplina == null) {
			return null;
		}

		Inscricoes inscricoes = new Inscricoes();
		inscricoes.setCod_Processo(cod_Processo);
		inscricoes.setCpf(cpf);
		inscricoes.setCod_Disciplina(cod_Disciplina);
		return inscricoes;
	}

	@Override
	public void entityToBoundary(Inscricoes objeto) {
		if (objeto != null) {
			txtcod_Processo.setText(Integer.toString(objeto.getCod_Processo()));
			txtcpf.setText(Integer.toString(objeto.getCpf()));
			txtcod_Disciplina.setText(Integer.toString(objeto.getCod_Disciplina()));
		}
	}

	private void limparCampos() {
		txtcod_Processo.setText("");
		txtcpf.setText("");
		txtcod_Disciplina.setText("");
	}
}
