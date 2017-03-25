package br.com.wiser.activity.app.login;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Button;

import com.facebook.AccessToken;

import java.util.Date;

import br.com.wiser.Sistema;
import br.com.wiser.activity.R;
import br.com.wiser.business.app.facebook.Facebook;
import br.com.wiser.business.app.usuario.UsuarioDAO;
import br.com.wiser.enums.Activities;
import br.com.wiser.utils.Utils;
import br.com.wiser.utils.UtilsLocation;

public class AppLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getBooleanExtra("LOGOUT", false)) {
            getIntent().removeExtra("LOGOUT");
            Sistema.logout(this);
        }
        else {
            if (AccessToken.getCurrentAccessToken() != null && salvar()) {
                if (Sistema.checkPermissoes(this)) {
                    loginSucesso();
                    return;
                }
            }
        }

        setContentView(R.layout.app_login);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Facebook.setBtnLogin(this, (Button) super.findViewById(R.id.btnLogin));

        if (Facebook.logado() && salvar()) {
            loginSucesso();
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Facebook.callbackManager(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        boolean permissoesAceitas = false;

        if (requestCode == Sistema.PERMISSION_ALL) {
            if (grantResults.length > 0) {
                permissoesAceitas = true;

                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissoesAceitas = false;
                    }
                }
            }

            if (!permissoesAceitas) {
                Toast.makeText(this, getString(R.string.necessario_permitir), Toast.LENGTH_SHORT).show();
                Sistema.logout(this);
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean salvar() {
        if (Sistema.logar(this, true)) {
            Toast.makeText(this, getString(R.string.falha_login), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void loginSucesso() {
        Toast.makeText(this, getString(R.string.boas_vindas, Facebook.getFirstName(Sistema.getUsuario(this).getFacebookID())), Toast.LENGTH_SHORT).show();
        Utils.chamarActivity(this, Activities.APP_PRINCIPAL);
        finish();
    }
}
