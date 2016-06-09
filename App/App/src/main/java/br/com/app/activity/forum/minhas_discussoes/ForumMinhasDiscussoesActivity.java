package br.com.app.activity.forum.minhas_discussoes;

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

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.adapter.DiscussaoCardViewAdapter;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.business.forum.discussao.Discussao;
import br.com.app.business.forum.discussao.DiscussaoDAO;
import br.com.app.enums.EnmTelas;
import br.com.app.utils.Utils;

/**
 * Created by Jefferson on 19/05/2016.
 */
public class ForumMinhasDiscussoesActivity extends Activity {

    private DiscussaoDAO objDiscussaoDAO;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressBar pgbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_minhas_discussoes);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        objDiscussaoDAO = new DiscussaoDAO();

        pgbLoading = (ProgressBar) findViewById(R.id.pgbLoading);

        carregar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void carregar(){

        final Context context = this;
        final Handler hCarregar = new Handler();

        pgbLoading.setVisibility(View.VISIBLE);
        pgbLoading.bringToFront();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final LinkedList<Discussao> listaDiscussoes = objDiscussaoDAO.pesquisarUsuario();

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
                            carregar();
                            return;
                        }
                    });
                }else {
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

    public void chamarNovaDiscussao(View view) {
        Utils.chamarActivity((Activity) view.getContext(), EnmTelas.FORUM_NOVA_DISCUSSAO);
    }
}
