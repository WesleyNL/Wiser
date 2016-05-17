package br.com.app.activity.forum.pesquisa;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import br.com.app.activity.R;
import br.com.app.adapter.CardViewAdapter;
import br.com.app.business.app.facebook.Facebook;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.business.forum.discussao.Discussao;

import android.widget.TextView;
import android.widget.EditText;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class ForumPesquisaActivity extends Activity {

    private EditText txtDiscussao;
    private TextView lblResultados;
    private TextView lblContResultados;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.forum_pesquisa);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        txtDiscussao = (EditText) findViewById(R.id.txtDiscussao);
        lblResultados = (TextView) findViewById(R.id.lblResultados);
        lblContResultados = (TextView) findViewById(R.id.lblContResultados);

        lblResultados.setVisibility(View.INVISIBLE);
        lblContResultados.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void procurarDiscussao(View view) {
        int cont = 2;

        lblResultados.setText(getString(R.string.resultados_para, txtDiscussao.getText()));
        lblContResultados.setText(getString(R.string.cont_discussoes, cont) + " " +
                (cont == 1 ? getString(R.string.discussao_encontrada) :
                getString(R.string.discussoes_encontradas)));

        lblResultados.setVisibility(View.VISIBLE);
        lblContResultados.setVisibility(View.VISIBLE);

        // Apenas como teste
        List<Discussao> discussaoList = new LinkedList<Discussao>();

        Discussao d = new Discussao();
        Contato c = new Contato();
        c.setUserID("4");
        c.setUserName(Facebook.getFirstName("4"));
        c.setProfilePicture(Facebook.getProfilePicture("4"));
        d.setContato(c);
        d.setIDDiscussao(1256);
        d.setTituloDiscussao("Escolas de Inglês");
        d.setDescricaoDiscussao("Alguém poderia me recomendar algumas escolas de inglês?");
        d.setContRespostas(2);

        discussaoList.add(0, d);

        d = new Discussao();
        c = new Contato();
        c.setUserID("1262642377098040");
        c.setUserName(Facebook.getFirstName("1262642377098040"));
        c.setProfilePicture(Facebook.getProfilePicture("1262642377098040"));
        d.setContato(c);
        d.setIDDiscussao(1257);
        d.setTituloDiscussao("Russo");
        d.setDescricaoDiscussao("Alguém fala Russo?");
        d.setContRespostas(1);

        discussaoList.add(1, d);
        //

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CardViewAdapter(this, discussaoList);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
