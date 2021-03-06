package com.yanhao.main.yanhaoandroid.usercenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.util.PrefHelper;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/11/12 0012.
 */
public class ProFireFrag extends Fragment implements View.OnClickListener {

    private static final int MAX_LENGTH = 120; // 最多支持120字
    private EditText mProfire_et;
    private TextView feedbackRemainNum, mTitle, mfinish;
    private LinearLayout mBack;

    public static ProFireFrag newInstance() {

        ProFireFrag fragment = new ProFireFrag();
        Bundle data = new Bundle();
        data.putString("title", " ");
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profire, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);
        feedbackRemainNum = (TextView) view.findViewById(R.id.num_words);
        mTitle = (TextView) view.findViewById(R.id.tv_profire_title_title);
        mTitle.setText("个人简介");
        mfinish = (TextView) view.findViewById(R.id.tv_profire_title_right);
        mfinish.setOnClickListener(this);
        mBack = (LinearLayout) view.findViewById(R.id.ll_profire_title_back);
        mBack.setOnClickListener(this);
        mProfire_et = (EditText) view.findViewById(R.id.etprofireContent);
        mProfire_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int i1, int i2) {
                String inputString = s.toString();
                String outputString = stringFilter(inputString);
                if (!inputString.equals(outputString)) {
                    mProfire_et.setText(outputString);
                    mProfire_et.setSelection(outputString.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String inputString = editable.toString();
                int left = MAX_LENGTH - inputString.length();
                feedbackRemainNum.setText("" + left);
                if (inputString.length() == MAX_LENGTH) {
                    Toast.makeText(getActivity(), MAX_LENGTH + "已满",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        mProfire_et.setText(PrefHelper.get().getString("intro", ""));
        return view;
    }

    protected String stringFilter(String string) {
        Log.i("in2Filter-----", string);
        final String RE = "[\\u4e00-\\u9fa5]+";
        Pattern p = Pattern.compile(RE);
        Matcher m = p.matcher(string);
        String fk = m.replaceAll("").trim();
        return string.replaceAll(fk, "");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_profire_title_right:

                save();
                break;
            case R.id.ll_profire_title_back:

                save();
                break;
            default:
                break;
        }

    }

    private void save() {

        Intent intent = new Intent();
        intent.putExtra("intro", mProfire_et.getText().toString());
        finish(Activity.RESULT_OK, intent);
    }

    protected void finish(int resultCode, Intent data) {
        if (getActivity() != null) {
            getActivity().setResult(resultCode, data);
            getActivity().finish();
        }
    }
}
