package br.com.projeto.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.LinkedList;

import br.com.projeto.conexao.Conexao;

public class Utils {
	
	public static DecimalFormat dfDouble = new DecimalFormat("####0.00");
	private boolean todos = false;

	/** @return Em Km*/
	public static double calcularDistanciaGeodesica(double latitudeP1, double longitudeP1, double latitudeP2, double longitudeP2){
		return Double.parseDouble(
				dfDouble.format((( Math.acos(Math.cos((90 - latitudeP1) * Math.PI / 180) * Math.cos((90 - latitudeP2) * Math.PI / 180) +
								   Math.sin((90 - latitudeP1) * Math.PI / 180) * Math.sin((90 - latitudeP2) * Math.PI / 180) *
								   Math.cos(Math.abs(((360 + longitudeP1) * Math.PI / 180) - ((360 + longitudeP2) * Math.PI / 180)))) ) * 6371.004)).replace(",", "."));
	}

	public LinkedList<String> pesquisarIdiomas(Utils utils){
		try {
			return pesquisarCombo("IDIOMA", utils.isTodos());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Não foi possível carregar os dados da tabela IDIOMA.");
		}

		return null;
	}

	public LinkedList<String> pesquisarFluencias(Utils utils) {
		try {
			return pesquisarCombo("FLUENCIA", utils.isTodos());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Não foi possível carregar os dados da tabela IDIOMA.");
		}

		return null;
	}

	public LinkedList<String> pesquisarCombo(String tabela, boolean todos) throws SQLException{

		LinkedList<String> lista = new LinkedList<String>();
		ResultSet rst = null;

		try {

			String sql = "SELECT * FROM " + tabela;
			
			if(tabela.trim().equals("IDIOMA")){
				sql += " ORDER BY DESCRICAO";
			}

			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			rst = objPS.executeQuery();

			while(rst.next()){
				lista.add(rst.getInt("CODIGO") + " - " + rst.getString("DESCRICAO"));
			}
			
			if(!todos){
				lista.remove(lista.indexOf("1 - Todos"));
			}
			else{
				lista.add(lista.remove(lista.indexOf("1 - Todos")));
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
	
	public boolean isTodos() {
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
	}

	public static void main(String[] args) {
		// Teste distância entre osasco e carapicuíba, ok
		DecimalFormat dfDouble = new DecimalFormat("####0.00");
		System.out.println(Double.parseDouble(dfDouble.format(Utils.calcularDistanciaGeodesica(-23.5317, -46.7899, -23.5192, -46.8367)).replace(",", ".")));
	}
}