package br.com.app.business.forum.discussao;

import java.io.Serializable;
import java.util.Date;

import br.com.app.business.chat.contatos.Contato;

/**
 * Created by Jefferson on 20/05/2016.
 */
public class Resposta implements Serializable {

    private long idResposta;
    private long idDiscussao;
    private Contato contato;
    private Date dataHora;
    private String resposta;

    public long getIdResposta() {
        return idResposta;
    }

    public long getIdDiscussao() {
        return idDiscussao;
    }

    public void setIdDiscussao(long idDiscussao) {
        this.idDiscussao = idDiscussao;
    }

    public void setIdResposta(long idResposta) {
        this.idResposta = idResposta;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
}
