package br.com.app.business.login;

import br.com.app.business.servidor.Servidor;

/**
 * Created by Wesley on 03/04/2016.
 */
public class LoginDAO extends Login{

    public boolean salvar(){
        return Servidor.salvarLogin(this);
    }
}