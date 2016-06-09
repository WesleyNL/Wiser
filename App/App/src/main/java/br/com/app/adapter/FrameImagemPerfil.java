package br.com.app.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.LinkedList;

import br.com.app.activity.R;
import br.com.app.activity.chat.mensagens.ChatMensagemActivity;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.utils.Utils;

/**
 * Created by Jefferson on 29/05/2016.
 */
public class FrameImagemPerfil {
    private static AlertDialog detalhes;
    private static AlertDialog.Builder builderDetalhes;
    private static View viewDetalhes;
    private static ImageView imgPerfil;
    private static ProgressBar prgBarra;
    private static TextView lblNome;
    private static TextView lblIdiomaNivel;
    private static TextView lblStatus;
    private static Button btnAbrirChat;

    public static void mostrarDetalhes(final Context context, final Contato contato){

        builderDetalhes = new AlertDialog.Builder(context);
        viewDetalhes = ((Activity) context).getLayoutInflater().inflate(R.layout.chat_perfil_detalhes, null);
        imgPerfil = (ImageView) viewDetalhes.findViewById(R.id.imgPerfil);
        prgBarra = (ProgressBar) viewDetalhes.findViewById(R.id.prgBarra);
        lblNome = (TextView) viewDetalhes.findViewById(R.id.lblNomeDetalhe);
        lblIdiomaNivel = (TextView) viewDetalhes.findViewById(R.id.lblIdiomaNivel);
        lblStatus = (TextView) viewDetalhes.findViewById(R.id.lblStatus);
        btnAbrirChat = (Button) viewDetalhes.findViewById(R.id.btnAbrirChat);

        Utils.loadImageInBackground(context, contato.getProfilePictureURL(), imgPerfil, prgBarra);
        lblNome.setText(contato.getFirstName());
        lblIdiomaNivel.setText(Utils.hashIdiomas.get(contato.getIdioma()) + " - " + Utils.hashFluencias.get(contato.getNivelFluencia()));
        lblStatus.setText(contato.getStatus());

        btnAbrirChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contato destinatario = new Contato();
                destinatario.setUserID(contato.getUserID());
                destinatario.setUserName(contato.getUserName());

                Bundle bdlContatos = new Bundle();
                LinkedList<Contato> contatos = new LinkedList<Contato>();
                contatos.add(0, destinatario);
                bdlContatos.putSerializable("contatos", contatos);

                Intent i = new Intent(v.getContext(), ChatMensagemActivity.class);
                i.putExtra("contatos", bdlContatos);
                i.putExtra("especifico", 1);
                v.getContext().startActivity(i);
            }
        });

        builderDetalhes.setView(viewDetalhes);
        detalhes = builderDetalhes.create();
        detalhes.show();
    }
}
