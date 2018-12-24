package con.me.kevindue.soundrecorder.ui.viewinterface;

import java.util.List;

import con.me.kevindue.soundrecorder.entity.bean.SoundBean;

public interface IRecordingView extends IBaseView{

    /**
     * 加载录音文件列表
     * @param recordingList
     */
    void loadRecordingList(List<SoundBean> recordingList);

}
