package Produto;

import java.math.BigDecimal;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

	public static Float TryParseFloat(String someText) {
		try {
			return Float.valueOf(someText);
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
				alert.setContentText("O campo só aceita números, respeite a quantidade maxima de 9 digitos");

				alert.showAndWait();

				return false;
			}
		}
		if (txtProduto.getText().trim() == "") {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo do nome do produto");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();

			return false;
		}
		
		if (txtProduto.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo do nome do produto");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();

			return false;
		}

		if (txtPrecoVenda.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo de Preço de venda");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();

			return false;
		}

		txtPrecoVenda.setText(txtPrecoVenda.getText().replace(',', '.'));
		
		if (TryParseFloat(txtPrecoVenda.getText()) == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo de Preço de venda");
			alert.setContentText("O campo só aceita números, respeite a quantidade maxima de 9 digitos");

			alert.showAndWait();

			return false;
		}
		
		if (TryParseFloat(txtPrecoVenda.getText()) < 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo de Preço de venda");
			alert.setContentText("O campo só aceita números positivos");

			alert.showAndWait();

			return false;
		}

		if (txtDescricao.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo de Descrição");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();

			return false;
		}

		if (txtTipoProduto.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo de Tipo do produto");
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

		ProdutoVO p = new ProdutoVO(id, txtProduto.getText(),
				txtDescricao.getText(), txtTipoProduto.getText(), qtdtotal,
				qtdvenda, BigDecimal.valueOf(Double.parseDouble(txtPrecoVenda.getText())));

		return p;
	}

	@FXML
	void CadastrarProduto(ActionEvent event) {
		if (VerificaTela("Cadastrar")) {
			ProdutoVO p = GerarProduto();
			if (prodDAO.BuscarID(p) == null) {
				prodDAO.AddProduto(p);

				LimparTela(event);

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("Registro bem sucedido");

				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("O produto "+ p.getNomeProduto() +" já está cadastrado no banco de dados");

				alert.showAndWait();
			}
		}
	}

	@FXML
	void AlterarProduto(ActionEvent event) {
		if (VerificaTela("Alterar")) {
			ProdutoVO p = GerarProduto();
			prodDAO.UpdateProduto(p);

			LimparTelaAlterar(event);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Alteração bem sucedido");

			alert.showAndWait();
			} 
	}

	@FXML
	void ApagarProduto(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(null);
		alert.setContentText("Deseja realmente deletar o registro?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			ProdutoVO p = GerarProduto();
			prodDAO.DeleteProduto(p);

			LimparTelaAlterar(event);
		}
	}

	@FXML
	void BuscarId(ActionEvent event) {

		ProdutoVO p = new ProdutoVO();
		if(TryParseInt(txtIdProduto.getText()) == null )
		{
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo de código do produto");
			alert.setContentText("O campo só aceita números inteiros");

			alert.showAndWait();
			return;
		}
		if(TryParseInt(txtIdProduto.getText()) < 0)
		{
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo de código do produto");
			alert.setContentText("O campo só aceita números positivos");

			alert.showAndWait();
			return;
		} 
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

			//txtProduto.setDisable(false);

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