package br.com.projeto.idioma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import br.com.projeto.Sistema;
import br.com.projeto.conexao.Conexao;

public class ManutencaoDAO extends Manutencao{

	private Connection con;

	public ManutencaoDAO(){
		Sistema.PATH_CONFIG =  ClassLoader.getSystemResource("executavel/projeto.config.ini").getPath();
		Sistema objParametros = new Sistema();
		objParametros.carregar();
		this.con = Conexao.getInstance().getConexao();
	}

	public boolean salvar(){
		try{
			switch (getTipoOperacao()){
			case OPERACAO_INCLUSAO:
				return incluir();
			case OPERACAO_EDICAO:
				return editar();
			default:
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}

	private boolean incluir() throws SQLException{

		boolean incluido = false;

		try{
			String sql = "";
			PreparedStatement objPS = null;
			String tabela = getTipoUtilizacao() == USAR_IDIOMA ? "IDIOMA" : "FLUENCIA";
			
			sql = "INSERT INTO " + tabela +
			   	  " (DESCRICAO_PTBR, DESCRICAO_EN)" +
				  " VALUES(?,?)";

			objPS = con.prepareStatement(sql);
			objPS.setString(1, getDescricao());
			objPS.setString(2, getDescricaoTraducao());
			objPS.execute();
			
			incluido = true;
		} catch (SQLException e) {
			e.printStackTrace();
			incluido = false;
		}

		return incluido;
	}

	private boolean editar(){

		boolean editado = false;

		try{
			String sql = "";
			PreparedStatement objPS = null;
			String tabela = getTipoUtilizacao() == USAR_IDIOMA ? "IDIOMA" : "FLUENCIA";

			 sql = "UPDATE " + tabela +
				   " SET DESCRICAO_PTBR = ?, " +
				   " DESCRICAO_EN = ?" +
				   " WHERE CODIGO = ?";
			
			objPS = con.prepareStatement(sql);
			objPS.setString(1, getDescricao());
			objPS.setString(2, getDescricaoTraducao());
			objPS.setInt(3, getCodigo());

			editado = objPS.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			editado = false;
		}
		
		return editado;
	}

	public boolean excluir(){

		boolean excluido = false;

		try{
			String sql = "";
			PreparedStatement objPS = null;
			String tabela = getTipoUtilizacao() == USAR_IDIOMA ? "IDIOMA" : "FLUENCIA";
			
			if(!existeUsuario()){
				sql = "DELETE FROM IDIOMA" +
				  	  " WHERE CODIGO = ?";
				
				objPS = con.prepareStatement(sql);
				objPS.setInt(1, getCodigo());
				objPS.execute();
	
				excluido = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			excluido = false;
		}
		
		return excluido;
	}
	
	public boolean existeUsuario() throws SQLException{
	
		boolean existe = false;
		ResultSet rst = null;
		
		try{
			PreparedStatement objPS = null;
			String campo = getTipoUtilizacao() == USAR_IDIOMA ? "IDIOMA" : "FLUENCIA";
			
			objPS = con.prepareStatement("SELECT * FROM USER_CONFIGURACAO WHERE " + campo + " = ?");
			objPS.setInt(1, getCodigo());
			rst = objPS.executeQuery();
			
			existe = rst.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rst != null){
				rst.close();
				existe = false;
			}
		}
		
		return existe;
	}
	
	public void pesquisarIdiomaFluencia() throws SQLException{
		
		Manutencao objManutencao = null;
		LinkedList<Manutencao> listaManutencao = new LinkedList<Manutencao>();
		LinkedList<Manutencao> listaManutencaoTraducao = new LinkedList<Manutencao>();
		ResultSet rst = null;
		
		try{
			PreparedStatement objPS = null;
			
			if(getTipoUtilizacao() == USAR_IDIOMA){
				objPS = con.prepareStatement("SELECT * FROM IDIOMA");
				rst = objPS.executeQuery();
				
				while(rst.next()){
					objManutencao = new Manutencao();
					objManutencao.setCodigo(rst.getInt("CODIGO"));
					objManutencao.setDescricao(rst.getString("DESCRICAO_PTBR"));
					objManutencao.setDescricaoTraducao(rst.getString("DESCRICAO_EN"));
					objManutencao.setTipoUtilizacao(USAR_IDIOMA);
					
					listaManutencao.add(objManutencao);
					
					objManutencao = new Manutencao();
					objManutencao.setCodigo(rst.getInt("CODIGO"));
					objManutencao.setDescricao(rst.getString("DESCRICAO_PTBR"));
					objManutencao.setDescricaoTraducao(rst.getString("DESCRICAO_EN"));
					objManutencao.setTipoUtilizacao(USAR_IDIOMA_EN);
					
					listaManutencaoTraducao.add(objManutencao);
				}
				
				setListaIdioma(listaManutencao);
				setListaIdiomaTraducao(listaManutencaoTraducao);
			}else{
				objPS = con.prepareStatement("SELECT * FROM FLUENCIA");
				rst = objPS.executeQuery();
				
				while(rst.next()){
					objManutencao = new Manutencao();
					objManutencao.setCodigo(rst.getInt("CODIGO"));
					objManutencao.setDescricao(rst.getString("DESCRICAO_PTBR"));
					objManutencao.setDescricaoTraducao(rst.getString("DESCRICAO_EN"));
					objManutencao.setTipoUtilizacao(USAR_FLUENCIA);
					
					listaManutencao.add(objManutencao);
					
					objManutencao = new Manutencao();
					objManutencao.setCodigo(rst.getInt("CODIGO"));
					objManutencao.setDescricao(rst.getString("DESCRICAO_PTBR"));
					objManutencao.setDescricaoTraducao(rst.getString("DESCRICAO_EN"));
					objManutencao.setTipoUtilizacao(USAR_FLUENCIA_EN);
					
					listaManutencaoTraducao.add(objManutencao);
				}
				
				setListaFluencia(listaManutencao);
				setListaFluenciaTraducao(listaManutencaoTraducao);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rst != null){
				rst.close();
			}
		}
	}
}