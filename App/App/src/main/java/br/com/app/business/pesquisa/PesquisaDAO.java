package br.com.app.business.pesquisa;

import br.com.app.activity.servidor.Servidor;

/**
 * Created by Wesley on 03/04/2016.
 */
public class PesquisaDAO extends Pesquisa {

    public boolean procurar() {
        return Servidor.pesquisarUsuarios(this);
    }
}
