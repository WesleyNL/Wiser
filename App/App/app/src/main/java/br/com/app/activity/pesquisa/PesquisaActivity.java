package br.com.app.activity.pesquisa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.app.activity.R;
import br.com.app.activity.configuracao.ConfiguracoesActivity;
import br.com.app.activity.contatos.ContatosActivity;
import br.com.app.activity.login.LoginActivity;
import br.com.app.activity.sobre.SobreActivity;
import br.com.app.enums.EnmTelas;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class PesquisaActivity extends Activity {
    public static String User_ID = "";

    private static final String ACCESS_EXPIRES = "access_expires";
    private static final String ACCESS_TOKEN = "access_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntent().getBooleanExtra("TELA_PESQUISA", false)) {
            chamarActivity(EnmTelas.LOGIN, "", false);
        }
    }

    public void chamarActivity(EnmTelas Activity, String extras, boolean valExtras) {
        Class classe = null;
        int flag = 0;

        switch (Activity) {
            case CONFIGURACOES:
                classe = ConfiguracoesActivity.class;
                break;
            case CONTATOS:
                classe = ContatosActivity.class;
                break;
            case LOGIN:
                classe = LoginActivity.class;
                flag = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                break;
            case PESQUISA:
                classe = PesquisaActivity.class;
                break;
            case SOBRE:
                classe = SobreActivity.class;
                break;
        }

        Intent i = new Intent(this, classe);
        if (flag != 0) {
            i.setFlags(flag);
        }
        if (!extras.equals("")) {
            i.putExtra(extras, valExtras);
        }
        startActivity(i);
        if (flag == Intent.FLAG_ACTIVITY_CLEAR_TOP) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

//        MenuItem m1 = menu.add(0, 0, 0, "Item 1");
//        m1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//
//        MenuItem m2 = menu.add(0, 1, 1, "Item 2");
//        m2.setIcon(R.drawable.com_facebook_button_icon);
//        m2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//
//        MenuItem m3 = menu.add(0, 2, 2, "Item 1");
//        m1.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//
//        MenuItem m4 = menu.add(0, 3, 3, "Item 1");
//        m1.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return (true);
    }

    @Override
    public boolean onMenuItemSelected(int panel, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itmConfiguracoes:
                chamarActivity(EnmTelas.CONFIGURACOES, "", false);
                break;

            case R.id.itmSobre:
                chamarActivity(EnmTelas.SOBRE, "", false);
                break;

            case R.id.itmSair:
                chamarActivity(EnmTelas.LOGIN, "LOGOUT", true);
                break;
        }

        return (true);
    }
}
