package br.com.app.business.pesquisa;

import java.util.LinkedList;

import br.com.app.business.contatos.Contato;

/**
 * Created by Wesley on 03/04/2016.
 */
public class Pesquisa {

    private String userId;
    private byte idioma;
    private byte fluencia;
    private int distancia;
    private LinkedList<Contato> listaUsuarios = new LinkedList<Contato>();

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

    public LinkedList<Contato> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(LinkedList<Contato> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

}
