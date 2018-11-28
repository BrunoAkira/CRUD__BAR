package Usuario;

import javax.swing.JOptionPane;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class UsuarioController implements Initializable{

	UsuarioDAO usuaDAO = new UsuarioDAO();
	
    @FXML
    private TextField txtSenha;

    @FXML
    private RadioButton rbMasc;

    @FXML
    private TextField txtCargo;

    @FXML
    private Button txtRegistrar;

    @FXML
    private Button txtCancelar;

    @FXML
    private TextField txtNome;

    @FXML
    private ToggleGroup Sexo;
    
    @FXML
    private RadioButton rbFem;

    @FXML
    private TextField txtIdFuncionario;

    @FXML
    private DatePicker txtNasc;

    @FXML
    private TextField txtUsuario;

    @FXML
    private DatePicker txtDataAdmissao;

    @FXML
    private Button btnBuscar;

    @FXML
    private TextField txtRG;
    
    public static Integer TryParseInt(String someText) {
    	   try {
    	      return Integer.parseInt(someText);
    	   } catch (NumberFormatException ex) {
    	      return null;
    	   }
    	}
    
    boolean VerificaTela(String Modo)
    {
    	if(Modo != "Cadastrar")
    	{
    		if(txtIdFuncionario.getText().trim().isEmpty())
    		{
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Erro");
    			alert.setHeaderText("Erro no campo de código do Usuario");
    			alert.setContentText("O campo precisa estar preenchido");

    			alert.showAndWait();
    		
    			return false;
    		}

    		if(TryParseInt(txtIdFuncionario.getText()) == null)
    		{
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Erro");
    			alert.setHeaderText("Erro no campo de código do Usuario");
    			alert.setContentText("O campo só aceita números, respeite a quantidade maxima de 9 digitos");

    			alert.showAndWait();
    		
    			return false;
    		}
    	}
		if(txtNome.getText().trim().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo Nome");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();
		
			return false;
		}

		if(txtCargo.getText().trim().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo Cargo");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();
		
			return false;
		}
		
		if(txtRG.getText().trim().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo do RG");
			alert.setContentText("O campo precisa estar preenchido\nDigite apenas os números EX:785642124");

			alert.showAndWait();
		
			return false;
		}
		
		if(TryParseInt(txtRG.getText()) == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo do RG");
			alert.setContentText("O campo só aceita números 	EX:385347121");

			alert.showAndWait();
		
			return false;
		}
		
		
		if(txtUsuario.getText().trim().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo do Login");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();
		
			return false;
		}
		
		if(txtSenha.getText().trim().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo da Senha");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();
		
			return false;
		}
		
		if(txtNasc.getValue() == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo da Data de Nascimento");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();
		
			return false;
		}
		
		if(txtNasc.getValue().isAfter(LocalDate.now()))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo da Data de Nascimento");
			alert.setContentText("O campo precisa estar preenchido com uma data válida");

			alert.showAndWait();
		
			return false;
		}
		
		if(txtDataAdmissao.getValue() == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo da Data de Admissão");
			alert.setContentText("O campo precisa estar preenchido");

			alert.showAndWait();
		
			return false;
		}
		
		if(txtDataAdmissao.getValue().isAfter(LocalDate.now()))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo da Data de Admissão");
			alert.setContentText("O campo precisa estar preenchido com uma data válida");

			alert.showAndWait();
		
			return false;
		}
		
    	return true;
    }
    
    UsuarioVO GerarUsuario()
    {

    	boolean verifica = true;
    	String sexo;
    	if(rbMasc.isSelected())
    		sexo = "F";
    	else
    		sexo = "M";

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  	
    	String admin =  String.valueOf((txtDataAdmissao.getValue()));
    	try {
			Date d = sdf.parse(admin);
			sdf.applyPattern("dd/MM/yyyy");
			admin = sdf.format(d);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	sdf.applyPattern("yyyy-MM-dd");
    		
    	String nasc =  String.valueOf(txtNasc.getValue());
    	try {
			Date d = sdf.parse(nasc);
			sdf.applyPattern("dd/MM/yyyy");
			nasc = sdf.format(d);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	int id = 0;
    	
		if (!txtIdFuncionario.getText().isEmpty()) 
		{
			id = Integer.parseInt(txtIdFuncionario.getText());
		}
    	
		
		if(txtNome.getLength() <= 0 )
		{
			verifica = false;
			JOptionPane.showMessageDialog(null,"O nome não pode ser vazio!");
		}
		
		if(nasc.isEmpty() )//|| (LocalDate.now().getYear() - nasc.)
		{
			verifica = false;
			JOptionPane.showMessageDialog(null,"");
		}
		
		if(verifica == true)
    	{
			UsuarioVO u = new UsuarioVO(id, txtNome.getText(), nasc, admin, txtCargo.getText(), 
    			txtRG.getText(), sexo, txtUsuario.getText(),txtSenha.getText());
			
			return u;
    	}		
		
		return null;
    	
    }
    
    @FXML
    void CadastrarUsuario(ActionEvent event) {
    	
    	if(VerificaTela("Cadastrar"))
    	{
    		UsuarioVO u =  GerarUsuario();

    		usuaDAO.AddUsuario(u);
    	
    		LimparTelaCadastro(event);
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setHeaderText(null);
    		alert.setContentText("Registro bem sucedido");

    		alert.showAndWait();
    	}
    }
    
    @FXML
    void AlterarUsuario(ActionEvent event) {

    	if(VerificaTela("Alterar"))
    	{
    		UsuarioVO u =  GerarUsuario(); 

    		usuaDAO.UpdateUsuario(u);
    		
        	LimparTelaAlterar(event);
        	
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setHeaderText(null);
    		alert.setContentText("Alteração bem sucedido");

    		alert.showAndWait();
    	}
    }
    

    @FXML
    void ApagarUsuario(ActionEvent event) {

    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle(null);
    	alert.setContentText("Deseja realmente deletar o registro?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		UsuarioVO u = GerarUsuario();

    		usuaDAO.DeleteUsuario(u.getId());
    
    		LimparTelaAlterar(event);    		
		}
    }
    
    @FXML
    void BuscarId(ActionEvent event) {
    	
		if(TryParseInt(txtIdFuncionario.getText()) == null )
		{
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo de código do produto");
			alert.setContentText("O campo só aceita números inteiros");

			alert.showAndWait();
			return;
		}
		if(TryParseInt(txtIdFuncionario.getText()) < 0)
		{
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro no campo de código do produto");
			alert.setContentText("O campo só aceita números positivos");

			alert.showAndWait();
			return;
		} 
		
    	UsuarioVO aux = usuaDAO.BuscarUsuario(Integer.parseInt(txtIdFuncionario.getText()));
    	if(aux != null)
    	{
    	txtSenha.setText(aux.getSenha());
    	
    	if( aux.getSexo().equals("M") )
    		rbFem.setSelected(true);
    	else
    		rbMasc.setSelected(true);
    		
    	txtCargo.setText(aux.getCargo());

        txtNome.setText(aux.getNome());
        
    	txtRG.setText(aux.getRG());
    	
        txtUsuario.setText(aux.getLogin());

        txtIdFuncionario.setText(String.valueOf(aux.getId()));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        txtNasc.setValue(LocalDate.parse(aux.getDatanasc(),formatter));

        txtDataAdmissao.setValue(LocalDate.parse(aux.getDataadmissao(),formatter));


    	
    	txtSenha.setDisable(false);

    	rbMasc.setDisable(false);
    	
    	rbFem.setDisable(false);

    	txtCargo.setDisable(false);

        txtNome.setDisable(false);

        txtIdFuncionario.setDisable(false);

        txtNasc.setDisable(false);

        txtUsuario.setDisable(false);

        txtDataAdmissao.setDisable(false);

    	txtRG.setDisable(false);
    	
    	txtIdFuncionario.setDisable(true);
    	
    	btnBuscar.setDisable(true);
    	
    	txtRegistrar.setDisable(false);
    	
    	txtCancelar.setDisable(false);
    	}
    	else
    	{
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle(null);
    		alert.setHeaderText(null);
    		alert.setContentText("Não existem Usuários com esse código");

    		alert.showAndWait();
    	}
    }
    
    void LimparTela() {
    	
    	txtSenha.clear();

    	rbMasc.setSelected(true);
    	
    	txtCargo.clear();

        txtNome.clear();

        txtIdFuncionario.clear();

        txtNasc.setValue(LocalDate.now());

        txtUsuario.clear();

        txtDataAdmissao.setValue(LocalDate.now());

    	txtRG.clear();
    }
    
    @FXML
    void LimparTelaCadastro(ActionEvent event) {

    	LimparTela();
    }
    
    @FXML
    void LimparTelaAlterar(ActionEvent event) {
    	
    	txtSenha.setDisable(true);

    	rbMasc.setDisable(true);
    	
    	rbFem.setDisable(true);

    	txtCargo.setDisable(true);

        txtNome.setDisable(true);

        txtIdFuncionario.setDisable(true);

        txtNasc.setDisable(true);

        txtUsuario.setDisable(true);

        txtDataAdmissao.setDisable(true);

    	txtRG.setDisable(true);
    	
    	txtIdFuncionario.setDisable(false);
    	
    	btnBuscar.setDisable(false);
    	
    	txtRegistrar.setDisable(true);
    	
    	txtCancelar.setDisable(true);
    	
    	LimparTela();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtNasc.setValue(LocalDate.now());
		txtDataAdmissao.setValue(LocalDate.now());
		
	}

}
