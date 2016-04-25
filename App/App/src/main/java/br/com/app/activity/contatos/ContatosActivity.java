package br.com.app.activity.contatos;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.app.activity.R;
import br.com.app.api.facebook.Facebook;
import br.com.app.business.contatos.Contato;
import br.com.app.business.contatos.ContatosGridAdapter;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class ContatosActivity extends Activity {

    private GridView grdResultado = null;
    private ContatosGridAdapter objCustomGridAdapter = null;
    private ArrayList<String> listaUsuarios = null;
    private ArrayList<Contato> listaItemContatos = null;
    private static HashMap<String, Contato> listaPerfis = null;
    private AlertDialog detalhes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contatos);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        listaUsuarios = (ArrayList<String>) getIntent().getExtras().get("listaUsuarios");
        grdResultado = (GridView) findViewById(R.id.grdResultado);

        listaItemContatos = new ArrayList<Contato>();

        carregar();

        grdResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String userIdSelecionado = listaUsuarios.get(position);
                Contato objSelecionado = listaPerfis.get(userIdSelecionado);
                mostrarDetalhes(objSelecionado);
            }
        });
    }

    public void mostrarDetalhes(final Contato contato){
        AlertDialog.Builder builderDetalhes = new AlertDialog.Builder(this);

        View viewDetalhesTitulo = getLayoutInflater().inflate(R.layout.contatos_detalhes_titulo, null);

        TextView lblFecharDetalhes = (TextView) viewDetalhesTitulo.findViewById(R.id.lblFecharDetalhes);
        lblFecharDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detalhes.cancel();
            }
        });

        builderDetalhes.setCustomTitle(viewDetalhesTitulo);

        View viewDetalhes = getLayoutInflater().inflate(R.layout.contatos_detalhes, null);

        ImageView imgPerfil = (ImageView) viewDetalhes.findViewById(R.id.imgPerfil);
        imgPerfil.setImageBitmap(contato.getProfilePicture());

        TextView lblNome = (TextView) viewDetalhes.findViewById(R.id.lblNomeDetalhe);
        lblNome.setText(contato.getUserName());

        TextView lblLinkPerfil = (TextView) viewDetalhes.findViewById(R.id.lblLinkPerfil);
        lblLinkPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Facebook.abrirPerfil(ContatosActivity.this, contato.getUserID());
            }
        });

        ImageView imgLinkPerfil = (ImageView) viewDetalhes.findViewById(R.id.imgLinkPerfil);
        imgLinkPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Facebook.abrirPerfil(ContatosActivity.this, contato.getUserID());
            }
        });

        builderDetalhes.setView(viewDetalhes);

        detalhes = builderDetalhes.create();
        detalhes.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void carregar(){
        listaPerfis = Facebook.getProfiles(listaUsuarios);
        listaItemContatos.addAll(listaPerfis.values());

        objCustomGridAdapter = new ContatosGridAdapter(this, R.layout.contatos_grid, listaItemContatos);
        grdResultado.setAdapter(objCustomGridAdapter);
    }
}