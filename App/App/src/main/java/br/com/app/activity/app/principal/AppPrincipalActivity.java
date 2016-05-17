package br.com.app.activity.app.principal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import br.com.app.activity.R;
import br.com.app.enums.EnmTelas;
import br.com.app.utils.Utils;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class AppPrincipalActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_principal);
    }

    public void chamarMensagens(View view) {
        Utils.chamarActivity(this, EnmTelas.CHAT_MENSAGENS);
    }

    public void chamarPesquisaContatos(View view) {
        Utils.chamarActivity(this, EnmTelas.CHAT_PESQUISA);
    }

    public void chamarForum(View view) {
        Utils.chamarActivity(this, EnmTelas.FORUM_PRINCIPAL);
    }
}
