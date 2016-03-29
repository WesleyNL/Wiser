package br.com.projeto.principal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import br.com.projeto.conexao.Conexao;
import br.com.projeto.configuracoes.Configuracao;
import br.com.projeto.util.Constantes;

public class LoginDAO {
	
	public boolean ativar(String userId) throws SQLException{
		return executarOpcao(userId, Constantes.CODIGO_ATIVO);
	}
	
	public boolean desativar(String userId) throws SQLException{
		return executarOpcao(userId, Constantes.CODIGO_INATIVO);
	}

	@SuppressWarnings("finally")
	public boolean executarOpcao(String userId, byte opcao) throws SQLException{

		boolean retorno = false;
		ResultSet rst = null;
		
		try {
			
			String sql = "UPDATE USER_LOGIN" +
						 " SET SITUACAO = ?" +
						 " WHERE USER_ID = ?";
		
			PreparedStatement objPS = Conexao.getConexao().prepareStatement(sql);
			objPS.setString(1, String.valueOf(opcao));
			objPS.setString(2, userId);

			retorno = objPS.executeUpdate() == 1;
						
		} catch (SQLException e) {
			e.printStackTrace();
			retorno = false;
		} finally{
			if(rst != null){
				rst.close();
			}
		}
		
		return retorno;
	}
	
	@SuppressWarnings("finally")
	public boolean salvar(String userId, Date dataUltimoAcesso, String coordUltimoAcesso) throws SQLException{
		
		boolean retorno = false;
		ResultSet rst = null;
		
		try {
			
			if(existe(userId)){
				return atualizar(userId, dataUltimoAcesso, coordUltimoAcesso);
			}
			
			String sql = "INSERT INTO USER_LOGIN" +
						 " VALUES(?,?,?,?)";
		
			PreparedStatement objPS = Conexao.getConexao().prepareStatement(sql);
			objPS.setString(1, userId);
			objPS.setString(2, String.valueOf(Constantes.CODIGO_ATIVO));
			objPS.setString(3, String.valueOf(dataUltimoAcesso)); //TODO Colocar DateFormat
			objPS.setString(4, coordUltimoAcesso);

			retorno = objPS.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
			retorno = false;
		} finally{
			if(rst != null){
				rst.close();
			}
		}
		
		return retorno;
	}
	
	public boolean existe(String userId) throws SQLException{
		
		boolean retorno = false;
		ResultSet rst = null;
		
		try {
			
			String sql = "SELECT * FROM USER_LOGIN" +
						 " WHERE USER_ID = ?";
			
			PreparedStatement objPS = Conexao.getConexao().prepareStatement(sql);
			objPS.setString(1, userId);
			rst = objPS.executeQuery();
			
			if(rst.next()){
				retorno = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			retorno = false;
		} finally{
			if(rst != null){
				rst.close();
			}
		}
		
		return retorno;
	}
	
	public boolean atualizar(String userId, Date dataUltimoAcesso, String coordUltimoAcesso) throws SQLException{

		boolean retorno = false;
		ResultSet rst = null;
		
		try {
			
			String sql = "UPDATE USER_LOGIN" +
						 " SET DATA_ULTIMO_ACESSO = ?," +
						 " COORDENADA_ULTIMO_ACESSO = ?" +
						 " WHERE USER_ID = ?";
		
			PreparedStatement objPS = Conexao.getConexao().prepareStatement(sql);
			objPS.setString(1, String.valueOf(dataUltimoAcesso));
			objPS.setString(2, coordUltimoAcesso);
			objPS.setString(3, userId);

			retorno = objPS.executeUpdate() == 1;
						
		} catch (SQLException e) {
			e.printStackTrace();
			retorno = false;
		} finally{
			if(rst != null){
				rst.close();
			}
		}
		
		return retorno;
	}
}
