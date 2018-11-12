package Login;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Login extends Application{
		
	@Override 
	public void start(Stage PrimaryStage) 
	{
		Pane root; 	
		try 
		{
			//carrega o arquivo FXML com a definição da tela
			root = (Pane)FXMLLoader.load(getClass().getResource("TelaLogin.fxml"));
			//define a cena principal
			Scene scene = new Scene(root, 600, 400);
		
			//Carrega o arquivo CSS
			//scene.getStylesheets().add(getClass().getResource("BarMain.css").toExternalForm());
			 
			PrimaryStage.setTitle("BarSystem Havanna Bar");
			 

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
	public static void main(String[] args) 
	{
		launch(args);
	}
}

