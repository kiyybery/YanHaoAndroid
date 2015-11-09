package com.yanhao.main.yanhaoandroid.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;

/**
 * Created by Administrator on 2015/10/29 0029.
 */
public class TopBar extends RelativeLayout{

    // 包含topbar上的元素：左按钮、右按钮、标题
    private Button mLeftButton, mRightButton;
    private TextView mTitleView;

    // 布局属性，用来控制组件元素在ViewGroup中的位置
    private LayoutParams mLeftParams, mTitleParams, mRightParams;

    // 左按钮的属性值，即我们在atts.xml文件中定义的属性
    private int mLeftTextColor;
    private Drawable mLeftBackground;
    private String mLeftText;
    // 右按钮的属性值，即我们在atts.xml文件中定义的属性
    private int mRightTextColor;
    private Drawable mRightBackground;
    private String mRightText;
    // 标题的属性值，即我们在atts.xml文件中定义的属性
    private float mTitleTextSize;
    private int mTitleTextColor;
    private String mTitle;

    // 映射传入的接口对象
    private topbarClickListener mListener;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        //设置TopBar的背景色
        setBackgroundColor(0xff0a82e1);

        //通过TypedArray取出对应值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        //取出attrs中的属性值
        mLeftTextColor = ta.getColor(R.styleable.TopBar_costumLeftTextColor, 0);
        mLeftBackground = ta.getDrawable(R.styleable.TopBar_costumLeftTextBackground);
        mLeftText = ta.getString(R.styleable.TopBar_costumLeftText);

        mRightTextColor = ta.getColor(R.styleable.TopBar_costumRightTextColor, 0);
        mRightBackground = ta.getDrawable(R.styleable.TopBar_costumRightTextBackground);
        mRightText = ta.getString(R.styleable.TopBar_costumRightText);

        mTitleTextSize = ta.getDimension(R.styleable.TopBar_costumTitleTextSize, 10);
        mTitleTextColor = ta.getColor(R.styleable.TopBar_costumTitleTextColor, 0);
        mTitle = ta.getString(R.styleable.TopBar_costumTitle);

        //释放ta
        ta.recycle();

        //实例化组件
        mLeftButton = new Button(context);
        mRightButton = new Button(context);
        mTitleView = new TextView(context);

        //为TopBar的元素赋值
        mLeftButton.setTextColor(mLeftTextColor);
        mLeftButton.setBackground(mLeftBackground);
        mLeftButton.setText(mLeftText);
        mRightButton.setTextColor(mRightTextColor);
        mRightButton.setBackground(mRightBackground);
        mRightButton.setText(mRightText);
        mTitleView.setTextColor(mTitleTextColor);
        mTitleView.setText(mTitle);
        mTitleView.setTextSize(mTitleTextSize);
        mTitleView.setGravity(Gravity.CENTER);

        //为组件设置相应的布局元素
        mLeftParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        //添加到viewGroup
        addView(mLeftButton, mLeftParams);
        mRightParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(mRightButton, mRightParams);
        mTitleParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mTitleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(mTitleView, mTitleParams);

        //按钮的点击事件
        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.leftClick();
            }
        });

        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.rightClick();
            }
        });
    }

    // 暴露一个方法给调用者来注册接口回调
    // 通过接口来获得回调者对接口方法的实现
    public void setOnTopbarClickListener(topbarClickListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 设置按钮的显示与否 通过id区分按钮，flag区分是否显示
     *
     * @param id   id
     * @param flag 是否显示
     */
    public void setButtonVisable(int id, boolean flag) {
        if (flag) {
            if (id == 0) {
                mLeftButton.setVisibility(View.VISIBLE);
            } else {
                mRightButton.setVisibility(View.VISIBLE);
            }
        } else {
            if (id == 0) {
                mLeftButton.setVisibility(View.GONE);
            } else {
                mRightButton.setVisibility(View.GONE);
            }
        }
    }

    public interface topbarClickListener{
        // 左按钮点击事件
        void leftClick();
        // 右按钮点击事件
        void rightClick();
    }
}
