package br.com.app.business.contatos;

import android.graphics.Bitmap;

/**
 * Created by Jefferson on 06/04/2016.
 */
public class Contato {

    private String userID = "";
    private String userName = "";
    private String profilePictureURL = "";
    private Bitmap profilePicture;
    private int idade = 0;
    private String idioma = "";
    private int nivelFluencia = 0;
    private String status = "";
    private int distancia = 0;

    public Contato() {

    }

    public Contato(Bitmap img, String nome) {
        super();
        this.profilePicture = img;
        this.userName = nome;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public Bitmap getProfilePicture() {
        return this.profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getNivelFluencia() {
        return nivelFluencia;
    }

    public void setNivelFluencia(int nivelFluencia) {
        this.nivelFluencia = nivelFluencia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
}
