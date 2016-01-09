package com.yanhao.main.yanhaoandroid.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.banner.T;
import com.yanhao.main.yanhaoandroid.http.HttpHeaders;
import com.yanhao.main.yanhaoandroid.http.NHttpCallback;
import com.yanhao.main.yanhaoandroid.http.NHttpProxy;
import com.yanhao.main.yanhaoandroid.http.NHttpRequest;
import com.yanhao.main.yanhaoandroid.http.NHttpResponse;
import com.yanhao.main.yanhaoandroid.util.Des;
import com.yanhao.main.yanhaoandroid.util.LogUtil;
import com.yanhao.main.yanhaoandroid.util.SecurityUtil;
import com.yanhao.main.yanhaoandroid.util.StringUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2015/10/27 0027.
 */
public class RigstFragment extends Fragment implements View.OnClickListener {

    private EditText mEt_regist_username, mEt_regist_pwd, mAccount_register_code_et;
    private Button mAccount_register_get_code_btn, btnRegister;
    private ImageButton mRigst_show_pwd;
    private final static int mMsgCodeRestBtn = 1;
    private final static int mMsgCodeCounting = 2;
    private static int mResendTime = 60;
    private final static int mMaxTime = 60;

    private class MyRightCallback extends StringCallback {


        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }
    }

    private class RigstCallBack extends StringCallback {

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {

                JSONObject jsonObject = new JSONObject(s);
                String ret = jsonObject.getString("ret");
                if (ret.equals("0")) {

                    String des_userId = jsonObject.getString("userId");
                    String userId = SecurityUtil.decrypt(des_userId);
                    Toast.makeText(getActivity(), userId, Toast.LENGTH_LONG).show();
                } else {

                    String info = jsonObject.getString("info");
                    T.show(getActivity(), info, 100);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    MyInnerHandler mHander = new MyInnerHandler(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //开启Handler
    static class MyInnerHandler extends Handler {
        WeakReference<RigstFragment> mFragReference;

        MyInnerHandler(RigstFragment aFragment) {
            mFragReference = new WeakReference<>(aFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            RigstFragment theFrag = mFragReference.get();
            if (!theFrag.isAdded()) {
                return;
            }
            switch (msg.what) {
                case mMsgCodeRestBtn:
                    mResendTime = mMaxTime;
                    theFrag.mAccount_register_get_code_btn.setClickable(true);
                    theFrag.mAccount_register_get_code_btn.setText(R.string.resend);
                    break;
                case mMsgCodeCounting:
                    mResendTime--;
                    if (mResendTime > 0) {
                        theFrag.mAccount_register_get_code_btn.setClickable(false);
                        theFrag.mAccount_register_get_code_btn.setText(theFrag.getString(R.string.send_code_counting_text, mResendTime));
                        sendEmptyMessageDelayed(mMsgCodeCounting, 1000);
                    } else {
                        sendEmptyMessageDelayed(mMsgCodeRestBtn, 1000);
                    }
                    break;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right_fragment, container, false);

        mResendTime = mMaxTime;
        mEt_regist_username = (EditText) view.findViewById(R.id.et_regist_username);
        mEt_regist_username.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEt_regist_pwd = (EditText) view.findViewById(R.id.et_regist_pwd);
        mAccount_register_code_et = (EditText) view.findViewById(R.id.account_register_code_et);
        mAccount_register_code_et.setInputType(InputType.TYPE_CLASS_NUMBER);
        mAccount_register_get_code_btn = (Button) view.findViewById(R.id.account_register_get_code_btn);
        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        mRigst_show_pwd = (ImageButton) view.findViewById(R.id.rigst_show_pwd);

        mAccount_register_get_code_btn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        mRigst_show_pwd.setOnClickListener(this);
        mAccount_register_get_code_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rigst_show_pwd:
                //控制密码的隐藏与显示
                if (mAccount_register_code_et.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    mAccount_register_code_et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mRigst_show_pwd.setImageResource(R.drawable.pwd_yes);
                } else {
                    mAccount_register_code_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mRigst_show_pwd.setImageResource(R.drawable.pwd_no);
                }
                //变更属性后记得让光标显示在输入框最后
                Editable editable = mAccount_register_code_et.getText();
                Selection.setSelection(editable, editable.length());
                break;

            case R.id.account_register_get_code_btn:
                String phone = mEt_regist_username.getText().toString();

                if (!StringUtil.isMobileNO(phone)) {
                    Toast.makeText(getActivity(), "您输入的手机号有误，请再次输入!!", Toast.LENGTH_LONG).show();
                } else {
                    getRigstCode();
                }
                break;

            case R.id.btnRegister:
                String user = mEt_regist_username.getText().toString();
                String rigstCode = mEt_regist_pwd.getText().toString();
                String pwd = mAccount_register_code_et.getText().toString();
                if (!StringUtil.isMobileNO(user) || rigstCode.length() < 4 || pwd.length() < 6) {
                    Toast.makeText(getActivity(), "您的信息填写不准确，请检查！！", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    rigst();
                }
                break;
        }
    }

    //获取验证码
    public void getRigstCode() {

        String str = "18101215049";
        String url = "http://210.51.190.27:8082/sendAuthCode.jspa";
        String result = SecurityUtil.encrypt(str);
        mHander.sendEmptyMessage(mMsgCodeCounting);
        Toast.makeText(getActivity(), "正在向用户" + mEt_regist_username.getText() + "发送验证码，请注意查收！！", Toast.LENGTH_LONG).show();
        OkHttpUtils
                .post()
                .url(url)//
                .addParams("mobile", result)//
                        //.addParams("password", "123")//
                .build()//
                .execute(new MyRightCallback());
    }

    public void rigst() {

        //Toast.makeText(getActivity(), "注册。。。", Toast.LENGTH_LONG).show();
        String regist_url = "http://210.51.190.27:8082/register.jspa";
        OkHttpUtils
                .post()
                .url(regist_url)
                .addParams("mobile", SecurityUtil.encrypt(mEt_regist_username.getText().toString()))
                .addParams("password", SecurityUtil.encrypt(mAccount_register_code_et.getText().toString()))
                .addParams("code", mEt_regist_pwd.getText().toString())
                .build()
                .execute(new RigstCallBack());
    }
}
