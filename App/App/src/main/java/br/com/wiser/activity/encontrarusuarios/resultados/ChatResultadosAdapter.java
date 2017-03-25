package br.com.wiser.activity.encontrarusuarios.resultados;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.LinkedList;

import br.com.wiser.activity.R;
import br.com.wiser.business.app.usuario.Usuario;
import br.com.wiser.utils.Utils;

/**
 * Created by Wesley on 08/04/2016.
 */
public class ChatResultadosAdapter extends ArrayAdapter<Usuario> {

    private Context context;
    private int layoutResourceId;
    private LinkedList<Usuario> listaItemUsuarios = null;

    public ChatResultadosAdapter(Context context, int layoutResourceId, LinkedList<Usuario> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.listaItemUsuarios = data;
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

        Usuario item = listaItemUsuarios.get(position);
        objHolder.txtNome.setText(item.getFirstName());
        Utils.loadImageInBackground(context, item.getUrlProfilePicture(), objHolder.imgPerfil, objHolder.prgBarra);

        return objView;
    }

    private static class RecordHolder {
        ImageView imgPerfil;
        ProgressBar prgBarra;
        TextView txtNome;
    }
}