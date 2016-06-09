package br.com.app.activity.chat.mensagens;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.adapter.MensagensAdapter;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.business.chat.mensagens.Mensagem;
import br.com.app.business.chat.mensagens.MensagemDAO;

/**
 * Created by Jefferson on 30/05/2016.
 */
public class ChatMensagemActivity extends Activity {

    private String titulo;
    private List<Mensagem> mensagens;
    private ArrayList<Contato> listaContatos;
    private boolean isGrupo = false;
    private boolean isEspecifico = false;
    private MensagemDAO objMensagemDAO = null;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private EditText txtResposta;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_mensagens);

        if(getIntent().hasExtra("mensagens")){
            mensagens = (ArrayList) getIntent().getBundleExtra("mensagens").get("mensagens");
        }

        listaContatos = (ArrayList) getIntent().getBundleExtra("contatos").get("contatos");

        if (getIntent().getStringExtra("nomeGrupo") != null) {
            isGrupo = true;
            titulo = getIntent().getStringExtra("nomeGrupo");
        }
        else {
            titulo = listaContatos.get(0).getUserName();
        }

        if(getIntent().hasExtra("especifico")){
            isEspecifico = true;
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
        btnEnviar = (Button) findViewById(R.id.btnEnviarResposta);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviar(v);
            }
        });

        final TextView lblContResposta = (TextView) super.findViewById(R.id.lblContResposta);

        TextWatcher textWatcher = new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lblContResposta.setText(String.valueOf(s.length()) + " / 250");
            }
        };
        txtResposta.addTextChangedListener(textWatcher);

        objMensagemDAO = new MensagemDAO();

        carregarDados();
    }

    private void carregarDados() {

        final Context context = this;
        final Handler hCarregar = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {

                    if (isEspecifico) {
                        objMensagemDAO.setContato(listaContatos.get(0));
                        mensagens = objMensagemDAO.carregarEspecifico();
                    }

                    if (mensagens == null || mensagens.isEmpty()) {
                        return;
                    }

                    hCarregar.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new MensagensAdapter(context, mensagens);
                            recyclerView.setAdapter(adapter);
                            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                        }
                    });

                    Mensagem objMensagem = new Mensagem();
                    objMensagem.setUserID(listaContatos.get(0).getUserID());
                    objMensagem.setIdMensagemItem(mensagens.get(0).getIdMensagemItem());
                    objMensagemDAO.atualizarLeitura(objMensagem);

                    isEspecifico = true;

                    try {
                        Thread.sleep(3000);
                    }catch (Exception e){
                        continue;
                    }
                }
            }
        }).start();
    }

    public void enviar(View view) {

        final Mensagem objMensagem = new Mensagem();
        objMensagem.setMensagem(txtResposta.getText().toString());

        if(objMensagem.getMensagem().isEmpty()){
            return;
        }

        objMensagem.setIdMensagemItem(mensagens == null || mensagens.isEmpty() ? 0 : mensagens.get(0).getIdMensagemItem());
        objMensagem.setUserID(Sistema.USER_ID);
        objMensagem.setDestinatario(listaContatos.get(0).getUserID());

        new Thread(new Runnable() {
            @Override
            public void run() {
                objMensagemDAO.enviar(objMensagem);
            }
        }).start();

        txtResposta.setText("");
    }
}
