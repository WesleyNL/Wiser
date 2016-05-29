package br.com.app.business.forum.discussao;

import java.util.LinkedList;

import br.com.app.business.app.servidor.Servidor;

/**
 * Created by Wesley on 26/05/2016.
 */
public class DiscussaoDAO extends Discussao {

    public LinkedList<Discussao> carregar(){
        return Servidor.carregarDiscussoes();
    }

    public LinkedList<Discussao> pesquisarUsuario(){
        return Servidor.pesquisarDiscussoesUsuario();
    }

    public LinkedList<Discussao> pesquisarEspecifico(){
        return Servidor.pesquisarDiscussoesEspecifico(this);
    }

    public boolean salvar(){
        setIdDiscussao(Servidor.criarDiscussao(this));
        return getIdDiscussao() != 0;
    }

    public boolean excluir(){
        return Servidor.desativarDiscussao(this);
    }

    public void responder(Resposta resposta){
        Servidor.responderDiscussao(resposta);
    }
}
