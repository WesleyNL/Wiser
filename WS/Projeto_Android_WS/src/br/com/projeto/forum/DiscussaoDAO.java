package br.com.projeto.forum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.Vector;

import br.com.projeto.conexao.Conexao;
import br.com.projeto.utils.Constantes;

public class DiscussaoDAO {

	private final long QTD_DISCUSSAO_ATUAL = 1;
	private final long QTD_DISCUSSAO_PRINCIPAL = 10;
	private final long QTD_DISCUSSAO_TODAS = 999999;
	
	private static long qtdDiscussoes = 10;

	public Vector<Discussao> pesquisarUsuario(Discussao discussao) throws SQLException{
		qtdDiscussoes = QTD_DISCUSSAO_TODAS;
		return carregar(null, discussao.getAutor());
	}
	
	public Vector<Discussao> pesquisarEspecifico(Discussao discussao) throws SQLException{
		qtdDiscussoes = QTD_DISCUSSAO_TODAS;
		return carregar(discussao, "");
	} 

	public Vector<Discussao> carregar(Discussao discussao) throws SQLException{
		qtdDiscussoes = QTD_DISCUSSAO_PRINCIPAL;
		return carregar(null, "");
	}

	private Vector<Discussao> carregar(Discussao discussao, String idUsuario) throws SQLException{

		Vector<Discussao> listaDiscussoes = new Vector<Discussao>();
		ResultSet rst = null;

		try {
			String sql = "SELECT d.CODIGO as ID_DISCUSSAO, d.TITULO, d.DESCRICAO, d.DATA_HORA as DATA_HORA_DISCUSSAO, d.AUTOR AS AUTOR_DISCUSSAO, d.SITUACAO, " + 
						 " r.CODIGO as ID_RESPOSTA, r.DATA_HORA as DATA_HORA_RESPOSTA, r.RESPOSTA, r.AUTOR AS AUTOR_RESPOSTA " +
						 " FROM DISCUSSAO d " +
						 " LEFT JOIN DISCUSSAO_RESPOSTA r " +
						 " ON d.CODIGO = r.DISCUSSAO ";

			if(!idUsuario.trim().isEmpty()){
				sql += " WHERE d.AUTOR = " + idUsuario;
			}else{
				if(discussao != null && discussao.getBuscaEspecifica() != 0){
					switch(discussao.getBuscaEspecifica()){
					case Constantes.BUSCA_DISCUSSAO_ID:
						sql += " WHERE d.CODIGO = " + discussao.getIdDiscussao();
						break;
					case Constantes.BUSCA_DISCUSSAO_TITULO:
						sql += " WHERE d.TITULO LIKE '%" + discussao.getTitulo() + "%'";
						break;
					case Constantes.BUSCA_DISCUSSAO_DESCRICAO:
						sql += " WHERE d.DESCRICAO LIKE '%" + discussao.getDescricao() + "%'";
						break;
					case Constantes.BUSCA_DISCUSSAO_TITULO_DESCRICAO:
						sql += " WHERE (d.TITULO LIKE '%" + discussao.getTitulo() + "%' OR d.DESCRICAO LIKE '%" + discussao.getDescricao() + "%')";
						break;
					}
				}
			}
			
			sql += " ORDER BY ID_DISCUSSAO DESC ";

			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			rst = objPS.executeQuery();

			Discussao objDiscussao = null;
			Resposta objResposta = null;
			Vector<Resposta> listaRespostas = null;
			long discussaoAtual = 0;
			boolean processando = true;
			
			while(processando && rst.next()){

				if(discussaoAtual != rst.getLong("ID_DISCUSSAO")){
					objDiscussao = new Discussao();
					listaRespostas = new Vector<Resposta>();
					
					objDiscussao.setIdDiscussao(rst.getLong("ID_DISCUSSAO"));
					objDiscussao.setTitulo(rst.getString("TITULO"));
					objDiscussao.setDescricao(rst.getString("DESCRICAO"));
					objDiscussao.setDataHora(rst.getTimestamp("DATA_HORA_DISCUSSAO"));
					objDiscussao.setDataHoraCustom(objDiscussao.getDataHora().toString().replaceAll("-", "/"));
					objDiscussao.setAutor(rst.getString("AUTOR_DISCUSSAO"));
					objDiscussao.setSituacao(rst.getByte("SITUACAO"));
					objDiscussao.setListaRespostas(listaRespostas);
					
					listaDiscussoes.add(objDiscussao);
				}

				if(rst.getString("ID_RESPOSTA") != null){
					objResposta = new Resposta();

					objResposta.setIdResposta(rst.getLong("ID_RESPOSTA"));
					objResposta.setDataHora(rst.getTimestamp("DATA_HORA_RESPOSTA"));
					objResposta.setDataHoraCustom(objResposta.getDataHora().toString().replaceAll("-", "/"));
					objResposta.setResposta(rst.getString("RESPOSTA"));
					objResposta.setAutor(rst.getString("AUTOR_RESPOSTA"));

					listaRespostas.add(objResposta);
				}else{
					objResposta = new Resposta();
					objResposta.setIdResposta(-1);
					listaRespostas.add(objResposta);
				}

				objDiscussao.setContRespostas(listaRespostas.size());
				discussaoAtual = rst.getLong("ID_DISCUSSAO");
				
				if(qtdDiscussoes == QTD_DISCUSSAO_PRINCIPAL){
					processando = listaDiscussoes.size() < QTD_DISCUSSAO_PRINCIPAL;
				}else if(qtdDiscussoes == QTD_DISCUSSAO_ATUAL){
					processando = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rst != null){
				rst.close();
			}
			qtdDiscussoes = QTD_DISCUSSAO_PRINCIPAL;
		}

		return listaDiscussoes;
	}
	
	public boolean desativar(Discussao discussao) throws SQLException{

		boolean retorno = false;
		ResultSet rst = null;

		try {
			String sql = "UPDATE DISCUSSAO" +
					     " SET SITUACAO = ?" +
					     " WHERE AUTOR = ?" +
					     " AND CODIGO = ?";

			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			objPS.setByte(1, Constantes.CODIGO_INATIVO);
			objPS.setString(2, discussao.getAutor());
			objPS.setLong(3, discussao.getIdDiscussao());

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

	public long salvar(Discussao discussao) throws SQLException{

		long idDiscussao = 0;
		ResultSet rst = null;

		try {
			String sql = "INSERT INTO DISCUSSAO" +
					     " (TITULO, DESCRICAO, DATA_HORA, AUTOR, SITUACAO)" +
					     " VALUES(?,?,?,?,?)";

			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			objPS.setString(1, discussao.getTitulo());
			objPS.setString(2, discussao.getDescricao());
			objPS.setTimestamp(3, new Timestamp(new GregorianCalendar().getTime().getTime()));
			objPS.setString(4, discussao.getAutor());
			objPS.setByte(5, Constantes.CODIGO_ATIVO);

			objPS.execute();
			qtdDiscussoes = QTD_DISCUSSAO_ATUAL;
			Vector<Discussao> listaDiscussao = pesquisarUsuario(discussao);
			idDiscussao = listaDiscussao.size() != 0 ? pesquisarUsuario(discussao).get(0).getIdDiscussao() : 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rst != null){
				rst.close();
			}
		}

		return idDiscussao;
	}
	
	public void responder(Resposta resposta) throws SQLException{

		ResultSet rst = null;

		try {
			String sql = "INSERT INTO DISCUSSAO_RESPOSTA" +
					     " (DISCUSSAO, RESPOSTA, DATA_HORA, AUTOR)" +
					     " VALUES(?,?,?,?)";

			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			objPS.setLong(1, resposta.getIdDiscussao());
			objPS.setString(2, resposta.getResposta());
			objPS.setTimestamp(3, new Timestamp(new GregorianCalendar().getTime().getTime()));
			objPS.setString(4, resposta.getAutor());

			objPS.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rst != null){
				rst.close();
			}
		}
	}
}