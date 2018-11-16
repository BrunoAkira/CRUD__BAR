package Produto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ProdutoController {

	ProdutoDAO prodDAO = new ProdutoDAO();

	@FXML
	private TextField txtDescricao;

	@FXML
	private TextField txtProduto;

	@FXML
	private TextField txtIdProduto;

	@FXML
	private TextField txtQtdVendida;

	@FXML
	private Button btnCancelar;

	@FXML
	private TextField txtTipoProduto;

	@FXML
	private TextField txtQtdTotal;

	@FXML
	private TextField txtPrecoVenda;

	@FXML
	private Button btnRegistrar;

	@FXML
	private Button btnBuscar;

	public static Integer TryParseInt(String someText) {
		try {
			return Integer.parseInt(someText);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	boolean VerificaTela(String Modo) {
		if (Modo != "Cadastrar") {
			if (txtIdProduto.getText().trim().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Erro no campo de código do Produto");
				alert.setContentText("O campo precisa estar preenchido");

				alert.showAndWait();

				return false;
			}

			if (TryParseInt(txtIdProduto.getText()) == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Erro no campo de código do Produto");
				alert.setContentText("O campo só aceita números");

				alert.showAndWait();

				return false;
			}
		}
		if (txtProduto.getText() == "") {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo do nome do produto");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();

			return false;
		}

		return true;
	}

	ProdutoVO GerarProduto() {
		int id = 0;
		int qtdtotal = 0;
		int qtdvenda = 0;

		if (!txtIdProduto.getText().isEmpty()) {
			id = Integer.parseInt(txtIdProduto.getText());
		}

		ProdutoVO p = new ProdutoVO(id, txtProduto.getText(), txtDescricao.getText(), txtTipoProduto.getText(),
				qtdtotal, qtdvenda, Float.parseFloat(txtPrecoVenda.getText()));

		return p;
	}

	@FXML
	void CadastrarProduto(ActionEvent event) {
		if (VerificaTela("Cadastrar")) {
			ProdutoVO p = GerarProduto();
			prodDAO.AddProduto(p);

			LimparTela(event);
		}
	}

	@FXML
	void AlterarProduto(ActionEvent event) {
		if (VerificaTela("Alterar")) {
			ProdutoVO p = GerarProduto();
			prodDAO.UpdateProduto(p);

			LimparTelaAlterar(event);
		}
	}

	@FXML
	void ApagarProduto(ActionEvent event) {
		if (VerificaTela("Deletar")) {
			ProdutoVO p = GerarProduto();
			prodDAO.DeleteProduto(p);

			LimparTelaAlterar(event);
		}
	}

	@FXML
	void BuscarId(ActionEvent event) {

		ProdutoVO p = new ProdutoVO();
		p.setId(Integer.parseInt(txtIdProduto.getText()));
		ProdutoVO aux = prodDAO.BuscarProduto(p);

		if (aux != null) {
			txtIdProduto.setText(String.valueOf(aux.getId()));

			txtProduto.setText(aux.getNomeProduto());

			txtDescricao.setText(aux.getDescricao());

			txtTipoProduto.setText(aux.getTipoProduto());

			txtPrecoVenda.setText(String.valueOf(aux.getPrecoUnit()));

			txtQtdTotal.setText(String.valueOf(aux.getQtdTotal()));

			txtQtdVendida.setText(String.valueOf(aux.getQtdVendida()));

			txtDescricao.setDisable(false);

			txtProduto.setDisable(false);

			txtTipoProduto.setDisable(false);

			txtPrecoVenda.setDisable(false);

			txtDescricao.setDisable(false);

			txtQtdVendida.setDisable(true);

			txtQtdTotal.setDisable(true);

			btnBuscar.setDisable(true);

			txtIdProduto.setDisable(true);

			btnRegistrar.setDisable(false);

			btnCancelar.setDisable(false);
		}
	}

	@FXML
	void LimparTela(ActionEvent event) {

		txtDescricao.clear();

		txtProduto.clear();

		txtTipoProduto.clear();

		txtPrecoVenda.clear();
	}

	@FXML
	void LimparTelaAlterar(ActionEvent event) {
		txtDescricao.setDisable(true);

		txtProduto.setDisable(true);

		txtTipoProduto.setDisable(true);

		txtPrecoVenda.setDisable(true);

		txtDescricao.setDisable(true);

		txtQtdVendida.setDisable(true);

		txtQtdTotal.setDisable(true);

		btnBuscar.setDisable(false);

		txtIdProduto.setDisable(false);

		btnRegistrar.setDisable(true);

		btnCancelar.setDisable(true);

		txtQtdVendida.clear();

		txtQtdTotal.clear();

		txtIdProduto.clear();

		LimparTela(event);
	}
}