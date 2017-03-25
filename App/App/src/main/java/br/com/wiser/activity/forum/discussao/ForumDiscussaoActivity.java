package br.com.wiser.activity.forum.discussao;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.EditText;
import android.widget.Button;

import java.util.Date;

import br.com.wiser.Sistema;
import br.com.wiser.activity.R;
import br.com.wiser.activity.forum.DiscussaoRespostaAdapter;
import br.com.wiser.business.forum.discussao.DiscussaoDAO;
import br.com.wiser.business.forum.resposta.Resposta;
import br.com.wiser.utils.FuncoesData;
import br.com.wiser.utils.Utils;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class ForumDiscussaoActivity extends Activity {

    private DiscussaoDAO objDiscussao;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private TextView lblIDDiscussao;
    private ImageView imgPerfil;
    private ProgressBar prgBarra;
    private TextView lblTituloDiscussao;
    private TextView lblDescricaoDiscussao;
    private TextView lblAutor;
    private TextView lblDataHora;
    private TextView lblRespostas;
    private EditText txtResposta;
    private ProgressBar pgbLoading;
    private Button btnEnviarMensagem;
    private TextView lblContResposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_discussao);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        objDiscussao = (DiscussaoDAO) getIntent().getBundleExtra("discussoes").get("discussao");

        carregarComponentes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_discussoes, menu);
        return (true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itmCompartilhar:
                Utils.compartilharComoImagem(findViewById(R.id.frmDiscussao));
                break;
            default:
                onBackPressed();
        }

        return true;
    }

    private void carregarComponentes() {
        lblIDDiscussao = (TextView) findViewById(R.id.lblIDDiscussao);
        imgPerfil = (ImageView) findViewById(R.id.imgPerfil);
        prgBarra = (ProgressBar) findViewById(R.id.prgBarra);
        lblTituloDiscussao = (TextView) findViewById(R.id.lblTituloDiscussao);
        lblDescricaoDiscussao = (TextView) findViewById(R.id.lblDescricaoDiscussao);
        lblAutor = (TextView) findViewById(R.id.lblAutor);
        lblDataHora = (TextView) findViewById(R.id.lblDataHora);
        lblRespostas = (TextView) findViewById(R.id.lblRespostas);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        txtResposta = (EditText) findViewById(R.id.txtResposta);

        lblContResposta = (TextView) findViewById(R.id.lblContResposta);

        txtResposta.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lblContResposta.setText(String.valueOf(s.length()) + " / 250");
            }
        });

        pgbLoading = (ProgressBar) findViewById(R.id.pgbLoading);

        if (!objDiscussao.getDiscussaoAtiva()) {
            findViewById(R.id.include).setVisibility(View.INVISIBLE);
        }

        carregarDados();
    }

    private void carregarDados() {
        lblIDDiscussao.setText("#" + objDiscussao.getId());
        lblTituloDiscussao.setText(objDiscussao.getTitulo());
        lblDescricaoDiscussao.setText(objDiscussao.getDescricao());
        lblAutor.setText(objDiscussao.getUsuario().getFirstName());
        Utils.loadImageInBackground(this, objDiscussao.getUsuario().getUrlProfilePicture(), imgPerfil, prgBarra);
        lblDataHora.setText(FuncoesData.formatDate(objDiscussao.getDataHora(), FuncoesData.DDMMYYYY_HHMMSS));
        lblRespostas.setText(getString(objDiscussao.getListaRespostas().size() == 1 ? R.string.resposta : R.string.respostas, objDiscussao.getListaRespostas().size()));

        if(objDiscussao.getListaRespostas() == null || objDiscussao.getListaRespostas().isEmpty()){
            return;
        }

        adapter = new DiscussaoRespostaAdapter(this, objDiscussao.getListaRespostas());
        recyclerView.setAdapter(adapter);
    }

    public void enviar(View view){

        if (txtResposta.getText().toString().trim().isEmpty()) {
            return;
        }

        pgbLoading.setVisibility(View.VISIBLE);
        pgbLoading.bringToFront();

        final Handler hCarregar = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Resposta objResposta = new Resposta();

                objResposta.setId(0);
                objResposta.setUsuario(Sistema.getUsuario(ForumDiscussaoActivity.this));
                objResposta.setResposta(txtResposta.getText().toString());
                objResposta.setDataHora(new Date());

                objDiscussao.enviarResposta(Sistema.getUsuario(ForumDiscussaoActivity.this), objResposta);

                hCarregar.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();

                        txtResposta.setText("");
                        pgbLoading.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }
}
