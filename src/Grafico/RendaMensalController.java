package Grafico;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Produto.ProdutoDAO;
import Produto.ProdutoVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

public class RendaMensalController implements Initializable {


    @FXML
    private CategoryAxis yAxis;

    @FXML
    private NumberAxis xAxis;
	
	@FXML
	private ComboBox<String> cbAno;

	@FXML
	private ComboBox<String> cbMes;

	@FXML
	private BarChart<String, BigDecimal>chart;


	@FXML
	void GeraGrafico(ActionEvent event) throws SQLException {
		GraficoDAO g = new GraficoDAO();

		XYChart.Series<String, BigDecimal> series = new XYChart.Series<>();
		series = g.consultaRendaMensal(cbMes.getSelectionModel().getSelectedIndex()+1,Integer.parseInt(cbAno.getSelectionModel().getSelectedItem()));
		chart.getData().clear();
		if (series != null) {			
			//Setting the data to bar chart        
			chart.getData().add(series);

		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbMes.getItems().addAll("Janeiro", "Fevereiro", "Março", "Abril",
				"Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro",
				"Novembro", "Dezembro");
		cbMes.getSelectionModel().select(0);

		cbAno.getItems().addAll("2015", "2016", "2017", "2018", "2019", "2020",
				"2021");
		cbAno.getSelectionModel().select(0);

		// Defining the x axis
		CategoryAxis xAxis = new CategoryAxis();
		ProdutoDAO p = new ProdutoDAO();
		ObservableList<ProdutoVO> produtos = null;
		try {
			produtos = p.ConsultaTudo("", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObservableList<String> produtonome = FXCollections
				.observableArrayList();
		if (produtos != null) {
			for (ProdutoVO prod : produtos) {
				produtonome.add(prod.getNomeProduto());
			}
			xAxis.setCategories(produtonome);
			xAxis.setLabel("Produto");

			// Defining the y axis
			NumberAxis yAxis = new NumberAxis();
			yAxis.setLabel("Renda($)");
		}
	}

}
