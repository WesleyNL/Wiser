package br.com.projeto.pesquisa;

import java.util.LinkedList;

public class Pesquisa {

	private String userId;
	private byte idioma;
	private byte fluencia;
	private int distancia;
	private LinkedList<String> listaUsuarios = new LinkedList<String>();
	
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
	public int getDistancia() {
		return distancia;
	}
	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
	public LinkedList<String> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(LinkedList<String> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
}
