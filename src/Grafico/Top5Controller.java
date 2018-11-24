package Grafico;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;


public class Top5Controller implements Initializable {

	@FXML
	private ComboBox<String> cbAno;

	@FXML
	private ComboBox<String> cbMês;

	@FXML
	private Pane chartPane;

	@FXML
	private PieChart chart;

	@FXML
	void PreencheGrafico(ActionEvent event) {
		GraficoDAO g = new GraficoDAO();

		ObservableList<PieChart.Data> pieChartData = null;
		try {
			pieChartData = g.ConsultaTop5(cbMês.getSelectionModel().getSelectedIndex()+1,Integer.parseInt(cbAno.getSelectionModel().getSelectedItem()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (pieChartData != null) {

			chart.setData(pieChartData);

		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		cbMês.getItems().addAll("Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto",
				"Setembro","Outubro","Novembro","Dezembro");
		
		cbMês.getSelectionModel().select(0);
		cbAno.getItems().addAll("2015","2016","2017","2018","2019","2020","2021");
		cbAno.getSelectionModel().select(0);
	}

}