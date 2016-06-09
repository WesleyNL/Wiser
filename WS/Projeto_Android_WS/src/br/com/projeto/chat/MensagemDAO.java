package br.com.projeto.chat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.Vector;

import br.com.projeto.conexao.Conexao;
import br.com.projeto.utils.Constantes;

public class MensagemDAO {

	public Vector<MensagemItem> carregarGeral(MensagemItem mensagemItem) throws SQLException{
		
		Vector<MensagemItem> listaMensagemItem = new Vector<MensagemItem>();
		ResultSet rst = null;
		
		try{
			String sql = "SELECT mi.CODIGO AS MI_CODIGO, mi.USER_ID_CARREGAR AS MI_CARREGAR, mi.USER_ID_DESTINATARIO AS MI_CONTATO, " +
						 " mi.DATA_HORA AS MI_DATA_HORA, m.LIDO, " +
						 " m.CODIGO as M_CODIGO, m.USER_ID_REMETENTE, m.DATA_HORA AS M_DATA_HORA, m.MENSAGEM " +
						 " FROM MENSAGEM_ITEM mi " +
						 " INNER JOIN MENSAGEM m " +
						 " ON mi.CODIGO = m.CODIGO_MENSAGEM_ITEM ";
			
			if(mensagemItem.getUserIdDestinatario().trim().isEmpty()){
				sql += " WHERE mi.USER_ID_CARREGAR = ? OR mi.USER_ID_DESTINATARIO = ?";
			}else{
				sql += " WHERE (mi.USER_ID_CARREGAR = ? AND mi.USER_ID_DESTINATARIO = ?) OR (mi.USER_ID_CARREGAR = ? AND mi.USER_ID_DESTINATARIO = ?)";
			}
			
			sql += " ORDER BY MI_DATA_HORA DESC, M_DATA_HORA ASC";
			
			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			
			if(mensagemItem.getUserIdDestinatario().trim().isEmpty()){
				objPS.setString(1, mensagemItem.getUserIdCarregar());
				objPS.setString(2, mensagemItem.getUserIdCarregar());
			}else{
				objPS.setString(1, mensagemItem.getUserIdCarregar());
				objPS.setString(2, mensagemItem.getUserIdDestinatario());
				objPS.setString(3, mensagemItem.getUserIdDestinatario());
				objPS.setString(4, mensagemItem.getUserIdCarregar());
			}
			
			rst = objPS.executeQuery();
			
			MensagemItem objMensagemItem = null;
			Mensagem objMensagem = null;
			Vector<Mensagem> listaMensagens = null;
			long msgItemAtual = 0;
			
			while(rst.next()){

				if(msgItemAtual != rst.getLong("MI_CODIGO")){
					objMensagemItem = new MensagemItem();
					listaMensagens = new Vector<Mensagem>();
					
					objMensagemItem.setUserIdDestinatario(rst.getString("MI_CONTATO"));
					objMensagemItem.setUserIdCarregar(rst.getString("MI_CARREGAR"));
					objMensagemItem.setDataHora(rst.getTimestamp("MI_DATA_HORA"));
					objMensagemItem.setDataHoraCustom(objMensagemItem.getDataHora().toString().replaceAll("-", "/"));
					objMensagemItem.setListaMensagens(listaMensagens);
					
					listaMensagemItem.add(objMensagemItem);
				}

				if(rst.getString("M_CODIGO") != null){
					objMensagem = new Mensagem();

					objMensagem.setCodigo(rst.getLong("M_CODIGO"));
					objMensagem.setCodigoMensagemItem(rst.getLong("MI_CODIGO"));
					objMensagem.setUserIdRemetente(rst.getString("USER_ID_REMETENTE"));
					objMensagem.setDataHora(rst.getTimestamp("M_DATA_HORA"));
					objMensagem.setDataHoraCustom(objMensagem.getDataHora().toString().replaceAll("-", "/"));
					objMensagem.setMensagem(rst.getString("MENSAGEM"));
					objMensagem.setLido(rst.getByte("LIDO"));

					listaMensagens.add(objMensagem);
				}else{
					objMensagem = new Mensagem();
					objMensagem.setCodigo(-1);
					listaMensagens.add(objMensagem);
				}

				msgItemAtual = rst.getLong("MI_CODIGO");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rst != null){
				rst.close();
			}
		}
		
		return listaMensagemItem;
	}
	
	public void atualizarLeitura(Mensagem mensagem) throws SQLException{
		
		ResultSet rst = null;

		try {
			String sql = "UPDATE MENSAGEM" +
					     " SET LIDO = ?" +
					     " WHERE CODIGO_MENSAGEM_ITEM = ? " +
					     " AND USER_ID_REMETENTE = ?";

			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			objPS.setByte(1, Constantes.CODIGO_ATIVO);
			objPS.setLong(2, mensagem.getCodigoMensagemItem());
			objPS.setString(3, mensagem.getUserIdRemetente());

			objPS.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rst != null){
				rst.close();
			}
		}
	}
	
	public void enviar(Mensagem mensagem) throws SQLException{
		
		ResultSet rst = null;

		try {
			Timestamp dataHoraMsg = new Timestamp(new GregorianCalendar().getTime().getTime());
			
			mensagem.setCodigoMensagemItem(salvarItem(mensagem.getCodigoMensagemItem(), dataHoraMsg, mensagem.getUserIdRemetente(), mensagem.getUserIdDestinatario()));
			
			String sql = "INSERT MENSAGEM (CODIGO_MENSAGEM_ITEM, USER_ID_REMETENTE, DATA_HORA, MENSAGEM, LIDO)" +
						 " VALUES (?,?,?,?,?)";

			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			objPS.setLong(1, mensagem.getCodigoMensagemItem());
			objPS.setString(2, mensagem.getUserIdRemetente());
			objPS.setTimestamp(3, new Timestamp(new GregorianCalendar().getTime().getTime()));
			objPS.setString(4, mensagem.getMensagem());
			objPS.setByte(5, Constantes.CODIGO_INATIVO);

			objPS.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rst != null){
				rst.close();
			}
		}
	}
	
	@SuppressWarnings("resource")
	private long salvarItem(long codigo, Timestamp dataHoraMsg, String remetente, String destinatario) throws SQLException{
		
		long codigoItem = 0;
		ResultSet rst = null;
		
		try{
			String sql = "SELECT CODIGO FROM MENSAGEM_ITEM WHERE CODIGO = ?";
			
			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			objPS.setLong(1, codigo);
			rst = objPS.executeQuery();
			
			if(rst.next()){
				sql = "UPDATE MENSAGEM_ITEM" +
					  " SET DATA_HORA = ?" +
					  " WHERE CODIGO = ? ";

				objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
				objPS.setTimestamp(1, dataHoraMsg);
				objPS.setLong(2, codigo);
				
				codigoItem = codigo;
				
				objPS.executeUpdate();
			}else{
				sql = "INSERT MENSAGEM_ITEM (USER_ID_DESTINATARIO, USER_ID_CARREGAR, DATA_HORA) " +
					  " VALUES(?,?,?)";
				
				objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
				objPS.setString(1, destinatario);
				objPS.setString(2, remetente);
				objPS.setTimestamp(3, dataHoraMsg);
				objPS.execute();
				
				sql = "SELECT CODIGO FROM MENSAGEM_ITEM " +
							 " WHERE USER_ID_DESTINATARIO = ? " +
							 " AND USER_ID_CARREGAR = ?";
				
				objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
				objPS.setString(1, destinatario);
				objPS.setString(2, remetente);
				rst = objPS.executeQuery();
				
				if(rst.next()){
					codigoItem = rst.getLong("CODIGO");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rst != null){
				rst.close();
			}
		}	
		
		return codigoItem;
	}
}