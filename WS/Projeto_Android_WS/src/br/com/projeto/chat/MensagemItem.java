package br.com.projeto.chat;

import java.util.Date;
import java.util.Vector;

public class MensagemItem {

	private String userIdDestinatario;
	private String userIdCarregar;
	private Date dataHora;
	private String dataHoraCustom;
	private Vector<Mensagem> listaMensagens;
	
	public String getUserIdDestinatario() {
		return userIdDestinatario;
	}
	public void setUserIdDestinatario(String userIdDestinatario) {
		this.userIdDestinatario = userIdDestinatario;
	}
	public String getUserIdCarregar() {
		return userIdCarregar;
	}
	public void setUserIdCarregar(String userIdCarregar) {
		this.userIdCarregar = userIdCarregar;
	}
	public Date getDataHora() {
		return dataHora;
	}
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	public String getDataHoraCustom() {
		return dataHoraCustom;
	}
	public void setDataHoraCustom(String dataHoraCustom) {
		this.dataHoraCustom = dataHoraCustom;
	}
	public Vector<Mensagem> getListaMensagens() {
		return listaMensagens;
	}
	public void setListaMensagens(Vector<Mensagem> listaMensagens) {
		this.listaMensagens = listaMensagens;
	}
}