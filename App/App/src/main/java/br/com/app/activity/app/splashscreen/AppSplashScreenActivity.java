package br.com.app.activity.app.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import com.facebook.AccessToken;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.activity.app.login.AppLoginActivity;
import br.com.app.activity.app.principal.AppPrincipalActivity;
import br.com.app.business.app.servidor.Servidor;
import br.com.app.business.app.facebook.Facebook;
import br.com.app.utils.Utils;

/**
 * Created by Wesley on 19/04/2016.
 */
public class AppSplashScreenActivity extends Activity {

    private final int SPLASH_TIMEOUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_splash_screen);
        new Facebook(this);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkAccessToken();

        if(Sistema.ACCESS_TOKEN == null){
            Toast.makeText(this, getString(R.string.indisponivel_sistema), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        iniciarAplicacao();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Facebook.callbackManager(requestCode, resultCode, data);
    }

    private void checkAccessToken() {
        String APP_ID = String.valueOf(R.string.facebook_app_id);
        String ID_ACCOUNT = String.valueOf(R.string.facebook_user_id);
        String ACCESS_TOKEN = "";

        try {
            ACCESS_TOKEN = Servidor.getAccessToken();

            if(ACCESS_TOKEN.trim().isEmpty()){
                return;
            }

            Sistema.ACCESS_TOKEN = new AccessToken(
                    ACCESS_TOKEN,
                    APP_ID,
                    ID_ACCOUNT,
                    Arrays.asList("public_profile", "user_friends"),
                    null,
                    null,
                    null,
                    null
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void iniciarAplicacao() {

        if(Sistema.APP_IDIOMA == 0) {
            Sistema.APP_IDIOMA = Utils.getCodAppIdioma(getResources().getConfiguration().locale.getLanguage());
        }

        if (AccessToken.getCurrentAccessToken() != null) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent i = new Intent(AppSplashScreenActivity.this, AppPrincipalActivity.class);
                    startActivity(i);

                    finish();
                }
            }, SPLASH_TIMEOUT);
        }
        else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent i = new Intent(AppSplashScreenActivity.this, AppLoginActivity.class);
                    startActivity(i);

                    finish();
                }
            }, SPLASH_TIMEOUT);
        }
    }
}