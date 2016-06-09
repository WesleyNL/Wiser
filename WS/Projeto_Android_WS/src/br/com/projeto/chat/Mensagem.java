package br.com.projeto.chat;

import java.util.Date;

public class Mensagem {
	
	private long codigo;
	private long codigoMensagemItem;
	private String userIdRemetente;
	private String userIdDestinatario;
	private Date dataHora;
	private String dataHoraCustom;
	private String mensagem;
	private byte lido;
	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public long getCodigoMensagemItem() {
		return codigoMensagemItem;
	}
	public void setCodigoMensagemItem(long codigoMensagemItem) {
		this.codigoMensagemItem = codigoMensagemItem;
	}
	public String getUserIdRemetente() {
		return userIdRemetente;
	}
	public void setUserIdRemetente(String userIdRemetente) {
		this.userIdRemetente = userIdRemetente;
	}
	public String getUserIdDestinatario() {
		return userIdDestinatario;
	}
	public void setUserIdDestinatario(String userIdDestinatario) {
		this.userIdDestinatario = userIdDestinatario;
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
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public byte getLido() {
		return lido;
	}
	public void setLido(byte lido) {
		this.lido = lido;
	}
}