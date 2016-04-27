package br.com.app.business.contatos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;

import br.com.app.activity.R;

/**
 * Created by Wesley on 08/04/2016.
 */
public class ContatosGridAdapter extends ArrayAdapter<Contato> {

    Context context;
    int layoutResourceId;
    LinkedList<Contato> listaItemContatos = null;

    public ContatosGridAdapter(Context context, int layoutResourceId, LinkedList<Contato> data) {
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
            objHolder.imgImagemContato = (ImageView) objView.findViewById(R.id.imgImagemContato);
            objHolder.txtNomeIdade = (TextView) objView.findViewById(R.id.txtNomeIdade);
            objHolder.txtIdiomaNivel = (TextView) objView.findViewById(R.id.txtIdiomaNivel);
            objView.setTag(objHolder);
        }
        else {
            objHolder = (RecordHolder) objView.getTag();
        }

        Contato item = listaItemContatos.get(position);
        objHolder.imgImagemContato.setImageBitmap(item.getProfilePicture());
        objHolder.txtNomeIdade.setText(item.getFirstName() + ", " + item.getIdade());
        objHolder.txtIdiomaNivel.setText(item.getIdioma() + " - " + item.getNivelFluencia());

        return objView;
    }

    static class RecordHolder {
        ImageView imgImagemContato;
        TextView txtNomeIdade;
        TextView txtIdiomaNivel;
    }
}