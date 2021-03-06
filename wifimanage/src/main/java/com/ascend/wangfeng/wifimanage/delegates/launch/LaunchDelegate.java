package com.ascend.wangfeng.wifimanage.delegates.launch;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.delegates.MainDelegate;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.Holder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2018/5/5.
 * email 1040441325@qq.com
 */

public class LaunchDelegate extends LatteDelegate {
    public static final String TAG = LaunchDelegate.class.getSimpleName();
    @BindView(R.id.banner_launch)
    ConvenientBanner<Integer> mBannerLaunch;

    @OnClick(R.id.btn_demo)
    void clickDemo() {
        MainApp.showDemo();
        startWithPop(MainDelegate.newInstance());
    }

    @OnClick(R.id.btn_start)
    void clickStart() {
        MainApp.showRelease();
        startWithPop(new LoginDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launch;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        ArrayList<Integer> mImages = new ArrayList<>();
        mImages.add(R.mipmap.lanunch_1);
        mImages.add(R.mipmap.launch_2);
        mImages.add(R.mipmap.lanunch_1);
        mBannerLaunch.setPages(() -> new LocalImageHolderView(), mImages)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .startTurning(5 * 1000);
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        mBannerLaunch.stopTurning();
        mBannerLaunch.destroyDrawingCache();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    class LocalImageHolderView implements Holder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            mImageView = new ImageView(context);
            mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return mImageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            mImageView.setImageResource(data);
        }
    }
}
