package br.com.app.activity.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.AccessToken;
import java.util.Arrays;
import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.activity.login.LoginActivity;
import br.com.app.activity.pesquisa.PesquisaActivity;
import br.com.app.api.facebook.Facebook;

/**
 * Created by Wesley on 19/04/2016.
 */
public class SplashScreenActivity extends Activity {

    private final int SPLASH_TIMEOUT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Facebook facebook = new Facebook(this);
        checkAccessToken();
        iniciarAplicacao();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Facebook.callbackManager(requestCode, resultCode, data);
    }

    private void checkAccessToken() {
        // TODO - String ACCEST_TOKEN = Server.getAccessToken();
        String ACCESS_TOKEN = "570854019757986|wlsnDSdcWhFs586zho4_dgJtc18";
        String APP_ID = String.valueOf(R.string.facebook_app_id);
        String ID_ACCOUNT = "1262642377098040";

        Sistema.accessToken = new AccessToken(
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

    private void iniciarAplicacao() {

        if (AccessToken.getCurrentAccessToken() != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent i = new Intent(SplashScreenActivity.this, PesquisaActivity.class);
                    startActivity(i);

                    finish();
                }
            }, SPLASH_TIMEOUT);
        }
        else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);

                    finish();
                }
            }, SPLASH_TIMEOUT);
        }
    }
}