package br.com.app.utils;

/**
 * Created by Wesley on 27/04/2016.
 */
public class IdiomaFluencia {

    private int id;
    private String descricao;

    public IdiomaFluencia(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override public String toString() {
        return descricao;
    }
}

