package br.com.app.business.forum.discussao;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

import br.com.app.business.chat.contatos.Contato;

/**
 * Created by Jefferson on 17/05/2016.
 */
public class Discussao implements Serializable {

    private long idDiscussao;
    private Contato contato;
    private String titulo;
    private String descricao;
    private Date dataHora;
    private byte buscaEspecifica;
    private long contRespostas;
    private long situacao;
    private LinkedList<Resposta> listaRespostas;

    public static final byte BUSCA_DISCUSSAO_ID = 1;
    public static final byte BUSCA_DISCUSSAO_TITULO = 2;
    public static final byte BUSCA_DISCUSSAO_DESCRICAO = 3;
    public static final byte BUSCA_DISCUSSAO_TITULO_DESCRICAO = 4;

    public long getIdDiscussao() {
        return idDiscussao;
    }

    public void setIdDiscussao(long idDiscussao) {
        this.idDiscussao = idDiscussao;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
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

    public byte getBuscaEspecifica() {
        return buscaEspecifica;
    }

    public void setBuscaEspecifica(byte buscaEspecifica) {
        this.buscaEspecifica = buscaEspecifica;
    }

    public long getContRespostas() {
        return contRespostas;
    }

    public void setContRespostas(long contRespostas) {
        this.contRespostas = contRespostas;
    }

    public long getSituacao() {
        return situacao;
    }

    public void setSituacao(long situacao) {
        this.situacao = situacao;
    }

    public LinkedList<Resposta> getListaRespostas() {
        return listaRespostas;
    }

    public void setListaRespostas(LinkedList<Resposta> listaRespostas) {
        this.listaRespostas = listaRespostas;
    }
}