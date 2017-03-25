package br.com.wiser.activity.chat.conversas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.ImageView;

import java.util.LinkedList;

import br.com.wiser.activity.R;
import br.com.wiser.activity.chat.mensagens.ChatMensagensActivity;
import br.com.wiser.business.chat.conversas.Conversas;
import br.com.wiser.business.chat.conversas.ConversasDAO;
import br.com.wiser.utils.Utils;

/**
 * Created by Jefferson on 23/05/2016.
 */
public class ChatConversasAdapter extends RecyclerView.Adapter<ChatConversasAdapter.ViewHolder> {

    private Context context;
    private static LinkedList<ConversasDAO> conversas = null;

    public ChatConversasAdapter(Context context, LinkedList<ConversasDAO> conversas) {
        this.context = context;
        this.conversas = conversas;
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
        Conversas m = conversas.get(position);

        holder.viewSeparator.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);

        Utils.loadImageInBackground(context, m.getDestinatario().getUrlProfilePicture(), holder.imgPerfil, holder.prgBarra);
        holder.lblDataHora.setText(m.getMensagens().getLast().getData().toString());
        holder.lblMensagens.setText(m.getMensagens().getLast().getMensagem());
        holder.lblContMensagens.setText(m.getContMsgNaoLidas() + " " + context.getString(m.getContMsgNaoLidas() <= 1 ? R.string.nao_lida : R.string.nao_lidas));

        if(m.getContMsgNaoLidas() != 0){
            Utils.vibrar(context, 150);
        }

        holder.setPosicao(position);
    }

    @Override
    public int getItemCount() {
        return conversas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public View viewSeparator;

        public ImageView imgPerfil;
        public ProgressBar prgBarra;

        public TextView lblDataHora;
        public TextView lblMensagens;
        public TextView lblContMensagens;

        private int posicao = 0;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);

            viewSeparator = itemLayoutView.findViewById(R.id.viewSeparator);

            imgPerfil = (ImageView) itemLayoutView.findViewById(R.id.imgPerfil);
            prgBarra = (ProgressBar) itemLayoutView.findViewById(R.id.prgBarra);

            lblDataHora = (TextView) itemLayoutView.findViewById(R.id.lblDataHora);
            lblMensagens = (TextView) itemLayoutView.findViewById(R.id.lblMensagens);
            lblContMensagens = (TextView) itemLayoutView.findViewById(R.id.lblContMensagens);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(), ChatMensagensActivity.class);
            i.putExtra("conversa", conversas.get(posicao));
            view.getContext().startActivity(i);
        }

        public void setPosicao(int posicao) {
            this.posicao = posicao;
        }
    }
}