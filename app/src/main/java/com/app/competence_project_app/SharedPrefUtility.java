package com.app.competence_project_app;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public final class SharedPrefUtility {
    private static final String preferenceName = "APP_PREF";
    private static final Gson gson = new Gson();

    private SharedPrefUtility() { }

    public static Object getDataByName(String name, Class<?> modelClass, Context context) {
        SharedPreferences pref = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        String json = pref.getString(name, "");

        return gson.fromJson(json, modelClass);
    }

    public static void setDataByName(String name, Object obj, Context context) {
        SharedPreferences pref = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String json = gson.toJson(obj);
        editor.putString(name, json);
        editor.apply();
    }

    public static void removeDataByName(String name, Context context) {
        SharedPreferences pref = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(name);
        editor.apply();
    }
}
