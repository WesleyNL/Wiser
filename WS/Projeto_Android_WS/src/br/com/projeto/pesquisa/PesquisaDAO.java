package br.com.projeto.pesquisa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Vector;

import br.com.projeto.conexao.Conexao;
import br.com.projeto.utils.Constantes;
import br.com.projeto.utils.Utils;

public class PesquisaDAO {
	
	public Vector<Pesquisa> procurar(Pesquisa procurar) throws SQLException{
		
		String coordUsuario = "";
		
		try {
			coordUsuario = getCoordUsuario(procurar.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		Vector<Pesquisa> listaProcurar = new Vector<Pesquisa>();
		ResultSet rst = null;
		
		try {
			String sql = "SELECT l.USER_ID, l.COORDENADA_ULTIMO_ACESSO," + 
						 " C.IDIOMA, C.FLUENCIA, C.STATUS" +
						 " FROM USER_LOGIN l" +
						 " INNER JOIN USER_CONFIGURACAO c" +
						 " ON l.USER_ID = c.USER_ID" +
						 " WHERE SITUACAO = ? " +
						 " AND IDIOMA = ? " +
						 " AND FLUENCIA = ? " +
						 " AND l.USER_ID <> ?";
			
			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			objPS.setByte(1, Constantes.CODIGO_ATIVO);
			objPS.setString(2, procurar.getIdioma() == Constantes.VALOR_TODOS ? "IDIOMA" : String.valueOf(procurar.getIdioma()));
			objPS.setString(3, procurar.getFluencia() == Constantes.VALOR_TODOS ? "FLUENCIA" : String.valueOf(procurar.getFluencia()));
			objPS.setString(4, procurar.getUserId());
			rst = objPS.executeQuery();
				
			double latitudeP1 = Double.parseDouble(coordUsuario.split("[|]")[0]);
			double longitudeP1 = Double.parseDouble(coordUsuario.split("[|]")[1]);
			double latitudeP2 = 0;
			double longitudeP2 = 0; 
			String[] coordenada = new String[2];
			
			Pesquisa pesquisa = null;
			double distancia = 0;
			
			while(rst.next()){
				coordenada = rst.getString("COORDENADA_ULTIMO_ACESSO").split("[|]");
				latitudeP2 = Double.parseDouble(coordenada[0]);
				longitudeP2 = Double.parseDouble(coordenada[1]);
				
				distancia = Utils.calcularDistanciaGeodesica(latitudeP1, longitudeP1, latitudeP2, longitudeP2);
				
				if(distancia <= procurar.getDistancia()){
					pesquisa = new Pesquisa();
					pesquisa.setUserId(rst.getString("USER_ID"));
					pesquisa.setIdioma(rst.getByte("IDIOMA"));
					pesquisa.setFluencia(rst.getByte("FLUENCIA"));
					pesquisa.setStatus(rst.getString("STATUS"));
					pesquisa.setDistancia(distancia);
					listaProcurar.add(pesquisa);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rst != null){
				rst.close();
			}
		}
		
		return listaProcurar;
	}
	
	private String getCoordUsuario(String userId) throws SQLException{
		
		ResultSet rst = null;
		
		try {
			
			String sql = "SELECT COORDENADA_ULTIMO_ACESSO FROM USER_LOGIN" +
						 " WHERE USER_ID = ?";
			
			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
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