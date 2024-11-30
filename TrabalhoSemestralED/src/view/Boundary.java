package view;

import controller.ConsultaDisciplinas;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Boundary extends Application {

	/*
	 * A tela principal e Main do programa, onde se adicionará as Abas, cada Aba se
	 * encarrega de implmentar os proprios metodos de Criar, Ler, Atualizar e
	 * Apagar. As Abas são chamadas de *Nome da Aba*Tab.java essas classes devem ser
	 * adicionadas no pacote view.
	 */

	@Override
	public void start(Stage stage) throws Exception {

		// Cria o painel principal e a Scene(scn) adiconando o painel principal e o
		// tamanho da tela da aplicação.
		TabPane principal = new TabPane();
		Scene scn = new Scene(principal, 550, 500);

		// Cria a nova Aba com base na tela criada no CusoTab.java, seus parametros são
		// o Nome da Aba e a sua tela
		Tab tabCurso = new Tab("Curso", new CursoTab().getTela());
		tabCurso.setClosable(false);

		Tab tabDisciplina = new Tab("Disciplina", new DisciplinaTab().getTela());
		tabDisciplina.setClosable(false);

		Tab tabInscricoes = new Tab("Inscricoes", new InscricoesTab().getTela());
		tabInscricoes.setClosable(false);

		Tab tabProfessores = new Tab("Professores", new ProfessoresTab().getTela());
		tabProfessores.setClosable(false);
		Tab tabConsulata = new Tab("Consulta de Disciplina", new ConsultaDisciplinas().getTela());
		tabConsulata.setClosable(false);
		// Adiciona as abas na Tela Principal
		principal.getTabs().addAll(tabCurso, tabDisciplina, tabInscricoes, tabProfessores, tabConsulata);

		// Adiciona o icone na Aplicacao, adicona a Scene (scn) ao Stage (stage), dá um
		// titulo para a Aplicação.
		stage.getIcons().add(new Image("/auxilio/livroMainIcon.png"));
		stage.setScene(scn);
		stage.setTitle("Chamada publica");
		stage.show();

	}

	public static void main(String[] args) {
		Application.launch(Boundary.class, args);
	}
	// Terminando de editar usa (Ctrl + Shift + F) pra as linhas do codigo.

	// -- Adicionar uma mensgem de erro nos botoes remove.
}
