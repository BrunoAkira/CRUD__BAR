package Venda;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Padrao.DbUtil;

public class VendaDAO {
	
	private static Connection connection;

	public VendaDAO() {
		connection = DbUtil.getConnection();
	}
	
public void AddVenda(VendaVO v) {
		
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement(
					"insert into Venda(Comanda, Data, PrecoTotal)  VALUES (?,?,?);");
			
			preparedStatement.setInt(1, v.getComanda());
			preparedStatement.setString(2, v.getData());
			preparedStatement.setBigDecimal(3, v.getPrecoTotal());			
			
			preparedStatement.execute();
			
			
			preparedStatement = connection.prepareStatement(" SELECT IDENT_CURRENT('venda')");
			int id = 0;
			ResultSet rs = preparedStatement.executeQuery();
			
        	if (rs.next()) {
        		id = rs.getInt(1);
        	}
        	
			ItemVendaDAO ivDAO = new ItemVendaDAO();
			for(ItemVendaVO iv : v.lista)
			{
				iv.setIdvenda(id);
		    	System.out.println(iv.getIdvenda());
				ivDAO.AddItemVenda(iv);
			}
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	} //AddVenda

public void UpdateVenda(VendaVO v) {
	
	try {
		
		PreparedStatement preparedStatement = connection.prepareStatement(
				" Update Venda SET"
				+ "	Comanda = ?, Data = ?, PrecoTotal = ? "
				+ " Where Id = ? ");

		preparedStatement.setInt(1, v.getComanda());
		preparedStatement.setString(2, v.getData());
		preparedStatement.setBigDecimal(3,v.getPrecoTotal());
		preparedStatement.setInt(4, v.getId());
				
		preparedStatement.execute();
		
		
		ItemVendaDAO ivDAO = new ItemVendaDAO();
		for(ItemVendaVO iv : v.lista)
		{
			iv.setIdvenda(v.getId());
			ivDAO.UpdateItemVenda(v.lista);
		}
		connection.commit();
	} catch (Exception e) {
		try {
			connection.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e.printStackTrace();
	}
	} //UpdateVenda

public void DeleteVenda(VendaVO v) {
	
	try {
		ItemVendaDAO ivDAO = new ItemVendaDAO();
		for(ItemVendaVO iv : v.lista)
		{	
			iv.setIdvenda(v.getId());
			ivDAO.DeleteItemVenda(iv);
		}
	
		PreparedStatement preparedStatement = connection.prepareStatement(
			" Delete from Venda where Id = ?");

		preparedStatement.setInt(1, v.getId());

		preparedStatement.execute();
		
		
		connection.commit();
	} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	} //DeleteVenda

public VendaVO BuscarVenda(VendaVO v) {

	try {     
		PreparedStatement preparedStatement = connection.prepareStatement(
		"SELECT * FROM Venda WHERE Id = ?;");
            
			preparedStatement.setInt(1, v.getId());
			
			// Execute query and store result in a resultset
			ResultSet rs = preparedStatement.executeQuery();
        	if (rs.next()) {
        		int id = rs.getInt(1); 
        		int comanda = rs.getInt(2); 
        		String Data = rs.getString(3);
        		BigDecimal PrecoTotal = rs.getBigDecimal(4);

        		VendaVO aux = new VendaVO(id, comanda, Data,PrecoTotal); 
        		ItemVendaVO iv = new ItemVendaVO();
        		iv.setIdvenda(aux.getId());
        		ItemVendaDAO ivDAO = new ItemVendaDAO();
        		aux.lista = ivDAO.BuscaItemVenda(iv);
        			
        		return aux;
        	}
        	return null;
        
	} catch (SQLException ex) {
		System.err.println("Error "+ex);
    	return null;
	}
}//BuscarVenda

public ObservableList<VendaVO> ConsultarTudo(String filtro, String tipo) {
    try {
		ObservableList<VendaVO> Lista = FXCollections.observableArrayList();
		Boolean num = false;
		String where =  " like Upper(?);";
		if (tipo == "PrecoTotal" || tipo == "Comanda")
		{
			where = " = ?;"; 
			num = true;
		}
		
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from venda"
				+ " where "+ tipo  + where);
		
		if(num)
		{
			if(filtro.trim().isEmpty())
				filtro = "0";
			preparedStatement.setInt(1,Integer.parseInt(filtro));
		}
		else
		{
			//preparedStatement.setString(1, tipo);
			preparedStatement.setString(1, "%"+filtro+"%");
		}
    	ResultSet rs = preparedStatement.executeQuery();
    	while (rs.next()) {
    		VendaVO aux = new VendaVO(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getBigDecimal(4));
    		
    		ItemVendaDAO ivDAO = new ItemVendaDAO();
    		ItemVendaVO iv = new ItemVendaVO();
    		iv.setIdvenda(aux.getId());
    		aux.lista = ivDAO.BuscaItemVenda(iv);
    		Lista.add(aux);
    	}
    	
    	return Lista;

	} catch (SQLException ex) {
    	System.err.println("Error"+ex);
		return null;
	}		
} //ConsultaTudo

public int ProximoID() throws SQLException {
	int ID = 0;
	try {
		Statement st =  connection.createStatement();
		ResultSet rs = st.executeQuery("Select ISnull(Max(id)+1,1) from Venda");
		
		while (rs.next()) {
		      int id = rs.getInt(1);
		      return id;
		    }
		

    } catch (SQLException ex) {
        System.err.println("Error"+ex);
    }
	return ID;
}

}
