package com.yanhao.main.yanhaoandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.yanhao.main.yanhaoandroid.YanHao;


public class PrefHelper {

    private static final String defaultPrefName = "lib.pref";

    public static PrefHelper get() {
        return get(defaultPrefName);
    }

    public static PrefHelper get(String prefName) {
        return new PrefHelper(prefName);
    }

    public PrefHelper(String prefName) {
        this.prefName = prefName;
    }

    public boolean put(String key, String value) {
        return getPref().edit().putString(key, value).commit();
    }

    public boolean put(String key, int value) {
        return getPref().edit().putInt(key, value).commit();
    }

    public boolean put(String key, boolean value) {
        return getPref().edit().putBoolean(key, value).commit();
    }

    public boolean put(String key, float value) {
        return getPref().edit().putFloat(key, value).commit();
    }

    public float getFloat(String key, float defValue) {
        return getPref().getFloat(key, defValue);
    }

    public String getString(String key, String defValue) {
        return getPref().getString(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return getPref().getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return getPref().getInt(key, defValue);
    }

    public void clear() {
        getPref().edit().clear().commit();
    }

    private SharedPreferences pref;

    private String prefName = defaultPrefName;

    private SharedPreferences getPref() {
        if (this.pref == null) {
            this.pref = YanHao.get().getSharedPreferences(prefName,
                    Context.MODE_PRIVATE);
        }
        return pref;
    }
}
