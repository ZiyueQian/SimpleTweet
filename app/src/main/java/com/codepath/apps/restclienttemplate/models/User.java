package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {
    public String name;
    public String screenName;
    public String imageURL;

    public User() {
    } //parcel lib

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.imageURL = jsonObject.getString("profile_image_url_https");
        return user;
    }
}
