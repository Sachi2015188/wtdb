package com.example.administrator.walkerstoursapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Administrator on 2/26/2018.
 */

public class SessionManager {

    // Shared Preferences
    SharedPreferences sharedPrefer;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared Pref mode
    int PRIVATE_MODE = 0;

    // Shared Pref file name
    private static final String PREF_NAME = "MySession";

    // SHARED PREF KEYS FOR ALL DATA

    // User's UserId
    public static final String KEY_USERID = "userId";

    // User's categoryId
    public static final String KEY_CATID = "catId";

    // User's categoryType[Teacher, Student, etc.,]
    public static final String KEY_CATTYPE = "categoryType";

    // User's batchId[like class or level or batch]
    public static final String KEY_BATCHID = "batchId";



    // Constructor
    public SessionManager(Context context) {
        this.context = context;
        sharedPrefer = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPrefer.edit();
    }

    /**
     * Call this method on/after login to store the details in session
     * */

    public void createLoginSession(String userId, String catId, String catTyp, String batchId) {

        // Storing userId in pref
        editor.putString(KEY_USERID, userId);

        // Storing catId in pref
        editor.putString(KEY_CATID, catId);

        // Storing catType in pref
        editor.putString(KEY_CATTYPE, catTyp);

        // Storing catType in pref
        editor.putString(KEY_BATCHID, batchId);

        // commit changes
        editor.commit();
    }

    /**
     * Call this method anywhere in the project to Get the stored session data
     * */
    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<String, String>();
        user.put("userId",sharedPrefer.getString(KEY_USERID, null));
        user.put("batchId",sharedPrefer.getString(KEY_BATCHID, null));
        user.put("catId", sharedPrefer.getString(KEY_CATID, null));
        user.put("catType", sharedPrefer.getString(KEY_CATTYPE, null));

        return user;
    }
}
