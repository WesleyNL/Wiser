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
import br.com.app.adapter.FrameImagemPerfil;
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
                FrameImagemPerfil.mostrarDetalhes(ChatResultadosActivity.this, listaUsuarios.get(position));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void carregar(){
        listaUsuarios = Facebook.getProfiles(listaUsuarios);

        objCustomGridAdapter = new ContatosGridAdapter(this, R.layout.chat_resultados_grid, listaUsuarios);
        grdResultado.setAdapter(objCustomGridAdapter);
    }
}