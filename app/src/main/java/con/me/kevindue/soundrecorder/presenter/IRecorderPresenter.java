package con.me.kevindue.soundrecorder.presenter;

import java.util.List;

import con.me.kevindue.recorder.listener.OnPlayCompletionListener;
import con.me.kevindue.soundrecorder.entity.bean.SoundBean;

public interface IRecorderPresenter {

    /**
     * 开始录音
     */
    void  startRecord();

    /**
     * 停止录音
     */
    void  stopRecord();

    /**
     * 是否正在录音中
     */
    boolean isRecording();

    /**
     * 释放录音资源
     */
    void  releaseRecord();


    /**
     * 开始播放录音
     */
    void  playAudio(String filePath, OnPlayCompletionListener listener);

    /**
     * 暂停播放录音
     */
    void  pauseAudio();

    /**
     * 继续播放录音
     */
    void  resumeAudio();

    /**
     * 停止播放录音
     */
    void  stopAudio();

    /**
     * 是否正在播放录音中
     */
    boolean  isPalying();

    /**
     * 释放播放资源
     */
    void  releaseAudio();

    /**
     * 获取录音文件
     */
    List<SoundBean> getRecordingList();

    /**
     *  删除录音文件
     */
    void deleteRecording(String filePath);
}
