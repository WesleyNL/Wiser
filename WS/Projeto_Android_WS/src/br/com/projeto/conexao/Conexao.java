package br.com.projeto.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.projeto.Sistema;

public class Conexao {

	private String URL = null;
	private final String USUARIO = Sistema.getParametro("CONEXAO.USUARIO");
	private final String SENHA = Sistema.getParametro("CONEXAO.SENHA");
	
	private Connection con = null;
	private boolean conectado = false;
	private static Conexao instancia = null;
	
	public Conexao(){
		try {
			abrirConexao();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Não foi possível conectar ao Banco de Dados.");
		}
	}
	
	private void abrirConexao() throws SQLException{

		try{
			switch(Integer.parseInt(Sistema.getParametro("CONEXAO.TIPO"))){
			case 1:
				URL = "jdbc:mysql://" + Sistema.getParametro("CONEXAO.SERVIDOR") + "/" + Sistema.getParametro("CONEXAO.BANCO");
				Class.forName("com.mysql.jdbc.Driver");
				break;
			case 2: 
				URL = "jdbc:sqlserver://" + Sistema.getParametro("CONEXAO.SERVIDOR") + ";databaseName=" + Sistema.getParametro("CONEXAO.BANCO");
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				break;
			default:
				System.out.println("Banco ainda não configurado.");
			}
		} catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		con = DriverManager.getConnection(URL, USUARIO, SENHA);
		
		if(con != null){
			conectado = true;
		}
	}
	
	public Connection getConexao(){
		return con;
	}
	
	public static synchronized Conexao getInstance() {
		
		if(instancia == null){
			instancia = new Conexao();
		}
		
		return instancia;
	}

	public boolean isConectado() {
		return conectado;
	}
	
	public void fecharConexao(){
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Não foi possível fechar a conexão.");
			}
		}
	}
}