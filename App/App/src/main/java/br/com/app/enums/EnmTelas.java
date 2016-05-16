package br.com.app.enums;

/**
 * Created by Jefferson on 30/03/2016.
 */
public enum EnmTelas {
    APP_CONFIGURACOES(0), APP_LOGIN(1), APP_PRINCIPAL(2), APP_SOBRE(3), APP_SPLASHSCREEN(4),
    CHAT_MENSAGENS(5), CHAT_PESQUISA(6), CHAT_RESULTADOS(7),
    FORUM_DISCUSSAO(8), FORUM_PESQUISA(9), FORUM_PRINCIPAL(10);

    public int getEnmTela;
    EnmTelas(int valor) {
        getEnmTela = valor;
    }
}
