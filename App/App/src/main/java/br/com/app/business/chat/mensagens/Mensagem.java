package br.com.app.business.chat.mensagens;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jefferson on 30/05/2016.
 */
public class Mensagem implements Serializable {

    private long ID;
    private long idMensagemItem;
    private String UserID;
    private String destinatario;
    private Date dataHora;
    private String mensagem;
    private boolean lido;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getIdMensagemItem() {
        return idMensagemItem;
    }

    public void setIdMensagemItem(long idMensagemItem) {
        this.idMensagemItem = idMensagemItem;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean getLido() {
        return lido;
    }

    public void setLido(boolean lido) {
        this.lido = lido;
    }
}
