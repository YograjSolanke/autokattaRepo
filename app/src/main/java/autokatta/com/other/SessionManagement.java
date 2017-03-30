package autokatta.com.other;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import autokatta.com.R;
import autokatta.com.view.LoginActivity;

/**
 * Created by ak-001 on 27/3/17.
 */

public class SessionManagement {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    int PRIVATE_MODE = 0;

    // Shared Preferences file name
    private static final String PREF_NAME = "autokatta_login";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_USERNAME = "user_name";
    private static final String KEY_PWD = "password";

    public SessionManagement(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(context.getString(R.string.my_preference), PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String username, String password) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing username in pref
        editor.putString(KEY_USERNAME, username);

        // Storing password in pref
        editor.putString(KEY_PWD, password);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            // Closing all the Activities
            // Add new Flag to start new Activity
            // Staring Login Activity
            _context.startActivity(new Intent(_context, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));

        // user password
        user.put(KEY_PWD, pref.getString(KEY_PWD, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        // Closing all the Activities
        // Add new Flag to start new Activity

        // Staring Login Activity
        _context.startActivity(new Intent(_context, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * Quick check for login
     * *
     */
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
