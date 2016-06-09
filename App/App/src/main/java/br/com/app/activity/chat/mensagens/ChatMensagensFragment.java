package br.com.app.activity.chat.mensagens;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.LinkedList;

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.adapter.MensagensListAdapter;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.business.chat.mensagens.MensagemDAO;
import br.com.app.business.chat.mensagens.MensagemItem;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class ChatMensagensFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinkedList<MensagemItem> mensagens;

    private MensagemDAO objMensagemDAO;

    private ProgressBar pgbLoading;

    public static ChatMensagensFragment newInstance() {
        return new ChatMensagensFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mensagens, container, false);

        carregarComponentes(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        carregarComponentes(this.getView());
    }

    private void carregarComponentes(View view) {

        objMensagemDAO = new MensagemDAO();

        pgbLoading = (ProgressBar) view.findViewById(R.id.pgbLoadingMsg);
        pgbLoading.setVisibility(View.VISIBLE);
        pgbLoading.bringToFront();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        final Handler hCarregar = new Handler();
        final Context context = this.getContext();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    Contato objContato = new Contato();
                    objContato.setUserID(Sistema.USER_ID);

                    objMensagemDAO = new MensagemDAO();
                    objMensagemDAO.setContato(objContato);

                    mensagens = objMensagemDAO.carregarGeral();
                    tratarLoading(hCarregar, context, mensagens);

                    try {
                        Thread.sleep(3000);
                    }catch (Exception e){
                        continue;
                    }
                }
            }
        }).start();
    }

    private void tratarLoading(Handler hCarregar, final Context context, final LinkedList<MensagemItem> listaMensagemItem){
        hCarregar.post(new Runnable() {
            @Override
            public void run() {

                if (listaMensagemItem != null) {
                    if (!listaMensagemItem.isEmpty()) {
                        adapter = new MensagensListAdapter(context, mensagens);
                        recyclerView.setAdapter(adapter);
                    }
                }

                pgbLoading.setVisibility(View.INVISIBLE);
            }
        });
    }
}
