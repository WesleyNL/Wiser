package br.com.wiser.enums;

public enum Models {

    DISCUSSAO(0),
    DISCUSSAORESPOSTA(1),
    FLUENCIA(2),
    IDIOMA(3),
    MENSAGENS(4),
    USER(5),
    ACCESSTOKEN(6);

    public int getEnmModels;
    Models(int valor) {
        getEnmModels = valor;
    }
}
