package con.me.kevindue.soundrecorder.ui.activity;


import android.Manifest;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;
import con.me.kevindue.base.BaseActivity;
import con.me.kevindue.soundrecorder.R;

/**
 * app启动页
 *
 *  Created by duxianguo on 2018/12/21.
 */
public class IntroActivity extends BaseActivity {
    private TextView mTvInto;
    private CountDownTimer mCountDownTimer;


    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_intro;
    }

    @Override
    protected void initViewsAndEvents() {
        mTvInto = internalFindViewById(R.id.tv_into);
        mTvInto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }
                //跳转首页
                readyGo(MainActivity.class);
                finish();
            }
        });
    }

    @Override
    protected void init() {
        initPermission();//申请权限
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initPermission() {
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        mCountDownTimer = new CountDownTimer(4000, 1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                if (millisUntilFinished / 1000 == 1) {
                                    //跳转首页
                                    readyGo(MainActivity.class);
                                    finish();
                                } else {
                                    mTvInto.setText("点击跳过 " + millisUntilFinished / 1000);
                                }
                            }

                            @Override
                            public void onFinish() {

                            }
                        };
                        mCountDownTimer.start();
                        Toast.makeText(IntroActivity.this, "权限通过，快去录音吧！",Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        Toast.makeText(IntroActivity.this, "权限没通过，请重启应用申请相关权限！",Toast.LENGTH_SHORT);
                        finish();
                    }
                }).start();
    }

    @Override
    protected void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        super.onDestroy();
    }
}
