package Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Padrao.DbUtil;


public class ProdutoDAO {

	private static Connection connection;

	public ProdutoDAO() {
		connection = DbUtil.getConnection();
	}
	
	public void AddProduto(ProdutoVO p) {
		
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement(
					"insert into Produto(NomeProduto,Descricao,TipoProduto,QtdTotal,QtdVendida,PrecoUnit)  VALUES (?,?,?,?,?,?)");
			
			
			preparedStatement.setString(1, p.getNomeProduto());
			preparedStatement.setString(2, p.getDescricao());
			preparedStatement.setString(3, p.getTipoProduto());
			preparedStatement.setInt(4, p.getQtdTotal());
			preparedStatement.setInt(5, p.getQtdVendida());
			preparedStatement.setFloat(6, p.getPrecoUnit());
			
			
			preparedStatement.execute();
			
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	} //AddProduto
	
public void UpdateProduto(ProdutoVO p) {
		
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement(
					" Update Produto SET"
					+ "	NomeProduto = ?, "
					+ " Descricao = ?, "
					+ " TipoProduto = ?,"
					+ " QtdTotal = ?, "
					+ " QtdVendida = ?, "
					+ " PrecoUnit = ? "
					+ " Where ID = ? ");
	
			preparedStatement.setString(1, p.getNomeProduto());
			preparedStatement.setString(2, p.getDescricao());
			preparedStatement.setString(3, p.getTipoProduto());
			preparedStatement.setInt(4, p.getQtdTotal());
			preparedStatement.setInt(5, p.getQtdVendida());
			preparedStatement.setFloat(6, p.getPrecoUnit());
			preparedStatement.setInt(7, p.getId());
			
			
			preparedStatement.execute();
			
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	} //UpdateProduto


public void DeleteProduto(ProdutoVO p) {
	
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"Delete from Lote where idProd = ?;");
			
			preparedStatement.setInt(1, p.getId());
		
			
			preparedStatement.execute();
			
			
		    preparedStatement = connection.prepareStatement(
				" Delete from Produto where ID = ?");

			preparedStatement.setInt(1, p.getId());

			preparedStatement.execute();
			
			connection.commit();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}//DeleteProduto
public ProdutoVO BuscarProduto(ProdutoVO p) {

		try {     
			PreparedStatement preparedStatement = connection.prepareStatement(
			"SELECT * FROM Produto WHERE Id = ?;");
	            
				preparedStatement.setInt(1, p.getId());
				
				// Execute query and store result in a resultset
				ResultSet rs = preparedStatement.executeQuery();
	        	if (rs.next()) {
	        		int id = rs.getInt(1); 
	        		String NomeProduto = rs.getString(2);
	        		String Descricao = rs.getString(3);
	        		String TipoProduto = rs.getString(4);
	        		int QtdTotal = rs.getInt(5);
	        		int QtdVendida = rs.getInt(6);
	        		float PrecoUnit = rs.getFloat(7);

	        		ProdutoVO aux = new ProdutoVO(id, NomeProduto, Descricao,TipoProduto, QtdTotal, QtdVendida, PrecoUnit); 
	        		return aux;
	        	}
	        	return null;
	        
		} catch (SQLException ex) {
			System.err.println("Error "+ex);
	    	return null;
		}
	}//BuscarProduto

public ProdutoVO BuscarID(ProdutoVO p) {

	try {     
		PreparedStatement preparedStatement = connection.prepareStatement(
		"SELECT * FROM Produto WHERE UPPER(nomeproduto) like UPPER(?);");
            
			preparedStatement.setString(1, p.getNomeProduto());
			
			// Execute query and store result in a resultset
			ResultSet rs = preparedStatement.executeQuery();
        	if (rs.next()) {
        		int id = rs.getInt(1); 
        		String NomeProduto = rs.getString(2);
        		String Descricao = rs.getString(3);
        		String TipoProduto = rs.getString(4);
        		int QtdTotal = rs.getInt(5);
        		int QtdVendida = rs.getInt(6);
        		float PrecoUnit = rs.getFloat(7);
        		
        		ProdutoVO aux = new ProdutoVO(id, NomeProduto, Descricao,TipoProduto, QtdTotal, QtdVendida, PrecoUnit); 
        		
        		return aux;
        	}
        	return null;
        
	} catch (SQLException ex) {
		System.err.println("Error "+ex);
    	return null;
	}
}//BuscarProduto

public ObservableList<ProdutoVO> ConsultaTudo(String tipo, String nome) throws SQLException {
    try {
    		ObservableList<ProdutoVO> Lista = FXCollections.observableArrayList();
    		
			PreparedStatement preparedStatement = connection.prepareStatement( "SELECT * FROM Produto "
											+ "where nomeproduto like Upper(?) AND tipoproduto like Upper(?);" );
        
			preparedStatement.setString(1, "%"+nome+"%");
			preparedStatement.setString(2, "%"+tipo+"%");
			
        	ResultSet rs = preparedStatement.executeQuery();
        	while (rs.next()) {
        		ProdutoVO aux = new ProdutoVO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getFloat(7));
        		Lista.add(aux);
        	}
        	
        	return Lista;

    	} catch (SQLException ex) {
        	System.err.println("Error"+ex);
			return null;
    	}		
	} //ConsultaTudo

public ObservableList<String> ConsultaTipoProduto() throws SQLException {
    try {
    		ObservableList<String> Lista = FXCollections.observableArrayList();
    		
			PreparedStatement preparedStatement = connection.prepareStatement( "SELECT distinct UPPER(tipoproduto) FROM Produto;" );
        
			
        	ResultSet rs = preparedStatement.executeQuery();
        	while (rs.next()) {
        		
        		Lista.add(rs.getString(1));
        	}
        	
        	return Lista;

    	} catch (SQLException ex) {
        	System.err.println("Error"+ex);
			return null;
    	}		
	} //ConsultaTipoProduto

public ObservableList<String> ConsultaNomeProduto() throws SQLException {
    try {
    		ObservableList<String> Lista = FXCollections.observableArrayList();
    		
			PreparedStatement preparedStatement = connection.prepareStatement( "SELECT distinct UPPER(nomeProduto) FROM Produto;" );
        
			
        	ResultSet rs = preparedStatement.executeQuery();
        	while (rs.next()) {
        		
        		Lista.add(rs.getString(1));
        	}
        	
        	return Lista;

    	} catch (SQLException ex) {
        	System.err.println("Error"+ex);
			return null;
    	}		
	} //ConsultaTipoProduto
}
