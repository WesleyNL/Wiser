package br.com.app.activity.contatos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.app.activity.R;
import br.com.app.api.Facebook;
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
    private Bitmap imgPerfilIndisponivel = null;
    private String lblNomeIndisponivel = "Usuário";
    private AlertDialog detalhes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contatos);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        listaUsuarios = (ArrayList<String>) getIntent().getExtras().get("listaUsuarios");
        grdResultado = (GridView) findViewById(R.id.grdResultado);

        listaItemContatos = new ArrayList<Contato>();
        imgPerfilIndisponivel = BitmapFactory.decodeResource(getResources(), R.drawable.com_facebook_profile_picture_blank_portrait);

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
                abrirPerfilFB(contato.getUserID());
            }
        });

        ImageView imgLinkPerfil = (ImageView) viewDetalhes.findViewById(R.id.imgLinkPerfil);
        imgLinkPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPerfilFB(contato.getUserID());
            }
        });

        builderDetalhes.setView(viewDetalhes);

        detalhes = builderDetalhes.create();
        detalhes.show();
    }

    public void abrirPerfilFB(String userId){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + userId));
            startActivity(intent);
        } catch(Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + userId)));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void carregar(){

        listaPerfis = Facebook.getProfiles(listaUsuarios);

        String nomeUsuario = "";
        Bitmap imgUsuario = null;

        for(Contato contato : listaPerfis.values()){
            nomeUsuario = contato.getUserName().trim().equals("") ? lblNomeIndisponivel : contato.getUserName();
            imgUsuario = contato.getProfilePicture() == null ? imgPerfilIndisponivel : contato.getProfilePicture();
            listaItemContatos.add(new Contato(imgUsuario, nomeUsuario));
        }

        objCustomGridAdapter = new ContatosGridAdapter(this, R.layout.contatos_grid, listaItemContatos);
        grdResultado.setAdapter(objCustomGridAdapter);
    }
}