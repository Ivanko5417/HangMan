package com.hangman.database;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class LocalStorage {
    SharedPreferences sPref;

    public LocalStorage(Activity activity) {
        sPref = activity.getPreferences(MODE_PRIVATE);
    }

    public void setItem(String key, String value) {
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(key, value);
        ed.commit();
    }

    public String getItem(String key) {
        return sPref.getString(key, "0");
    }
}
