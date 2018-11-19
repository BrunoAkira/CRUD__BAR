package Lote;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import Produto.ProdutoDAO;
import Produto.ProdutoVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoteController implements Initializable {

	LoteDAO lotDAO = new LoteDAO();

	@FXML
	private TextField txtFornecedor;

	@FXML
	private DatePicker txtData;

	@FXML
	private TextField txtQtd;

	@FXML
	private Button btnLessqtd;

	@FXML
	private TextField txtIdLote;

	@FXML
	private Button btnCancelar;

	@FXML
	private ComboBox<String> cbProduto;

	@FXML
	private Button btnRegistrar;

	@FXML
	private TextField txtCustounit;

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnAddqtd;

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
			if (txtIdLote.getText().trim().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Erro no campo de código do Lote");
				alert.setContentText("O campo precisa estar preenchido");

				alert.showAndWait();

				return false;
			}

			if (TryParseInt(txtIdLote.getText()) == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Erro no campo de código do Lote");
				alert.setContentText("O campo só aceita números");

				alert.showAndWait();

				return false;
			}
		}
		if (cbProduto.getItems().size() == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro de produto");
			alert.setContentText("Não existem produtos para registrar o lote, cadastre um produto primeiro");

			alert.showAndWait();

			return false;
		}
		if (cbProduto.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo Produto");
			alert.setContentText("O campo precisa ter algum valor selecionado");

			alert.showAndWait();

			return false;
		}
		if (txtQtd.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo de quantidade comprada");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();

			return false;
		}

		if (TryParseInt(txtQtd.getText()) == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo de quantidade comprada");
			alert.setContentText("O campo só aceita números");

			alert.showAndWait();

			return false;
		}
		if (txtFornecedor.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo de Fornecedor");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();

			return false;
		}

		System.out.println(LocalDate.now());
		System.out.println(txtData.getValue());
		
		if (txtData.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo da Data de compra");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();

			return false;
		}

		if (txtData.getValue().isAfter(LocalDate.now())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo da Data de compra");
			alert.setContentText("O campo precisa estar preenchido com uma data válida");

			alert.showAndWait();

			return false;
		}

		if (txtCustounit.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo de Preço de venda");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();

			return false;
		}
		txtCustounit.setText(txtCustounit.getText().replace(',', '.'));
		
		if (TryParseFloat(txtCustounit.getText()) == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo de preço de compra");
			alert.setContentText("O campo só aceita números");

			alert.showAndWait();

			return false;
		}
		return true;
	}

	LoteVO GerarLote() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String data = String.valueOf((txtData.getValue()));
		try {
			Date d = sdf.parse(data);
			sdf.applyPattern("dd/MM/yyyy");
			data = sdf.format(d);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int id = 0;

		if (!txtIdLote.getText().isEmpty()) {
			id = Integer.parseInt(txtIdLote.getText());
		}

		int idprod = 0;
		ProdutoVO p = new ProdutoVO();
		p.setNomeProduto(cbProduto.getValue());
		ProdutoDAO prodao = new ProdutoDAO();
		idprod = prodao.BuscarID(p).getId();

		LoteVO l = new LoteVO(id, idprod, txtFornecedor.getText(), data,
				Integer.parseInt(txtQtd.getText()),
				BigDecimal.valueOf(Double.parseDouble(txtCustounit.getText())));

		return l;
	}

	@FXML
	void BuscarId(ActionEvent event) {
		LoteVO l = new LoteVO();
		l.setIdLote(Integer.parseInt(txtIdLote.getText()));
		LoteVO aux = lotDAO.BuscarLote(l);

		if (aux != null) {
			txtFornecedor.setText(aux.getFornecedor());

			txtQtd.setText(String.valueOf(aux.getQuantidade()));

			txtIdLote.setText(String.valueOf(aux.getIdLote()));

			txtCustounit.setText(String.valueOf(aux.getCustoUnit()));

			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("dd/MM/yyyy");

			txtData.setValue(LocalDate.parse(aux.getDataCompra(), formatter));

			ProdutoDAO prodao = new ProdutoDAO();
			ProdutoVO p = new ProdutoVO();
			p.setId(aux.getIdProd());

			ProdutoVO auxp = prodao.BuscarProduto(p);

			cbProduto.getSelectionModel().select(
					prodao.BuscarProduto(auxp).getNomeProduto());

			txtFornecedor.setDisable(false);

			txtQtd.setDisable(false);

			txtIdLote.setDisable(false);

			txtCustounit.setDisable(false);

			txtData.setDisable(false);

			cbProduto.setDisable(false);

			txtIdLote.setDisable(true);

			btnBuscar.setDisable(true);

			btnRegistrar.setDisable(false);

			btnCancelar.setDisable(false);
			
			btnLessqtd.setDisable(false);
			
			btnAddqtd.setDisable(false);
		}
	}

	@FXML
	void CadastrarLote(ActionEvent event) {
		if (VerificaTela("Cadastrar")) {
			LoteVO l = GerarLote();

			lotDAO.AddLote(l);

			LimparTela(event);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Registro bem sucedido");

			alert.showAndWait();
		}
	}

	@FXML
	void AlterarLote(ActionEvent event) {
		if (VerificaTela("Alterar")) {
			LoteVO l = GerarLote();

			lotDAO.UpdateLote(l);

			LimparTelaAlterar(event);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Alteração bem sucedido");

			alert.showAndWait();
		}
	}

	@FXML
	void ApagarLote(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(null);
		alert.setContentText("Deseja realmente deletar o registro?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			LoteVO l = GerarLote();

			lotDAO.DeleteLote(l);

			LimparTelaAlterar(event);
		}
	}

	@FXML
	void LimparTela(ActionEvent event) {

		txtFornecedor.clear();

		txtData.setValue(LocalDate.now());

		txtQtd.clear();

		txtIdLote.clear();

		if (!cbProduto.getSelectionModel().isEmpty())
			cbProduto.getSelectionModel().select(0);
		else
			cbProduto.getSelectionModel().select(-1);
		txtCustounit.clear();
	}

	@FXML
	void LimparTelaAlterar(ActionEvent event) {

		txtFornecedor.setDisable(true);

		txtData.setDisable(true);

		txtQtd.setDisable(true);

		btnLessqtd.setDisable(true);

		txtIdLote.setDisable(false);

		btnCancelar.setDisable(true);

		cbProduto.setDisable(true);

		btnRegistrar.setDisable(true);

		txtCustounit.setDisable(true);

		btnBuscar.setDisable(false);

		btnAddqtd.setDisable(true);

		LimparTela(event);
	}

	@FXML
	public void Addqtd() {
		try {
			Integer.parseInt(txtQtd.getText());
			if (Integer.parseInt(txtQtd.getText()) < 0) {
				txtQtd.setText("1");
			} else
				txtQtd.setText(String.valueOf(Integer.parseInt(txtQtd.getText()) + 1));
		} catch (Exception e) {
			txtQtd.setText("1");
		}
	}

	@FXML
	public void Lessqtd() {
		try {
			Integer.parseInt(txtQtd.getText());
			if (Integer.parseInt(txtQtd.getText()) <= 0) {
				txtQtd.setText("0");
			} else
				txtQtd.setText(String.valueOf(Integer.parseInt(txtQtd.getText()) - 1));
		} catch (Exception e) {
			txtQtd.setText("0");
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		txtData.setValue(LocalDate.now());

		ObservableList<ProdutoVO> Lista = FXCollections.observableArrayList();

		try {
			ProdutoDAO prodao = new ProdutoDAO();
			Lista = prodao.ConsultaTudo("", "");
		} catch (SQLException e) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro de produto");
			alert.setContentText("Não existem produtos para registrar o lote, cadastre um produto primeiro");

			alert.showAndWait();
		}
		if (Lista != null) {
			cbProduto.getItems().clear();
			for (ProdutoVO p : Lista) {
				cbProduto.getItems().add(p.getNomeProduto());
				cbProduto.getSelectionModel().select(0);
			}
		}
		/*
		 * if(cbProduto.getItems().size() == 0) { Alert alert = new
		 * Alert(AlertType.ERROR); alert.setTitle("Erro");
		 * alert.setHeaderText("Erro de produto"); alert.setContentText(
		 * "Não existem produtos para registrar o lote, cadastre um produto primeiro"
		 * );
		 * 
		 * alert.showAndWait();
		 * 
		 * }
		 */
	}

}