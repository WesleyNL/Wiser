package br.com.app.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;

/**
 * Created by Jefferson on 03/04/2016.
 */
public class FacebookActivity {

    private IFacebook contexto;
    private CallbackManager callbackManager;
    private LoginButton loginButton;

    private static String user_ID = "";
    private static String user_name = "";

    /**
     * @param contexto Necessário extender de Activity
     */
    public FacebookActivity(IFacebook contexto) {
        this.contexto = contexto;

        getUserInfo();

        FacebookSdk.sdkInitialize((Activity) this.contexto);
        callbackManager = CallbackManager.Factory.create();
    }

    private void getUserInfo() {
        Context contexto = (Activity) this.contexto;

        try {
            PackageInfo info = contexto.getPackageManager().getPackageInfo(
                    contexto.getPackageName(), PackageManager.GET_SIGNATURES);

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

    // Quando clica no botão de LoginActivity, é iniciado uma nova Activity e este método é o retorno desta Activity
    public void callbackManager(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void setBtnLogin(Button loginButton) {
        try {
            this.loginButton = (LoginButton) loginButton;
            this.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    user_ID = loginResult.getAccessToken().getUserId();
                    contexto.setResultado_Login("SUCESSO");
                }

                @Override
                public void onCancel() {
                    contexto.setResultado_Login("Login Cancelado");
                }

                @Override
                public void onError(FacebookException e) {
                    e.printStackTrace();
                    contexto.setResultado_Login("Login Falhou");
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
            contexto.setResultado_Login("Erro: " + e.getMessage());
        }
    }

    public String getUser_ID() {
        return this.user_ID;
    }

    public String getUser_Name() {

        if (user_name.equals("")) {
            final GraphRequest request = GraphRequest.newGraphPathRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/" + user_ID,
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            try {
                                JSONObject obj = response.getJSONObject();
                                user_name = obj.getString("name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "name");
            request.setParameters(parameters);

            new Thread() {
                public void run() {
                    request.executeAndWait();
                }
            }.start();

            while (user_name.equals(""));
        }

        return user_name;
    }

    public boolean logado() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    public void logout() {
        LoginManager.getInstance().logOut();
        this.user_ID = "";
        this.user_name = "";
    }
}
