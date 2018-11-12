package Produto;

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

public class ConsultaProdutoController implements Initializable{

	ProdutoDAO prodDAO = new ProdutoDAO();
    @FXML
    private TableView<ProdutoVO> tableEstoque;

    @FXML
    private TextField txtFiltro;

    @FXML
    private Button btnBuscar;

    @FXML
    private ComboBox<String> cbTipo;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ObservableList<String> ul = FXCollections.observableArrayList();
		
		try {
			ul = prodDAO.ConsultaTipoProduto();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ul != null)
		{
			cbTipo.getItems().clear();	
			cbTipo.getItems().add("Todos");
			for (String t : ul) {
				cbTipo.getItems().add(t);
			}			
			cbTipo.getSelectionModel().select(0);
		}
		
		TableColumn colunaId = new TableColumn<Object, Object>("Id");
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
			
		TableColumn colunaProduto = new TableColumn<Object, Object>("Produto");
		colunaProduto.setCellValueFactory(new PropertyValueFactory<>("NomeProduto"));

		TableColumn colunaDescricao = new TableColumn<Object, Object>("Descrição");
		colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
			
		TableColumn colunaPrecoUnit = new TableColumn<Object, Object>("Preço Unitário");
		colunaPrecoUnit.setCellValueFactory(new PropertyValueFactory<>("PrecoUnit"));
		
		TableColumn colunaTotal = new TableColumn<Object, Object>("Total");
		colunaTotal.setCellValueFactory(new PropertyValueFactory<>("QtdTotal"));
		
		//, , ,TipoProduto, , QtdVendida, 
		
		
		tableEstoque.getColumns().clear();
		tableEstoque.getColumns().addAll(colunaId, colunaProduto,colunaDescricao,colunaPrecoUnit,colunaTotal);
		colunaDescricao.setPrefWidth(220);
		try {
			if(ul != null)
				AddTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	public void AddTable () throws SQLException {
		tableEstoque.getItems().clear();
		String TipoProduto = "";
		if(cbTipo.getSelectionModel().getSelectedIndex() != 0)
			TipoProduto = cbTipo.getValue().toString();
		String NomeProduto = txtFiltro.getText();

		ObservableList<ProdutoVO> ul = FXCollections.observableArrayList();
		ul = prodDAO.ConsultaTudo(TipoProduto,NomeProduto);
		for (ProdutoVO p : ul) {
			tableEstoque.getItems().add(p);
		}
	}
}
