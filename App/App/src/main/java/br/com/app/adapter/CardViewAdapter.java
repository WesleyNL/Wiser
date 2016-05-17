package br.com.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.app.activity.R;
import br.com.app.business.forum.discussao.Discussao;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private Context context;
    private static List<Discussao> discussoes;

    public CardViewAdapter(Context context, List<Discussao> discussoes) {
        this.context = context;
        this.discussoes = discussoes;
    }

    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.forum_resultados_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Discussao discussao = discussoes.get(position);

        viewHolder.lblIDDiscussao.setText("#" + discussao.getIDDiscussao());

        if (discussao.getContato().getProfilePicture() == null) {
            String url = null;
            if (!TextUtils.isEmpty(discussao.getContato().getProfilePictureURL())) {
                url = discussao.getContato().getProfilePictureURL();
            }

            Picasso.with(context)
                    .load(url)
                    .into(viewHolder.imgPerfil);
            discussao.getContato().setProfilePicture(viewHolder.imgPerfil.getDrawingCache());
        }
        else {
            viewHolder.imgPerfil.setImageBitmap(discussao.getContato().getProfilePicture());
        }

        viewHolder.lblAutorDiscussao.setText(discussao.getContato().getUserName());
        viewHolder.lblTituloDiscussao.setText(discussao.getTituloDiscussao());
        viewHolder.lblDescricaoDiscussao.setText(discussao.getDescricaoDiscussao());
        viewHolder.lblContRespostas.setText(
                discussao.getContRespostas() == 1 ?
                        discussao.getContRespostas() + " Resposta" :
                        discussao.getContRespostas() + " Respostas");
    }

    @Override
    public int getItemCount() {
        return discussoes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView lblIDDiscussao;
        public ImageView imgPerfil;
        public TextView lblAutorDiscussao;
        public TextView lblTituloDiscussao;
        public TextView lblDescricaoDiscussao;
        public TextView lblContRespostas;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            lblIDDiscussao = (TextView) itemLayoutView.findViewById(R.id.lblIDDiscussao);
            imgPerfil = (ImageView) itemLayoutView.findViewById(R.id.imgPerfil);
            lblAutorDiscussao = (TextView) itemLayoutView.findViewById(R.id.lblAutorDiscussao);
            lblTituloDiscussao = (TextView) itemLayoutView.findViewById(R.id.lblTituloDiscussao);
            lblDescricaoDiscussao = (TextView) itemLayoutView.findViewById(R.id.lblDescricaoDiscussao);
            lblContRespostas = (TextView) itemLayoutView.findViewById(R.id.lblContRespostas);

//            itemLayoutView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), "OnClick Version :" + versionName,
//                            Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            itemLayoutView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    Toast.makeText(v.getContext(), "OnLongClick Version :" + versionName,
//                            Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//            });
        }
    }
}
