package br.com.app.business.contatos;

import android.graphics.Bitmap;

/**
 * Created by Wesley on 08/04/2016.
 */
public class Item {

    private Bitmap imgContato;
    private String nomeContato;

    public Item(Bitmap image, String title) {
        super();
        this.imgContato = image;
        this.nomeContato = title;
    }

    public Bitmap getImgContato() {
        return imgContato;
    }

    public void setImgContato(Bitmap imgContato) {
        this.imgContato = imgContato;
    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }
}