package Venda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Padrao.DbUtil;

public class ItemVendaDAO {
	private static Connection connection;

	public ItemVendaDAO() {
		connection = DbUtil.getConnection();
	}
	
public void AddItemVenda(ItemVendaVO iv) {
		
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement(
					"insert into ItemVenda(Idvenda, Idprod, Qtd)  VALUES (?,?,?);");
			
			preparedStatement.setInt(1, iv.getIdvenda());
			preparedStatement.setInt(2, iv.getIdprod());
			preparedStatement.setInt(3, iv.getQtd());

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

	} //AddItemVenda

public void UpdateItemVenda(ObservableList<ItemVendaVO> lista) {
	
	try {
		
		PreparedStatement preparedStatement = connection.prepareStatement(
				" Delete from ItemVenda Where IdVenda = ?;");

		preparedStatement.setInt(1, lista.get(0).getIdvenda());
		
		preparedStatement.execute();
		
		connection.commit();
		
		for(ItemVendaVO iv : lista)
		{	
			 AddItemVenda(iv);
		}

	} catch (Exception e) {
		try {
			connection.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e.printStackTrace();
	}
	} //UpdateItemVenda

public void DeleteItemVenda(ItemVendaVO iv) {
	
	try {
	
		PreparedStatement preparedStatement = connection.prepareStatement(
			" Delete from ItemVenda where IdVenda = ?");

		preparedStatement.setInt(1, iv.getIdvenda());

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
	} //DeleteItemVenda

public ObservableList<ItemVendaVO> BuscaItemVenda(ItemVendaVO iv) throws SQLException {
    try {
    	ObservableList<ItemVendaVO> Lista = FXCollections.observableArrayList();
        
		PreparedStatement preparedStatement = connection.prepareStatement(
				"select iv.*, p.NomeProduto, p.PrecoUnit from itemvenda iv inner join Produto p on iv.idprod = p.id  WHERE Idvenda = ?;");
		preparedStatement.setInt(1, iv.getIdvenda());
    	
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
        	ItemVendaVO aux = new ItemVendaVO(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4));

        	aux.setId(rs.getInt(1));
        	aux.setNomeProduto(rs.getString(6));
        	aux.setPrecoUnit(rs.getBigDecimal(7));
        	Lista.add(aux);
        }
        
        return Lista;

    } catch (SQLException ex) {
        System.err.println("Error"+ex);
		return null;
    }		
} //BuscaItemVenda

public ItemVendaVO BuscaItemVendaPorID(ItemVendaVO iv) throws SQLException {
    try {        
		PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from itemvenda WHERE ID = ?;");
		preparedStatement.setInt(1, iv.getId());
    	
        ResultSet rs = preparedStatement.executeQuery();
        ItemVendaVO aux = new ItemVendaVO();
        if (rs.next()) {
        	aux.setId(rs.getInt(1));
        	aux.setIdprod(rs.getInt(2));
        	aux.setIdvenda(rs.getInt(3));
        	aux.setQtd(rs.getInt(4));
        }
        
        return aux;

    } catch (SQLException ex) {
        System.err.println("Error"+ex);
		return null;
    }		
} //BuscaItemVenda

}

