package br.com.wiser.enums;

public enum Activities {

    APP_CONFIGURACOES(0),
    APP_LOGIN(1),
    APP_PRINCIPAL(2),
    APP_SOBRE(3),
    APP_SPLASHSCREEN(4),
    CHAT_MENSAGENS(5),
    CHAT_PESQUISA(6),
    CHAT_RESULTADOS(7),
    FORUM_DISCUSSAO(8),
    FORUM_MINHAS_DISCUSSOES(9),
    FORUM_NOVA_DISCUSSAO(10),
    FORUM_PESQUISA(11),
    FORUM_PRINCIPAL(12);

    public int getEnmTela;
    Activities(int valor) {
        getEnmTela = valor;
    }
}
