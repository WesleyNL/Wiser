package br.com.app.api.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.facebook.HttpMethod;
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
import br.com.app.activity.R;
import br.com.app.business.contatos.Contato;
import br.com.app.utils.Utils;

/**
 * Created by Jefferson on 03/04/2016.
 */
public class Facebook {
    private static Context contexto;
    private static CallbackManager callbackManager;

    private static GraphResponse response;
    private static boolean requisitando = false;

    private static final String nomeIndisponivel = "Usuário";
    private static Bitmap imgPerfilIndisponivel = null;

    public Facebook(Context contexto) {
        this.contexto = contexto;

        FacebookSdk.sdkInitialize(contexto);
        getSignatures();

        if (logado()) {
            Sistema.USER_ID = getUserID();
        }

        imgPerfilIndisponivel = BitmapFactory.decodeResource(
                contexto.getResources(), R.drawable.com_facebook_profile_picture_blank_portrait);
    }

    private void getSignatures() {
        try {
            PackageInfo info = contexto.getPackageManager().getPackageInfo(
                    contexto.getPackageName(), PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void callbackManager(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public static void setBtnLogin(final Activity loginActivity, Button loginButton) {
        Button btnLogin = loginButton;
        callbackManager = CallbackManager.Factory.create();

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
                LoginManager.getInstance().logInWithReadPermissions(loginActivity,
                        Arrays.asList("public_profile", "user_friends"));
            }
        });
    }

    /**
     *
     * @param node Pode ser vazio
     * @param parametros
     */
    private static void request(final String node, final Bundle parametros) {
        response = null;
        requisitando = true;

        new Thread() {
            public void run() {
                new GraphRequest(Sistema.accessToken, "/" + node, parametros, HttpMethod.GET,
                        new GraphRequest.Callback() {

                            @Override
                            public void onCompleted(GraphResponse graphResponse) {
                                if (graphResponse != null) {
                                    response = graphResponse;
                                }
                                requisitando = false;
                            }
                        }).executeAndWait();
            }
        }.start();

        while (requisitando);
    }

    public static String getUserID() {
        try {
            return AccessToken.getCurrentAccessToken().getUserId();
        }
        catch (Exception e) {
            return "";
        }
    }

    public static String getUserName(String userID) {
        String userName = "";

        Bundle parametros = new Bundle();
        parametros.putString("fields", "name");
        request(userID, parametros);

        try {
            JSONObject obj = response.getJSONObject();
            userName = obj.optString("name");

            if (userName.equals("")) {
                userName = nomeIndisponivel;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return userName;
    }

    public static Bitmap getProfilePicture(String userID) {
        Bitmap mIcon1 = imgPerfilIndisponivel;
        URL imgValue = null;

        String profilePicture = "";

        Bundle parametros = new Bundle();
        parametros.putString("fields", "picture");
        request(userID, parametros);

        try {
            JSONObject obj = response.getJSONObject();
            if (obj.has("picture")) {
                profilePicture = obj.getJSONObject("picture").getJSONObject("data").optString("url");
            }

            if (!profilePicture.equals("")) {
                imgValue = new URL(profilePicture);
                mIcon1 = BitmapFactory.decodeStream(imgValue.openConnection().getInputStream());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return mIcon1;
    }

    public static HashMap<String, Contato> getProfiles(final List<String> usersID) {
        String listUsersID = "";
        final HashMap contatos = new HashMap<String, Contato>();
        Contato c = null;

        for (String id : usersID) {
            c = new Contato();
            c.setUserName(nomeIndisponivel);
            c.setProfilePicture(imgPerfilIndisponivel);

            contatos.put(id, c);

            listUsersID += id + ",";
        }
        listUsersID = listUsersID.substring(0, listUsersID.length() - 1);

        Bundle parametros = new Bundle();
        parametros.putString("ids", listUsersID);
        parametros.putString("fields", "name,picture.width(250).height(250){url}");
        request("", parametros);

        try {
            JSONObject listUsers = response.getJSONObject();
            JSONObject user = null;
            URL imgValue = null;
            c = null;

            for (String id : usersID) {
                if (listUsers.has(id)) {
                    user = listUsers.getJSONObject(id);
                    c = (Contato) contatos.get(id);
                    c.setUserID(id);
                    if (user.has("name")) {
                        c.setUserName(user.getString("name"));
                    }

                    if (user.has("picture")) {
                        c.setProfilePictureURL(user
                                .getJSONObject("picture")
                                .getJSONObject("data")
                                .optString("url"));

                        if (!c.getProfilePictureURL().equals("")) {
                            c.setProfilePicture(Utils.carregarImagem(c.getProfilePictureURL()));
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return contatos;
    }

    public static void abrirPerfil(Activity activity, String userID) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + userID));
            activity.startActivity(intent);
        }
        catch (Exception e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + userID)));
        }
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
    }
}
