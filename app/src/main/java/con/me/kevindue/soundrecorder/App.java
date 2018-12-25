package con.me.kevindue.soundrecorder;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import java.util.Stack;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import con.me.kevindue.base.BuildConfig;

public class App extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {
    private static Stack<Activity> mActivities = new Stack<>();

    @Override
    public void onCreate() {
        super.onCreate();
//        //如果是主进程(包名和进程名称相同)才做初始化业务 ，否则return，为了只初始化一次应用配置作此判断
//        if (this.getPackageName().equals(AppUtil.getCurProcessName(this))) {
//            return;
//        }
        init(); //初始化一些应用配置
    }

    private void init() {
        //监测Activity的生命周期事件
        registerActivityLifecycleCallbacks(this);
        //Crash全局异常捕获，应用发生异常时customactivityoncrash库会打开一个Activity页面可以查看错误
        if (BuildConfig.DEBUG) {
            CustomActivityOnCrash.install(this);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (!mActivities.contains(activity)) {
            mActivities.add(activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    /**
     * 获取当前的Activity
     *
     * @return
     */
    public static Activity getCurActivity() {
        if (!mActivities.isEmpty()) {
            return mActivities.lastElement(); //返回栈顶Activity
        }
        return null;
    }

}
