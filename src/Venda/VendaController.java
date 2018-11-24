package Venda;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import Produto.ProdutoDAO;
import Produto.ProdutoVO;

public class VendaController implements Initializable {

	VendaDAO vDAO = new VendaDAO();
	BigDecimal total = new BigDecimal("0.00");
	@FXML
	private TableView<ItemVendaVO> tbProduto;

	@FXML
	private DatePicker txtData;

	@FXML
	private TextField txtQtd;

	@FXML
	private Button btnRegistar;

	@FXML
	private TextField txtComanda;

	@FXML
	private Button btnAddProduto;

	@FXML
	private Button btnCancelar;

	@FXML
	private ComboBox<String> cbProduto;

	@FXML
	private TextField txtIdVenda;

	@FXML
	private TextField txtTotal;

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnRemoveProduto;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		txtTotal.setText(String.valueOf(total));
		txtData.setValue(LocalDate.now());

		TableColumn colunaNome = new TableColumn("Produto");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		colunaNome.setEditable(true);

		TableColumn colunaQuantidade = new TableColumn("Quantidade");
		colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("qtd"));
		colunaQuantidade.setEditable(true);

		TableColumn colunaPreco = new TableColumn("Preço Unitario");
		colunaPreco.setCellValueFactory(new PropertyValueFactory<>("PrecoUnit"));
		colunaPreco.setEditable(true);

		tbProduto.getColumns().clear();
		tbProduto.getColumns().addAll(colunaNome, colunaQuantidade, colunaPreco);
		tbProduto.setEditable(true);
		colunaNome.setPrefWidth(200);
		colunaPreco.setPrefWidth(100);

		ObservableList<String> ul = FXCollections.observableArrayList();
		ProdutoDAO prodDAO = new ProdutoDAO();
		try {
			ul = prodDAO.ConsultaNomeProduto();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ul != null) {
			btnAddProduto.setDisable(false);
			btnRemoveProduto.setDisable(false);
			cbProduto.getItems().clear();
			for (String t : ul) {
				cbProduto.getItems().add(t);
			}
			cbProduto.getSelectionModel().select(0);
		} else {
			btnAddProduto.setDisable(true);
			btnRemoveProduto.setDisable(true);
		}
	}

	public static Integer TryParseInt(String someText) {
		try {
			return Integer.parseInt(someText);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	boolean VerificaTela(String Modo) {
		if (Modo != "Cadastrar") {
			if (txtIdVenda.getText().trim().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Erro no campo de código do Lote");
				alert.setContentText("O campo precisa estar preenchido");

				alert.showAndWait();

				return false;
			}

			if (TryParseInt(txtIdVenda.getText()) == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Erro no campo de código do Lote");
				alert.setContentText("O campo só aceita números");

				alert.showAndWait();

				return false;
			}
		}

		if (txtComanda.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo do Número da comanda");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();

			return false;
		}

		if (TryParseInt(txtComanda.getText()) == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo do Número da comanda");
			alert.setContentText("O campo só aceita números");

			alert.showAndWait();

			return false;
		}

		if (txtData.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo da Data de venda");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();

			return false;
		}

		if (txtData.getValue().isAfter(LocalDate.now())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo da Data de venda");
			alert.setContentText("O campo precisa estar preenchido com uma data válida");

			alert.showAndWait();

			return false;
		}

		if (cbProduto.getItems().size() == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro de produto");
			alert.setContentText("Não existem produtos para adicionar à venda, cadastre um produto primeiro");

			alert.showAndWait();

			return false;
		}
		if (cbProduto.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo Produto");
			alert.setContentText("Selecione algum produto para adicionar a venda");

			alert.showAndWait();

			return false;
		}

		if (tbProduto.getItems().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro, a tabela de produtos não pode estar vazia");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();

			return false;
		}

		return true;
	}

	VendaVO GerarVenda() {
		VendaDAO vDAO = new VendaDAO();

		ProdutoDAO prodDAO = new ProdutoDAO();

		int id = 0;
		if (txtIdVenda.getText().isEmpty()) {

			try {
				id = vDAO.ProximoID();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			id = Integer.parseInt(txtIdVenda.getText());
		}

		int comanda = Integer.parseInt(txtComanda.getText());
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

		BigDecimal total = BigDecimal.valueOf(Double.parseDouble(txtTotal.getText()));

		VendaVO v = new VendaVO(id, comanda, data, total);
		for (ItemVendaVO row : tbProduto.getItems()) {

			ItemVendaVO iv = new ItemVendaVO();

			ProdutoVO p = new ProdutoVO();
			p.setNomeProduto(row.getNomeProduto());
			
			iv.setIdprod(prodDAO.BuscarID(p).getId());
			iv.setIdvenda(id);
			iv.setQtd(row.getQtd());
			v.lista.add(iv);
		}

		return v;
	}

	@FXML
	void addTable(ActionEvent event) throws SQLException {

		ItemVendaVO ivVO = new ItemVendaVO();
		ProdutoDAO prodDAO = new ProdutoDAO();
		ProdutoVO prodVO = new ProdutoVO();
		Boolean correto = true;

		if (!cbProduto.getItems().isEmpty()) {
			ivVO.setNomeProduto(cbProduto.getValue().toString());
			prodVO.setNomeProduto(cbProduto.getValue().toString());
			prodVO = prodDAO.BuscarID(prodVO);
			ivVO.setIdprod(prodVO.getId());
			ivVO.setPrecoUnit(prodVO.getPrecoUnit());
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro na quantidade do produto");
			alert.setContentText("O campo precisa estar preenchido corretamente");

			alert.showAndWait();

			correto = false;
		}
		if (!txtQtd.getText().isEmpty() && TryParseInt(txtQtd.getText()) != null) {
			ivVO.setQtd(Integer.parseInt(txtQtd.getText()));
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro na quantidade do produto");
			alert.setContentText("O campo precisa estar preenchido corretamente");

			alert.showAndWait();

			correto = false;
		}

		if (correto) {

			if ((prodVO.getQtdTotal() - ivVO.getQtd()) >= 0) {
				boolean novoproduto = true;

				for (ItemVendaVO iv : tbProduto.getItems()) {
					
					if (iv.getNomeProduto().trim().toUpperCase().equals(ivVO.getNomeProduto().trim().toUpperCase())) {
						ItemVendaDAO ivDAO = new ItemVendaDAO();
						novoproduto = false;
						if (btnRegistar.getText().equals("Registrar")
								&& (prodVO.getQtdTotal() - (iv.getQtd() + ivVO.getQtd())) >= 0) {

							iv.setQtd(iv.getQtd() + ivVO.getQtd());
						}

						else if (btnRegistar.getText().equals("Alterar") && (prodVO.getQtdTotal()
								+ ivDAO.BuscaItemVendaPorID(iv).getQtd() - ivVO.getQtd() - iv.getQtd()) >= 0) {
							iv.setQtd(iv.getQtd() + ivVO.getQtd());
						} else {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Erro");
							alert.setHeaderText("Erro ao adicionar produto da tabela");
							alert.setContentText(
									"O produto selecionado não possui a quantidade que você deseja inserir o valor atual no estoque é de "
											+ prodVO.getQtdTotal());

							alert.showAndWait();
						}
						break;
					}
				}

				if (novoproduto) {
					tbProduto.getItems().add(ivVO);
				}
				total = new BigDecimal("0.00");
				for (ItemVendaVO iv : tbProduto.getItems()) {

					total = total.add(iv.getPrecoUnit().multiply(BigDecimal.valueOf(iv.getQtd())));
				}
				tbProduto.getColumns().get(0).setVisible(false);
				tbProduto.getColumns().get(0).setVisible(true);
				txtTotal.setText(String.format("%.2f", total).replace(',', '.'));
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Erro ao adicionar produto da tabela");
				alert.setContentText(
						"O produto selecionado não possui a quantidade que você deseja inserir o valor atual no estoque é de "
								+ prodVO.getQtdTotal());

				alert.showAndWait();
			}
		}
	}

	@FXML
	void RemoveTable(ActionEvent event) {

		int i = tbProduto.getSelectionModel().getSelectedIndex();
		if (i != -1) {
			tbProduto.getItems().remove(i);

			total = new BigDecimal("0.00");
			for (ItemVendaVO iv : tbProduto.getItems())
				total = total.add(iv.getPrecoUnit().multiply(BigDecimal.valueOf(iv.getQtd())));
			txtTotal.setText(String.format("%.2f", total).replace(',', '.'));
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro ao excluir produto da tabela");
			alert.setContentText("Selecione algum produto para excluir");

			alert.showAndWait();
		}
	}

	@FXML
	void CadastrarVenda(ActionEvent event) {
		if (VerificaTela("Cadastrar")) {
			VendaVO v = GerarVenda();

			vDAO.AddVenda(v);

			LimparTela(event);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Registro bem sucedido");

			alert.showAndWait();
		}
	}

	@FXML
	void AlterarVenda(ActionEvent event) {
		if (VerificaTela("Alterar")) {
			VendaVO v = GerarVenda();

			vDAO.UpdateVenda(v);

			LimparTelaAlterar(event);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Alteração bem sucedida");

			alert.showAndWait();
		}
	}

	@FXML
	void DeletarVenda(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(null);
		alert.setContentText("Deseja realmente deletar o registro?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			VendaVO v = GerarVenda();

			vDAO.DeleteVenda(v);

			LimparTelaAlterar(event);
		}
	}

	@FXML
	void LimparTela(ActionEvent event) {

		tbProduto.getItems().clear();

		txtData.setValue(LocalDate.now());

		txtQtd.clear();

		txtComanda.clear();

		if (!cbProduto.getItems().isEmpty())
			cbProduto.getSelectionModel().select(0);

		txtIdVenda.clear();

		total = new BigDecimal("0.00");
		txtTotal.setText(String.format("%.2f", total).replace(',', '.'));
	}

	@FXML
	void LimparTelaAlterar(ActionEvent event) {
		tbProduto.setDisable(true);

		txtData.setDisable(true);

		txtQtd.setDisable(true);

		txtComanda.setDisable(true);

		cbProduto.setDisable(true);

		txtTotal.setDisable(true);

		txtIdVenda.setDisable(false);

		btnAddProduto.setDisable(true);

		btnRemoveProduto.setDisable(true);

		btnBuscar.setDisable(false);

		btnRegistar.setDisable(true);

		btnCancelar.setDisable(true);

		LimparTela(event);
	}

	@FXML
	void BuscarId(ActionEvent event) {
		VendaVO v = new VendaVO();
		v.setId(Integer.parseInt(txtIdVenda.getText()));
		VendaVO aux = vDAO.BuscarVenda(v);
		if (aux != null) {
			txtComanda.setText(String.valueOf(aux.getComanda()));

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			txtData.setValue(LocalDate.parse(aux.getData(), formatter));

			total = new BigDecimal("0.00");
			tbProduto.getItems().clear();

			for (ItemVendaVO iv : aux.lista) {
				tbProduto.getItems().add(iv);
				total = total.add(iv.getPrecoUnit().multiply(BigDecimal.valueOf(iv.getQtd())));
			}
			txtTotal.setText(String.format("%.2f", total).replace(',', '.'));

			tbProduto.setDisable(false);

			txtData.setDisable(false);

			txtQtd.setDisable(false);

			txtComanda.setDisable(false);

			cbProduto.setDisable(false);

			txtTotal.setDisable(false);

			txtIdVenda.setDisable(true);

			btnAddProduto.setDisable(false);

			btnRemoveProduto.setDisable(false);

			btnBuscar.setDisable(true);

			btnRegistar.setDisable(false);

			btnCancelar.setDisable(false);
		}
	}

}
