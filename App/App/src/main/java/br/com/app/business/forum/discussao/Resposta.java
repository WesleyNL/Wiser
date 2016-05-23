package br.com.app.business.forum.discussao;

import java.io.Serializable;
import java.util.Date;

import br.com.app.business.chat.contatos.Contato;

/**
 * Created by Jefferson on 20/05/2016.
 */
public class Resposta implements Serializable {

    private long IDResposta;
    private Contato contato;
    private Date dataHora;
    private String resposta;

    public long getIDResposta() {
        return IDResposta;
    }

    public void setIDResposta(long IDResposta) {
        this.IDResposta = IDResposta;
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
