package br.com.app.business.forum.discussao;

import br.com.app.business.chat.contatos.Contato;

/**
 * Created by Jefferson on 17/05/2016.
 */
public class Discussao {

    private long IDDiscussao;
    private Contato contato;
    private String tituloDiscussao;
    private String descricaoDiscussao;
    private long contRespostas;

    public long getIDDiscussao() {
        return IDDiscussao;
    }

    public void setIDDiscussao(long IDDiscussao) {
        this.IDDiscussao = IDDiscussao;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public String getTituloDiscussao() {
        return tituloDiscussao;
    }

    public void setTituloDiscussao(String tituloDiscussao) {
        this.tituloDiscussao = tituloDiscussao;
    }

    public String getDescricaoDiscussao() {
        return descricaoDiscussao;
    }

    public void setDescricaoDiscussao(String descricaoDiscussao) {
        this.descricaoDiscussao = descricaoDiscussao;
    }

    public long getContRespostas() {
        return contRespostas;
    }

    public void setContRespostas(long contRespostas) {
        this.contRespostas = contRespostas;
    }
}
