package br.com.projeto.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.com.projeto.conexao.Conexao;
import br.com.projeto.principal.LoginDAO;

public class ThreadDesativarData implements Runnable{
	
	private Date dataExpiracao = null;
	private Calendar cal = new GregorianCalendar();
	
	@Override
	public void run() {
		
		while(true){
			
			try {
				desativarExpirados();
			} catch (SQLException e) {
				e.printStackTrace();
				System.setOut(null);
			}
			
			try {
				Thread.sleep(86400000); // 1 Dia
			} catch (InterruptedException i) {
				i.printStackTrace();
				System.setOut(null);
			}
		}
	}
	
	private void desativarExpirados() throws SQLException{
		
		ResultSet rst = null;
		
		try {
			cal.setTime(new Date());
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
			dataExpiracao = cal.getTime();
			
			String sql = "SELECT USER_ID FROM USER_LOGIN" +
						 " WHERE DATA_ULTIMO_ACESSO = ?";
			
			PreparedStatement objPS = Conexao.getInstance().getConexao().prepareStatement(sql);
			objPS.setDate(1, new java.sql.Date(dataExpiracao.getTime()));
			rst = objPS.executeQuery();
			
			LoginDAO objLogin = new LoginDAO();
			
			while(rst.next()){
				objLogin.desativar(rst.getString("USER_ID"));
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