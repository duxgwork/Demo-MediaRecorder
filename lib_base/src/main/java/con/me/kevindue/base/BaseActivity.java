package con.me.kevindue.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import con.me.kevindue.autolayout.AutoLayoutActivity;

public abstract class BaseActivity extends AutoLayoutActivity {
    /**
     * Log tag
     */
    protected static String TAG_LOG = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG_LOG = this.getClass().getSimpleName();
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
//        //加入管理栈
//        AppManager.getInstance().addActivity(this);
        setContentView(getContentViewLayoutID());
        initViewsAndEvents();
        init();
    }

    protected <T extends View> T internalFindViewById(int id) {
        return ((T) this.findViewById(id));
    }

    @Override
    public void finish() {
//        AppManager.getInstance().removeActivity(this);
        super.finish();
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * get bundle data
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * 加载 layout布局
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 加载控件以及监听事件
     */
    protected abstract void initViewsAndEvents();

    /**
     * 初始化内容
     */
    protected abstract void init();

}
