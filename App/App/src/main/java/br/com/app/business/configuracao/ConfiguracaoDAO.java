package br.com.app.business.configuracao;

import br.com.app.business.servidor.Servidor;

public class ConfiguracaoDAO extends Configuracao{

    public boolean consultar(){
        return Servidor.consultarConfig(this);
    }

    public boolean salvar(){
        return Servidor.salvarConfig(this);
    }

    public boolean desativar(){
        return Servidor.desativarConfig(this);
    }

    public boolean existe(){
        return Servidor.existeConfig(this);
    }
}