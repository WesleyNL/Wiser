package br.com.wiser.business.forum.discussao;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

import br.com.wiser.business.app.usuario.Usuario;
import br.com.wiser.business.forum.resposta.Resposta;

/**
 * Created by Jefferson on 17/05/2016.
 */
public class Discussao implements Serializable {

    private long id;
    private Usuario usuario;
    private String titulo;
    private String descricao;
    private Date dataHora;
    private boolean discussaoAtiva;
    private LinkedList<Resposta> listaRespostas = new LinkedList<Resposta>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public boolean getDiscussaoAtiva() {
        return discussaoAtiva;
    }

    public void setDiscussaoAtiva(boolean discussaoAtiva) {
        this.discussaoAtiva = discussaoAtiva;
    }

    public LinkedList<Resposta> getListaRespostas() {
        return listaRespostas;
    }

    public void setListaRespostas(LinkedList<Resposta> listaRespostas) {
        this.listaRespostas = listaRespostas;
    }
}