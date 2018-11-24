package Venda;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ConsultaVendaController implements Initializable{
	
	VendaDAO vDAO = new VendaDAO();
	
    @FXML
    private TableView<VendaVO> tbVenda;

    @FXML
    private TextField txtFiltro;

    @FXML
    private Button btnBuscar;

    @FXML
    private ComboBox<String> cbTipo;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		cbTipo.getItems().clear();	
		cbTipo.getItems().addAll("Comanda","Data","PrecoTotal");	
		cbTipo.getSelectionModel().select(0);

		
		TableColumn colunaId = new TableColumn<Object, Object>("Id");
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
			
		TableColumn colunaProduto = new TableColumn<Object, Object>("Comanda");
		colunaProduto.setCellValueFactory(new PropertyValueFactory<>("Comanda"));

		TableColumn colunaDescricao = new TableColumn<Object, Object>("Data");
		colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("Data"));
			
		TableColumn colunaPrecoTotal = new TableColumn<Object, Object>("Preço Total");
		colunaPrecoTotal.setCellValueFactory(new PropertyValueFactory<>("PrecoTotal"));

		tbVenda.getColumns().clear();
		tbVenda.getColumns().addAll(colunaId, colunaProduto,colunaDescricao,colunaPrecoTotal);
		colunaDescricao.setPrefWidth(220);
		try {
				AddTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public static Float TryParseFloat(String someText) {
		try {
			return Float.valueOf(someText);
		} catch (NumberFormatException ex) {
			return null;
		}
	}
		
	@FXML
	public void AddTable () throws SQLException {
		tbVenda.getItems().clear();
		String tipo = cbTipo.getValue().toString();
		String filtro = txtFiltro.getText().replace(',', '.');

		
		if(tipo == "Comanda" || tipo == "PrecoTotal")
		{
			if(TryParseFloat(filtro) == null)
			{				
				return;
			}
			filtro = filtro.replace(',', '.');
		}
		ObservableList<VendaVO> ul = FXCollections.observableArrayList();
		ul = vDAO.ConsultarTudo(filtro, tipo);
		for (VendaVO p : ul) {
			
			tbVenda.getItems().add(p);
		}
	}

}
