package br.com.projeto.configuracoes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.projeto.conexao.Conexao;
import br.com.projeto.principal.LoginDAO;

public class ConfiguracaoDAO {
	
	public boolean desativar(Configuracao configuracao){
		
		LoginDAO objLogin = new LoginDAO();
		
		try {
			return objLogin.desativar(configuracao.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Configuracao consultar(Configuracao configuracao) throws SQLException{

		Configuracao objConfiguracao = null;
		ResultSet rst = null;
		
		try {

			if(!existe(configuracao.getUserId())){
				salvar(configuracao);
			}
			
			String sql = "SELECT * FROM USER_CONFIGURACAO" +
						 " WHERE USER_ID = ?";
			
			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			objPS.setString(1, configuracao.getUserId());
			rst = objPS.executeQuery();
			
			if(rst.next()){
				objConfiguracao = new Configuracao();
				objConfiguracao.setUserId(rst.getString("USER_ID"));
				objConfiguracao.setIdioma(rst.getByte("IDIOMA"));
				objConfiguracao.setFluencia(rst.getByte("FLUENCIA"));
				objConfiguracao.setStatus(rst.getString("STATUS"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			objConfiguracao = null;
		} finally{
			if(rst != null){
				rst.close();
			}
		}
		
		return objConfiguracao;
	}
	
	public boolean salvar(Configuracao configuracao) throws SQLException{

		boolean retorno = false;
		ResultSet rst = null;
		
		try {
			
			if(existe(configuracao.getUserId())){
				return atualizar(configuracao.getIdioma(), configuracao.getFluencia(), configuracao.getStatus(), configuracao.getUserId());
			}
			
			String sql = "INSERT INTO USER_CONFIGURACAO" +
						 " (USER_ID, IDIOMA, FLUENCIA, STATUS)" +
						 " VALUES(?,?,?,?)";
	
			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			objPS.setString(1, configuracao.getUserId());
			objPS.setByte(2, (byte)1);
			objPS.setByte(3, (byte)1);
			objPS.setString(4, "...");
	
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

	private boolean existe(String userId) throws SQLException{
		
		boolean retorno = false;
		ResultSet rst = null;
		
		try {
			
			String sql = "SELECT * FROM USER_CONFIGURACAO" +
						 " WHERE USER_ID = ?";
			
			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
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
	
	private boolean atualizar(byte idioma, byte fluencia, String status, String userId) throws SQLException {
		
		boolean retorno = false;
		ResultSet rst = null;
		
		try{
			String sql = "UPDATE USER_CONFIGURACAO" +
					 " SET IDIOMA = ?," +
					 " FLUENCIA = ?," +
					 " STATUS = ?" +
					 " WHERE USER_ID = ?";

			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			objPS.setByte(1, idioma);
			objPS.setByte(2, fluencia);
			objPS.setString(3, status);
			objPS.setString(4, userId);
		
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