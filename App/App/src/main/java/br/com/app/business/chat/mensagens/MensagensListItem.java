package br.com.app.business.chat.mensagens;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import br.com.app.business.chat.contatos.Contato;

/**
 * Created by Jefferson on 23/05/2016.
 */
public class MensagensListItem implements Serializable {

    private Contato contato;
    private Date dataHora;
    private LinkedList<Mensagem> mensagens;

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

    public LinkedList<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(LinkedList<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public int getContMsgNaoLidas() {
        int naoLidos = 0;

        if (mensagens != null) {
            for (Mensagem m : this.mensagens) {
                if (!m.getLido()) {
                    naoLidos++;
                }
            }
        }

        return naoLidos;
    }
}
