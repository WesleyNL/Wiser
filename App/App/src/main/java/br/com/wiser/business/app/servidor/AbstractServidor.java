package br.com.wiser.business.app.servidor;

import br.com.wiser.business.app.usuario.Usuario;

/**
 * Created by Jefferson on 07/09/2016.
 */
public abstract class AbstractServidor {
    protected Usuario usuario;

    public AbstractServidor(Usuario usuario) {
        this.usuario = usuario;
    }
}
