package br.com.app.business.chat.contatos;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Jefferson on 06/04/2016.
 */
public class Contato implements Serializable{

    private String userID = "";
    private String userName = "";
    private String firstName = "";
    private String profilePictureURL = "";
    private int idade = 0;
    private int idioma = 0;
    private int nivelFluencia = 0;
    private String status = "";
    private double distancia = 0;

    public Contato() {
        this("");
    }

    public Contato(String userID) {
        super();
        this.userID = userID;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getIdioma() {
        return idioma;
    }

    public void setIdioma(int idioma) {
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

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
}
