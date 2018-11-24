package Grafico;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import Padrao.DbUtil;
import Produto.ProdutoVO;

public class GraficoDAO {

	private static Connection connection;

	public GraficoDAO() {
		connection = DbUtil.getConnection();
	}

	public ObservableList<PieChart.Data> ConsultaTop5(int mes, int ano)
			throws SQLException {
		connection = DbUtil.getConnection();
		try {
			ObservableList<ProdutoVO> Lista = FXCollections
					.observableArrayList();

			PreparedStatement preparedStatement = connection
					.prepareStatement("set dateformat dmy "
							+ " select distinct top 5 NomeProduto,iv.qtd\r\n"
							+ "from Produto p \r\n"
							+ "inner join ItemVenda iv \r\n"
							+ "on p.ID = iv.idprod \r\n"
							+ "inner join Venda v \r\n"
							+ "on iv.idVenda = v.Id   \r\n"
							+ "where MONTH(CAST(v.Data AS smalldatetime)) = ? and YEAR(CAST(v.Data AS smalldatetime)) = ?");

			preparedStatement.setInt(1, mes);
			preparedStatement.setInt(2, ano);

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ProdutoVO aux = new ProdutoVO();
				aux.setNomeProduto(rs.getString(1));
				aux.setQtdVendida(rs.getInt(2));
				Lista.add(aux);
			}

			ObservableList<PieChart.Data> pieChartData = FXCollections
					.observableArrayList();

			int total = 0;
			for (ProdutoVO p : Lista) {
				total = total + p.getQtdVendida();
			}

			for (ProdutoVO p : Lista) {
				String unidade = " unidades vendidas";
				if (p.getQtdVendida() == 1) {
					unidade = " unidade vendida";
				}
				pieChartData.add(new PieChart.Data(p.getNomeProduto() + " - "
						+ p.getQtdVendida() + unidade, Math.round((double) p
						.getQtdVendida() / total * 100)));
			}

			return pieChartData;

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
			return null;
		}
	} // ConsultaTudo

	public XYChart.Series<String, BigDecimal>  consultaRendaMensal(
			int mes, int ano) throws SQLException {
		connection = DbUtil.getConnection();
		try {
			XYChart.Series<String, BigDecimal> series = new XYChart.Series<>();
			ObservableList<XYChart.Data<String, BigDecimal>> data = FXCollections
					.observableArrayList();
			PreparedStatement preparedStatement = connection
					.prepareStatement("select p.nomeproduto as Nome,"
							+ " v.PrecoTotal-Sum(Quantidade*CustoUnit) as Renda"
							+ " from lote l"
							+ " inner join itemvenda iv"
							+ " on iv.idprod = l.idprod"
							+ " inner join venda v"
							+ " on iv.idvenda = v.id"
							+ " inner join produto p"
							+ " on iv.idprod = p.id"
							+ " where MONTH(CAST(v.Data AS smalldatetime)) = ? and YEAR(CAST(v.Data AS smalldatetime)) = ?"
							+ " group by l.idprod,v.PrecoTotal,p.nomeproduto");

			preparedStatement.setInt(1, mes);
			preparedStatement.setInt(2, ano);

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				data.add(new Data<String, BigDecimal>(rs.getString(1), rs
						.getBigDecimal(2)));
			}
			series.getData().addAll(data);
			return series;

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
			return null;
		}
	}
	
	public void GerarEstoque() throws Exception{
		System.out.println("foi");
		printToCsv(fetchDataFromDatabase("select p.NomeProduto,l.Fornecedor,l.DataCompra,l.CustoUnit,l.Quantidade,p.qtdTotal from lote l inner join produto p  on l.idprod = p.id"));
		
	}
	
	public static void printToCsv(ResultSet resultArray) throws Exception{

	    File csvOutputFile = new File("estoque.csv");
	    FileWriter fileWriter = new FileWriter(csvOutputFile, false);
	   // int numCols = resultArray.getMetaData().getColumnCount();
	    
	    fileWriter.write("Produto" + ";");
		fileWriter.write("Fornecedor" + ";");
		fileWriter.write("Data da Compra" + ";");
		fileWriter.write("Preço" + ";");
		fileWriter.write("Quantidade Vendida" + ";");
		fileWriter.write("Quantidade no Estoque");
		fileWriter.write("\n" );
		
	    while(resultArray.next()) {
	    	fileWriter.write( resultArray.getObject(1).toString() + ";");
	    	fileWriter.write( resultArray.getObject(2).toString() + ";");
	    	fileWriter.write( resultArray.getObject(3).toString() + ";");
	    	fileWriter.write( resultArray.getObject(4).toString() + ";");
	    	fileWriter.write( resultArray.getObject(5).toString() + ";");
	    	fileWriter.write( resultArray.getObject(6).toString() + "\n");
	    }
	    	
	    	fileWriter.close();
	    Desktop.getDesktop().open(csvOutputFile);
	}


	public ResultSet fetchDataFromDatabase(String selectQuery) throws  Exception{
	    try {
	        Statement stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(selectQuery);
	        return rs;

	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    Statement stmt = connection.createStatement();
	    ResultSet rs = stmt.executeQuery(selectQuery);
	    System.out.println("foi2");
		return rs;
	}
}
