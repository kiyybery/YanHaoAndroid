package com.yanhao.main.yanhaoandroid.login;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.R;

/**
 * Created by Administrator on 2015/10/29 0029.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mLoginBtn,mRigstBtn;
    private View mPinnedIndicator;
    private float mSlidlogin, mSlidrigst;
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft;
    private LoginFragment mLoginFragment;
    private RigstFragment mRigstFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
        setListener();
        if(savedInstanceState == null){
            ft = fm.beginTransaction();
        }

        FragmentTransaction mFragementTransaction = getSupportFragmentManager()
                .beginTransaction();
        mFragementTransaction.commit();
        mLoginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mLoginFragment).commit();
    }

    public void initview() {
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mRigstBtn = (Button) findViewById(R.id.rigst_btn);
        mPinnedIndicator = findViewById(R.id.pinnedIndicator);
    }

    public void setListener() {
        mLoginBtn.setOnClickListener(this);
        mRigstBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        initIndexOffset();

        switch (view.getId()){

            case R.id.login_btn:
                if(isRight){

                    return;
                }
                //Toast.makeText(MainActivity.this, "login", Toast.LENGTH_LONG).show();
                /*ft.replace(android.R.id.content, LoginFragment.newInstance());
                ft.addToBackStack(null);
                ft.commit();*/
                FragmentTransaction mFragementTransaction = getSupportFragmentManager()
                        .beginTransaction();
                mFragementTransaction.commit();
                mLoginFragment = new LoginFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, mLoginFragment).commit();
                mPinnedIndicator.animate().x(mSlidlogin);
                mPinnedIndicator.setX(mSlidlogin);
                isRight = false;
                break;

            case R.id.rigst_btn:
                if(isRight){

                    return;
                }

                mFragementTransaction = getSupportFragmentManager()
                        .beginTransaction();
                mFragementTransaction.commit();
                mRigstFragment = new RigstFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, mRigstFragment).commit();
                Toast.makeText(LoginActivity.this, "rigst", Toast.LENGTH_LONG).show();
                mPinnedIndicator.animate().x(mSlidrigst);
                mPinnedIndicator.setX(mSlidrigst);
                isRight = false;
                break;

            default:
                break;
        }
    }

    private boolean isRight;

    public void initIndexOffset(){
        mSlidlogin = mLoginBtn.getX();
        mSlidrigst = mRigstBtn.getX();
    }
}
