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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.com.app.activity.R;
import br.com.app.activity.chat.mensagens.ChatMensagemActivity;
import br.com.app.activity.forum.discussao.ForumDiscussaoActivity;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.business.chat.mensagens.MensagensListItem;
import br.com.app.business.forum.resposta.Resposta;
import br.com.app.utils.FormatData;
import br.com.app.utils.Utils;
import android.widget.ProgressBar;

/**
 * Created by Jefferson on 23/05/2016.
 */
public class MensagensListAdapter extends RecyclerView.Adapter<MensagensListAdapter.ViewHolder> {

    private Context context;
    private static LinkedList<MensagensListItem> mensagens = null;

    public MensagensListAdapter(Context context, LinkedList<MensagensListItem> mensagens) {
        this.context = context;
        this.mensagens = mensagens;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.chat_mensagens_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MensagensListItem m = mensagens.get(position);

        Utils.loadImageInBackground(context, m.getContato().getProfilePictureURL(), holder.imgPerfil, holder.prgBarra);
        holder.lblDataHora.setText(FormatData.formatDate(m.getDataHora(), FormatData.HHMM));
        holder.lblMensagens.setText(m.getMensagens().get(m.getMensagens().size() - 1).getMensagem());
        holder.lblContMensagens.setText(m.getContMsgNaoLidas() + " não lidas");

        holder.setPosicao(position);
    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imgPerfil;
        public ProgressBar prgBarra;

        public TextView lblDataHora;
        public TextView lblMensagens;
        public TextView lblContMensagens;

        private int posicao = 0;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);

            imgPerfil = (ImageView) itemLayoutView.findViewById(R.id.imgPerfil);
            prgBarra = (ProgressBar) itemLayoutView.findViewById(R.id.prgBarra);

            lblDataHora = (TextView) itemLayoutView.findViewById(R.id.lblDataHora);
            lblMensagens = (TextView) itemLayoutView.findViewById(R.id.lblMensagens);
            lblContMensagens = (TextView) itemLayoutView.findViewById(R.id.lblContMensagens);
        }

        @Override
        public void onClick(View view) {

            Bundle bdlMensagens = new Bundle();
            bdlMensagens.putSerializable("mensagens", mensagens.get(posicao).getMensagens());

            Bundle bdlContatos = new Bundle();
            LinkedList<Contato> contatos = new LinkedList<Contato>();
            contatos.add(0, mensagens.get(posicao).getContato());
            bdlContatos.putSerializable("contatos", contatos);

            Intent i = new Intent(view.getContext(), ChatMensagemActivity.class);
            i.putExtra("mensagens", bdlMensagens);
            i.putExtra("contatos", bdlContatos);
            view.getContext().startActivity(i);
        }

        public void setPosicao(int posicao) {
            this.posicao = posicao;
        }
    }
}