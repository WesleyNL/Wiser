package br.com.wiser.business.app.usuario;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jefferson on 06/04/2016.
 */
public class Usuario implements Serializable{

    private long userID;
    private String facebookID;
    private String fullName;
    private String firstName;
    private String urlProfilePicture;
    private int idade;

    private Date dataUltimoAcesso;
    private double latitude;
    private double longitude;
    private int idioma;
    private int fluencia;
    private String status;

    private boolean contaAtiva = true;
    private boolean setouConfiguracoes = false;

    public Usuario(long userID) {
        super();
        this.userID = userID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUrlProfilePicture() {
        return urlProfilePicture;
    }

    public void setUrlProfilePicture(String urlProfilePicture) {
        this.urlProfilePicture = urlProfilePicture;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Date getDataUltimoAcesso() {
        return dataUltimoAcesso;
    }

    public void setDataUltimoAcesso(Date dataUltimoAcesso) {
        this.dataUltimoAcesso = dataUltimoAcesso;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getIdioma() {
        return idioma;
    }

    public void setIdioma(int idioma) {
        this.idioma = idioma;
    }

    public int getFluencia() {
        return fluencia;
    }

    public void setFluencia(int fluencia) {
        this.fluencia = fluencia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isContaAtiva() {
        return contaAtiva;
    }

    public void setContaAtiva(boolean contaAtiva) {
        this.contaAtiva = contaAtiva;
    }

    public boolean isSetouConfiguracoes() {
        return setouConfiguracoes;
    }

    public void setSetouConfiguracoes(boolean setouConfiguracoes) {
        this.setouConfiguracoes = setouConfiguracoes;
    }
}
