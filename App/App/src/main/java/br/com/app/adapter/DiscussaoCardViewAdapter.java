package br.com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.activity.forum.discussao.ForumDiscussaoActivity;
import br.com.app.activity.forum.minhas_discussoes.ForumMinhasDiscussoesActivity;
import br.com.app.activity.forum.pesquisa.ForumPesquisaActivity;
import br.com.app.business.forum.discussao.Discussao;
import br.com.app.utils.FormatData;
import br.com.app.utils.Utils;
import android.widget.ProgressBar;
/**
 * Created by Jefferson on 16/05/2016.
 */
public class DiscussaoCardViewAdapter extends RecyclerView.Adapter<DiscussaoCardViewAdapter.ViewHolder> {

    private Context context;
    private static List<Discussao> listaDiscussoes;

    public DiscussaoCardViewAdapter(Context context, List<Discussao> listaDiscussoes) {
        this.context = context;
        this.listaDiscussoes = listaDiscussoes;
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
        Discussao objDiscussao = listaDiscussoes.get(position);

        Utils.loadImageInBackground(context, objDiscussao.getContato().getProfilePictureURL(), viewHolder.imgPerfil, viewHolder.prgBarra);
        viewHolder.lblIDDiscussao.setText("#" + objDiscussao.getIdDiscussao());
        viewHolder.lblAutorDiscussao.setText(objDiscussao.getContato().getFirstName());
        viewHolder.lblTituloDiscussao.setText(objDiscussao.getTitulo());
        viewHolder.lblDescricaoDiscussao.setText(objDiscussao.getDescricao());
        viewHolder.lblContRespostas.setText(
                context.getString(objDiscussao.getContRespostas() == 1 ? R.string.resposta : R.string.respostas,
                        objDiscussao.getContRespostas()));
        viewHolder.lblDataHora.setText(FormatData.formatDate(objDiscussao.getDataHora(), FormatData.DDMMYYYY_HHMMSS));

        if(!objDiscussao.getContato().getUserID().trim().equalsIgnoreCase(Sistema.USER_ID)){
            viewHolder.btnExcluir.setVisibility(View.INVISIBLE);
        }

        viewHolder.setPosicao(position);
    }

    @Override
    public int getItemCount() {
        return listaDiscussoes.size();
    }

    public Discussao getDiscussao(int posicao) {
        return this.listaDiscussoes.get(posicao);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int posicao;

        public TextView lblIDDiscussao;
        public ImageView imgPerfil;
        public ProgressBar prgBarra;
        public TextView lblAutorDiscussao;
        public TextView lblTituloDiscussao;
        public TextView lblDescricaoDiscussao;
        public TextView lblContRespostas;
        public TextView lblDataHora;
        public Button btnExcluir;
        public Button btnCompartilhar;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);

            lblIDDiscussao = (TextView) itemLayoutView.findViewById(R.id.lblIDDiscussao);
            imgPerfil = (ImageView) itemLayoutView.findViewById(R.id.imgPerfil);
            prgBarra = (ProgressBar) itemLayoutView.findViewById(R.id.prgBarra);
            lblAutorDiscussao = (TextView) itemLayoutView.findViewById(R.id.lblAutorDiscussao);
            lblTituloDiscussao = (TextView) itemLayoutView.findViewById(R.id.lblTituloDiscussao);
            lblDescricaoDiscussao = (TextView) itemLayoutView.findViewById(R.id.lblDescricaoDiscussao);
            lblContRespostas = (TextView) itemLayoutView.findViewById(R.id.lblContRespostas);
            lblDataHora = (TextView) itemLayoutView.findViewById(R.id.lblDataHora);
            btnExcluir = (Button) itemLayoutView.findViewById(R.id.btnExcluir);
            btnCompartilhar = (Button) itemLayoutView.findViewById(R.id.btnCompartilhar);

            View.OnClickListener btnExcluirListener = new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Context contextAtual = v.getContext();
                    long idDiscussao = listaDiscussoes.get(posicao).getIdDiscussao();

                    if(contextAtual instanceof ForumMinhasDiscussoesActivity) {
                        ((ForumMinhasDiscussoesActivity) v.getContext()).excluir(idDiscussao);
                    }
                    if(contextAtual instanceof ForumPesquisaActivity) {
                        ((ForumPesquisaActivity) v.getContext()).excluir(idDiscussao);
                    }
                }
            };

            btnExcluir.setOnClickListener(btnExcluirListener);

            imgPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FrameImagemPerfil.mostrarDetalhes(v.getContext(), listaDiscussoes.get(posicao).getContato());
                }
            });

            btnCompartilhar.setTag(itemLayoutView);

            View.OnClickListener btnCompartilharListener = new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Utils.compartilharEmImagem((CardView) v.getTag());
                }
            };

            btnCompartilhar.setOnClickListener(btnCompartilharListener);
        }

        @Override
        public void onClick(View view) {

            Bundle bundle = new Bundle();
            bundle.putSerializable("discussao", listaDiscussoes.get(posicao));

            Intent i = new Intent(view.getContext(), ForumDiscussaoActivity.class);
            i.putExtra("discussoes", bundle);
            view.getContext().startActivity(i);
        }

        public void setPosicao(int posicao) {
            this.posicao = posicao;
        }
    }
}
