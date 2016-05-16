package br.com.app.business.contatos;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

import br.com.app.activity.R;

/**
 * Created by Wesley on 08/04/2016.
 */
public class ContatosGridAdapter extends ArrayAdapter<Contato> {

    private Context context;
    private int layoutResourceId;
    private LinkedList<Contato> listaItemContatos = null;

    public ContatosGridAdapter(Context context, int layoutResourceId, LinkedList<Contato> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.listaItemContatos = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Trocar para GridLayoutManager

        View objView = convertView;;
        RecordHolder objHolder = null;

        if (objView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            objView = inflater.inflate(layoutResourceId, parent, false);

            objHolder = new RecordHolder();
            objHolder.imgImagemContato = (ImageView) objView.findViewById(R.id.imgImagemContato);
            objHolder.txtNome = (TextView) objView.findViewById(R.id.txtNomeLista);

            objView.setTag(objHolder);
        }
        else {
            objHolder = (RecordHolder) objView.getTag();
        }

        Contato item = listaItemContatos.get(position);
        objHolder.txtNome.setText(item.getFirstName());

        String url = null;
        if (!TextUtils.isEmpty(item.getProfilePictureURL())) {
            url = item.getProfilePictureURL();
        }

        Picasso.with(context)
                .load(url)
                .into(objHolder.imgImagemContato);

        return objView;
    }

    private static class RecordHolder {
        ImageView imgImagemContato;
        TextView txtNome;
    }
}