package br.com.app.business.app.login;

import br.com.app.business.app.servidor.Servidor;

/**
 * Created by Wesley on 03/04/2016.
 */
public class LoginDAO extends Login{

    public boolean salvar(){
        return Servidor.salvarLogin(this);
    }
}