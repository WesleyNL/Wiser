package br.com.app.activity.forum.pesquisa;

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

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.adapter.DiscussaoCardViewAdapter;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.business.forum.discussao.Discussao;
import br.com.app.business.forum.discussao.DiscussaoDAO;
import br.com.app.utils.Utils;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class ForumPesquisaActivity extends Activity {

    private EditText txtDiscussao;
    private TextView lblResultados;
    private TextView lblContResultados;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private ProgressBar pgbLoading;

    private DiscussaoDAO objDiscussaoDAO;

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

        pgbLoading = (ProgressBar)findViewById(R.id.pgbLoading);

        objDiscussaoDAO = new DiscussaoDAO();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void procurarDiscussao(View view) {

        pgbLoading = (ProgressBar)findViewById(R.id.pgbLoading);
        pgbLoading.setVisibility(View.VISIBLE);
        pgbLoading.bringToFront();

        String procurar = txtDiscussao.getText().toString().trim();

        if(Utils.soNumeros(procurar)){
            objDiscussaoDAO.setIdDiscussao(Long.parseLong(procurar));
            objDiscussaoDAO.setBuscaEspecifica(Discussao.BUSCA_DISCUSSAO_ID);
        }else {
            objDiscussaoDAO.setBuscaEspecifica(Discussao.BUSCA_DISCUSSAO_TITULO_DESCRICAO);
        }

        Contato objContato = new Contato();
        objContato.setUserID(Sistema.USER_ID);
        objDiscussaoDAO.setContato(objContato);

        objDiscussaoDAO.setTitulo(procurar);
        objDiscussaoDAO.setDescricao(procurar);

        final Context context = this;
        final Handler hRecycleView = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final LinkedList<Discussao> listaDiscussoes = objDiscussaoDAO.pesquisarEspecifico();

                hRecycleView.post(new Runnable() {
                    @Override
                    public void run() {
                        if(listaDiscussoes == null || listaDiscussoes.isEmpty()){
                            Toast.makeText(context, getString(R.string.erro_discussao_nao_encontrada), Toast.LENGTH_LONG).show();
                            pgbLoading.setVisibility(View.INVISIBLE);
                            return;
                        }

                        long cont = listaDiscussoes.size();

                        lblResultados.setText(getString(R.string.resultados_para, txtDiscussao.getText()));
                        lblContResultados.setText(getString(R.string.cont_discussoes, cont) + " " +
                                (cont == 1 ? getString(R.string.discussao_encontrada) :
                                        getString(R.string.discussoes_encontradas)));

                        lblResultados.setVisibility(View.VISIBLE);
                        lblContResultados.setVisibility(View.VISIBLE);

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

    public void excluir(final Long idDiscussao){

        final AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        AlertDialog.Builder confirmar = new AlertDialog.Builder(this);
        confirmar.setTitle(getString(R.string.confirmar));
        confirmar.setMessage(getString(R.string.confirmar_excluir_discussao));

        confirmar.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Contato objContato = new Contato();
                objContato.setUserID(Sistema.USER_ID);
                objDiscussaoDAO.setContato(objContato);

                objDiscussaoDAO.setIdDiscussao(idDiscussao);

                if (objDiscussaoDAO.excluir()) {
                    dialogo.setTitle(getString(R.string.sucesso));
                    dialogo.setMessage(R.string.sucesso_discussao_excluida);
                    dialogo.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            procurarDiscussao(null);
                            return;
                        }
                    });
                } else {
                    dialogo.setTitle(getString(R.string.erro));
                    dialogo.setMessage(getString(R.string.erro_excluir_discussao));
                    dialogo.setNeutralButton(getString(R.string.ok), null);
                }

                dialogo.show();
            }
        });
        confirmar.setNegativeButton(getString(R.string.nao), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                return;
            }
        });

        confirmar.show();
    }
}
