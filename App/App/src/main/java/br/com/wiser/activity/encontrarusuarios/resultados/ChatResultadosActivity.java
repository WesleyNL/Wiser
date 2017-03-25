package br.com.wiser.activity.encontrarusuarios.resultados;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.LinkedList;

import br.com.wiser.activity.R;
import br.com.wiser.business.app.facebook.Facebook;
import br.com.wiser.business.app.usuario.Usuario;
import br.com.wiser.frames.FrameImagemPerfil;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class ChatResultadosActivity extends Activity {

    private GridView grdResultado = null;
    private ChatResultadosAdapter objCustomGridAdapter = null;
    private LinkedList<Usuario> listaUsuarios = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_resultados);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        listaUsuarios = new LinkedList<Usuario>((ArrayList<Usuario>) getIntent().getBundleExtra("listaUsuarios").get("listaUsuarios"));
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

        objCustomGridAdapter = new ChatResultadosAdapter(this, R.layout.chat_resultados_grid, listaUsuarios);
        grdResultado.setAdapter(objCustomGridAdapter);
    }
}