package br.com.app.activity.forum.discussao;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;

import br.com.app.activity.R;
import br.com.app.adapter.DiscussaoCardViewAdapter;
import br.com.app.adapter.DiscussaoRespostaAdapter;
import br.com.app.business.forum.discussao.Discussao;
import br.com.app.utils.Utils;

import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class ForumDiscussaoActivity extends Activity {

    private Discussao discussao;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView lblIDDiscussao;
    private ImageView imgPerfil;
    private TextView lblTituloDiscussao;
    private TextView lblDescricaoDiscussao;
    private TextView lblAutor;
    private TextView lblDataHora;
    private TextView lblRespostas;

    private EditText txtResposta;
    private Button btnEnviarResposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_discussao);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        discussao = (Discussao) getIntent().getBundleExtra("discussoes").get("discussoes");

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
        btnEnviarResposta = (Button) findViewById(R.id.btnEnviarResposta);

        final TextView lblContResposta = (TextView) super.findViewById(R.id.lblContResposta);

        TextWatcher textWatcher = new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lblContResposta.setText(String.valueOf(s.length()) + " / 250");
            }
        };
        txtResposta.addTextChangedListener(textWatcher);

        carregarDados();
    }

    private void carregarDados() {
        lblIDDiscussao.setText("#" + discussao.getIDDiscussao());
        lblTituloDiscussao.setText(discussao.getTituloDiscussao());
        lblDescricaoDiscussao.setText(discussao.getDescricaoDiscussao());
        lblAutor.setText("Por: " + discussao.getContato().getFirstName());
        Utils.carregarImagem(this, discussao.getContato().getProfilePictureURL(), imgPerfil);
        lblDataHora.setText(Utils.formatDate(discussao.getDataHora(), "dd/MM/yyyy HH:mm:ss"));
        lblRespostas.setText(getString(discussao.getContRespostas() == 1 ? R.string.resposta : R.string.respostas,
                discussao.getContRespostas()));

        adapter = new DiscussaoRespostaAdapter(this, R.layout.forum_discussao_resposta, discussao.getRespostas());
        recyclerView.setAdapter(adapter);
    }
}
