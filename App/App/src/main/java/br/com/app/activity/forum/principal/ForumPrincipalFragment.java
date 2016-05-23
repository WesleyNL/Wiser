package br.com.app.activity.forum.principal;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import br.com.app.activity.R;
import br.com.app.activity.forum.discussao.ForumDiscussaoActivity;
import br.com.app.adapter.DiscussaoCardViewAdapter;
import br.com.app.business.app.facebook.Facebook;
import br.com.app.business.forum.discussao.Discussao;
import br.com.app.business.forum.discussao.Resposta;
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

    public static ForumPrincipalFragment newInstance() {
        return new ForumPrincipalFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_principal, container, false);
        initComponentes(view);

        return view;
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

        carregarDados(view);
    }

    private void carregarDados(View view) {
        // Apenas como teste
        List<Discussao> discussaoList = new LinkedList<Discussao>();

        Discussao d = new Discussao();
        d.setContato(Facebook.getProfile("4"));
        d.setIDDiscussao(1256);
        d.setTituloDiscussao("Escolas de Inglês");
        d.setDescricaoDiscussao("Alguém poderia me recomendar algumas escolas de inglês?");
        d.setContRespostas(2);
        d.setDataHora(new Date());

        LinkedList<Resposta> respostas = new LinkedList<Resposta>();
        Resposta r = new Resposta();
        r.setContato(Facebook.getProfile("6"));
        r.setIDResposta(1);
        r.setDataHora(new Date());
        r.setResposta("Yazigi");
        respostas.add(0, r);

        r = new Resposta();
        r.setContato(Facebook.getProfile("4"));
        r.setIDResposta(2);
        r.setDataHora(new Date());
        r.setResposta("Mais alguma?");
        respostas.add(1, r);

        d.setRespostas(respostas);

        discussaoList.add(0, d);

        d = new Discussao();
        d.setContato(Facebook.getProfile("6"));
        d.setIDDiscussao(1257);
        d.setTituloDiscussao("Russo");
        d.setDescricaoDiscussao("Alguém fala Russo?");
        d.setContRespostas(1);
        d.setDataHora(new Date());

        respostas = new LinkedList<Resposta>();
        r = new Resposta();
        r.setContato(Facebook.getProfile("6"));
        r.setIDResposta(1);
        r.setDataHora(new Date());
        r.setResposta("YA govoryu");
        respostas.add(0, r);

        d.setRespostas(respostas);

        discussaoList.add(1, d);
        //

        adapter = new DiscussaoCardViewAdapter(view.getContext(), discussaoList);
        recyclerView.setAdapter(adapter);
    }

    public void chamarNovaDiscussao(View view) {
        Utils.chamarActivity((Activity) view.getContext(), EnmTelas.FORUM_NOVA_DISCUSSAO);
    }

    public void chamarProcurarDiscussao(View view) {
        Utils.chamarActivity((Activity) view.getContext(), EnmTelas.FORUM_PESQUISA);
    }

    public void atualizarDiscussoes(View view) {

    }
}
