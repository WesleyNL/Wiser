package br.com.app.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import br.com.app.Sistema;
import br.com.app.business.contatos.Contato;

/**
 * Created by Jefferson on 03/04/2016.
 */
public class Facebook {
    private Context contexto;
    private CallbackManager callbackManager;

    private static String userName = "";
    private static String profilePicture = "";
    private static boolean requisitando = false;

    public Facebook(Context contexto) {
        this.contexto = contexto;

        getUserInfo();

        FacebookSdk.sdkInitialize(this.contexto);
        callbackManager = CallbackManager.Factory.create();
    }

    private void getUserInfo() {
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

    public void callbackManager(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void setBtnLogin(Button loginButton) {
        Button btnLogin = loginButton;

        try {
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Sistema.USER_ID = loginResult.getAccessToken().getUserId();
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {
                    error.printStackTrace();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions((Activity) contexto, Arrays.asList("public_profile", "user_friends"));
            }
        });
    }

    /**
     * Não chamar este método antes de chamar a LoginActivity,
     * pois é nela que a API do Facebook é inicializada
     * @return ID do Usuário com até 16 caracteres ou então "" caso não tenha um usuário conectado.
     */
    public static String getUserID() {
        try {
            return AccessToken.getCurrentAccessToken().getUserId();
        }
        catch (Exception e) {
            return "";
        }
    }

    /**
     * Não chamar este método antes de chamar a LoginActivity,
     * pois é nela que a API do Facebook é inicializada
     * @param userID
     * @return
     */
    public static String getUserName(String userID) {
        userName = "";
        requisitando = false;

        final GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + userID,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            JSONObject obj = response.getJSONObject();
                            userName = obj.optString("name");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        requisitando = false;
                    }
                });

        requisitando = true;

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name");
        request.setParameters(parameters);

        new Thread() {
            public void run() {
                request.executeAndWait();
            }
        }.start();

        while (requisitando);

        return userName;
    }

    /**
     * Não chamar este método antes de chamar a LoginActivity,
     * pois é nela que a API do Facebook é inicializada
     * @param userID
     * @return
     */
    public static Bitmap getProfilePicture(String userID) {
        Bitmap mIcon1 = null;
        URL imgValue = null;

        profilePicture = "";
        requisitando = false;

        final GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + userID,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            JSONObject obj = response.getJSONObject();
                            if (obj.optString("picture") != null) {
                                profilePicture = obj.getJSONObject("picture").getJSONObject("data").optString("url");
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        requisitando = false;
                    }
                });

        requisitando = true;

        Bundle parameters = new Bundle();
        parameters.putString("fields", "picture");
        request.setParameters(parameters);

        new Thread() {
            public void run() {
                request.executeAndWait();
            }
        }.start();

        while (requisitando);

        try {
            imgValue = new URL(profilePicture);
            mIcon1 = BitmapFactory.decodeStream(imgValue.openConnection().getInputStream());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return mIcon1;
    }

    /**
     * Não chamar este método antes de chamar a LoginActivity,
     * pois é nela que a API do Facebook é inicializada
     * @param usersID
     * @return
     */
    public static HashMap<String, Contato> getProfiles(final List<String> usersID) {
        String listUsersID = "";
        final HashMap contatos = new HashMap<String, Contato>();
        Contato c = null;

        for (String id : usersID) {
            c = new Contato();
            contatos.put(id, c);

            listUsersID += id + ",";
        }
        listUsersID = listUsersID.substring(0, listUsersID.length() - 1);

        final GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            JSONObject listUsers = response.getJSONObject();
                            JSONObject user = null;
                            Contato c = null;
                            URL imgValue = null;

                            for (String id : usersID) {
                                if (!listUsers.optString(id).equals("")) {
                                    user = listUsers.getJSONObject(id);
                                    c = (Contato) contatos.get(id);
                                    c.setUserID(id);
                                    c.setUserName(user.optString("name"));

                                    if (!user.optString("picture").equals("")) {
                                        c.setProfilePictureURL(user
                                                .getJSONObject("picture")
                                                .getJSONObject("data")
                                                .optString("url"));

                                        if (!c.getProfilePictureURL().equals("")) {
                                            imgValue = new URL(c.getProfilePictureURL());
                                            c.setProfilePicture(BitmapFactory.decodeStream(
                                                    imgValue.openConnection().getInputStream()));
                                        }
                                    }
                                }
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        requisitando = false;
                    }
                });

        requisitando = true;

        Bundle parametros = new Bundle();
        parametros.putString("ids", listUsersID);
        parametros.putString("fields", "name,picture.width(250).height(250){url}");
        request.setParameters(parametros);

        new Thread() {
            @Override
            public void run() {
                request.executeAndWait();
            }
        }.start();

        while (requisitando);

        return contatos;
    }

    public static boolean logado() {
        try {
            return AccessToken.getCurrentAccessToken() != null;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static void logout() {
        try {
            LoginManager.getInstance().logOut();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Sistema.USER_ID = "";
        userName = "";
    }
}
