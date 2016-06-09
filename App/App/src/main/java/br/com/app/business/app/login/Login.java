package br.com.app.business.app.login;

import java.util.Date;

/**
 * Created by Wesley on 03/04/2016.
 */
public class Login {

    private String userId;
    private byte situacao;
    private Date dataUltimoAcesso;
    private String coordUltimoAcesso;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public byte getSituacao() {
        return situacao;
    }

    public void setSituacao(byte situacao) {
        this.situacao = situacao;
    }

    public Date getDataUltimoAcesso() {
        return dataUltimoAcesso;
    }

    public void setDataUltimoAcesso(Date dataUltimoAcesso) {
        this.dataUltimoAcesso = dataUltimoAcesso;
    }

    public String getCoordUltimoAcesso() {
        return coordUltimoAcesso;
    }

    public void setCoordUltimoAcesso(String coordUltimoAcesso) {
        this.coordUltimoAcesso = coordUltimoAcesso;
    }
}
