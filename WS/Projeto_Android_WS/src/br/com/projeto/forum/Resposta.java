package br.com.projeto.forum;

import java.util.Date;

public class Resposta {
	
	private long idResposta;
	private long idDiscussao;
	private Date dataHora;
	private String dataHoraCustom;
	private String resposta;
	private String autor;

	public long getIdResposta() {
		return idResposta;
	}

	public void setIdResposta(long idResposta) {
		this.idResposta = idResposta;
	}

	public long getIdDiscussao() {
		return idDiscussao;
	}

	public void setIdDiscussao(long idDiscussao) {
		this.idDiscussao = idDiscussao;
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

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
}
