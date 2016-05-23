package br.com.app.activity.chat.resultados;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.LinkedList;

import br.com.app.activity.R;
import br.com.app.adapter.ContatosGridAdapter;
import br.com.app.business.app.facebook.Facebook;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.utils.Utils;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class ChatResultadosActivity extends Activity {

    private GridView grdResultado = null;
    private ContatosGridAdapter objCustomGridAdapter = null;
    private LinkedList<Contato> listaUsuarios = null;
    private AlertDialog detalhes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_resultados);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        listaUsuarios = new LinkedList<Contato>((ArrayList<Contato>) getIntent().getBundleExtra("listaUsuarios").get("listaUsuarios"));
        grdResultado = (GridView) findViewById(R.id.grdResultado);

        carregar();

        grdResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarDetalhes(listaUsuarios.get(position));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void mostrarDetalhes(final Contato contato){
        AlertDialog.Builder builderDetalhes = new AlertDialog.Builder(this);

        View viewDetalhes = getLayoutInflater().inflate(R.layout.chat_perfil_detalhes, null);

        ImageView imgPerfil = (ImageView) viewDetalhes.findViewById(R.id.imgPerfil);
        Utils.carregarImagem(this, contato.getProfilePictureURL(), imgPerfil);

        TextView lblNome = (TextView) viewDetalhes.findViewById(R.id.lblNomeDetalhe);
        lblNome.setText(contato.getFirstName());

        TextView lblIdiomaNivel = (TextView) viewDetalhes.findViewById(R.id.lblIdiomaNivel);
        lblIdiomaNivel.setText(Utils.hashIdiomas.get(contato.getIdioma()) + " - " + Utils.hashFluencias.get(contato.getNivelFluencia()));

        TextView lblStatus = (TextView) viewDetalhes.findViewById(R.id.lblStatus);
        lblStatus.setText(contato.getStatus());

        Button btnAbrirPerfil = (Button) viewDetalhes.findViewById(R.id.btnAbrirPerfil);
        btnAbrirPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Facebook.abrirPerfil(ChatResultadosActivity.this, contato.getUserID());
            }
        });

        builderDetalhes.setView(viewDetalhes);

        detalhes = builderDetalhes.create();
        detalhes.show();
    }

    private void carregar(){
        listaUsuarios = Facebook.getProfiles(listaUsuarios);

        objCustomGridAdapter = new ContatosGridAdapter(this, R.layout.chat_resultados_grid, listaUsuarios);
        grdResultado.setAdapter(objCustomGridAdapter);
    }
}