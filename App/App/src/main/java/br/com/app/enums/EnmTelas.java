package br.com.app.enums;

/**
 * Created by Jefferson on 30/03/2016.
 */
public enum EnmTelas {
    CONFIGURACOES(0), CONTATOS(1), LOGIN(2), PESQUISA(3), SOBRE(4);

    public int getEnmTela;
    EnmTelas(int valor) {
        getEnmTela = valor;
    }
}
