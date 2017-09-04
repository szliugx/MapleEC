package com.ascend.wangfeng.mapleec;

import android.app.Application;

import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.delegates.web.event.TestEvent;
import com.ascend.wangfeng.latte.ec.database.DatabaseManager;
import com.ascend.wangfeng.latte.ec.icon.FontEcModule;
import com.ascend.wangfeng.latte.net.interceptors.AddCookieInterceptor;
import com.ascend.wangfeng.latte.net.interceptors.DebugInterceptor;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import me.yokeyword.fragmentation.Fragmentation;

/**
 * Created by fengye on 2017/8/15.
 * email 1040441325@qq.com
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("http://gank.io/")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withInterceptor(new DebugInterceptor("user", R.raw.test))
                .withInterceptor(new DebugInterceptor("index",R.raw.index))
                .withInterceptor(new DebugInterceptor("sortlist",R.raw.sortlist))
                .withInterceptor(new DebugInterceptor("sortcontent",R.raw.sortcontent))
                .withInterceptor(new DebugInterceptor("shopcart",R.raw.shopcart))
                .withInterceptor(new DebugInterceptor("orderlist",R.raw.orderlist))
                .withInterceptor(new AddCookieInterceptor())
                .withWeChatAppId("1")
                .withWeChatAppSecret("2")
                .withJavascriptInterface("latte")
                .withWebEvent("test", new TestEvent())
                .configure();
        DatabaseManager.getInstance().init(this);
        initStetho();
        //初始化日志
        Logger.addLogAdapter(new AndroidLogAdapter());

    }

    private void initFragmentation() {
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .install();
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );
    }
}
