package br.com.wiser.business.forum.discussao;

import java.util.LinkedList;

import br.com.wiser.business.app.servidor.Servidor;
import br.com.wiser.business.app.usuario.Usuario;
import br.com.wiser.business.forum.resposta.Resposta;

public class DiscussaoDAO extends Discussao {

    public LinkedList<DiscussaoDAO> carregarDiscussoes(Usuario usuario) {
        return new Servidor().new Forum(usuario).carregarDiscussoes(false);
    }

    public boolean salvarDiscussao(Usuario usuario) {
        return new Servidor().new Forum(usuario).salvarDiscussao(this);
    }

    public LinkedList<DiscussaoDAO> carregarMinhasDiscussoes(Usuario usuario) {
        return new Servidor().new Forum(usuario).carregarDiscussoes(true);
    }

    public LinkedList<DiscussaoDAO> procurarDiscussoes(Usuario usuario, String chave) {
        return new Servidor().new Forum(usuario).procurarDiscussoes(chave);
    }

    public boolean desativarDiscussao(Usuario usuario) {
        return new Servidor().new Forum(usuario).desativarDiscussao(this);
    }

    public void enviarResposta(Usuario usuario, Resposta resposta) {
        if (new Servidor().new Forum(usuario).enviarResposta(this, resposta)) {
            this.getListaRespostas().add(resposta);
        }
    }
}
