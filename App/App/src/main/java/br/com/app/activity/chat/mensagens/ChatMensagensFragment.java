package br.com.app.activity.chat.mensagens;

import android.hardware.camera2.params.Face;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import br.com.app.activity.R;
import br.com.app.adapter.MensagensListAdapter;
import br.com.app.business.app.facebook.Facebook;
import br.com.app.business.chat.mensagens.Mensagem;
import br.com.app.business.chat.mensagens.MensagensListItem;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class ChatMensagensFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinkedList<MensagensListItem> mensagens;

    public static ChatMensagensFragment newInstance() {
        return new ChatMensagensFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mensagens, container, false);

        carregarComponentes(view);

        return view;
    }

    private void carregarComponentes(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mensagens = new LinkedList<MensagensListItem>();

        // Apenas para Testes
        MensagensListItem m = new MensagensListItem();
        m.setContato(Facebook.getProfile("4"));
        m.setDataHora(new Date());

        LinkedList<Mensagem> mensa = new LinkedList<Mensagem>();

        Mensagem m1 = new Mensagem();
        m1.setID(1);
        m1.setUserID("4");
        m1.setDataHora(new Date());
        m1.setMensagem("Oi");
        mensa.add(0, m1);

        m1 = new Mensagem();
        m1.setID(2);
        m1.setUserID("1262642377098040");
        m1.setDataHora(new Date());
        m1.setMensagem("Blz?");
        mensa.add(1, m1);

        m1 = new Mensagem();
        m1.setID(3);
        m1.setUserID("4");
        m1.setDataHora(new Date());
        m1.setMensagem("Estou bem, e você?");
        mensa.add(2, m1);

        m1 = new Mensagem();
        m1.setID(4);
        m1.setUserID("1262642377098040");
        m1.setDataHora(new Date());
        m1.setMensagem("Estou bem.");
        mensa.add(3, m1);

        m1 = new Mensagem();
        m1.setID(5);
        m1.setUserID("4");
        m1.setDataHora(new Date());
        m1.setMensagem("Que bom. \n\n\n\n ahsushuahsuahsuahsuahsuhasuhaushaushaushuahsuahsuahsuahsuhaushaus ahsushuahsuahsuahsuahsuhasuhaushaushaushuahsuahsuahsuahsuhaushaus");
        mensa.add(4, m1);

        m1 = new Mensagem();
        m1.setID(6);
        m1.setUserID("1262642377098040");
        m1.setDataHora(new Date());
        m1.setMensagem("Então cara");
        mensa.add(5, m1);

        m1 = new Mensagem();
        m1.setID(7);
        m1.setUserID("1262642377098040");
        m1.setDataHora(new Date());
        m1.setMensagem("Como vai aí em Palo Alto? \n ahsushuahsuahsuahsuahsuhasuhaushaushaushuahsuahsuahsuahsuhaushaus");
        mensa.add(6, m1);

        m1 = new Mensagem();
        m1.setID(8);
        m1.setUserID("4");
        m1.setDataHora(new Date());
        m1.setMensagem("Vai tudo bem");
        mensa.add(7, m1);

        m1 = new Mensagem();
        m1.setID(9);
        m1.setUserID("4");
        m1.setDataHora(new Date());
        m1.setMensagem(";D");
        mensa.add(8, m1);

        m.setMensagens(mensa);
        mensagens.add(0, m);

        // Outro usuário
        m = new MensagensListItem();
        m.setContato(Facebook.getProfile("6"));
        m.setDataHora(new Date());

        mensa = new LinkedList<Mensagem>();

        m1 = new Mensagem();
        m1.setID(1);
        m1.setUserID("6");
        m1.setDataHora(new Date());
        m1.setMensagem("Oi");
        mensa.add(0, m1);

        m.setMensagens(mensa);
        mensagens.add(1, m);
        //

        adapter = new MensagensListAdapter(view.getContext(), mensagens);
        recyclerView.setAdapter(adapter);
    }
}
