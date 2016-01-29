package com.yanhao.main.yanhaoandroid.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.MainActivity;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.banner.T;
import com.yanhao.main.yanhaoandroid.http.HttpHeaders;
import com.yanhao.main.yanhaoandroid.http.NHttpCallback;
import com.yanhao.main.yanhaoandroid.http.NHttpProxy;
import com.yanhao.main.yanhaoandroid.http.NHttpRequest;
import com.yanhao.main.yanhaoandroid.http.NHttpResponse;
import com.yanhao.main.yanhaoandroid.util.ACache;
import com.yanhao.main.yanhaoandroid.util.CommonUtils;
import com.yanhao.main.yanhaoandroid.util.CustomProgressDialog;
import com.yanhao.main.yanhaoandroid.util.LogUtil;
import com.yanhao.main.yanhaoandroid.util.PrefHelper;
import com.yanhao.main.yanhaoandroid.util.SecurityUtil;
import com.yanhao.main.yanhaoandroid.util.SharedPreferencesUtils;
import com.yanhao.main.yanhaoandroid.util.StringUtil;
import com.yanhao.main.yanhaoandroid.util.Type;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/10/27 0027.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private TextView mFrogetPwdtv;
    private EditText mLogin_username_et, mLogin_pw_et;
    private ImageButton mLogin_show_pwd;
    private Button mLogin_login_btn;
    private String userId;
    private int userType;
    private String portraitUrl;

    ACache mCache;

    private class MyLoginCallback extends StringCallback {

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonObject = new JSONObject(s);
                int ret = jsonObject.getInt("ret");

                if (ret == 1) {

                    String info = jsonObject.getString("info");
                    //T.show(getActivity(), info, 100);

                } else if (ret == 0) {

                    userId = jsonObject.getString("userId");
                    userType = jsonObject.getInt("userType");
                    portraitUrl = jsonObject.getString("portraitUrl");
                    //String nickName = jsonObject.getString("nickName");
                    //String portraitUrl = jsonObject.getString("portraitUrl");
                    //Log.i("userId_login", userId);

                    Message message = new Message();
                    message.arg1 = 1;
                    mHnadler.sendMessage(message);

                    Toast.makeText(getActivity(), userId, Toast.LENGTH_LONG).show();
                    /*Intent i = new Intent(getActivity(), MainActivity.class);
                    i.putExtra("userId", userId);
                    startActivity(i);

                    SharedPreferences sp = getActivity().getSharedPreferences("userId", getActivity().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("userId", userId);
                    editor.putString("nickName", nickName);
                    //editor.putString("portraitUrl", portraitUrl);
                    editor.putString("userPhone", mLogin_username_et.getText().toString());
                    editor.putString("password", mLogin_pw_et.getText().toString());
                    editor.commit();*/

                    //getActivity().finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    private Handler mHnadler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.arg1) {

                case 1:
                    //Toast.makeText(getActivity(), "login success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("userId", userId);
                    finish(Activity.RESULT_OK, intent);

                    /*Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    getActivity().finish();*/

                    SharedPreferences sp = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("username", mLogin_username_et.getText().toString());
                    editor.putString("password", mLogin_pw_et.getText().toString());
                    editor.putString("userId", userId);
                    editor.commit();

                    PrefHelper.get().put("userId", userId);
                    PrefHelper.get().put("userType", userType);
                    PrefHelper.get().put("portraitUrl", portraitUrl);

                    EventBus.getDefault().post(new Type(mLogin_username_et.getText().toString(), mLogin_pw_et.getText().toString()));
                    break;

                case 2:

                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        mCache = ACache.get(getActivity());
        mFrogetPwdtv = (TextView) view.findViewById(R.id.froget_tv);
        mFrogetPwdtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FrogetPwdActivity.class);
                startActivity(intent);
            }
        });

        mLogin_username_et = (EditText) view.findViewById(R.id.login_username_et);
        mLogin_username_et.setInputType(InputType.TYPE_CLASS_NUMBER);
        mLogin_pw_et = (EditText) view.findViewById(R.id.login_pw_et);

        // 如果用户名改变，清空密码
        mLogin_username_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mLogin_pw_et.setText(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mLogin_show_pwd = (ImageButton) view.findViewById(R.id.login_show_pwd);
        mLogin_show_pwd.setOnClickListener(this);
        mLogin_login_btn = (Button) view.findViewById(R.id.login_login_btn);
        mLogin_login_btn.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.login_show_pwd:

                //控制密码的隐藏与显示
                if (mLogin_pw_et.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    mLogin_pw_et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //mLogin_show_pwd.setImageResource(R.drawable.pwd_yes);
                    mLogin_show_pwd.setBackgroundResource(R.drawable.pwd_yes);
                } else {
                    mLogin_pw_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //mLogin_show_pwd.setImageResource(R.drawable.pwd_no);
                    mLogin_show_pwd.setBackgroundResource(R.drawable.pwd_no);
                }
                //变更属性后记得让光标显示在输入框最后
                Editable editable = mLogin_pw_et.getText();
                Selection.setSelection(editable, editable.length());
                break;

            case R.id.login_login_btn:
                if (!CommonUtils.isNetWorkConnected(getActivity())) {
                    Toast.makeText(getActivity(), R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
                    return;
                }
                String text = mLogin_username_et.getText().toString();
                String pwd = mLogin_pw_et.getText().toString();

                if (!StringUtil.isMobileNO(text) || pwd.length() == 0) {

                    Toast.makeText(getActivity(), "手机号输入错误或者输入为空!!", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    /*new Thread(new Runnable() {
                        @Override
                        public void run() {

                            login();
                        }
                    }
                    ).start();*/

                    String user = mLogin_username_et.getText().toString();
                    String des_user = SecurityUtil.encrypt(user);
                    String url_user = null;
                    try {
                        url_user = URLEncoder.encode(des_user, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String url = "http://210.51.190.27:8082/login.jspa";
                    OkHttpUtils
                            .get()//
                            .url(url)//
                            .addParams("mobile", url_user)//
                            .addParams("password", SecurityUtil.encrypt(mLogin_pw_et.getText().toString()))//
                                    //.addParams("mobile", "q6Zz8HYfnrSZtEm6oS/b1w==")
                                    //.addParams("password", "jGvZ4024/fI=")
                            .build()//
                            .execute(new MyLoginCallback());
                }
                break;
        }
    }

    public void login() {
        Message message = new Message();
        message.arg1 = 1;
        mHnadler.sendMessage(message);
    }

    protected void finish(int resultCode, Intent data) {
        if (getActivity() != null) {
            getActivity().setResult(resultCode, data);
            getActivity().finish();
        }
    }
}
