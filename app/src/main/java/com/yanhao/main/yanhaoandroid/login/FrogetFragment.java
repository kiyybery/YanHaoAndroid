package com.yanhao.main.yanhaoandroid.login;

import android.app.Dialog;
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
import com.yanhao.main.yanhaoandroid.banner.T;
import com.yanhao.main.yanhaoandroid.util.SecurityUtil;
import com.yanhao.main.yanhaoandroid.util.StringUtil;
import com.yanhao.main.yanhaoandroid.util.TopBar;
import com.yanhao.main.yanhaoandroid.util.UIHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2015/10/28 0028.
 */
public class FrogetFragment extends Fragment implements View.OnClickListener {

    private EditText mEt_phone, mEt_froget_pwd, mAccount_froget_code_et;
    private Button mAccount_forget_get_code_btn, mBtnGetPwd;
    private ImageButton mForget_show_pwd;
    private final static int mMsgCodeRestBtn = 1;
    private final static int mMsgCodeCounting = 2;
    private static int mResendTime = 60;
    private final static int mMaxTime = 60;
    TopBar mTopBar;
    Dialog mDialog;

    private class ModifyPwdCallBack extends StringCallback {


        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                int ret = jsonObject.getInt("ret");
                if (ret == 1) {

                    T.show(getActivity(), jsonObject.getString("info"), 100);
                } else {

                    T.show(getActivity(), jsonObject.getString("info"), 100);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    MyInnerHandler mHander = new MyInnerHandler(this);

    public static FrogetFragment newInstance() {

        FrogetFragment frogetFragment = new FrogetFragment();
        Bundle data = new Bundle();
        data.putString("title", "忘记密码");
        frogetFragment.setArguments(data);
        return frogetFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //开启Handler,
    static class MyInnerHandler extends Handler {
        WeakReference<FrogetFragment> mFragReference;

        MyInnerHandler(FrogetFragment aFragment) {
            mFragReference = new WeakReference<>(aFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            FrogetFragment theFrag = mFragReference.get();
            if (!theFrag.isAdded()) {
                return;
            }
            switch (msg.what) {
                case mMsgCodeRestBtn:
                    mResendTime = mMaxTime;
                    theFrag.mAccount_forget_get_code_btn.setClickable(true);
                    theFrag.mAccount_forget_get_code_btn.setText(R.string.resend);
                    break;
                case mMsgCodeCounting:
                    mResendTime--;

                    if (mResendTime > 0) {
                        theFrag.mAccount_forget_get_code_btn.setClickable(false);
                        theFrag.mAccount_forget_get_code_btn.setText(theFrag.getString(R.string.send_code_counting_text, mResendTime));
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
        View view = inflater.inflate(R.layout.frogetpsw_fragment, container, false);

        mResendTime = mMaxTime;
        mEt_phone = (EditText) view.findViewById(R.id.et_phone);
        mEt_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEt_froget_pwd = (EditText) view.findViewById(R.id.et_froget_pwd);

        mAccount_froget_code_et = (EditText) view.findViewById(R.id.account_froget_code_et);

        mAccount_forget_get_code_btn = (Button) view.findViewById(R.id.account_froget_get_code_btn);
        mAccount_forget_get_code_btn.setOnClickListener(this);
        mForget_show_pwd = (ImageButton) view.findViewById(R.id.forget_show_pwd);
        mForget_show_pwd.setOnClickListener(this);

        mBtnGetPwd = (Button) view.findViewById(R.id.btnGetPwd);
        mBtnGetPwd.setOnClickListener(this);

        mTopBar = (TopBar) view.findViewById(R.id.topBar);
        mTopBar.setOnTopbarClickListener(new TopBar.topbarClickListener() {
            @Override
            public void leftClick() {
                getActivity().finish();
            }

            @Override
            public void rightClick() {

            }
        });

        // 控制topbar上组件的状态
        // params (0,true) 显示
        // params (1,false) 隐藏
        mTopBar.setButtonVisable(0, true);
        mTopBar.setButtonVisable(1, false);
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.forget_show_pwd:
                //控制密码的隐藏与显示
                if (mEt_froget_pwd.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    mEt_froget_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    // mForget_show_pwd.setImageResource(R.drawable.pwd_yes);
                    mForget_show_pwd.setBackgroundResource(R.drawable.pwd_yes);
                } else {
                    mEt_froget_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //mForget_show_pwd.setImageResource(R.drawable.pwd_no);
                    mForget_show_pwd.setBackgroundResource(R.drawable.pwd_no);
                }
                //变更属性后记得让光标显示在输入框最后
                Editable editable = mEt_froget_pwd.getText();
                Selection.setSelection(editable, editable.length());
                break;

            case R.id.account_froget_get_code_btn:
                String phone = mEt_phone.getText().toString();
                if (!StringUtil.isMobileNO(phone)) {

                    Toast.makeText(getActivity(), "您输入的手机号有误，请再次输入!!", Toast.LENGTH_LONG).show();
                } else {

                    sendCode();
                }

                break;

            case R.id.btnGetPwd:
                String phoneNUM = mEt_phone.getText().toString();
                String pwd = mEt_froget_pwd.getText().toString();
                String code = mAccount_froget_code_et.getText().toString();
                if (!StringUtil.isMobileNO(phoneNUM) || pwd.length() < 6 || code.length() < 0) {

                    Toast.makeText(getActivity(), "您输入的信息有误，请检查！！", Toast.LENGTH_LONG).show();
                } else {

                    getPwd();
                }
                break;

            default:
                break;

        }
    }

    public void sendCode() {

        mHander.sendEmptyMessage(mMsgCodeCounting);

        String str = "18101215049";
        String url = "http://210.51.190.27:8082/sendAuthCode.jspa";
        OkHttpUtils
                .post()
                .url(url)//
                .addParams("mobile", SecurityUtil.encrypt(mEt_phone.getText().toString()))//
                        //.addParams("password", "123")//
                .build()//
                .execute(new ModifyPwdCallBack());
        Toast.makeText(getActivity(), "正在向用户" + mEt_phone.getText() + "发送验证码，请注意查收！！", Toast.LENGTH_LONG).show();
    }

    public void getPwd() {

        modifyPwd();
        mDialog = UIHelper.buildConfirm(
                getActivity(),
                "用户" + mEt_phone.getText() + "密码重置！请牢记！！", "确定", "取消",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        getActivity().finish();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        getActivity().finish();
                    }
                });
        //Toast.makeText(getActivity(), "用户" + mEt_phone.getText() + "密码重置！请牢记！！", Toast.LENGTH_LONG).show();

    }

    private void modifyPwd() {

        String url = "http://210.51.190.27:8082/modifyPwd.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("mobile", SecurityUtil.encrypt(mEt_phone.getText().toString()))
                .addParams("code", mAccount_froget_code_et.getText().toString())
                .addParams("newPwd", SecurityUtil.encrypt(mEt_froget_pwd.getText().toString()))
                .build()
                .execute(new ModifyPwdCallBack());
    }
}
