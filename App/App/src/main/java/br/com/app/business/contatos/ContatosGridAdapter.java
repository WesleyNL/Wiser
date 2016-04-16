package br.com.app.business.contatos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.app.activity.R;

/**
 * Created by Wesley on 08/04/2016.
 */
public class ContatosGridAdapter extends ArrayAdapter<Contato> {

    Context context;
    int layoutResourceId;
    ArrayList<Contato> listaItemContatos = null;

    public ContatosGridAdapter(Context context, int layoutResourceId, ArrayList<Contato> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.listaItemContatos = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View objView = convertView;
        RecordHolder objHolder = null;

        if (objView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            objView = inflater.inflate(layoutResourceId, parent, false);

            objHolder = new RecordHolder();
            objHolder.txtNome = (TextView) objView.findViewById(R.id.itemNomeContato);
            objHolder.imgPerfil = (ImageView) objView.findViewById(R.id.itemImagemContato);
            objView.setTag(objHolder);
        } else {
            objHolder = (RecordHolder) objView.getTag();
        }

        Contato item = listaItemContatos.get(position);
        objHolder.txtNome.setText(item.getUserName());
        objHolder.imgPerfil.setImageBitmap(item.getProfilePicture());

        return objView;
    }

    static class RecordHolder {
        TextView txtNome;
        ImageView imgPerfil;
    }
}