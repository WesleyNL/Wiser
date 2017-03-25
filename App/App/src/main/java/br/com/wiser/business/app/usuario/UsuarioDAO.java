package br.com.wiser.business.app.usuario;

import br.com.wiser.Sistema;
import br.com.wiser.business.app.servidor.Servidor;

/**
 * Created by Jefferson on 07/08/2016.
 */
public class UsuarioDAO extends Usuario{

    public UsuarioDAO (long userID){
        super(userID);
    }

    public boolean salvarLogin() {
        try {
            new Servidor().new Usuarios(this).salvarLogin(this);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean salvarConfiguracoes() {
        return new Servidor().new Usuarios(this).salvarConfiguracoes(this);
    }

    public boolean desativarConta(){
        return new Servidor().new Usuarios(this).desativarConta();
    }
}
