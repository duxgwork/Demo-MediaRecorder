package con.me.kevindue.soundrecorder.ui.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import con.me.kevindue.base.AppManager;
import con.me.kevindue.base.BaseActivity;
import con.me.kevindue.common.ToastUtil;
import con.me.kevindue.soundrecorder.R;
import con.me.kevindue.soundrecorder.entity.bean.SoundBean;
import con.me.kevindue.soundrecorder.presenter.impl.RecorderPresenter;
import con.me.kevindue.soundrecorder.ui.adapter.SoundAdapter;
import con.me.kevindue.soundrecorder.ui.dialog.TwoBtnDialogFragment;
import con.me.kevindue.soundrecorder.ui.viewinterface.IRecordingView;

/**
 * 首页面
 *
 * Created by duxianguo on 2018/12/21.
 */
public class MainActivity extends BaseActivity implements IRecordingView,
        View.OnClickListener, SoundAdapter.OnItemClickLister, SoundAdapter.OnLongClickLister {
    private long EXIT_START_TIME = -1;
    private Chronometer recordingTime;
    private RecyclerView mRvSoundList;
    private TextView mTvWarn, mTvRecord, mTvFinish;
    private SoundAdapter mSoundAdapter;
    private RecorderPresenter mRecorderControl;
    private TwoBtnDialogFragment mDialogFragment;
    private int mCurrentPosition;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        recordingTime = internalFindViewById(R.id.chronometer);
        mTvWarn = internalFindViewById(R.id.tv_warn);
        mTvRecord = internalFindViewById(R.id.tv_record);
        mTvFinish = internalFindViewById(R.id.tv_finish);
        mRvSoundList = internalFindViewById(R.id.rv_sound_list);
        mTvRecord.setOnClickListener(this);
        mTvFinish.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mRvSoundList.setLayoutManager(new LinearLayoutManager(this));
        mRvSoundList.addItemDecoration(new SpaceItemDecoration(30));//设置item间距，30dp
        mSoundAdapter = new SoundAdapter(this);
        mSoundAdapter.setOnItemClickLister(this);
        mSoundAdapter.setOnLongClickLister(this);
        mRvSoundList.setAdapter(mSoundAdapter);
    }

    @Override
    protected void init() {
        initRecyclerView();
        if (mRecorderControl == null){
            mRecorderControl = new RecorderPresenter(this);
        }
        //提前加载录音文件目录下的录音文件
        loadRecordingList(mRecorderControl.getRecordingList());
    }

    @Override
    public void onItemClick(SoundBean sondBean, int position) {
        mSoundAdapter.updateItemsIcon(mRecorderControl, mCurrentPosition, position, sondBean);
        mCurrentPosition = position;
    }

    @Override
    public void onLongClick(final String filePath) {
        if (mDialogFragment == null){
            mDialogFragment = TwoBtnDialogFragment.newInstance("确定删除该录音文件？", "确定", "取消", false);
            mDialogFragment.setDialogOnclickListener(new TwoBtnDialogFragment.TwoButtonDialogOnclickListener() {
                @Override
                public void positiveClick(MaterialDialog dialog) {
                    mRecorderControl.deleteRecording(filePath);
                    dialog.dismiss();
                }

                @Override
                public void negativeClick(MaterialDialog dialog) {
                    dialog.dismiss();
                }
            });
        }
        mDialogFragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_record:
                if (mRecorderControl.isRecording()) {
                    mRecorderControl.stopRecord();
                }
                //开始录制
                mRecorderControl.startRecord();
                //重置控件状态
                resetViewsSituation(true);
                break;
            case R.id.tv_finish:
                //停止录制
                mRecorderControl.stopRecord();
                //重置控件状态
                resetViewsSituation(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void loadRecordingList(List<SoundBean> recordingList) {
        //更新录音文件列表
        mSoundAdapter.fillData(recordingList);
    }

    @Override
    protected void onDestroy() {
        if (mRecorderControl != null){
            mRecorderControl.releaseRecord();
            mRecorderControl.releaseAudio();
            mRecorderControl = null;
        }
        mDialogFragment.dismiss();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - EXIT_START_TIME > 2000) {
                EXIT_START_TIME = System.currentTimeMillis();
                ToastUtil.showToast(this, "再按一次离开该应用");
            } else {
                AppManager.getInstance().clear();
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 重置控件状态
     * @param isStart 是否点击了"开始录制"
     */
    private void resetViewsSituation(boolean isStart){
        if (isStart){
            mTvRecord.setEnabled(false);
            mTvFinish.setEnabled(true);
            mTvWarn.setVisibility(View.VISIBLE);
            recordingTime.setVisibility(View.VISIBLE);
            //设置开始计时时间
            recordingTime.setBase(SystemClock.elapsedRealtime());
            //启动计时器
            recordingTime.start();
        } else {
            mTvRecord.setEnabled(true);
            mTvFinish.setEnabled(false);
            mTvWarn.setVisibility(View.GONE);
            recordingTime.setVisibility(View.GONE);
            recordingTime.stop();
        }

    }

    /**
     * 自定义RecyclerView的Item之间的分割线
     */
    class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        int mSpace;

        public SpaceItemDecoration(int space) {
            this.mSpace = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = mSpace;
            }
        }
    }
}
