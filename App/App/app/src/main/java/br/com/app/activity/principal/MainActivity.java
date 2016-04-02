package br.com.app.activity.principal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import br.com.app.activity.configuracao.ConfiguracoesActivity;
import br.com.app.activity.contatos.ContatosActivity;
import br.com.app.activity.login.LoginActivity;
import br.com.app.activity.pesquisa.PesquisaActivity;
import br.com.app.activity.sobre.SobreActivity;
import br.com.app.enums.EnmTelas;

public class MainActivity extends Activity {
    private static final String ACCESS_EXPIRES = "access_expires";
    private static final String ACCESS_TOKEN = "access_token";

    public static String User_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getBooleanExtra("TELA_PESQUISA", false)) {
            chamarActivity(EnmTelas.PESQUISA);
        }
        else {
            chamarActivity(EnmTelas.LOGIN);
        }
    }

    public void chamarActivity(EnmTelas Activity) {
        Class classe = null;

        switch (Activity) {
            case CONFIGURACOES:
                classe = ConfiguracoesActivity.class;
                break;
            case CONTATOS:
                classe = ContatosActivity.class;
                break;
            case LOGIN:
                classe = LoginActivity.class;
                break;
            case PESQUISA:
                classe = PesquisaActivity.class;
                break;
            case SOBRE:
                classe = SobreActivity.class;
                break;
        }

        Intent i = new Intent(MainActivity.this, classe);
        startActivity(i);
    }

    private void mostrarMensagem(String texto) {
        Toast.makeText(MainActivity.this, texto, Toast.LENGTH_SHORT).show();
    }
}