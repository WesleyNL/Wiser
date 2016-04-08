package br.com.app.api;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Jefferson on 04/04/2016.
 */
public class Facebook {
    private CallbackManager callbackManager;

    private String userName = "";
    private String profilePicture = "";
    private boolean requisitando;

    public Facebook(Activity contexto) {
        FacebookSdk.sdkInitialize(contexto);
        callbackManager = CallbackManager.Factory.create();
    }

    public HashMap<String, Contato> getProfiles(final List<String> usersID) {
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

                            requisitando = false;
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        requisitando = true;

        Bundle parametros = new Bundle();
        parametros.putString("ids", listUsersID);
        parametros.putString("fields", "name,picture{url}");
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

    public String getUser_Name(String userID) {
        userName = "";

        final GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + userID,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            JSONObject obj = response.getJSONObject();
                            userName = obj.optString("name");

                            requisitando = false;
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
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

    public Bitmap getProfilePicture(String userID) {
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

                            requisitando = false;
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
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

    @Deprecated
    public Bitmap getProfilePictureViaURL(String userID) {
        try {
            URL img_value = new URL("http://graph.facebook.com/" + userID + "/picture?type=large");
            return BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
