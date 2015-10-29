package com.yanhao.main.yanhaoandroid.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.util.StringUtil;

/**
 * Created by Administrator on 2015/10/27 0027.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    private TextView mFrogetPwdtv;
    private EditText mLogin_username_et,mLogin_pw_et;
    private ImageButton mLogin_show_pwd;
    private Button mLogin_login_btn;
    private String mCurrentUsername, mCurrentPassword;
    final static int THRESHOLD = 11;

    public static LoginFragment newInstance(){
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment,container,false);

        mFrogetPwdtv = (TextView) view.findViewById(R.id.froget_tv);
        mFrogetPwdtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),FrogetPwdActivity.class);
                startActivity(intent);
            }
        });

        mLogin_username_et = (EditText) view.findViewById(R.id.login_username_et);
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
        switch (view.getId()){

            case R.id.login_show_pwd:

                //控制密码的隐藏与显示
                if (mLogin_pw_et.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    mLogin_pw_et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mLogin_show_pwd.setImageResource(R.drawable.pwd_yes);
                } else {
                    mLogin_pw_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mLogin_show_pwd.setImageResource(R.drawable.pwd_no);
                }
                //变更属性后记得让光标显示在输入框最后
                Editable editable = mLogin_pw_et.getText();
                Selection.setSelection(editable, editable.length());
                break;

            case R.id.login_login_btn:
                String text = mLogin_username_et.getText().toString();
                String pwd = mLogin_pw_et.getText().toString();

                if(!StringUtil.isMobileNO(text) || pwd.length() == 0){

                    Toast.makeText(getActivity(),"手机号输入错误或者输入为空!!",Toast.LENGTH_LONG).show();
                    return;
                }else {

                    login();
                }
                break;
        }
    }

    public void login(){

        Toast.makeText(getActivity(),"login success",Toast.LENGTH_LONG).show();
    }
}
