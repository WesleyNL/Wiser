package br.com.app.api;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.app.activity.login.IFacebook;

/**
 * Created by Jefferson on 04/04/2016.
 */
public class Facebook {
    private CallbackManager callbackManager;

    private static String user_name = "";

    public Facebook(Activity contexto) {
        FacebookSdk.sdkInitialize(contexto);
        callbackManager = CallbackManager.Factory.create();
    }

    public String getUser_Name(String user_ID) {

        final GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + user_ID,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            JSONObject obj = response.getJSONObject();
                            user_name = obj.optString("name");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,picture");
        request.setParameters(parameters);

        new Thread() {
            public void run() {
                request.executeAndWait();
            }
        }.start();

        while (user_name.equals(""));

        return user_name;
    }
}
