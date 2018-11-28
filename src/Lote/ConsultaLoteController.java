package Lote;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class ConsultaLoteController implements Initializable {

	LoteDAO lotDAO = new LoteDAO();

	@FXML
	private TableView<LoteVO> tableEstoque;

	@FXML
	private TextField txtFiltro;

	@FXML
	private Button btnBuscar;

	@FXML
	private ComboBox<String> cbTipo;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbTipo.getItems().clear();
		cbTipo.getItems().addAll("Produto", "Fornecedor", "Quantidade",
				"Custo Unitário");
		cbTipo.getSelectionModel().select("Produto");

		TableColumn colunaId = new TableColumn<Object, Object>("Id");
		colunaId.setCellValueFactory(new PropertyValueFactory<>("IdLote"));

		TableColumn colunaNome = new TableColumn<Object, Object>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeProd"));

		TableColumn colunaFornecedor = new TableColumn<Object, Object>(
				"Fornecedor");
		colunaFornecedor.setCellValueFactory(new PropertyValueFactory<>(
				"fornecedor"));

		TableColumn colunadataCompra = new TableColumn<Object, Object>(
				"Data da Compra");
		colunadataCompra.setCellValueFactory(new PropertyValueFactory<>(
				"dataCompra"));

		TableColumn colunaQuantidade = new TableColumn<Object, Object>(
				"Quantidade");
		colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>(
				"quantidade"));

		TableColumn colunacustoUnit = new TableColumn<Object, Object>(
				"Custo Unitário");
		colunacustoUnit.setCellValueFactory(new PropertyValueFactory<>(
				"custoUnit"));

		tableEstoque.getColumns().clear();
		tableEstoque.getColumns().addAll(colunaId, colunaNome,
				colunaFornecedor, colunadataCompra, colunaQuantidade,
				colunacustoUnit);
		colunaNome.setPrefWidth(220);

		try {
			AddTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	
	public void AddTable() throws SQLException {
		tableEstoque.getItems().clear();
		String tipo = cbTipo.getValue().toString();
		String filtro = txtFiltro.getText().replace(',', '.');
		if (tipo == "Custo Unitário") {
			if(TryParseFloat(filtro) == null)
			{
				return;
			}
			tipo = "custoUnit";
		} else if (tipo == "Produto") {
			tipo = "NomeProduto";
		}
		try {
			ObservableList<LoteVO> ul = FXCollections.observableArrayList();
			ul = lotDAO.ConsultaTudo(filtro, tipo);
			if (ul != null) {
				for (LoteVO u : ul) {
					tableEstoque.getItems().add(u);
				}
			}
		} catch (SQLServerException e) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro de Lote");
			alert.setContentText("A tabela de lote não existe no banco de dados");

			alert.showAndWait();
		}
	}
}