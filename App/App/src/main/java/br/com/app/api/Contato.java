package br.com.app.api;

import android.graphics.Bitmap;

/**
 * Created by Jefferson on 06/04/2016.
 */
public class Contato {
    private String userID = "";
    private String userName = "";
    private String profilePictureURL = "";
    private Bitmap profilePicture;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public Bitmap getProfilePicture() {
        return this.profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }
}
