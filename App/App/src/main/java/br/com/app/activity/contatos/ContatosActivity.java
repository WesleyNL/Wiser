package br.com.app.activity.contatos;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.util.LinkedList;

import br.com.app.activity.R;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class ContatosActivity extends Activity {

    private GridView grdResultado = null;

    private LinkedList<String> listaUsuarios = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contatos);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        listaUsuarios = (LinkedList) getIntent().getExtras().get("listaUsuarios");
        grdResultado = (GridView) findViewById(R.id.grdResultado);

        carregar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void carregar(){

       //TODO Pegar resultado Jefferson
    }
}
