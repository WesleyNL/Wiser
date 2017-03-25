package br.com.wiser.activity.chat.conversas;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.LinkedList;

import br.com.wiser.activity.R;
import br.com.wiser.business.chat.conversas.Conversas;
import br.com.wiser.business.chat.conversas.ConversasDAO;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class ChatConversasFragment extends Fragment {

    private ProgressBar pgbLoading;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private ConversasDAO objMensagens;

    public static ChatConversasFragment newInstance() {
        return new ChatConversasFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mensagens, container, false);

        objMensagens = new ConversasDAO();
        carregarComponentes(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        carregarComponentes(this.getView());
    }

    private void carregarComponentes(View view) {

        objMensagens = new ConversasDAO();

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
                    tratarLoading(hCarregar, context, objMensagens.carregarGeral());

                    try {
                        Thread.sleep(3000);
                    }catch (Exception e){
                        continue;
                    }
                }
            }
        }).start();
    }

    private void tratarLoading(Handler hCarregar, final Context context, final LinkedList<ConversasDAO> listaConversas) {
        hCarregar.post(new Runnable() {
            @Override
            public void run() {

                if (listaConversas != null) {
                    if (!listaConversas.isEmpty()) {
                        adapter = new ChatConversasAdapter(context, listaConversas);
                        recyclerView.setAdapter(adapter);
                    }
                }

                pgbLoading.setVisibility(View.INVISIBLE);
            }
        });
    }
}
