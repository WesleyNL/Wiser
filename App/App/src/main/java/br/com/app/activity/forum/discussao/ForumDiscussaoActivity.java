package br.com.app.activity.forum.discussao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.adapter.DiscussaoRespostaAdapter;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.business.forum.discussao.Discussao;
import br.com.app.business.forum.discussao.DiscussaoDAO;
import br.com.app.business.forum.discussao.Resposta;
import br.com.app.utils.Utils;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.EditText;

import java.util.LinkedList;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class ForumDiscussaoActivity extends Activity {

    private Discussao objDiscussao;
    private DiscussaoDAO objDiscussaoDAO;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private TextView lblIDDiscussao;
    private ImageView imgPerfil;
    private TextView lblTituloDiscussao;
    private TextView lblDescricaoDiscussao;
    private TextView lblAutor;
    private TextView lblDataHora;
    private TextView lblRespostas;

    private EditText txtResposta;

    private ProgressBar pgbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_discussao);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        objDiscussao = (Discussao) getIntent().getBundleExtra("discussoes").get("discussao");
        objDiscussaoDAO = new DiscussaoDAO();

        carregarComponentes();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void carregarComponentes() {
        lblIDDiscussao = (TextView) findViewById(R.id.lblIDDiscussao);
        imgPerfil = (ImageView) findViewById(R.id.imgPerfil);
        lblTituloDiscussao = (TextView) findViewById(R.id.lblTituloDiscussao);
        lblDescricaoDiscussao = (TextView) findViewById(R.id.lblDescricaoDiscussao);
        lblAutor = (TextView) findViewById(R.id.lblAutor);
        lblDataHora = (TextView) findViewById(R.id.lblDataHora);
        lblRespostas = (TextView) findViewById(R.id.lblRespostas);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        txtResposta = (EditText) findViewById(R.id.txtResposta);

        final TextView lblContResposta = (TextView) super.findViewById(R.id.lblContResposta);

        TextWatcher textWatcher = new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lblContResposta.setText(String.valueOf(s.length()) + " / 250");
            }
        };
        txtResposta.addTextChangedListener(textWatcher);

        pgbLoading = (ProgressBar) findViewById(R.id.pgbLoading);

        carregarDados();
    }

    private void carregarDados() {
        lblIDDiscussao.setText("#" + objDiscussao.getIdDiscussao());
        lblTituloDiscussao.setText(objDiscussao.getTitulo());
        lblDescricaoDiscussao.setText(objDiscussao.getDescricao());
        lblAutor.setText(objDiscussao.getContato().getFirstName());
        Utils.carregarImagem(this, objDiscussao.getContato().getProfilePictureURL(), imgPerfil);
        lblDataHora.setText(Utils.formatDate(objDiscussao.getDataHora(), "dd/MM/yyyy HH:mm:ss"));
        lblRespostas.setText(getString(objDiscussao.getContRespostas() == 1 ? R.string.resposta : R.string.respostas, objDiscussao.getContRespostas()));

        if(objDiscussao.getListaRespostas() == null || objDiscussao.getListaRespostas().isEmpty()){
            return;
        }

        adapter = new DiscussaoRespostaAdapter(this, R.layout.forum_discussao_resposta, objDiscussao.getListaRespostas());
        recyclerView.setAdapter(adapter);
    }

    public void enviar(View view){

        if(txtResposta.getText().toString().trim().isEmpty()){
            return;
        }

        pgbLoading.setVisibility(View.VISIBLE);
        pgbLoading.bringToFront();

        final Handler hCarregar = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Resposta objResposta = new Resposta();

                Contato objContato = new Contato();
                objContato.setUserID(Sistema.USER_ID);
                objResposta.setContato(objContato);

                objResposta.setIdDiscussao(objDiscussao.getIdDiscussao());
                objResposta.setResposta(txtResposta.getText().toString());

                objDiscussaoDAO.responder(objResposta);
                recarregar();

                hCarregar.post(new Runnable() {
                    @Override
                    public void run() {
                        txtResposta.setText("");
                        pgbLoading.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }

    private void recarregar(){

        objDiscussaoDAO.setBuscaEspecifica(Discussao.BUSCA_DISCUSSAO_ID);
        objDiscussaoDAO.setIdDiscussao(objDiscussao.getIdDiscussao());

        Contato objContato = new Contato();
        objContato.setUserID(Sistema.USER_ID);
        objDiscussaoDAO.setContato(objContato);

        LinkedList<Discussao> listaDiscussoes = objDiscussaoDAO.pesquisarEspecifico();

        if(listaDiscussoes != null && !listaDiscussoes.isEmpty()) {
            objDiscussao = listaDiscussoes.get(0);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("discussao", objDiscussao);

        finish();

        Intent i = new Intent(this, ForumDiscussaoActivity.class);
        i.putExtra("discussoes", bundle);
        startActivity(i);
    }
}
