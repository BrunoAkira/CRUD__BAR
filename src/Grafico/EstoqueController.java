package Grafico;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class EstoqueController implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		GraficoDAO g = new GraficoDAO();
		try {
			g.GerarEstoque();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Arquivo gerado");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}



