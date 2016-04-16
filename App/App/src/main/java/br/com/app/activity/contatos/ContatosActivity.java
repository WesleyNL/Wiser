package br.com.app.activity.contatos;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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
//                Item objItem = (Item) grdResultado.getAdapter().getItem(position);
                String userIdSelecionado = listaUsuarios.get(position);
                Contato objSelecionado = listaPerfis.get(userIdSelecionado);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void carregar(){

        Facebook facebook = new Facebook(this);
        listaPerfis = facebook.getProfiles(listaUsuarios);

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