package br.com.projeto.pesquisa;

public class Pesquisa {

	private String userId;
	private byte idioma;
	private byte fluencia;
	private double distancia;
	private String status;
	
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
	public double getDistancia() {
		return distancia;
	}
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
