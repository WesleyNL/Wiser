package br.com.app.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.app.activity.R;
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
    private static Button btnAbrirPerfil;

    public static void mostrarDetalhes(final Context context, final Contato contato){

        builderDetalhes = new AlertDialog.Builder(context);
        viewDetalhes = ((Activity) context).getLayoutInflater().inflate(R.layout.chat_perfil_detalhes, null);
        imgPerfil = (ImageView) viewDetalhes.findViewById(R.id.imgPerfil);
        prgBarra = (ProgressBar) viewDetalhes.findViewById(R.id.prgBarra);
        lblNome = (TextView) viewDetalhes.findViewById(R.id.lblNomeDetalhe);
        lblIdiomaNivel = (TextView) viewDetalhes.findViewById(R.id.lblIdiomaNivel);
        lblStatus = (TextView) viewDetalhes.findViewById(R.id.lblStatus);
        btnAbrirPerfil = (Button) viewDetalhes.findViewById(R.id.btnAbrirPerfil);

        Utils.loadImageInBackground(context, contato.getProfilePictureURL(), imgPerfil, prgBarra);
        lblNome.setText(contato.getFirstName());
        lblIdiomaNivel.setText(Utils.hashIdiomas.get(contato.getIdioma()) + " - " + Utils.hashFluencias.get(contato.getNivelFluencia()));
        lblStatus.setText(contato.getStatus());

//        btnAbrirPerfil.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Facebook.abrirPerfil(context, contato.getUserID());
//            }
//        });



        builderDetalhes.setView(viewDetalhes);
        detalhes = builderDetalhes.create();
        detalhes.show();
    }
}
