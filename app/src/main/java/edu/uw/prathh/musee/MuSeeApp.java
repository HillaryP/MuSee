package edu.uw.prathh.musee;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.parse.Parse;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hillaryprather on 3/7/15.
 */
public class MuSeeApp extends Application {
    public static final String PREFS_NAME = "MyPrefs";
    private SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/Avenir.ttc");

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "0gdTYz0qLwmWSo1X1R7f0BySH5RUpw7X2gkzJPRC", "SVheNhubmxUkEprZ67RObXf98rdU7KRKSN7WL61y");

        Log.i("MuSeeApp", "Application created");
    }

    public String getAccessToken() {
        return prefs.getString("accessToken", null);
    }

    public void setAccessToken(String token) {
        Log.i("ChangeApp", "Access token set to: " + token);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("accessToken", token);
        editor.apply();
    }

    public Set<String> getFavorites() {
        return prefs.getStringSet("list", null);
    }

    public void addToFavorites(String artifactName) {
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> favorites = new HashSet<>(prefs.getStringSet("list", null));
        favorites.add(artifactName);
        editor.putStringSet("list", favorites);
        editor.apply();
    }

    public void removeFromFavorites(String artifactName) {
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> favorites = new HashSet<>(prefs.getStringSet("list", null));
        favorites.remove(artifactName);
        editor.putStringSet("list", favorites);
        editor.apply();
    }
}
