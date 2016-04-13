package br.com.projeto.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import br.com.projeto.conexao.Conexao;

public class Utils {

	/** @return Em Km*/
    public static double calcularDistanciaGeodesica(double latitudeP1, double longitudeP1, double latitudeP2, double longitudeP2){
        return (( Math.acos(Math.cos((90 - latitudeP1) * Math.PI / 180) * Math.cos((90 - latitudeP2) * Math.PI / 180) +
                            Math.sin((90 - latitudeP1) * Math.PI / 180) * Math.sin((90 - latitudeP2) * Math.PI / 180) *
                            Math.cos(Math.abs(((360 + longitudeP1) * Math.PI / 180) - ((360 + longitudeP2) * Math.PI / 180)))) ) * 6371.004);
    }
    
    public LinkedList<String> pesquisarIdiomas(Object obj){
    	try {
			return pesquisarCombo("IDIOMA");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Não foi possível carregar os dados da tabela IDIOMA.");
		}
    	
    	return null;
	}

    public LinkedList<String> pesquisarFluencias(Object obj) {
    	try {
			return pesquisarCombo("FLUENCIA");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Não foi possível carregar os dados da tabela IDIOMA.");
		}
    	
    	return null;
	}
    
    public LinkedList<String> pesquisarCombo(String tabela) throws SQLException{
    	
    	LinkedList<String> lista = new LinkedList<String>();
		ResultSet rst = null;
		
		try {
			
			String sql = "SELECT * FROM " + tabela;
			
			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			rst = objPS.executeQuery();
				
			while(rst.next()){
				lista.add(rst.getInt("CODIGO") + " - " + rst.getString("DESCRICAO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rst != null){
				rst.close();
			}
		}
		
		return lista;
    }
}