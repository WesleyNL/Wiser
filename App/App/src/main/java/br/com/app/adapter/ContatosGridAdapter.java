package br.com.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.LinkedList;

import br.com.app.activity.R;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.utils.Utils;

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

        View objView = convertView;;
        RecordHolder objHolder = null;

        if (objView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            objView = inflater.inflate(layoutResourceId, parent, false);

            objHolder = new RecordHolder();
            objHolder.imgPerfil = (ImageView) objView.findViewById(R.id.imgPerfil);
            objHolder.prgBarra = (ProgressBar) objView.findViewById(R.id.prgBarra);
            objHolder.txtNome = (TextView) objView.findViewById(R.id.txtNomeLista);

            objView.setTag(objHolder);
        }
        else {
            objHolder = (RecordHolder) objView.getTag();
        }

        Contato item = listaItemContatos.get(position);
        objHolder.txtNome.setText(item.getFirstName());
        Utils.loadImageInBackground(context, item.getProfilePictureURL(), objHolder.imgPerfil, objHolder.prgBarra);

        return objView;
    }

    private static class RecordHolder {
        ImageView imgPerfil;
        ProgressBar prgBarra;
        TextView txtNome;
    }
}