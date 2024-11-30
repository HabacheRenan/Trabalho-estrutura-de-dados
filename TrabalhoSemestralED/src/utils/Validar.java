package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

//Classe de validação de dados e geração de mensagens.
public class Validar {

	/*
	 * Valida se o campo digitado(String) pode ser convertido em um numero(Integer).
	 * Se passa como parametro; o Texto digitado e o Nome do campo
	 */
	public static Integer validarParseInt(String txtField, String campo) {
		try {
			int valorGravar;
			valorGravar = Integer.parseInt(txtField);
			return valorGravar;
		} catch (Exception e) {
			mensagemAviso("Campo invalido.", "O campo (" + campo.toUpperCase() + ") deve" + " conter apenas numero.",
					e);
			return null;
		}
	}

	/*
	 * Realiza a mesma validação que a classe anterior, entretando com parametros
	 * diferentes Se passa como parametro; o Texto digitado, o texto inicial de
	 * aviso da mensagem e o texto final de aviso.
	 */
	public static Integer validarParseInt(String txtField, String textoMensagem, String textoMensagemAviso) {
		try {
			int valorGravar;
			valorGravar = Integer.parseInt(txtField);
			return valorGravar;
		} catch (Exception e) {
			mensagemAviso(textoMensagem, textoMensagemAviso, e);
			return null;
		}
	}

	// Verifica se um ou mais campos estão vazios. varargs
	public static boolean isEmptyCampos(String... txtFields) {
		for (String txt : txtFields) {
			if (txt == null || txt.isEmpty()) {
				mensagemAviso("Campo em branco.", "Todos os campos devem ser preenchidos.");
				return true;
			}
		}
		return false;
	}

	// Gera um pop-up, Informativo.
	public static void mensagemInformacao(String informacao, String textoInformacao) {
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("Informacao");
		alerta.setHeaderText(informacao);
		alerta.setContentText(textoInformacao);
		alerta.show();
	}

	// Gera um pop-up, de Aviso. (Informa a Exception no aviso)
	public static void mensagemAviso(String aviso, String textoAviso, Exception e) {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Aviso");
		alerta.setHeaderText(aviso);
		alerta.setContentText(textoAviso + "\n" + e.getMessage());
		alerta.show();
	}

	// Gera um pop-up, de Aviso
	public static void mensagemAviso(String aviso, String textoAviso) {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Aviso");
		alerta.setHeaderText(aviso);
		alerta.setContentText(textoAviso);
		alerta.show();
	}

}