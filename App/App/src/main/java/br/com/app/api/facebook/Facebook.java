package br.com.app.api.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
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

import java.net.URL;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.LinkedList;

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
        callbackManager = CallbackManager.Factory.create();
        getSignatures();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Sistema.USER_ID = getUserID();

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
                new GraphRequest(Sistema.ACCESS_TOKEN, "/" + node, parametros, HttpMethod.GET,
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

    public static String getFirstName(String userID) {
        String firstName = "";

        Bundle parametros = new Bundle();
        parametros.putString("fields", "first_name");
        request(userID, parametros);

        try {
            JSONObject obj = response.getJSONObject();
            firstName = obj.optString("first_name");

            if (firstName.equals("")) {
                firstName = nomeIndisponivel;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return firstName;
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

    public static LinkedList<Contato> getProfiles(final LinkedList<Contato> contatos) {
        String listUsersID = "";

        for (Contato c : contatos) {
            c.setUserName(nomeIndisponivel);
            c.setFirstName(nomeIndisponivel);
            c.setProfilePicture(imgPerfilIndisponivel);

            listUsersID += c.getUserID() + ",";
        }
        listUsersID = listUsersID.substring(0, listUsersID.length() - 1);

        Bundle parametros = new Bundle();
        parametros.putString("ids", listUsersID);
        parametros.putString("fields", "name,first_name,picture.width(250).height(250){url}");
        request("", parametros);

        try {
            JSONObject listUsers = response.getJSONObject();
            JSONObject user = null;
            URL imgValue = null;

            for (Contato c : contatos) {
                if (listUsers.has(c.getUserID())) {
                    user = listUsers.getJSONObject(c.getUserID());

                    if (user.has("name")) {
                        c.setUserName(user.getString("name"));
                    }

                    if (user.has("first_name")) {
                        c.setFirstName(user.getString("first_name"));
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
        String url = "https://www.facebook.com/" + userID;
        Uri uri;

        try {
            ApplicationInfo applicationInfo = activity.getApplicationContext().getApplicationInfo();
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + url)));
            }
        }
        catch (Exception e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
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
