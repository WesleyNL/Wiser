package br.com.wiser.activity.forum.minhas_discussoes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.LinkedList;

import br.com.wiser.Sistema;
import br.com.wiser.activity.R;
import br.com.wiser.activity.forum.DiscussaoCardViewAdapter;
import br.com.wiser.business.forum.discussao.Discussao;
import br.com.wiser.business.forum.discussao.DiscussaoDAO;
import br.com.wiser.enums.Activities;
import br.com.wiser.utils.Utils;

/**
 * Created by Jefferson on 19/05/2016.
 */
public class ForumMinhasDiscussoesActivity extends Activity {

    private DiscussaoDAO objDiscussao;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressBar pgbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_minhas_discussoes);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        objDiscussao = new DiscussaoDAO();

        pgbLoading = (ProgressBar) findViewById(R.id.pgbLoading);

        carregarDiscussoes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarDiscussoes();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void carregarDiscussoes(){

        final Context context = this;
        final Handler hCarregar = new Handler();

        pgbLoading.setVisibility(View.VISIBLE);
        pgbLoading.bringToFront();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final LinkedList<DiscussaoDAO> listaDiscussoes = objDiscussao.carregarMinhasDiscussoes(Sistema.getUsuario(context));

                hCarregar.post(new Runnable() {
                    @Override
                    public void run() {
                        if(listaDiscussoes == null || listaDiscussoes.isEmpty()){
                            Toast.makeText(context, getString(R.string.erro_usuario_sem_discussao), Toast.LENGTH_LONG);
                            return;
                        }

                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        recyclerView.setVisibility(View.INVISIBLE);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));

                        adapter = new DiscussaoCardViewAdapter(context, listaDiscussoes);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setVisibility(View.VISIBLE);

                        pgbLoading.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }

    public void chamarNovaDiscussao(View view) {
        Utils.chamarActivity((Activity) view.getContext(), Activities.FORUM_NOVA_DISCUSSAO);
    }
}