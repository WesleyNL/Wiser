package com.example.adminstrador.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends Activity {
    private static final String ACCESS_EXPIRES = "access_expires";
    private static final String ACCESS_TOKEN = "access_token";

    private static String User_ID = "";

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.login);
    }

    // Quando clica no botão de Login, é iniciado uma nova Activity e este método é o retorno desta Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void mostrarMensagem(String texto) {
        Toast.makeText(MainActivity.this, texto, Toast.LENGTH_SHORT).show();
    }

    private void setBtnLogin_Event() {
        try {
            loginButton = (LoginButton) super.findViewById(R.id.btnLogin);
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    User_ID = loginResult.getAccessToken().getUserId();
                    mostrarMensagem("User ID: " + User_ID);
                }

                @Override
                public void onCancel() {
                    mostrarMensagem("Login Cancelado");
                }

                @Override
                public void onError(FacebookException e) {
                    mostrarMensagem("Login Falhou");
                }
            });
        }
        catch (Exception e) {
            mostrarMensagem("Erro: " + e.getMessage());
        }
    }
}