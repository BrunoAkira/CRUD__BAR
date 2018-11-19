package Grafico;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

public class Top5Controller implements Initializable {

	@FXML
	PieChart chart;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		GraficoDAO g = new GraficoDAO();

		ObservableList<PieChart.Data> pieChartData = null;
		try {
			pieChartData = g.ConsultaTop5();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (pieChartData != null) {
			chart.setData(pieChartData);
		}
	}
}