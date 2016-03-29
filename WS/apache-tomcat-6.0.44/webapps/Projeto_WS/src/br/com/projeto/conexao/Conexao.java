package br.com.projeto.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	private static final String URL = "jdbc:mysql://localhost/PROJETO_ANDROID";
	private static final String USER = "FATEC";
	private static final String PW = "1234";
	private static Connection con = null;
	
	public static Connection getConexao() throws SQLException{
		
		if(con != null){
			return con;
		}

		try{
			Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		con = DriverManager.getConnection(URL, USER, PW);
		return con;
	}
	
}
