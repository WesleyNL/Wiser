package br.com.app.activity.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.os.Handler;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import android.widget.Button;

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.activity.login.LoginActivity;
import br.com.app.activity.pesquisa.PesquisaActivity;
import br.com.app.api.facebook.Facebook;
import br.com.app.business.pesquisa.Pesquisa;
=======

import java.util.Timer;
import java.util.TimerTask;

import br.com.app.activity.R;
import br.com.app.activity.pesquisa.PesquisaActivity;
>>>>>>> f3fd8b3e964252419c8ace6e5a64e4ca2dee8e2f

/**
 * Created by Wesley on 19/04/2016.
 */
public class SplashScreenActivity extends Activity {

<<<<<<< HEAD
    private final int SPLASH_TIMEOUT = 500;

=======
>>>>>>> f3fd8b3e964252419c8ace6e5a64e4ca2dee8e2f
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

<<<<<<< HEAD
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
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                finish();

                Intent i = new Intent();
                i.setClass(SplashScreenActivity.this, PesquisaActivity.class);
                startActivity(i);
            }
        }, 6000);
    }
}
