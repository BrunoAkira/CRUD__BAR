package Usuario;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;

public class ConsultaUsuarioController implements Initializable{

	UsuarioDAO usuaDAO = new UsuarioDAO();
	
	 @FXML
	 private TextField txtFiltro;

	 @FXML
	 private Button btnBuscar;

	 @FXML
	 private ComboBox<String> cbTipo;
	 
	// @FXML
	// private TableColumn<UsuarioVO, String> Cargo;

	// @FXML
	// private TableColumn<UsuarioVO, String> Usuario;

	// @FXML
	// private TableColumn<UsuarioVO, String> Admissao;



	// @FXML
	 //private TableColumn<UsuarioVO, Integer> Id;

	// @FXML
	// private TableColumn<UsuarioVO, String> Nome;
	    
	 @FXML
	 private TableView<UsuarioVO> tableUsuario;

	@SuppressWarnings({"unchecked", "rawtypes" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbTipo.getItems().clear();
		cbTipo.getItems().addAll("Nome","cargo","login","Sexo");
				
			
		cbTipo.getSelectionModel().select("Nome");	
			
		TableColumn colunaId = new TableColumn<Object, Object>("Id");
		colunaId.setCellValueFactory(new PropertyValueFactory<>("Id"));
			
		TableColumn colunaNome = new TableColumn<Object, Object>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));

		TableColumn colunaCargo = new TableColumn<Object, Object>("Cargo");
		colunaCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
			
		TableColumn colunaUsuario = new TableColumn<Object, Object>("Usuario");
		colunaUsuario.setCellValueFactory(new PropertyValueFactory<>("login"));
		
		TableColumn colunaSenha = new TableColumn<Object, Object>("Senha");
		colunaSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
		
		TableColumn colunaAdmissao = new TableColumn<Object, Object>("Admissao");
		colunaAdmissao.setCellValueFactory(new PropertyValueFactory<>("Dataadmissao"));
		
		TableColumn colunaRG = new TableColumn<Object, Object>("RG");
		colunaRG.setCellValueFactory(new PropertyValueFactory<>("RG"));
		
		
		
		tableUsuario.getColumns().clear();
		tableUsuario.getColumns().addAll(colunaId, colunaNome,colunaRG,colunaCargo,colunaAdmissao,colunaUsuario,colunaSenha);
		colunaNome.setPrefWidth(220);
		try {
			AddTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			
	}
		
	public void AddTable () throws SQLException {
		tableUsuario.getItems().clear();
		String tipo = cbTipo.getValue().toString();
		String filtro = txtFiltro.getText();
		if(tipo == "Sexo" )
		{
			filtro = filtro.substring(0,1);
		}

		ObservableList<UsuarioVO> ul = FXCollections.observableArrayList();
		ul = usuaDAO.ConsultaTudo(filtro,tipo);
		for (UsuarioVO u : ul) {
			tableUsuario.getItems().add(u);
		}
	}
}
   
