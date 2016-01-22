package com.yanhao.main.yanhaoandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.banner.anim.select.RotateEnter;
import com.flyco.banner.anim.select.ZoomInEnter;
import com.yanhao.main.yanhaoandroid.banner.DataProvider;
import com.yanhao.main.yanhaoandroid.login.LoginActivity;
import com.yanhao.main.yanhaoandroid.util.ViewFindUtils;

/**
 * Created by Administrator on 2016/1/13 0013.
 */
public class UserGuideActivity extends Activity {

    private Context context = this;
    private View decorView;
    private boolean isFromBannerHome;
    private Class<? extends ViewPager.PageTransformer> transformerClass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        isFromBannerHome = getIntent().getBooleanExtra("isFromBannerHome", false);
        int position = getIntent().getIntExtra("position", -1);
        transformerClass = position != -1 ? DataProvider.transformers[position] : null;

        decorView = getWindow().getDecorView();
        sgb();
    }

    private void sgb() {
        SimpleGuideBanner sgb = ViewFindUtils.find(decorView, R.id.sgb);

        sgb
                .setIndicatorSelectColor(0xFF000FFF)
                .setIndicatorUnselectColor(0xFFCCCCCC)
                .setIndicatorWidth(5)
                .setIndicatorHeight(5)
                .setIndicatorGap(12)
                .setIndicatorCornerRadius(3.5f)
                .setSelectAnimClass(RotateEnter.class)
                .setTransformerClass(transformerClass)
                .barPadding(0, 10, 0, 10)
                .setSource(DataProvider.geUsertGuides())
                .startScroll();

        sgb.setOnJumpClickL(new SimpleGuideBanner.OnJumpClickL() {
            @Override
            public void onJumpClick() {
                if (isFromBannerHome) {
                    finish();
                    return;
                }

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
