package com.taxi.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sharma on 13/6/15.
 */
public class Preferences {

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.KEY_APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    //saving user id.
    public static void setUserId(Context context, String userId) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(Constants.KEY_USER_ID, userId);
        editor.commit();
    }

    public static String getUserId(Context context) {
        return getSharedPreferences(context).getString(Constants.KEY_USER_ID, "");
    }

}
