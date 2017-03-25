package br.com.wiser.business.chat.mensagem;

import java.util.Date;

/**
 * Created by Jefferson on 10/08/2016.
 */
public class Mensagem {
    private long id;
    private boolean isDestinatario;
    private Date data;
    private String mensagem;
    private boolean lida;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDestinatario() {
        return isDestinatario;
    }

    public void setDestinatario(boolean destinatario) {
        isDestinatario = destinatario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }
}
