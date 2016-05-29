package br.com.app.activity.forum.principal;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.LinkedList;

import br.com.app.activity.R;
import br.com.app.adapter.DiscussaoCardViewAdapter;
import br.com.app.business.forum.discussao.Discussao;
import br.com.app.business.forum.discussao.DiscussaoDAO;
import br.com.app.enums.EnmTelas;
import br.com.app.utils.Utils;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class ForumPrincipalFragment extends Fragment {

    private Button btnNovaDiscussao;
    private Button btnProcurarDiscussao;
    private Button btnAtualizarDiscussoes;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private ProgressBar pgbLoading;

    private DiscussaoDAO objDiscussaoDAO = null;

    public static ForumPrincipalFragment newInstance() {
        return new ForumPrincipalFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_principal, container, false);
        initComponentes(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        initComponentes(this.getView());
    }

    private void initComponentes(View view) {
        btnNovaDiscussao = (Button) view.findViewById(R.id.btnNovaDiscussao);
        btnProcurarDiscussao = (Button) view.findViewById(R.id.btnProcurarDiscussao);
        btnAtualizarDiscussoes = (Button) view.findViewById(R.id.btnAtualizarDiscussoes);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        btnNovaDiscussao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamarNovaDiscussao(v);
            }
        });
        btnProcurarDiscussao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamarProcurarDiscussao(v);
            }
        });
        btnAtualizarDiscussoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarDiscussoes(v);
            }
        });

        pgbLoading = (ProgressBar) view.findViewById(R.id.pgbLoading);

        objDiscussaoDAO = new DiscussaoDAO();

        carregarDados(view);
    }

    private void carregarDados(View view) {

        pgbLoading.setVisibility(View.VISIBLE);
        pgbLoading.bringToFront();

        final Handler hCarregar = new Handler();
        final Context context = this.getContext();

        new Thread(new Runnable() {
            @Override
            public void run() {
                LinkedList<Discussao> listaDiscussoes = objDiscussaoDAO.carregar();
                tratarLoading(hCarregar, context, listaDiscussoes);
            }
        }).start();
    }

    private void tratarLoading(Handler hCarregar, final Context context, final LinkedList<Discussao> listaDiscussoes){
        hCarregar.post(new Runnable() {
            @Override
            public void run() {

                if (listaDiscussoes != null || !listaDiscussoes.isEmpty()) {
                    adapter = new DiscussaoCardViewAdapter(context, listaDiscussoes);
                    recyclerView.setAdapter(adapter);
                }

                pgbLoading.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void chamarNovaDiscussao(View view) {
        Utils.chamarActivity((Activity) view.getContext(), EnmTelas.FORUM_NOVA_DISCUSSAO);
    }

    public void chamarProcurarDiscussao(View view) {
        Utils.chamarActivity((Activity) view.getContext(), EnmTelas.FORUM_PESQUISA);
    }

    public void atualizarDiscussoes(View view) {
        carregarDados(view);
    }
}
