package com.yanhao.main.yanhaoandroid.usercenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.R;

/**
 * Created by Administrator on 2015/11/10 0010.
 */
public class UpdateNameFragment extends Fragment {

    private static final String TAG = UpdateNameFragment.class.getSimpleName();
    private EditText mUpdate_name_et;
    private String mNewName;
    private TextView mFinish_tv;

    public static UpdateNameFragment newInstance() {

        UpdateNameFragment fragment = new UpdateNameFragment();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updatename, container, false);
        mUpdate_name_et = (EditText) view.findViewById(R.id.update_name_et);

        mFinish_tv = (TextView) view.findViewById(R.id.tv_text_title_right);
        mFinish_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        return view;
    }

    public void save(){

        mNewName = mUpdate_name_et.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("username",mNewName);
        Toast.makeText(getActivity(),mNewName,Toast.LENGTH_LONG).show();
        finish(Activity.RESULT_OK,intent);
    }

    protected void finish(int resultCode, Intent data) {
        if (getActivity() != null) {
            getActivity().setResult(resultCode, data);
            getActivity().finish();
        }
    }
}
