package br.com.app.business.app.configuracao;

public class Configuracao {

	private int codigo;
	private String userId;
	private byte idioma;
	private byte fluencia;
	private String status;
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public byte getIdioma() {
		return idioma;
	}

	public void setIdioma(byte idioma) {
		this.idioma = idioma;
	}

	public byte getFluencia() {
		return fluencia;
	}

	public void setFluencia(byte fluencia) {
		this.fluencia = fluencia;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}