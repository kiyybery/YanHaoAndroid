package com.yanhao.main.yanhaoandroid.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.RecommendAdapter;
import com.yanhao.main.yanhaoandroid.bean.Recommend;
import com.yanhao.main.yanhaoandroid.bean.UserInfo;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015/12/7 0007.
 */
public class RecommendFragment extends Fragment {

    private List<Recommend> mList;
    private Recommend mRecommendBean;
    private ListView mListView;
    private TextView mTitle;
    private LinearLayout mBack;
    private RecommendAdapter mAdapter;

    private class MyRecommedCallback extends StringCallback{


        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("recommedList");
                for (int i = 0; i < jsonArray.length(); i++) {

                    mRecommendBean = new Recommend();
                    JSONObject job = (JSONObject) jsonArray.get(i);
                    mRecommendBean.userId = job.getInt("userId");
                    mRecommendBean.pic = job.getString("photoUrl");
                    mRecommendBean.subName = job.getString("name");
                    mRecommendBean.level_pic = job.getInt("level");
                    mRecommendBean.distence = job.getString("address");
                    mRecommendBean.describe = job.getString("intro");

                    mList.add(mRecommendBean);
                }
                mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public static RecommendFragment newInstance() {

        RecommendFragment fragment = new RecommendFragment();
        Bundle data = new Bundle();
        data.putString("title", " ");
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getData();
        getCommendData();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        mList = new ArrayList<>();

        mListView = (ListView) view.findViewById(R.id.recommend_listview);
        mAdapter = new RecommendAdapter(getActivity(),mList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), HomePageActivity.class);
                intent.putExtra("userId",mList.get(i).userId);
                startActivity(intent);
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("咨询师");
        mBack = (LinearLayout) view.findViewById(R.id.ll_section_title_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return view;
    }

    public void getData() {

        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            mRecommendBean = new Recommend();
            mRecommendBean.setSubName("张朝阳" + i);
            mRecommendBean.setDistence("北京" + i);
            mRecommendBean.setDescribe("华东师范大学心理咨询中心主任，副教授，心理学博士，中国心理卫生协会理事，" +
                    "中国心理学会临床与咨询心理学专业机构与专业人员注册系统首批注册心理督导师和注册心理师、上海心理" +
                    "咨询行业协会副会长、上海高校心理咨询协会副会长兼学术部部长、上海市心理卫生学会理事、上海心理学" +
                    "会心理咨询和心理治疗专业委员会副主任委员、上海市心理咨询师职业培训项目专家委员会委员、上海市教" +
                    "委学校心理咨询师培训项目专家委员会委员、《大众心理学》杂志编委，多家管理咨询公司的签约讲师。他" +
                    "有长达近二十年心理咨询、训练和培训的经验，擅长团体心理训练、人际关系和情绪问题的心理辅导、青少" +
                    "年心理咨询，在企业管理心理培训、人事测评和员工心理援助领域也有丰富的经验。他擅长的课程包括职业" +
                    "压力处理、管理沟通、自我探索、团队建设等。接受过他培训及服务的企业及单位包括：平安保险、太平洋" +
                    "安泰保险、浦发银行、招商银行、上海电信、贝尔阿尔卡特、BP石油、3M公司、英业达、京滨电子、东方航" +
                    "空、宝钢集团、延锋伟世通、小糸车灯、上海通用汽车、两面针集团、上海机场、中智公司、上海烟草、上" +
                    "海电力、德力西集团、上海市人事局、上海市检察院等上百企事业单位以及交通大学、复旦大学、清华大学、" +
                    "东华大学、华东理工等多家大学管理学院总裁班或MBA班、各大中小学教师培训等等。他著有《心理咨询师对你说》、" +
                    "《生存心境界》、《心理侦探》等书二十多本，并曾为数十家报刊开设心理专栏或撰稿，发表文章达百万字。");
            mList.add(mRecommendBean);
        }
    }

    private void getCommendData(){

        String url = "http://7xop51.com1.z0.glb.clouddn.com/home_recommed_constant.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo("zhy", "123")))
                .build()
                .execute(new MyRecommedCallback());
    }

}
