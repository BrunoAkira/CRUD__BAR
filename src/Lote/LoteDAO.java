package Lote;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Padrao.DbUtil;

public class LoteDAO {

	private static Connection connection;

	public LoteDAO() {
		connection = DbUtil.getConnection();
	}

	public void AddLote(LoteVO l) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"insert into Lote(IdProd,Fornecedor,DataCompra,Quantidade,CustoUnit)  VALUES (?,?,?,?,?)");

			preparedStatement.setInt(1, l.getIdProd());
			preparedStatement.setString(2, l.getFornecedor());
			preparedStatement.setString(3, l.getDataCompra());
			preparedStatement.setInt(4, l.getQuantidade());
			preparedStatement.setBigDecimal(5, l.getCustoUnit());

			preparedStatement.execute();

			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	} // AddLote

	public void UpdateLote(LoteVO l) {
		try {

			PreparedStatement preparedStatement = connection
					.prepareStatement(" Update Lote SET" + "	IdProd = ?, " + " Fornecedor = ?, " + " DataCompra = ?,"
							+ " Quantidade = ?, " + " CustoUnit = ? " + " Where IDLote = ? ");

			preparedStatement.setInt(1, l.getIdProd());
			preparedStatement.setString(2, l.getFornecedor());
			preparedStatement.setString(3, l.getDataCompra());
			preparedStatement.setInt(4, l.getQuantidade());
			preparedStatement.setBigDecimal(5, l.getCustoUnit());
			preparedStatement.setInt(6, l.getIdLote());

			preparedStatement.execute();
			
			connection.commit();

		}  catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	} // UpdateLote

	public void DeleteLote(LoteVO l) {
		try {

			PreparedStatement preparedStatement = connection.prepareStatement(" Delete from Lote where IdLote = ?");

			preparedStatement.setInt(1, l.getIdLote());

			preparedStatement.execute();

			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}// DeleteLote

	public LoteVO BuscarLote(LoteVO l) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Lote WHERE IdLote = ?;");

			preparedStatement.setInt(1, l.getIdLote());

			// Execute query and store result in a resultset
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				int idLote = rs.getInt(1);
				int idProd = rs.getInt(2);
				String fornecedor = rs.getString(3);
				String dataCompra = rs.getString(4);
				int quantidade = rs.getInt(5);
				BigDecimal custoUnit = rs.getBigDecimal(6);

				LoteVO aux = new LoteVO(idLote, idProd, fornecedor, dataCompra, quantidade, custoUnit);

				return aux;
			}
			return null;

		} catch (SQLException ex) {
			System.err.println("Error " + ex);
			return null;
		}
	}// BuscarLote

	public ObservableList<LoteVO> ConsultaTudo(String filtro, String tipo) throws SQLException {
		try {
			ObservableList<LoteVO> Lista = FXCollections.observableArrayList();
			Boolean num = false;
			String where = " like Upper(?);";
			if (tipo == "custoUnit" || tipo == "Quantidade") {
				where = " = ?";
				filtro = filtro.replace(',','.');
				num = true;
			}

			PreparedStatement preparedStatement = connection.prepareStatement("SELECT l.*,p.NomeProduto FROM Lote l "
					+ "inner join produto p on l.IdProd = p.Id " + "where " + tipo + where);

			if (num) {
				if (filtro.trim().isEmpty())
					filtro = "0";
				preparedStatement.setBigDecimal(1, BigDecimal.valueOf(Double.parseDouble(filtro)));
			} else {
				// preparedStatement.setString(1, tipo);
				preparedStatement.setString(1, "%" + filtro + "%");
			}
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				LoteVO aux = new LoteVO(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getBigDecimal(6));
				aux.setNomeProd(rs.getString(7));
				Lista.add(aux);
			}

			return Lista;

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
			return null;
		}
	} // ConsultaTudo

}
