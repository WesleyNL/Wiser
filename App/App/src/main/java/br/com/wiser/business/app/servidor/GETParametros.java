package br.com.wiser.business.app.servidor;

/**
 * Created by Jefferson on 04/08/2016.
 */
public class GETParametros {

    private String parametros = "";

    public void put(String chave, Object valor) {
        parametros += (parametros.length() > 0 ? "&" : "") + chave + "=" + valor;
    }

    @Override
    public String toString() {
        return parametros;
    }
}
