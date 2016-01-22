package com.yanhao.main.yanhaoandroid.consult;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.adapter.AreaGridAdapter;
import com.yanhao.main.yanhaoandroid.bean.AreaItem;
import com.yanhao.main.yanhaoandroid.login.LoginActivity;
import com.yanhao.main.yanhaoandroid.util.TopBar;
import com.yanhao.main.yanhaoandroid.util.Type;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class ConsultFragment extends Fragment implements View.OnClickListener {

    public final static int REQUEST_MODIFY_GOLOGIN = 801;
    private GridView mGridView;
    private List<AreaItem> arealist = new ArrayList<AreaItem>();
    private TopBar mTopBar;
    private TextView mUnLogin_tv;
    private RelativeLayout mUnLogin_layout;

    private int[] areaimages = new int[]{
            R.drawable.marriage, R.drawable.children,
            R.drawable.workplace, R.drawable.mood,
            R.drawable.interpersonal, R.drawable.personal,
            R.drawable.sex, R.drawable.other
    };
    private String[] areatexts = new String[]{
            "婚姻关系", "青少年儿童", "职场困惑",
            "情绪与神经症", "人际社交", "个人成长",
            "性方面", "其他"
    };

    SharedPreferences sp;
    String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < areatexts.length; i++) {
            AreaItem areaItem = new AreaItem();
            areaItem.setImg(areaimages[i]);
            areaItem.setText(areatexts[i]);
            arealist.add(areaItem);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //EventBus.getDefault().register(ConsultFragment.this);
        //sp = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
        //userId = sp.getString("userId", "");
        View view = inflater.inflate(R.layout.frag_subarea, container, false);
        mGridView = (GridView) view.findViewById(R.id.AreaGridView);
        mGridView.setAdapter(new AreaGridAdapter(getActivity(), arealist));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ConsultListActivity.class);
                startActivity(intent);
            }
        });

        mUnLogin_layout = (RelativeLayout) view.findViewById(R.id.subarea_unlogin_layout);
        /*if (!userId.equals("")) {

            mUnLogin_layout.setVisibility(View.GONE);
        }*/
        mUnLogin_tv = (TextView) view.findViewById(R.id.tv_subarea_unlogin);
        mUnLogin_tv.setOnClickListener(this);
        mTopBar = (TopBar) view.findViewById(R.id.topBar_consult);
        mTopBar.setOnTopbarClickListener(new TopBar.topbarClickListener() {
            @Override
            public void leftClick() {

            }

            @Override
            public void rightClick() {
                //Toast.makeText(getActivity(), "区域选择", Toast.LENGTH_LONG).show();
                //// TODO: 2015/11/17 0017 是否需要跳转citylist？ 
            }
        });

        mTopBar.setButtonVisable(0, false);
        mTopBar.setButtonVisable(1, true);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(ConsultFragment.this);
    }

    /*public void onEventMainThread(Type type) {
        String msg = "onEventMainThread收到了消息：" + type.getMsg();
        Log.d("harvic", msg);
        if (type.getMsg().equals("11111")) {

            mUnLogin_layout.setVisibility(View.GONE);
        }
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_subarea_unlogin:

                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivityForResult(intent, REQUEST_MODIFY_GOLOGIN);
                break;

            default:
                break;
        }
    }
}
