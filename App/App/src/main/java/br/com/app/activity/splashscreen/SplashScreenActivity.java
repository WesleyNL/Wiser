package br.com.app.activity.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

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

    private static final String URL = "http://" + Sistema.SERVIDOR_WS + "/Projeto_Android_WS/services/Sistema?wsdl";
    private static final String NAMESPACE = "http://projeto.com.br";
    private static final String CONSULTAR = "getAccessToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Facebook facebook = new Facebook(this);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        checkAccessToken();
        iniciarAplicacao();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Facebook.callbackManager(requestCode, resultCode, data);
    }

    private void checkAccessToken() {

        String ACCESS_TOKEN = getAccessToken();
        String APP_ID = String.valueOf(R.string.facebook_app_id);
        String ID_ACCOUNT = "1262642377098040";

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

    private void iniciarAplicacao() {

        if (AccessToken.getCurrentAccessToken() != null) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    finish();

                    Intent i = new Intent();
                    i.setClass(SplashScreenActivity.this, PesquisaActivity.class);
                    startActivity(i);
                }
            }, SPLASH_TIMEOUT);
        }
        else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    finish();

                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);

                    finish();
                }
            }, SPLASH_TIMEOUT);
        }
    }

    public static String getAccessToken(){

        if(Sistema.ACCESS_TOKEN != null){
            return Sistema.ACCESS_TOKEN.getToken();
        }

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(new SoapObject(NAMESPACE, CONSULTAR));
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL);

        String accessToken = "";

        try{
            objHTTP.call("urn:" + CONSULTAR, objEnvelope);

            accessToken = ((SoapPrimitive) objEnvelope.getResponse()).toString();
        } catch(Exception e){
            e.printStackTrace();
        }

        return accessToken;
    }
}