package br.com.app.activity.app.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Button;

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.business.app.facebook.Facebook;
import br.com.app.enums.EnmTelas;
import br.com.app.utils.Utils;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class AppLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_login);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Facebook.setBtnLogin(this, (Button) super.findViewById(R.id.btnLogin));

        if (getIntent().getBooleanExtra("LOGOUT", false)) {
            getIntent().removeExtra("LOGOUT");
            Facebook.logout(this);
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
        Facebook.callbackManager(requestCode, resultCode, data);
    }

    private void mostrarMensagem(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void encerrar() {
        Sistema.USER_ID = Facebook.getUserID();
        mostrarMensagem(getString(R.string.boas_vindas, Facebook.getFirstName(Sistema.USER_ID)));
        Utils.chamarActivity(this, EnmTelas.APP_PRINCIPAL);
    }
}
