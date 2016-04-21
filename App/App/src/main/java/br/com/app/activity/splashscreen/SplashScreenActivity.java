package br.com.app.activity.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import br.com.app.activity.R;
import br.com.app.activity.pesquisa.PesquisaActivity;

/**
 * Created by Wesley on 19/04/2016.
 */
public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

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
