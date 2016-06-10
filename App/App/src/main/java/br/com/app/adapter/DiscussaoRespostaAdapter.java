package br.com.app.adapter;

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.activity.chat.mensagens.ChatMensagemActivity;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.business.forum.discussao.Resposta;
import br.com.app.utils.FuncoesData;
import br.com.app.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import android.widget.ProgressBar;
/**
 * Created by Jefferson on 20/05/2016.
 */
public class DiscussaoRespostaAdapter extends RecyclerView.Adapter<DiscussaoRespostaAdapter.ViewHolder> {

    private Context context;
    private int layout;
    private LinkedList<Resposta> resposta = null;

    public DiscussaoRespostaAdapter(Context context, int layout, LinkedList<Resposta> resposta) {
        this.context = context;
        this.layout = layout;
        this.resposta = resposta;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Resposta r = resposta.get(position);

        holder.viewSeparator.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        holder.btnEnviarMensagem.setVisibility(r.getContato().getUserID().equals(Sistema.USER_ID) ? View.INVISIBLE : View.VISIBLE);

        Utils.loadImageInBackground(context, r.getContato().getProfilePictureURL(), holder.imgPerfil, holder.prgBarra);
        holder.lblIDResposta.setText("#" + r.getIdResposta());
        holder.lblAutor.setText(r.getContato().getFirstName());
        holder.lblDataHora.setText(FuncoesData.formatDate(r.getDataHora(), FuncoesData.DDMMYYYY_HHMMSS));
        holder.lblResposta.setText(r.getResposta());

        holder.btnEnviarMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contato destinatario = new Contato();
                destinatario.setUserID(r.getContato().getUserID());
                destinatario.setUserName(r.getContato().getUserName());

                Bundle bdlContatos = new Bundle();
                LinkedList<Contato> contatos = new LinkedList<Contato>();
                contatos.add(0, destinatario);
                bdlContatos.putSerializable("contatos", contatos);

                Intent i = new Intent(v.getContext(), ChatMensagemActivity.class);
                i.putExtra("contatos", bdlContatos);
                i.putExtra("especifico", 1);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resposta.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View viewSeparator;

        public ImageView imgPerfil;
        public ProgressBar prgBarra;
        public TextView lblIDResposta;
        public TextView lblAutor;
        public TextView lblDataHora;
        public TextView lblResposta;
        public Button btnEnviarMensagem;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            viewSeparator = (View) itemLayoutView.findViewById(R.id.viewSeparator);

            imgPerfil = (ImageView) itemLayoutView.findViewById(R.id.imgPerfil);
            prgBarra = (ProgressBar) itemLayoutView.findViewById(R.id.prgBarra);
            lblIDResposta = (TextView) itemLayoutView.findViewById(R.id.lblIDResposta);
            lblAutor = (TextView) itemLayoutView.findViewById(R.id.lblAutor);
            lblDataHora = (TextView) itemLayoutView.findViewById(R.id.lblDataHora);
            lblResposta = (TextView) itemLayoutView.findViewById(R.id.lblResposta);
            btnEnviarMensagem = (Button) itemLayoutView.findViewById(R.id.btnAbrirChat);
        }
    }
}
