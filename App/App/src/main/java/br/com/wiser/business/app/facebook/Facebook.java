package br.com.wiser.business.app.facebook;

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
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.net.URL;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.LinkedList;

import br.com.wiser.Sistema;
import br.com.wiser.activity.R;
import br.com.wiser.business.app.usuario.Usuario;

/**
 * Created by Jefferson on 03/04/2016.
 */
public class Facebook {
    private static Context contexto;
    private static CallbackManager callbackManager;

    private static GraphResponse response;
    private static boolean requisitando = false;

    private static String nomeIndisponivel = "";

    public Facebook(Context contexto) {
        this.contexto = contexto;

        FacebookSdk.sdkInitialize(contexto);
        callbackManager = CallbackManager.Factory.create();
        getSignatures();

        //TODO while (!FacebookSdk.isInitialized());
        while (!FacebookSdk.isInitialized());

        /*
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        */

        nomeIndisponivel = contexto.getResources().getString(R.string.usuario);
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
                LoginManager.getInstance().logInWithReadPermissions(loginActivity, Sistema.ACCESS_TOKEN.getPermissions());
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

    public static String getFacebookID() {
        try {
            return Profile.getCurrentProfile().getId();
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
        Bitmap mIcon1 = null;
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

    // TODO Fazer o método sem preguiça
    public static Usuario getProfile(long userID) {
        LinkedList<Usuario> conts = new LinkedList<Usuario>();
        conts.add(0, new Usuario(userID));
        return getProfiles(conts).get(0);
    }

    public static Usuario getProfile(Usuario user) {
        LinkedList<Usuario> conts = new LinkedList<Usuario>();
        conts.add(0, user);
        return getProfiles(conts).get(0);
    }

    public static LinkedList<Usuario> getProfiles(final LinkedList<Usuario> usuarios) {
        String listUsersID = "";

        for (Usuario c : usuarios) {
            c.setFullName(nomeIndisponivel);
            c.setFirstName(nomeIndisponivel);

            listUsersID += c.getFacebookID() + ",";
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

            for (Usuario c : usuarios) {
                if (listUsers.has(c.getFacebookID())) {
                    user = listUsers.getJSONObject(c.getFacebookID());

                    if (user.has("name")) {
                        c.setFullName(user.getString("name"));
                    }

                    if (user.has("first_name")) {
                        c.setFirstName(user.getString("first_name"));
                    }

                    if (user.has("picture")) {
                        c.setUrlProfilePicture(user
                                .getJSONObject("picture")
                                .getJSONObject("data")
                                .optString("url"));
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public static void abrirPerfil(Activity activity, String userID) {
        String facebookUrl = "https://www.facebook.com/" + userID;

        try {
            int versionCode = activity.getApplicationContext().getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;

            if(!userID.isEmpty()) {
                Uri uri = Uri.parse("fb://profile/" + userID);
                activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
            else if (versionCode >= 3002850 && !facebookUrl.isEmpty()) {
                Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
            else {
                Uri uri = Uri.parse(facebookUrl);
                activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        }
        catch (PackageManager.NameNotFoundException e) {
            Uri uri = Uri.parse(facebookUrl);
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
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

    public static void logout(Context contexto) {

        if (!FacebookSdk.isInitialized()) {
            Facebook.contexto = contexto;
            FacebookSdk.sdkInitialize(contexto);

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            LoginManager.getInstance().logOut();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
