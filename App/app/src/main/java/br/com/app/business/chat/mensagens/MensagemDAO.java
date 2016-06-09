package br.com.app.business.chat.mensagens;

import java.util.LinkedList;

import br.com.app.business.app.servidor.Servidor;

/**
 * Created by Wesley on 05/06/2016.
 */
public class MensagemDAO extends MensagemItem {

    public LinkedList<MensagemItem> carregarGeral(){
        return Servidor.carregarMensagensGeral(this);
    }

    public LinkedList<Mensagem> carregarEspecifico(){
        LinkedList<Mensagem> listaMensagens = new LinkedList<Mensagem>();

        LinkedList<MensagemItem> listaMensagemItem = Servidor.carregarMensagensEspecifico(this);

        if(listaMensagemItem != null && !listaMensagemItem.isEmpty()){
            listaMensagens = listaMensagemItem.get(0).getMensagens();
        }

        return listaMensagens;
    }

    public void atualizarLeitura(Mensagem mensagem){
        Servidor.atualizarMensagemLeitura(mensagem);
    }

    public void enviar(Mensagem mensagem){
        Servidor.enviarMensagem(mensagem);
    }
}