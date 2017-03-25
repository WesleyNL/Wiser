package br.com.wiser.business.chat.conversas;

import java.io.Serializable;
import java.util.LinkedList;

import br.com.wiser.business.app.usuario.Usuario;
import br.com.wiser.business.chat.mensagem.Mensagem;

/**
 * Created by Jefferson on 23/05/2016.
 */
public class Conversas implements Serializable {

    private Usuario destinatario;
    private LinkedList<Mensagem> mensagens = new LinkedList<Mensagem>();

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
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
                if (!m.isLida()) {
                    naoLidos++;
                }
            }
        }

        return naoLidos;
    }

    public Mensagem getLastMensagem() {
        return mensagens.getLast();
    }
}
