package br.com.app.activity.forum.pesquisa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import br.com.app.activity.R;
import br.com.app.activity.chat.resultados.ChatResultadosActivity;
import br.com.app.activity.forum.discussao.ForumDiscussaoActivity;
import br.com.app.adapter.DiscussaoCardViewAdapter;
import br.com.app.business.app.facebook.Facebook;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.business.forum.discussao.Discussao;
import br.com.app.business.forum.discussao.Resposta;

import android.widget.TextView;
import android.widget.EditText;

import java.util.Date;
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
        d.setContato(Facebook.getProfile("4"));
        d.setIDDiscussao(1256);
        d.setTituloDiscussao("Escolas de Inglês");
        d.setDescricaoDiscussao("Alguém poderia me recomendar algumas escolas de inglês?");
        d.setContRespostas(2);
        d.setDataHora(new Date());

        LinkedList<Resposta> respostas = new LinkedList<Resposta>();
        Resposta r = new Resposta();
        r.setContato(Facebook.getProfile("6"));
        r.setIDResposta(1);
        r.setDataHora(new Date());
        r.setResposta("Yazigi");
        respostas.add(0, r);

        r = new Resposta();
        r.setContato(Facebook.getProfile("4"));
        r.setIDResposta(2);
        r.setDataHora(new Date());
        r.setResposta("Mais alguma?");
        respostas.add(1, r);

        d.setRespostas(respostas);

        discussaoList.add(0, d);

        d = new Discussao();
        d.setContato(Facebook.getProfile("6"));
        d.setIDDiscussao(1257);
        d.setTituloDiscussao("Russo");
        d.setDescricaoDiscussao("Alguém fala Russo?");
        d.setContRespostas(1);
        d.setDataHora(new Date());

        respostas = new LinkedList<Resposta>();
        r = new Resposta();
        r.setContato(Facebook.getProfile("6"));
        r.setIDResposta(1);
        r.setDataHora(new Date());
        r.setResposta("YA govoryu");
        respostas.add(0, r);

        d.setRespostas(respostas);

        discussaoList.add(1, d);
        //

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DiscussaoCardViewAdapter(this, discussaoList);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
