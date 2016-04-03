package br.com.app.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import br.com.app.activity.pesquisa.PesquisaActivity;
import br.com.app.activity.R;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class LoginActivity extends Activity{
    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getUserInfo();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        if (getIntent().getBooleanExtra("LOGOUT", false)) {
            LoginManager.getInstance().logOut();
        }

        if (AccessToken.getCurrentAccessToken() != null) {
            mostrarBoasVindas(AccessToken.getCurrentAccessToken());
            encerrar();
            return;
        }

        setContentView(R.layout.login);

        setBtnLogin_Event();
        setLogout_Event();

    }

    // Quando clica no botão de LoginActivity, é iniciado uma nova Activity e este método é o retorno desta Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getUserInfo() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    this.getPackageName(), PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBtnLogin_Event() {
        try {
            loginButton = (LoginButton) super.findViewById(R.id.btnLogin);
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    PesquisaActivity.User_ID = loginResult.getAccessToken().getUserId();
                    mostrarBoasVindas(loginResult.getAccessToken());
                    encerrar();
                }

                @Override
                public void onCancel() {
                    mostrarMensagem("Login Cancelado");
                }

                @Override
                public void onError(FacebookException e) {
                    mostrarMensagem("Login Falhou");
                    e.printStackTrace();
                }
            });
        }
        catch (Exception e) {
            mostrarMensagem("Erro: " + e.getMessage());
        }
    }

    private void setLogout_Event() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    //setButtonsForLogout();
                }
            }
        };
    }

    private void mostrarMensagem(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void mostrarBoasVindas(AccessToken accessToken) {
        // Pegar informações do Usuário
        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback(){

                    @Override
                    public void onCompleted(JSONObject obj, GraphResponse response) {
                        try {
                            mostrarMensagem("Olá, " + obj.getString("name") + " ;)");
                            encerrar();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parametros = new Bundle();
        parametros.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parametros);
        request.executeAsync();
    }

    private void encerrar() {
        Intent i = new Intent(this, PesquisaActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("TELA_PESQUISA", true);
        startActivity(i);
        finish();
    }
}
