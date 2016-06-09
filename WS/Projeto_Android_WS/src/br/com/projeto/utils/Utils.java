package br.com.projeto.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import br.com.projeto.conexao.Conexao;

public class Utils {
	
	private boolean todos = false;
	private byte appIdioma = 0;
	
	public static DecimalFormat dfDouble = new DecimalFormat("####0.00");

	/** @return Em Km*/
	public static double calcularDistanciaGeodesica(double latitudeP1, double longitudeP1, double latitudeP2, double longitudeP2){
		return Double.parseDouble(
				dfDouble.format((( Math.acos(Math.cos((90 - latitudeP1) * Math.PI / 180) * Math.cos((90 - latitudeP2) * Math.PI / 180) +
								   Math.sin((90 - latitudeP1) * Math.PI / 180) * Math.sin((90 - latitudeP2) * Math.PI / 180) *
								   Math.cos(Math.abs(((360 + longitudeP1) * Math.PI / 180) - ((360 + longitudeP2) * Math.PI / 180)))) ) * 6371.004)).replace(",", "."));
	}

	public LinkedList<String> pesquisarIdiomas(Utils utils){
		
		setAppIdioma(utils.getAppIdioma());
		setTodos(utils.isTodos());
		
		try {
			return pesquisarCombo("IDIOMA");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Não foi possível carregar os dados da tabela IDIOMA.");
			return null;
		}
	}

	public LinkedList<String> pesquisarFluencias(Utils utils) {
		
		setAppIdioma(utils.getAppIdioma());
		setTodos(utils.isTodos());
		
		try {
			return pesquisarCombo("FLUENCIA");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Não foi possível carregar os dados da tabela IDIOMA.");
			return null;
		}
	}

	public LinkedList<String> pesquisarCombo(String tabela) throws SQLException{

		LinkedList<String> lista = new LinkedList<String>();
		ResultSet rst = null;

		try {
			String sql = "SELECT * FROM " + tabela;
			String descricao = getDescricaoAppIdioma(getAppIdioma());
			String opcaoTodos = getAppIdioma() == Constantes.IDIOMA_PORTUGUES ? "1 - Todos" : "1 - All";
			
			if(tabela.trim().equalsIgnoreCase("IDIOMA")){
				sql += " ORDER BY " + descricao;
			}

			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			rst = objPS.executeQuery();

			while(rst.next()){
				lista.add(rst.getInt("CODIGO") + " - " + rst.getString(descricao));
			}
			
			if(!isTodos()){
				lista.remove(lista.indexOf(opcaoTodos));
			}else{
				lista.add(lista.remove(lista.indexOf(opcaoTodos)));
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
	
	public String getDescricaoAppIdioma(byte appIdioma){
        switch(appIdioma){
            case Constantes.IDIOMA_PORTUGUES:
                return "DESCRICAO_PTBR";
            case Constantes.IDIOMA_INGLES:
                return "DESCRICAO_EN";
            default:
                return "DESCRICAO_PTBR";
        }
    }
	
	public boolean isTodos(){
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
	}
	
	public byte getAppIdioma(){
		return appIdioma;
	}
	
	public void setAppIdioma(byte appIdioma){
		this.appIdioma = appIdioma;
	}

	public static void main(String[] args) {
		// Teste distância entre osasco e carapicuíba, ok
		DecimalFormat dfDouble = new DecimalFormat("####0.00");
		System.out.println(Double.parseDouble(dfDouble.format(Utils.calcularDistanciaGeodesica(-23.5317, -46.7899, -23.5192, -46.8367)).replace(",", ".")));
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
		Date data = null;
		try {
			data = fmt.parse("2016-05-27 17:58:37".replaceAll("-", "/"));
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		System.out.println(fmt.format(data));
	}
}