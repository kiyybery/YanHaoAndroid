package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.yanhao.main.yanhaoandroid.util.SettingPrefernceUtil;
import com.yanhao.main.yanhaoandroid.util.SwitchView;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class NotificationsFragment extends Fragment {

    private SwitchView view_switch1;
    private TextView tv_section_title_title;
    private ImageView iv_section_title_back;
    private Boolean isChecked = false;
    private SettingPrefernceUtil mSettingPrefernceUtil;

    public static NotificationsFragment newInstance() {

        NotificationsFragment fragment = new NotificationsFragment();
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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        mSettingPrefernceUtil = new SettingPrefernceUtil(getActivity());
        tv_section_title_title = (TextView) view.findViewById(R.id.tv_section_title_title);
        tv_section_title_title.setText("通知");
        iv_section_title_back = (ImageView) view.findViewById(R.id.iv_section_title_back);
        iv_section_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        view_switch1 = (SwitchView) view.findViewById(R.id.view_switch1);
        //view_switch1.toggleSwitch(mSettingPrefernceUtil.getSettingMsgNotification());

        view_switch1.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn() {
                view_switch1.toggleSwitch(true);
                Toast.makeText(getActivity(), "通知开", Toast.LENGTH_LONG).show();
                isChecked = true;
                mSettingPrefernceUtil.setSettingMsgNotification(true);
            }

            @Override
            public void toggleToOff() {
                view_switch1.toggleSwitch(false);
                Toast.makeText(getActivity(), "通知关", Toast.LENGTH_LONG).show();
                isChecked = false;
                mSettingPrefernceUtil.setSettingMsgNotification(false);
            }
        });
        view_switch1.toggleSwitch(mSettingPrefernceUtil.getSettingMsgNotification());
        return view;
    }
}
