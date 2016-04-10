package br.com.app.activity.contatos;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import br.com.app.activity.R;
import br.com.app.api.Contato;
import br.com.app.api.Facebook;
import br.com.app.telas.contatos.ContatosGridAdapter;
import br.com.app.telas.contatos.Item;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class ContatosActivity extends Activity {

    private GridView grdResultado = null;
    private ContatosGridAdapter objCustomGridAdapter = null;
    private LinkedList<String> listaUsuarios = null;
    private ArrayList<Item> listaItemContatos = null;
    private static HashMap<String, Contato> listaPerfis = null;
    private Bitmap imgPerfilIndisponivel = null;
    private String lblNomeIndisponivel = "Usuário";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contatos);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        listaUsuarios = (LinkedList) getIntent().getExtras().get("listaUsuarios");
        grdResultado = (GridView) findViewById(R.id.grdResultado);

        listaItemContatos = new ArrayList<Item>();
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

        Facebook objFacebook = new Facebook(this);
        listaPerfis = objFacebook.getProfiles(listaUsuarios);

        String nomeUsuario = "";
        Bitmap imgUsuario = null;

        for(Contato contato : listaPerfis.values()){
            nomeUsuario = contato.getUserName().trim().equals("") ? lblNomeIndisponivel : contato.getUserName();
            imgUsuario = contato.getProfilePicture() == null ? imgPerfilIndisponivel : contato.getProfilePicture();
            listaItemContatos.add(new Item(imgUsuario, nomeUsuario));
        }

        objCustomGridAdapter = new ContatosGridAdapter(this, R.layout.contatos_grid, listaItemContatos);
        grdResultado.setAdapter(objCustomGridAdapter);
    }
}