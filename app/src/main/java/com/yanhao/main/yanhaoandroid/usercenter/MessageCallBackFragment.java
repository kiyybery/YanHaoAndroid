package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.ChatItemListViewAdapter;
import com.yanhao.main.yanhaoandroid.bean.ChatListViewBean;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/25 0025.
 */
public class MessageCallBackFragment extends Fragment implements View.OnClickListener {

    private ListView mListView;
    private Button mSend_comment;
    private EditText mEt_comment;
    private ChatListViewBean mBean;
    private List<ChatListViewBean> mList_comment;
    private List<ChatListViewBean> mList_auto;
    private TextView mProblem_tv1, mProblem_tv2, mProblem_tv3, mProblem_tv4, mTitle;
    private ImageView mBackImage;

    final String auto_alert = "感谢您的反馈！燕好已经记录了您的问题，将与一个工作日内给您回复，请注意查收！";

    public static MessageCallBackFragment newInstance() {

        MessageCallBackFragment fragment = new MessageCallBackFragment();
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

        View view = inflater.inflate(R.layout.fragment_callback, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        mBackImage = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("意见反馈");

        mList_comment = new ArrayList<ChatListViewBean>();
        mList_auto = new ArrayList<ChatListViewBean>();
        mListView = (ListView) view.findViewById(R.id.listView_chat);
        mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        mSend_comment = (Button) view.findViewById(R.id.btn_send_comment);
        mSend_comment.setOnClickListener(this);
        mEt_comment = (EditText) view.findViewById(R.id.et_comment);
        mProblem_tv1 = (TextView) view.findViewById(R.id.problem_one);
        mProblem_tv1.setText(Html.fromHtml("<u>" + "如何在燕好心理预约咨询?" + "</u>"));
        mProblem_tv2 = (TextView) view.findViewById(R.id.problem_two);
        mProblem_tv2.setText(Html.fromHtml("<u>" + "怎样鉴定老师的专业性？" + "</u>"));
        mProblem_tv3 = (TextView) view.findViewById(R.id.problem_three);
        mProblem_tv3.setText(Html.fromHtml("<u>" + "怎样取消预约？" + "</u>"));
        mProblem_tv4 = (TextView) view.findViewById(R.id.problem_four);
        mProblem_tv4.setText(Html.fromHtml("<u>" + "取消预约后何时能够退款？" + "</u>"));
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_send_comment:

                mBean = new ChatListViewBean();
                String comment = mEt_comment.getText().toString();
                int type = 1;
                mBean.setText(comment);
                mBean.setType(type);
                mList_comment.add(mBean);
                mListView.setAdapter(new ChatItemListViewAdapter(getActivity(), mList_comment));

                scrollMyListViewToBottom();

                if (mList_comment == null) {
                    Toast.makeText(getActivity(), "您还没输入任何东西！", Toast.LENGTH_LONG).show();
                } else {
                    autoAlert();
                }

                break;
            default:
                break;
        }
    }

    public void autoAlert() {

        mBean = new ChatListViewBean();
        mBean.setText(auto_alert);
        mBean.setType(0);
        mList_comment.add(mBean);
        mListView.setAdapter(new ChatItemListViewAdapter(getActivity(), mList_comment));
        mEt_comment.setText("");
    }

    private void scrollMyListViewToBottom() {
        mListView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                mListView.setSelection(mListView.getCount() - 1);
            }
        });
    }
}
