package br.com.wiser.frames;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Button;

import br.com.wiser.activity.R;
import br.com.wiser.business.app.usuario.Usuario;
import br.com.wiser.utils.Utils;

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

    public static void mostrarDetalhes(final Context context, final Usuario contato) {

        builderDetalhes = new AlertDialog.Builder(context);
        viewDetalhes = ((Activity) context).getLayoutInflater().inflate(R.layout.chat_perfil_detalhes, null);
        imgPerfil = (ImageView) viewDetalhes.findViewById(R.id.imgPerfil);
        prgBarra = (ProgressBar) viewDetalhes.findViewById(R.id.prgBarra);
        lblNome = (TextView) viewDetalhes.findViewById(R.id.lblNomeDetalhe);
        lblIdiomaNivel = (TextView) viewDetalhes.findViewById(R.id.lblIdiomaNivel);
        lblStatus = (TextView) viewDetalhes.findViewById(R.id.lblStatus);
        btnAbrirChat = (Button) viewDetalhes.findViewById(R.id.btnAbrirChat);

        Utils.loadImageInBackground(context, contato.getUrlProfilePicture(), imgPerfil, prgBarra);
        lblNome.setText(contato.getFirstName());
        lblIdiomaNivel.setText(Utils.getDescricaoIdioma(contato.getIdioma()) + " - " + Utils.getDescricaoFluencia(contato.getFluencia()));
        lblStatus.setText(contato.getStatus());

        /*
        btnAbrirChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario destinatario = new Usuario();
                destinatario.setUserID(contato.getUserID());
                destinatario.setFullName(contato.getFullName());

                Bundle bdlContatos = new Bundle();
                LinkedList<Usuario> contatos = new LinkedList<Usuario>();
                contatos.add(0, destinatario);
                bdlContatos.putSerializable("contatos", contatos);

                Intent i = new Intent(v.getContext(), ChatMensagensActivity.class);
                i.putExtra("contatos", bdlContatos);
                i.putExtra("especifico", 1);
                v.getContext().startActivity(i);
            }
        });
        */

        builderDetalhes.setView(viewDetalhes);
        detalhes = builderDetalhes.create();
        detalhes.show();
    }
}
