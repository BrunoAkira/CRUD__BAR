package Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Padrao.DbUtil;


public class UsuarioDAO {

	private static Connection connection;

	public UsuarioDAO() {
		connection = DbUtil.getConnection();
	}

	public boolean VerificaLogin(String usuario, String senha) {
		
        try {     
   
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT TOP 1 ID FROM usuario WHERE login = ? AND senha = ?;");
            
			preparedStatement.setString(1, usuario);
			preparedStatement.setString(2, senha);
			
			 // Execute query and store result in a resultset
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next())
            {
            	return false;
            }
        	return true;
            

        } catch (SQLException ex) {
            System.err.println("Error "+ex);
            return true;
        }
	}
	
	public void AddUsuario(UsuarioVO u) {
		
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement(
					"insert into Usuario(Nome,DataNascimento,DataAdmissao,Cargo,RG,Sexo,Login,Senha) VALUES (?,?,?,?,?,?,?,?)");
			
			
			preparedStatement.setString(1, u.getNome());
			preparedStatement.setString(2, u.getDatanasc());
			preparedStatement.setString(3, u.getDataadmissao());
			preparedStatement.setString(4, u.getCargo());
			preparedStatement.setString(5, u.getRG());
			preparedStatement.setString(6, u.getSexo());
			preparedStatement.setString(7, u.getLogin());
			preparedStatement.setString(8, u.getSenha());
			
			
			preparedStatement.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	} //addUsuario
	
	public void UpdateUsuario(UsuarioVO u) {
		
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement(
					" Update Usuario SET"
					+ "	Nome = ?, "
					+ " DataNascimento = ?, "
					+ " DataAdmissao = ?, "
					+ " Cargo = ?, "
					+ " RG = ?, "
					+ " Sexo = ?, "
					+ " Login = ?, "
					+ " Senha = ? "
					+ " Where ID = ? ");
					
			preparedStatement.setString(1, u.getNome());
			preparedStatement.setString(2, u.getDatanasc());
			preparedStatement.setString(3, u.getDataadmissao());
			preparedStatement.setString(4, u.getCargo());
			preparedStatement.setString(5, u.getRG());
			preparedStatement.setString(6, u.getSexo());
			preparedStatement.setString(7, u.getLogin());
			preparedStatement.setString(8, u.getSenha());
			preparedStatement.setInt(9, u.getId());
			
			
			preparedStatement.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	} //UpdateUsuario
	
	public void DeleteUsuario(int id) {
		
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement(
					" Delete from Usuario where ID = ?");

			preparedStatement.setInt(1, id);
			
			
			preparedStatement.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	} //DeleteUsuario
	
	public UsuarioVO BuscarUsuario(int id) {

		try {     
				   
			PreparedStatement preparedStatement = connection.prepareStatement(
				"SELECT * FROM usuario WHERE Id = ?;");
		            
				preparedStatement.setInt(1, id);
					
				// Execute query and store result in a resultset
		        ResultSet rs = preparedStatement.executeQuery();
		        if (rs.next()) {
		        	int idf = rs.getInt(1); 
		        	String nome = rs.getString(2);
		        	String DataNascimento = rs.getString(3);
		        	String DataAdmissao = rs.getString(4);
		        	String Cargo = rs.getString(5);
		        	String RG = rs.getString(6);
		        	String Sexo = rs.getString(7);
		        	String Login = rs.getString(8);
		        	String Senha = rs.getString(9);

		        	UsuarioVO u = new UsuarioVO(idf, nome, DataNascimento, DataAdmissao, Cargo, RG, Sexo, Login, Senha); 
		        	return u;
		        }
		        return null;

		} catch (SQLException ex) {
			System.err.println("Error "+ex);
		    return null;
		}
		
	} //BuscarUsuario
	
	public ObservableList<UsuarioVO> ConsultaTudo(String filtro, String tipo) throws SQLException {
        try {
        	ObservableList<UsuarioVO> Lista = FXCollections.observableArrayList();
            
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT * FROM usuario where "+ tipo  + " like Upper(?);");
            
			//preparedStatement.setString(1, tipo);
			preparedStatement.setString(1, "%"+filtro+"%");
        	
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
            	UsuarioVO aux = new UsuarioVO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),
            			rs.getString(7),rs.getString(8),rs.getString(9));
            	Lista.add(aux);
            }
            
            return Lista;

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
    		return null;
        }		
	} //ConsultaTudo

}
