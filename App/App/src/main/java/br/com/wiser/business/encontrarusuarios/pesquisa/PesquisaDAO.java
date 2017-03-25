package br.com.wiser.business.encontrarusuarios.pesquisa;

import br.com.wiser.Sistema;
import br.com.wiser.business.app.servidor.Servidor;
import br.com.wiser.business.app.usuario.Usuario;

public class PesquisaDAO extends Pesquisa {

    public boolean procurarUsuarios(Usuario usuario) {
        this.setListaResultados(new Servidor().new Usuarios(usuario).pesquisarUsuarios(this));
        return (this.getListaResultados().size() > 0);
    }
}