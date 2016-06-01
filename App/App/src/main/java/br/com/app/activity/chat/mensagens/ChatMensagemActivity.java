package br.com.app.activity.chat.mensagens;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import br.com.app.activity.R;
import br.com.app.adapter.MensagensAdapter;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.business.chat.mensagens.Mensagem;

/**
 * Created by Jefferson on 30/05/2016.
 */
public class ChatMensagemActivity extends Activity {

    private List<Mensagem> mensagens;
    private ArrayList<Contato> listaContatos;
    private boolean isGrupo = false;
    private String titulo;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private EditText txtResposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_mensagens);

        mensagens = (ArrayList) getIntent().getBundleExtra("mensagens").get("mensagens");
        listaContatos = (ArrayList) getIntent().getBundleExtra("contatos").get("contatos");

        if (getIntent().getStringExtra("nomeGrupo") != null) {
            isGrupo = true;
            titulo = getIntent().getStringExtra("nomeGrupo");
        }
        else {
            titulo = listaContatos.get(0).getUserName();
        }

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(titulo);

        carregaComponentes();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void carregaComponentes() {

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

        carregarDados();
    }

    private void carregarDados() {

        if(mensagens == null || mensagens.isEmpty()){
            return;
        }

        adapter = new MensagensAdapter(this, mensagens);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);

        for (Mensagem m : mensagens) {
            m.setLido(true);
        }
    }

    public void enviar(View view) {

    }
}
