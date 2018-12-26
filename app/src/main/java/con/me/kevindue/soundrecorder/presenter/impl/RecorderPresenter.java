package con.me.kevindue.soundrecorder.presenter.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import con.me.kevindue.recorder.RecordFileUtil;
import con.me.kevindue.recorder.Recorder;
import con.me.kevindue.recorder.RecorderPlayer;
import con.me.kevindue.recorder.listener.OnPlayCompletionListener;
import con.me.kevindue.soundrecorder.entity.bean.SoundBean;
import con.me.kevindue.soundrecorder.presenter.IRecorderPresenter;
import con.me.kevindue.soundrecorder.ui.viewinterface.IRecordingView;

public class RecorderPresenter implements IRecorderPresenter {
    private IRecordingView recordingView;

    public RecorderPresenter(IRecordingView recordingView) {
        this.recordingView = recordingView;
    }

    /**
     * 开始录音
     */
    @Override
    public void startRecord() {
        Recorder.getInstance().startRecording(RecordFileUtil.getCurrentRecordingPath());
    }

    /**
     * 停止录音
     */
    @Override
    public void stopRecord() {
        Recorder.getInstance().stopRecording();
        if (recordingView != null) {
            recordingView.loadRecordingList(getRecordingList());
        }
    }

    /**
     * 是否正在录音中
     */
    @Override
    public boolean isRecording() {
        return Recorder.getInstance().isRecording();
    }

    /**
     * 释放录音资源
     */
    @Override
    public void releaseRecord() {
        Recorder.getInstance().releaseRecoder();
    }


    /**
     * 开始播放录音
     */
    @Override
    public void playAudio(String filePath, OnPlayCompletionListener listener) {
        RecorderPlayer.getInstance().startPlay(filePath, listener);
    }

    /**
     * 暂停播放录音
     */
    @Override
    public void pauseAudio() {
        RecorderPlayer.getInstance().pausePlay();
    }

    /**
     * 继续播放录音
     */
    @Override
    public void resumeAudio() {
        RecorderPlayer.getInstance().resumePlay();
    }

    /**
     * 停止播放录音
     */
    @Override
    public void stopAudio() {
        RecorderPlayer.getInstance().stopPlay();
    }

    /**
     * 是否正在播放录音中
     */
    @Override
    public boolean isPalying() {
        return RecorderPlayer.getInstance().isPalying();
    }

    /**
     * 释放播放资源
     */
    @Override
    public void releaseAudio() {
        RecorderPlayer.getInstance().releasePlay();
    }

    /**
     * 获取录音文件
     */
    @Override
    public List<SoundBean> getRecordingList() {
        List<SoundBean> recordingList = new ArrayList<>();
        File[] files = RecordFileUtil.getAllRecordingFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (!file.isDirectory()) {
                    recordingList.add(new SoundBean(file.getAbsolutePath(), file.getName()));
                }
            }
        }
        return recordingList;
    }

    /**
     * 删除录音文件
     */
    @Override
    public void deleteRecording(String filePath) {
        if (RecordFileUtil.deleteRecordingFile(filePath) && recordingView != null) {
            recordingView.loadRecordingList(getRecordingList());
        }
    }

}
