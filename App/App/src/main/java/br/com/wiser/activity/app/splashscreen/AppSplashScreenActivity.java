package br.com.wiser.activity.app.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import br.com.wiser.Sistema;
import br.com.wiser.activity.R;
import br.com.wiser.activity.app.login.AppLoginActivity;

public class AppSplashScreenActivity extends Activity {

    private final int SPLASH_TIMEOUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_splash_screen);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (!Sistema.inicializar(this)) {
            Toast.makeText(this, "Servidor em Manutenção", Toast.LENGTH_SHORT).show();
            return;
        }

        iniciarAplicacao();
    }

    private void iniciarAplicacao() {
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