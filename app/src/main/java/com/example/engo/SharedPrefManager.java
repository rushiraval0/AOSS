package com.example.engo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Belal on 9/5/2017.
 */

//here for this class we are using a singleton pattern

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_PASS = "keygender";
    private static final String KEY_ID = "keyid";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_ROLE = "keyadd";
    private static final String KEY_STATUS = "keystate";
    private static final String KEY_FLAG = "keycity";
    private static final String KEY_PROOF = "keycountry";
    private static final String KEY_PROFILE = "keyprofile";
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, user.getU_id());
        editor.putString(KEY_EMAIL, user.getU_email());
        editor.putString(KEY_PASS, user.getU_password());
        editor.putString(KEY_STATUS, user.getU_status());
        editor.putString(KEY_ROLE, user.getU_role());
        editor.putString(KEY_USERNAME, user.getU_name());
        editor.putString(KEY_PROFILE, user.getProfile_pic());
        editor.putString(KEY_PHONE, user.getPhone_no());
        editor.putString(KEY_PROOF, user.getProof());
        editor.putString(KEY_FLAG, user.getFlag());

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PASS, null),
                sharedPreferences.getString(KEY_STATUS, null),
                sharedPreferences.getString(KEY_ROLE, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_PROFILE, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_PROOF, null),
                sharedPreferences.getString(KEY_FLAG, null)


        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}