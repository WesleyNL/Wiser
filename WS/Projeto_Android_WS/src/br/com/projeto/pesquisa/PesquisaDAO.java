package br.com.projeto.pesquisa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import br.com.projeto.conexao.Conexao;
import br.com.projeto.configuracoes.Configuracao;
import br.com.projeto.configuracoes.ConfiguracaoDAO;
import br.com.projeto.utils.Constantes;
import br.com.projeto.utils.Utils;

public class PesquisaDAO {
	
	public Pesquisa procurar(Pesquisa procurar) throws SQLException{
		
		String coordUsuario = "";
		
		try {
			coordUsuario = getCoordUsuario(procurar.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		Pesquisa objProcurar = null;
		LinkedList<Pesquisa> listaProcurar = new LinkedList<Pesquisa>();
		ResultSet rst = null;
		
		try {
			
			String sql = "SELECT USER_ID FROM USER_LOGIN" +
						 " WHERE SITUACAO = ?, " +
						 " AND IDIOMA = ?, " +
						 " AND FLUENCIA = ?";
			
			PreparedStatement objPS = Conexao.getConexao().prepareStatement(sql);
			objPS.setByte(1, Constantes.CODIGO_ATIVO);
			objPS.setByte(2, procurar.getIdioma());
			objPS.setByte(3, procurar.getFluencia());
			rst = objPS.executeQuery();
				
			double latitudeP1 = Double.parseDouble(coordUsuario.split("|")[0]);
			double longitudeP1 = Double.parseDouble(coordUsuario.split("|")[1]);
			double latitudeP2 = 0;
			double longitudeP2 = 0; 
			String[] coordenada = new String[2];
			
			while(rst.next()){
				
				coordenada = rst.getString("COORDENADA_ULTIMO_ACESSO").split("|");
				latitudeP2 = Double.parseDouble(coordenada[0]);
				longitudeP2 = Double.parseDouble(coordenada[1]);
				
				if(Utils.calcularDistanciaGeodesica(latitudeP1, longitudeP1, latitudeP2, longitudeP2) <= procurar.getDistancia()){
					objProcurar = new Pesquisa();
					objProcurar.setUserId(rst.getString("USER_ID"));
					listaProcurar .add(objProcurar);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rst != null){
				rst.close();
			}
		}
		
		return objProcurar;
	}
	
	private String getCoordUsuario(String userId) throws SQLException{
		
		ResultSet rst = null;
		
		try {
			
			String sql = "SELECT COORDENADA_ULTIMO_ACESSO FROM USER_LOGIN" +
						 " WHERE USER_ID = ?";
			
			PreparedStatement objPS = Conexao.getConexao().prepareStatement(sql);
			objPS.setString(1, userId);
			rst = objPS.executeQuery();
			
			if(rst.next()){
				return rst.getString("COORDENADA_ULTIMO_ACESSO");
			}
			
			return "";
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		} finally{
			if(rst != null){
				rst.close();
			}
		}
	}
}
