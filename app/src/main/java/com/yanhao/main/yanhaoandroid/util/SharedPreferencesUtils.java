/**
 *
 */
package com.yanhao.main.yanhaoandroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by Abner on 15/2/26.
 * QQ 230877476
 * Email nimengbo@gmail.com
 */
public class SharedPreferencesUtils {
    /**
     * 文件名
     */
    private static final String FILE_NAME = "fun";

    private static final String TAG = "SPUtils";

    /**
     * 保存数据
     *
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String key, Object object) {
        if (context == null || TextUtils.isEmpty(key) || object == null) {
            return;
        }
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        } else if ("Double".equals(type)) {
            editor.putFloat(key, ((Double) object).floatValue());
        }
        editor.apply();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject 不能为null
     * @return
     */
    public static Object getParam(Context context, String key,
                                  Object defaultObject) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        } else if ("Double".equals(type)) {
            return Double.valueOf(sp.getFloat(key,
                    ((Double) defaultObject).floatValue()));
        }

        return null;
    }
}
