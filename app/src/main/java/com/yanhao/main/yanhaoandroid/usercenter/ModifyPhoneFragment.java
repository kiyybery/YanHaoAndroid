package com.yanhao.main.yanhaoandroid.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.yanhao.main.yanhaoandroid.util.StringUtil;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2015/11/25 0025.
 */
public class ModifyPhoneFragment extends Fragment implements View.OnClickListener {

    private TextView mTitle;
    private ImageView mBackImage;
    private Button mGetCodeBtn, mCommitBnt;
    private EditText mGetPhoneNum_ed;

    private final static int mMsgCodeRestBtn = 1;
    private final static int mMsgCodeCounting = 2;
    private static int mResendTime = 60;
    private final static int mMaxTime = 60;

    MyInnerHandler mHander = new MyInnerHandler(this);


    public static ModifyPhoneFragment newInstance() {

        ModifyPhoneFragment fragment = new ModifyPhoneFragment();
        Bundle data = new Bundle();
        data.putString("title", " ");
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //开启Handler
    static class MyInnerHandler extends Handler {
        WeakReference<ModifyPhoneFragment> mFragReference;

        MyInnerHandler(ModifyPhoneFragment aFragment) {
            mFragReference = new WeakReference<>(aFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            ModifyPhoneFragment theFrag = mFragReference.get();
            if (!theFrag.isAdded()) {
                return;
            }
            switch (msg.what) {
                case mMsgCodeRestBtn:
                    mResendTime = mMaxTime;
                    theFrag.mGetCodeBtn.setClickable(true);
                    theFrag.mGetCodeBtn.setText(R.string.resend);
                    break;
                case mMsgCodeCounting:
                    mResendTime--;
                    if (mResendTime > 0) {
                        theFrag.mGetCodeBtn.setClickable(false);
                        theFrag.mGetCodeBtn.setText(theFrag.getString(R.string.send_code_counting_text, mResendTime));
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

        View view = inflater.inflate(R.layout.fragment_modify_phone, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        mBackImage = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("修改手机号");

        mGetCodeBtn = (Button) view.findViewById(R.id.account_modifyer_get_code_btn);
        mGetCodeBtn.setOnClickListener(this);
        mGetPhoneNum_ed = (EditText) view.findViewById(R.id.et_modify_username);
        mCommitBnt = (Button) view.findViewById(R.id.btnCommit);
        mCommitBnt.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.account_modifyer_get_code_btn:
                String num = mGetPhoneNum_ed.getText().toString();
                if (!StringUtil.isMobileNO(num)) {

                    Toast.makeText(getActivity(), "您输入的手机号有误，请再次输入!!", Toast.LENGTH_LONG).show();
                } else {

                    getCode();
                }
                break;

            case R.id.btnCommit:

                Intent intent = new Intent();
                intent.setClass(getActivity(), ModifyOKActivity.class);
                intent.putExtra("titleName", mTitle.getText());
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void getCode() {

        mHander.sendEmptyMessage(mMsgCodeCounting);
        Toast.makeText(getActivity(), "正在向用户" + mGetPhoneNum_ed.getText() + "发送验证码，请注意查收！！", Toast.LENGTH_LONG).show();
    }
}
