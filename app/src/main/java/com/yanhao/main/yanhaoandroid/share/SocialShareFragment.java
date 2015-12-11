package com.yanhao.main.yanhaoandroid.share;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yanhao.main.yanhaoandroid.R;

/**
 * Created by Administrator on 2015/12/11 0011.
 */
public class SocialShareFragment extends DialogFragment implements View.OnClickListener {

    private static final String TAG = SocialShareFragment.class.getSimpleName();
    private Button mCancel_btn;

    public static SocialShareFragment getInstance() {
        SocialShareFragment f = new SocialShareFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        setStyle(style, theme);
        //data = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        getDialog().setCanceledOnTouchOutside(true);

        View view = inflater.inflate(R.layout.social_share_fragment, container, false);
        mCancel_btn = (Button) view.findViewById(R.id.btn_cancel_social_share);
        mCancel_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_cancel_social_share:
                getActivity().onBackPressed();
                break;

            default:

                break;
        }


    }
}
