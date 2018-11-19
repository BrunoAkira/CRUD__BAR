package Grafico;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import Padrao.DbUtil;
import Produto.ProdutoVO;

public class GraficoDAO {
	
	private static Connection connection;

	public GraficoDAO() {
		connection = DbUtil.getConnection();
	}
	
	public ObservableList<PieChart.Data> ConsultaTop5() throws SQLException {
		connection = DbUtil.getConnection();
		try {
        	ObservableList<ProdutoVO> Lista = FXCollections.observableArrayList();
            
			PreparedStatement preparedStatement = connection.prepareStatement(
					"select top 5 nomeProduto,qtdVendida from Produto where qtdVendida <> 0;");
            
			        	
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
            	ProdutoVO aux = new ProdutoVO();
            	aux.setNomeProduto(rs.getString(1));
            	aux.setQtdVendida(rs.getInt(2));
            	Lista.add(aux);
            }
            
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            
            int total = 0;
            for (ProdutoVO p : Lista)
            {
            	total = total + p.getQtdVendida();       	
            }
            
            
            for (ProdutoVO p : Lista)
            {
            	System.out.println(p.getQtdVendida());
            	System.out.println(total);
            	System.out.println(Math.round((double)p.getQtdVendida()/total*100));
            	pieChartData.add(new PieChart.Data(p.getNomeProduto(),Math.round((double)p.getQtdVendida()/total*100)));    	
            }
            
            return pieChartData;

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
    		return null;
        }		
	} //ConsultaTudo
}
