package br.com.projeto.configuracoes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import br.com.projeto.conexao.Conexao;

public class ConfiguracaoDAO {

	public Configuracao consultar(String userId) throws SQLException{

		Configuracao objConfiguracao = null;
		ResultSet rst = null;
		
		try {
			
			String sql = "SELECT * FROM USER_CONFIGURACAO" +
						 " WHERE USER_ID = ?";
			
			PreparedStatement objPS = Conexao.getConexao().prepareStatement(sql);
			objPS.setString(1, userId);
			rst = objPS.executeQuery();
			
			if(rst.next()){
				objConfiguracao = new Configuracao();
				objConfiguracao.setUserId(rst.getString("USER_ID"));
				objConfiguracao.setIdioma(rst.getString("IDIOMA"));
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
	
	/** A inserção é realizada no primeiro acesso */
	@SuppressWarnings("finally")
	public boolean salvar(String userId, String idioma, byte fluencia, String status) throws SQLException{

		boolean retorno = false;
		ResultSet rst = null;
		
		try {
						
			String sql = "UPDATE USER_CONFIGURACAO" +
						 " SET IDIOMA = ?," +
						 " FLUENCIA = ?," +
						 " STATUS = ?" +
						 " WHERE USER_ID = ?";
		
			PreparedStatement objPS = Conexao.getConexao().prepareStatement(sql);
			objPS.setString(1, String.valueOf(idioma));
			objPS.setString(2, String.valueOf(fluencia));
			objPS.setString(3, String.valueOf(status));
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
