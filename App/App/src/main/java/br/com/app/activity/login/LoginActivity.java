package br.com.app.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Button;

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.api.Facebook;
import br.com.app.enums.EnmTelas;
import br.com.app.utils.Utils;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class LoginActivity extends Activity {
    private Facebook facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            facebook = new Facebook(this);
            facebook.setBtnLogin((Button) super.findViewById(R.id.btnLogin));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (getIntent().getBooleanExtra("LOGOUT", false)) {
            getIntent().removeExtra("LOGOUT");
            Facebook.logout();
            return;
        }

        if (Facebook.logado()) {
            encerrar();
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebook.callbackManager(requestCode, resultCode, data);
    }

    private void mostrarMensagem(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void encerrar() {
        Sistema.USER_ID = Facebook.getUserID();
        mostrarMensagem("Olá, " + Facebook.getUserName(Sistema.USER_ID) + " ;)");

        Utils.chamarActivity(this, EnmTelas.PESQUISA, EnmTelas.PESQUISA.name(), true);
    }
}
