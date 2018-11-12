package Login;

import java.io.IOException;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import Usuario.UsuarioDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

	UsuarioDAO usuaDAO = new UsuarioDAO();
	
    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtSenha;

    @FXML
    private Button btnLogin;
    
    @FXML
    private Text lblErro;
    
    @FXML
    void AbrirPrograma(ActionEvent event) throws SQLServerException
    {
    	boolean existe = false;
    	existe = usuaDAO.VerificaLogin(txtLogin.getText(), txtSenha.getText());
    	if (existe)
    	{
    		Stage PrimaryStage  = new Stage();
    		BorderPane root; 	
    	
    		try 
    		{
    			//carrega o arquivo FXML com a definição da tela
    			root = (BorderPane)FXMLLoader.load(getClass().getResource("/Padrao/TelaPadrao.fxml"));

    			//define a cena principal
    			Scene scene = new Scene(root, 950, 600);
    			
    			//Carrega o arquivo CSS
    			//scene.getStylesheets().add(getClass().getResource("BarMain.css").toExternalForm());
    				 
    			PrimaryStage.setTitle("BarSystem Havanna Bar");
    				 
    			Stage Login = (Stage)btnLogin.getScene().getWindow();
    		
    			Login.close();
    		
    			//Carrega e mostra a tela
    			PrimaryStage.setResizable(false);
    			PrimaryStage.setScene(scene);
    			PrimaryStage.show();			
    		} 
    		catch (IOException e) 
    		{
    			e.printStackTrace();
    		}
    	}
    	else
    	{
    		lblErro.setVisible(true);
    	}
    }

}
