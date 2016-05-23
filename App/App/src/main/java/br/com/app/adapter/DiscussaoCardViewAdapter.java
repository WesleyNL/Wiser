package br.com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.app.activity.R;
import br.com.app.activity.forum.discussao.ForumDiscussaoActivity;
import br.com.app.business.forum.discussao.Discussao;
import br.com.app.utils.Utils;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class DiscussaoCardViewAdapter extends RecyclerView.Adapter<DiscussaoCardViewAdapter.ViewHolder> {

    private Context context;
    private static List<Discussao> discussoes;

    public DiscussaoCardViewAdapter(Context context, List<Discussao> discussoes) {
        this.context = context;
        this.discussoes = discussoes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.forum_discussao_resultados, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Discussao discussao = discussoes.get(position);

        Utils.carregarImagem(context, discussao.getContato().getProfilePictureURL(), viewHolder.imgPerfil);
        viewHolder.lblIDDiscussao.setText("#" + discussao.getIDDiscussao());
        viewHolder.lblAutorDiscussao.setText(discussao.getContato().getFirstName());
        viewHolder.lblTituloDiscussao.setText(discussao.getTituloDiscussao());
        viewHolder.lblDescricaoDiscussao.setText(discussao.getDescricaoDiscussao());
        viewHolder.lblContRespostas.setText(
                context.getString(discussao.getContRespostas() == 1 ? R.string.resposta : R.string.respostas,
                        discussao.getContRespostas()));
        viewHolder.lblDataHora.setText(Utils.formatDate(discussao.getDataHora(), "dd/MM/yyyy HH:mm:ss"));

        viewHolder.setPosicao(position);
    }

    @Override
    public int getItemCount() {
        return discussoes.size();
    }

    public Discussao getDiscussao(int posicao) {
        return this.discussoes.get(posicao);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int posicao;

        public TextView lblIDDiscussao;
        public ImageView imgPerfil;
        public TextView lblAutorDiscussao;
        public TextView lblTituloDiscussao;
        public TextView lblDescricaoDiscussao;
        public TextView lblContRespostas;
        public TextView lblDataHora;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);

            lblIDDiscussao = (TextView) itemLayoutView.findViewById(R.id.lblIDDiscussao);
            imgPerfil = (ImageView) itemLayoutView.findViewById(R.id.imgPerfil);
            lblAutorDiscussao = (TextView) itemLayoutView.findViewById(R.id.lblAutorDiscussao);
            lblTituloDiscussao = (TextView) itemLayoutView.findViewById(R.id.lblTituloDiscussao);
            lblDescricaoDiscussao = (TextView) itemLayoutView.findViewById(R.id.lblDescricaoDiscussao);
            lblContRespostas = (TextView) itemLayoutView.findViewById(R.id.lblContRespostas);
            lblDataHora = (TextView) itemLayoutView.findViewById(R.id.lblDataHora);
        }

        @Override
        public void onClick(View view) {
            CardView cv = (CardView) view;

            Bundle bundle = new Bundle();
            bundle.putSerializable("discussoes", discussoes.get(posicao));

            Intent i = new Intent(view.getContext(), ForumDiscussaoActivity.class);
            i.putExtra("discussoes", bundle);
            view.getContext().startActivity(i);
        }

        public void setPosicao(int posicao) {
            this.posicao = posicao;
        }
    }
}
