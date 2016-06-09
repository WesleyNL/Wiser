package br.com.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.business.chat.mensagens.Mensagem;
import br.com.app.utils.FormatData;
import br.com.app.utils.Utils;

/**
 * Created by Jefferson on 30/05/2016.
 */
public class MensagensAdapter extends RecyclerView.Adapter<MensagensAdapter.ViewHolder> {

    private Context context;
    private List<Mensagem> mensagens;

    private final int USUARIO = 0;
    private final int CONTATO = 1;

    public MensagensAdapter(Context context, List<Mensagem> mensagens) {
        this.context = context;
        this.mensagens = mensagens;
    }

    @Override
    public int getItemViewType(int position) {
        return (mensagens.get(position).getUserID().equals(Sistema.USER_ID)) ? USUARIO : CONTATO;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView;

        if (viewType == USUARIO) {
            itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.chat_mensagens_baloes_usuario, parent, false);
        }
        else {
            itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.chat_mensagens_baloes_contato, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Mensagem m = mensagens.get(position);
        holder.lblDataHora.setText(FormatData.formatDate(m.getDataHora(), FormatData.HHMM));
        holder.lblMensagem.setText(m.getMensagem().trim());

        if(!m.getLido()){
            Utils.vibrar(context, 150);
        }
    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View linearlayout;
        public TextView lblDataHora;
        public TextView lblMensagem;

        public ViewHolder(View view) {
            super(view);

            linearlayout = (View) view.findViewById(R.id.linearlayout);
            lblDataHora = (TextView) view.findViewById(R.id.lblDataHora);
            lblMensagem = (TextView) view.findViewById(R.id.lblMensagem);
        }
    }
}
