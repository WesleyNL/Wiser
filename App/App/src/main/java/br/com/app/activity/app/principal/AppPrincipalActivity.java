package br.com.app.activity.app.principal;

import android.app.Activity;
import android.view.View;

import br.com.app.enums.EnmTelas;
import br.com.app.utils.Utils;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class AppPrincipalActivity extends Activity {
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
