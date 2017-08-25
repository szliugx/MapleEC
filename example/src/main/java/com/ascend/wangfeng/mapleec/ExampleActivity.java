package com.ascend.wangfeng.mapleec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.ascend.wangfeng.latte.activities.ProxyActivity;
import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.launcher.LauncherDelegate;
import com.ascend.wangfeng.latte.ec.main.EcBottomDelegate;
import com.ascend.wangfeng.latte.ec.sign.ISignListener;
import com.ascend.wangfeng.latte.ec.sign.SignInDelegate;
import com.ascend.wangfeng.latte.ui.launcher.ILauncherListener;
import com.ascend.wangfeng.latte.ui.launcher.OnLauncherFinishTag;

public class ExampleActivity extends ProxyActivity implements ISignListener,ILauncherListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar =getSupportActionBar();
        if (actionBar!=null)actionBar.hide();
    }

    @Override
    public LatteDelegate setRootDelegate() {
        //return new ExampleDelegate();
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        startWithPop(new EcBottomDelegate());
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag){
            case SIGNED:
                startWithPop(new EcBottomDelegate());
                break;
            case NOT_SIGNED:
                startWithPop(new SignInDelegate());
                break;

        }
    }
}
